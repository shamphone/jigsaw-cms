package com.fulong.taglib.site;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.TagUtils;

import com.fulong.longcon.counter.AccessCounterRepository;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.site.Channel;
import com.fulong.longcon.site.Site;
import com.fulong.longcon.workflow.Activity;
import com.fulong.longcon.workflow.ProcessDefinition;
import com.fulong.portlet.ServletNodeWorkItem;
import com.fulong.taglib.SpringTagSupport;


/**
 * <p>Title: Coolink平台协同管理系统</p>
 *
 * <p>Description: Coolink平台协同管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2010</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author luobin
 * @date 2010-6-4	
 * @version 1.0.1
 */
public class HtmlTag extends SpringTagSupport {
	private static final long serialVersionUID = -1041118265209850557L;
	private String definition;
	private String allow;
	private String checkLeaser;
	private String ontagstart;
	private String dir;
	private String lang;

	public void setOntagstart(String ontagstart) {
		this.ontagstart = ontagstart;
	}

	public void setOntagend(String ontagend) {
	}
	
	public void setDefinition(String definition) {
		this.definition = definition;
	}

	public void setAllow(String allow) {
		this.allow = allow;
	}

	public void setCheckLeaser(String checkLeaser) {
		this.checkLeaser = checkLeaser;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public int doStartTag() throws JspException {
		HttpServletRequest  request = (HttpServletRequest)pageContext.getRequest();
		//根据大纲的id获取大纲
		NodeDefinition nodeDefinition = null;
		if(definition!=null){
			nodeDefinition = this.getRepository().getDefinitionManager().getDefinition(definition);
			if(nodeDefinition==null){
				String msg = "Unable to find definition for ID :" + definition +".";
				if(log.isErrorEnabled()){
					log.error(msg);
				}
				throw new JspException(msg);
			}
		}
		//获取节点
		Node node = this.lookupNode(pageContext.getRequest());
		//如果node和nodeDefinition都不为null,检查节点是否是nodeDefinition下的节点
		if(node != null && nodeDefinition != null){
			if(!node.isNodeType(definition)){
				String msg = "The current node:" + node.getID()+" is not in the definition:"+ definition +".";
				if(log.isErrorEnabled()){
					log.error(msg);
				}
				throw new JspException(msg);
			}
		}
		//当前登录用户是否属于可以访问这个栏目的用户类别
		boolean hasPermission = checkPermission(allow, request);
		if(!hasPermission){
			if(log.isErrorEnabled()){
				log.error("forbidden:no permission to access.");
			}
			
			try {
				String fromUrl = TagUtils.getInstance().computeURL(pageContext, null, request.getServletPath(), null, null, null, request.getParameterMap(), null, false);
				((HttpServletResponse)pageContext.getResponse()).sendRedirect(request.getContextPath()+"/login.jsp?fromURL="+fromUrl);
			} catch (IOException e) {
				throw new JspException(e);
			}
			return SKIP_PAGE;
		}
		
		//检查租户（checkLeaser) 
    	if(node!=null&&("true".equals(checkLeaser)||checkLeaser==null)){
    		boolean legalLeaser = checkLeaser(node, request);
    		if(!legalLeaser){
    			String msg = "no such node:"+node.getID()+" in the current site:"+getCurrentSite(request).getDomain()+".";
    			if(log.isErrorEnabled()){
    				log.error(msg);
    			}
    			throw new JspException(msg);
    		}
		}
    	//访问计数
    	accessCounter(node, request);
    	
    	//执行流程
    	if(ontagstart!=null&&ontagstart.trim().length()!=0&&node!=null){
    		String[] s = ontagstart.split("\\.");
    		if(s.length==2){
    			String processId = s[0];
        		String activityId = s[1];
        		try {
					doProcess(processId, activityId, node);
				} catch (Exception e) {
					throw new JspException(e);
				}
    		}
    	}
    	
    	String text = renderTagText();
		TagUtils.getInstance().write(pageContext, text);
		return EVAL_BODY_INCLUDE;
	}
	public int doEndTag() throws JspException {
		TagUtils.getInstance().write(pageContext, "</html>");
		return EVAL_PAGE;
	}
    /**
     * 检查当前登录用户是否有权限
     * @param allows 允许访问该栏目的用户类型（大纲）ID，以逗号隔开
     * @param request
     * @return
     * @throws JspException
     */
    private boolean checkPermission(String allows,HttpServletRequest request) throws JspException{
    	boolean flag = false;
    	if(allows != null){
			String[] groupIds = allows.split(",");
			Node user = this.getCurrentUser(request);
			if(user!=null){
				for(int i=0;i<groupIds.length;i++){
					if(user.isNodeType(groupIds[i])){
						flag = true;
						break;
					}
				}
			}
		}else{
			flag = true;
		}
    	return flag;
    }
    /**
     * 当前节点必须是网站租户的子节点
     * @param node
     * @param request
     * @return
     * @throws JspException
     */
    private boolean checkLeaser(Node node,ServletRequest  request) throws JspException{
    	boolean flag = true;
    	Site site = this.getCurrentSite(request);
    	if(site==null){
			String msg = "unable to find site for domain : '"+request.getServerName();
			if(request.getServerPort()!=80){
				msg += (":"+request.getServerPort());
			}
			msg += "'.";
			if(log.isErrorEnabled()){
				log.error(msg);
			}
			throw new JspException(msg);
		}
		Node owner = site.getOwner();
		if(!node.isChild(owner)){
			flag = false;
		}
		return flag;
    }
    /**
     * 访问计数
     * @param node
     * @param request
     * @return
     * @throws JspException
     */
    private void accessCounter(Node node,HttpServletRequest request) throws JspException{
    	AccessCounterRepository counterRepository = this.getAccessCounterRepository();
    	//系统计数
		counterRepository.increase("system");
		
		//网站计数
		Site site = this.getCurrentSite(request);
		if(site!=null){
			counterRepository.increase(site.getDomain());
		}
		
		//栏目计数
		Channel channel = this.getCurrentChannel(request);
		if(channel!=null){
			counterRepository.increase(request.getRequestURL().toString());
		}
		
		//内容计数
		if(node!=null){
			counterRepository.increase(node.getID());
		}
    }
    /**
     * 执行流程
     * @param processId
     * @param activityId
     * @param node
     * @throws Exception
     */
    private void doProcess(String processId,String activityId,Node node) throws Exception{
    	ProcessDefinition definition = this.getWorkflowService().getDefinition(processId);
		if(definition!=null){
			Activity activity = definition.getActivity(activityId);
			if(activity!=null){
				activity.execute(new ServletNodeWorkItem(node, (HttpServletRequest)pageContext.getRequest(),(HttpServletResponse)pageContext.getResponse()));
			}
		}
    }
    
    /**
     * 渲染标记输出文本
     * @return
     */
    private String renderTagText(){
    	StringBuffer buffer = new StringBuffer("<html");
    	if(dir!=null){
    		buffer.append(" dir=\""+dir+"\"");
    	}
    	if(lang!=null){
    		buffer.append(" lang=\""+lang+"\"");
    	}
    	buffer.append(">");
    	return buffer.toString();
    }
}
