package com.fulong.portlet.button.save.single;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.expression.FilterParser;
import com.fulong.longcon.repository.ChildNodeDefinition;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.longcon.repository.Property;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.longcon.repository.PropertyType;
import com.fulong.longcon.repository.Value;
import com.fulong.longcon.workflow.Activity;
import com.fulong.longcon.workflow.ProcessDefinition;
import com.fulong.portlet.PortletNodeWorkItem;
import com.fulong.portlet.button.ButtionPortletAction;
import com.fulong.portlet.button.submit.PortletConfig;

/**
 * <p>
 * Title: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Description: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术有限公司 2006
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术有限公司
 * </p>
 * 
 * @author lixf,liuzijun
 * @version 2.0
 */
public class SaveNodeAction extends ButtionPortletAction {
	protected final Log log = LogFactory.getLog(this.getClass());

	/**
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            ActionRequest
	 * @param response
	 *            ActionResponse
	 * @return ActionForward
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward action(ActionMapping mapping, ActionForm form, ActionRequest request, ActionResponse response)
			throws Exception {
		//boolean isfileUpload = org.apache.commons.fileupload.portlet.PortletFileUpload.isMultipartContent(request);
		PortletConfig config = new PortletConfig(request.getPreferences());
		String definitionId = request.getParameter("definition");
		String hasIDParameter = request.getPreferences().getValue("hasIDParameter", "false");
		NodeDefinition nodeDefinition = this.getRepository().getDefinitionManager().getDefinition(definitionId);
		Iterator<PropertyDefinition> propertyDefinitions = nodeDefinition.propertyDefinitions();
		String ownerType = request.getPreferences().getValue("owner", "site");
		Node owner = null;
		//会员节点父节点都为root by mali 2010-9-8
		if(nodeDefinition.isNodeType("principal-scheme")){
			owner = this.getRepository().getRootNode();
		}else if (ownerType.equalsIgnoreCase("site")) {
			if(this.getCurrentSite(request, response)!=null){
				owner = this.getCurrentSite(request, response).getOwner();
			}			
		} else if(ownerType.equalsIgnoreCase("member")) {
			owner = this.getCurrentOrg(request, response);
		}else if(ownerType.equalsIgnoreCase("currentContent")){
			owner = this.getRepository().getNode(request.getParameter("contentId"));
		}
		if (owner == null) {
			owner = this.getPassportProvider().getDefaultOrganization();
		}

		Node node = null;
		if (config.isCreate()) {
			if(nodeDefinition.isNodeType("principal-scheme")){
				node = owner.addNode(nodeDefinition, "contents");
			}else{
				String parentCategoryID = request.getPreferences().getValue("parentCategory", "");
				String parentFixFieldID = request.getPreferences().getValue("parentField", "");
				NodeDefinition parentCategory = this.getRepository().getDefinitionManager().getDefinition(parentCategoryID);
				if(parentCategory!=null){
					PropertyDefinition fixField = parentCategory.getPropertyDefinition(parentFixFieldID);
					if(fixField!=null&&fixField.getType()==PropertyType.FIX){
						node = owner.addNode(nodeDefinition, fixField.getID());
					}
				}
			}
			//兼容旧版本代码
			if(node==null){
				node = owner.addNode(nodeDefinition, "contents");
			}
			//
		} else {
			node = this.lookupNode(request, response);
		}
		if (node == null) {
			return mapping.findForward("noNode");
		}
		/**
		 * 处理默认值
		 */
		String dValues[] = config.getDefaultValues();
		for (int i = 0; i < dValues.length; i++) {
			FilterParser parser = this.getFilterParser(request, response);
			parser.parser(dValues[i]);			
			node.setProperty(parser.getPropertyDefinition(), parser.getValue());

		}

