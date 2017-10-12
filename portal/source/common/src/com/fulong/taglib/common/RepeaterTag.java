package com.fulong.taglib.common;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.struts.taglib.TagUtils;

/**
 * 生成重复的内容，如空格
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
 * @author <a href='mailto:lixf@fulong.com.cn'>lixf</a>
 * @version 2.0
 */
public class RepeaterTag extends BodyTagSupport {
	
	private static final long serialVersionUID = -5199410050008374993L;
	
	private String name;
	private String property;
	private String scope;

	public RepeaterTag() {
	}

	public int doEndTag() throws JspException {
		String numString = "" + TagUtils.getInstance().lookup(this.pageContext, name, property, scope);
		int number = Integer.parseInt(numString);
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < number; i++) {
			buffer.append(this.getBodyContent().getString());
		}
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
