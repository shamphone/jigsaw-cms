package com.fulong.taglib.site;

import org.apache.struts.taglib.TagUtils;
import javax.servlet.jsp.JspException;
import com.fulong.longcon.site.Channel;
import com.fulong.taglib.SpringTagSupport;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * Title: 龙驭网站管理系统
 * </p>
 * 
 * <p>
 * Description: 龙驭网站管理系统
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * @author lixf
 * @version 1.0
 */
public class ChannelURLTag extends SpringTagSupport {

	private static final long serialVersionUID = 1373803880970786390L;

	private String name;
	private String property;
	private String scope;

	public ChannelURLTag() {
	}

	public int doEndTag() throws JspException {
		Channel channel = (Channel) TagUtils.getInstance().lookup(
				this.pageContext, name, property, scope);
		HttpServletRequest request = (HttpServletRequest) this.pageContext
				.getRequest();
		StringBuffer buffer = new StringBuffer(request.getContextPath());
		buffer.append("/sites/").append(channel.getSiteTemplate().getName())
				.append("/").append(channel.getName()).append(".jsp");
		TagUtils.getInstance().write(pageContext, buffer.toString());
		return EVAL_PAGE;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getName() {
		return name;
	}

	public String getProperty() {
		return property;
	}

	public String getScope() {
		return scope;
	}

}
