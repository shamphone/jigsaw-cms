package com.fulong.taglib.site;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;

import org.apache.struts.taglib.TagUtils;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.Property;
import com.fulong.longcon.site.Site;
import com.fulong.longcon.site.SiteTemplate;
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
 * @date 2010-6-7
 * @version 1.0.1
 */
public class Title2Tag extends SpringTagSupport {
	private static final long serialVersionUID = 2496777111686933611L;
	private static final String SITE_DISPLAYNAME_VARIABLE = "$S";				//网站显示名占位符
	private static final String TEMPLATE_DISPLAYNAME_VARIABLE = "$T";			//模板显示名占位符
	private static final String CHANNEL_DISPLAYNAME_VARIABLE = "$C";			//栏目显示名占位符
	private static final String CONTENT_VARIABLE = "\\{[\\w\\.\\d]+\\}";		//节点属性占位符
	private static final String TAG_BODY_VARIABLE = "$B";						//标签体占位符
	private String format;
	private String dir;
	private String lang;
	public void setFormat(String format) {
		this.format = format;
	}
	public void setDir(String dir) {
		this.dir = dir;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public Title2Tag() {
    }
	public int doStartTag() throws JspException {
		TagUtils.getInstance().write(pageContext, renderTagText());
		return EVAL_BODY_BUFFERED;
	}
	public int doEndTag() throws JspException {
		//如果不指定标题格式，默认为标记体
		if(format==null){
			format = TAG_BODY_VARIABLE;
		}
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		String text = format;
		//替换节点属性占位符
		Node node = lookupNode(request);
		if(node!=null){
			text = replaceContent(text, node);
		}
		//替换标记体占位符
		String content = "";
		BodyContent bodyContent = this.getBodyContent();
		if(bodyContent!=null){
			content = this.getBodyContent().getString();
		}
		text = text.replace(TAG_BODY_VARIABLE, content);
		//替换网站显示名占位符
		Site site = this.getCurrentSite(pageContext.getRequest());
		String siteDisplayName = "";
		if(site!=null){
			siteDisplayName = site.getDisplayName();
		}
		text = text.replace(SITE_DISPLAYNAME_VARIABLE, siteDisplayName);
		//替换模板显示名占位符
		SiteTemplate template = getCurrentTemplate(request);
		String templateDisplayName = "";
		if(template!=null){
			templateDisplayName = template.getDisplayName();
		}
		text = text.replace(TEMPLATE_DISPLAYNAME_VARIABLE, templateDisplayName);
		//替换栏目显示名占位符
		String channelDisplayName = ((Servlet)pageContext.getPage()).getServletInfo();
		text = text.replace(CHANNEL_DISPLAYNAME_VARIABLE, channelDisplayName);
		
		TagUtils.getInstance().write(pageContext, text+"</title>");
		return EVAL_PAGE;
	}
    /**
     * 渲染标记输出文本
     * @return
     */
    private String renderTagText(){
    	StringBuffer buffer = new StringBuffer("<title");
    	if(dir!=null){
    		buffer.append(" dir=\""+dir+"\"");
    	}
    	if(lang!=null){
    		buffer.append(" lang=\""+lang+"\"");
    	}
    	buffer.append(">");
    	return buffer.toString();
    }
    
    /**
     * 获取当前模板
     * @param request
     * @return
     * @throws JspException 
     */
    private SiteTemplate getCurrentTemplate(HttpServletRequest  request) throws JspException{
    	String templateName = request.getContextPath();
    	templateName = templateName.substring(1);
    	SiteTemplate template = this.getSiteFactory().getTemplate(templateName);
    	return template;
    }
    
    /**
     * 替换text中的属性占位符，从当前的node中获取对应的属性值
     * @param text
     * @param node
     * @return
     */
    public String replaceContent(String text,Node node){
    	Pattern pattern = Pattern.compile(CONTENT_VARIABLE);
    	Matcher mather  = pattern.matcher(text);
    	StringBuffer buffer = new StringBuffer();
    	while(mather.find()){
    		String s = mather.group();
    		if(s.length()>2){
    			s = s.substring(1, s.length()-1);
    			Property value = node.getProperty(s);
    			if(value!=null){
    				String prop = value.getString();
    				mather.appendReplacement(buffer, prop);
    			}
    		}
    	}
    	mather.appendTail(buffer);
    	return buffer.toString();
    }
}