		/**
		 * 填充表单值
		 */
		Map parameters = request.getParameterMap();
		while (propertyDefinitions.hasNext()) {
			PropertyDefinition propertyDefinition = (PropertyDefinition) propertyDefinitions.next();
			int propType = propertyDefinition.getType();
			if(propType==0){  //如果是复合属性，则创建复合属性节点，并且对复合属性节点的属性赋值
				//String requestID = "";
				List<String> list = new ArrayList<String>();
				Iterator iter = parameters.entrySet().iterator(); 				
				while (iter.hasNext()) {  //遍历request里的所有值，取出复合属性大纲下的属性，存放到一个list
				    Map.Entry entry = (Map.Entry) iter.next(); 
				    Object key = entry.getKey(); 
				    String requestID = String.valueOf(key);
				    String[] temp = requestID.split("\\.");
				    if(temp.length>1&&temp[0].equals(propertyDefinition.getID())){
				    	list.add(requestID);
				    }
				} 
				if(list.size()>1){ //查看属于复合属性的表单提交过来值的数目（通过多值提交），来判断创建子节点的个数
					int nodeNum = request.getParameterValues(list.get(0)).length;
					
					NodeDefinition childDefinition = ((ChildNodeDefinition)propertyDefinition).getNodeDefinition();
					NodeIterator oldNodes = node.getNodes(propertyDefinition.getID());
					while(oldNodes.hasNext()){   //在创建子节点之前先清空这个复合属性下的所有子节点，这是为了兼容修改节点的情况
						this.getRepository().delete(oldNodes.nextNode());
					}
					for(int u = 0; u<nodeNum; u++){
						boolean tag = false;
						for(int z=0; z<list.size(); z++){  //查看是否所有的复合属性的属性都填有值，若没有则不创建复合属性节点
							tag = false;
							if (!request.getParameterValues(list.get(z))[u].trim().equals("")) {
								tag = true;
								break;
							}
						}
						if (!tag)
							continue;
						Node childNode = node.addNode(childDefinition, propertyDefinition.getID());
						for(int z=0; z<list.size(); z++){
							String[] temp = list.get(z).split("\\.");
							childNode.setProperty(temp[1], request.getParameterValues(list.get(z))[u].trim());
						}
					}
				}
			}else{  //如果不是符合属性，则按普通属性规则处理
				String propName = propertyDefinition.getID();
				Object value = parameters.get(propName);
				if (value == null) {
					continue;
				} else if (value instanceof FileItem) {
					FileItem values = (FileItem) value;
					String path = null;
					try {
						if(nodeDefinition.isNodeType("principal-scheme"))
							path = this.upload(values, node, (HttpServletRequest) request);
						else{
							//资源节点统一放在当前网站的所有者即租户下
							if(this.getCurrentSite(request, response)!=null){
								path = this.upload(values, this.getCurrentSite(request, response).getOwner(), (HttpServletRequest) request);
							}else{
								path = this.upload(values, this.getPassportProvider().getDefaultOrganization(), (HttpServletRequest) request);
							}
						}
					} catch (Exception ex) {
						log.error("Upload " + values.getName() + " failed.", ex);
					}
					if (path != null)
						node.setProperty(propName, path);
				} else if (value instanceof FileItem[]) {
					FileItem[] values = (FileItem[]) value;
					List<String> list = new ArrayList<String>(values.length);
					for (int i = 0; i < values.length; i++) {
						String path = null;
						try {
							if(nodeDefinition.isNodeType("principal-scheme"))
								path = this.upload(values[i], node, (HttpServletRequest) request);
							else{
								//资源节点统一放在当前网站的所有者即租户下
								if(this.getCurrentSite(request, response)!=null){
									path = this.upload(values[i], this.getCurrentSite(request, response).getOwner(), (HttpServletRequest) request);
								}else{
									path = this.upload(values[i], this.getPassportProvider().getDefaultOrganization(), (HttpServletRequest) request);
								}
							}
								
						} catch (Exception ex) {
							log.error("Upload " + values[i].getName() + " failed.", ex);
						}
						list.add(path);
					}
					if (list.size() > 0) {
						String[] news = list.toArray(new String[list.size()]);
						Property prop = node.getProperty(propName);
						if (prop != null) {
							Value[] vs = prop.getValues();
							for (int i = 0; i < vs.length && i < news.length; i++) {
								if (vs[i].getString() != null && news[i] == null)
									news[i] = vs[i].getString();
							}
						}
						node.setProperty(propName, news);
					}
				} else {
					String[] values = request.getParameterValues(propName);
					//兼容异步上传占位符置空处理    by mali 2010-9-8
					if(values.length == 1 && values[0].equals("clear") && (propType == PropertyType.PATH|propType == PropertyType.REFERENCE))
						values[0] = null;
					node.setProperty(propName, values);
				}
			}			
		}		
		String processId=request.getPreferences().getValue("process", "blank");
		String activityId = request.getPreferences().getValue("activity", "begin");
		ProcessDefinition definition = this.getWorkflowService().getDefinition(processId);
		if(definition!=null){
			Activity activity = definition.getActivity(activityId);
			if(activity!=null){
				activity.execute(new PortletNodeWorkItem(node, request, response));
			}
		}
		
		if(hasIDParameter.equals("false")){
			String forwardSelf = request.getPreferences().getValue("forwardSelf", "false");
			String forwardHistory = request.getPreferences().getValue("forwardFirstHistory", "false");
			if(forwardSelf.equals("false")&&forwardHistory.equals("false")){
				String path = request.getPreferences().getValue("channel", "");
				if(path!=null&&!path.equals("")){
					response.sendRedirect(path);
				}else{
					this.redirect(request, response, "index.jsp");
				}
			}else{
				response.sendRedirect(request.getParameter("selfURL"));
			}
		}else{
			String forwardSelf = request.getPreferences().getValue("forwardSelf", "false");
			String forwardHistory = request.getPreferences().getValue("forwardFirstHistory", "false");
			if(forwardSelf.equals("false")&&forwardHistory.equals("false")){
				String path = request.getPreferences().getValue("channel", "");
				if(path!=null&&!path.equals("")){
					if(node.getID()!=null){
						response.sendRedirect(path+"?contentId=" + node.getID());
					}else{
						response.sendRedirect(path);
					}
				}else{
					this.redirect(request, response, "index.jsp",node.getID());
				}
			}else{
				response.sendRedirect(request.getParameter("selfURL"));
			}
		}
		return null;
	}
}
