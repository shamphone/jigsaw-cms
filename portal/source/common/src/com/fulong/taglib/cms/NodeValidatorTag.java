package com.fulong.taglib.cms;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.jsp.JspException;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.ValidatorParser;
import com.fulong.taglib.SpringTagSupport;

import org.apache.commons.collections.EnumerationUtils;
import org.apache.commons.collections.IteratorUtils;
import org.apache.struts.taglib.TagUtils;

/**
 * 用于在页面上产生针对节点定义的脚本，为了实现实时定义，使用这个代码时，必须像如下方式:
 * 
 * <fulong:nodeValidator name="definition"> <script type="text/Javascript"
 * language="Javascript"> //在应用时，必须包含这两个脚本文件，用来处理对属性property验证成功或者失败时的回调
 * function validateSuccess(property){
 * //当对属性property校验成功时，系统将调用这个方法。可以在这里实现如修改提示文字颜色之类的功能。
 * 
 * } //在应用时，必须包含这两个脚本文件，用来处理对属性property验证成功或者失败时的回调 function
 * validateFailed(property){
 * //当对属性property校验失败时，系统将调用这个方法。可以在这里实现如修改提示文字颜色之类的功能。 } </script>
 * </fulong:nodeValidator>
 * 
 * <p>
 * Title: 龙驭网站内容管理系统
 * </p>
 * 
 * <p>
 * Description: 龙驭网站内容管理系统
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术股份有限公司 2006
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * @author lixf
 * @version 2.0
 */
public class NodeValidatorTag extends SpringTagSupport {

	private static final long serialVersionUID = 5708304233666422540L;

	/**
	 * 由name+property或者definition确定一个NodeDefinition对象
	 */
	private String name;
	private String property;
	private String scope;
	private String formName;
	private String propertyDefinitions;
	private String categoryName;
	private String categoryProperty;
	private String contentName;
	private String contentProperty;

	public String getName() {
		return name;
	}

	public String getProperty() {
		return property;
	}

	public String getScope() {
		return scope;
	}

	public String getFormName() {
		return formName;
	}

	public String getPropertyDefinitions() {
		return propertyDefinitions;
	}

	public String getCategoryName() {

		return categoryName;
	}

	public String getCategoryProperty() {

		return categoryProperty;
	}

	public String getContentName() {
		return contentName;
	}

	public String getContentProperty() {
		return contentProperty;
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

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public void setPropertyDefinitions(String propertyDefinitions) {
		this.propertyDefinitions = propertyDefinitions;
	}

	public void setCategoryName(String categoryName) {

		this.categoryName = categoryName;
	}

	public void setCategoryProperty(String categoryProperty) {

		this.categoryProperty = categoryProperty;
	}

	public void setContentName(String contentName) {
		this.contentName = contentName;
	}

	public void setContentProperty(String contentProperty) {
		this.contentProperty = contentProperty;
	}

	public void release() {
		super.release();
		this.name = null;
		this.property = null;
		this.scope = null;
		this.formName = null;
		this.propertyDefinitions = null;

	}

	public int doEndTag() throws JspException {
		String id = this.id;
		if (id == null) {
			id = this.formName;
		}
		if (id == null) {
			id = "";
		}
		NodeDefinition definition = null;
		try {
			definition = (NodeDefinition) TagUtils.getInstance().lookup(
					pageContext, name, property, scope);
			this.pageContext.getRequest().setAttribute("definitionID",
					definition.getID());
		} catch (Exception ex1) {
		}

		if (definition == null) {
			this.pageContext.getRequest().setAttribute(
					"properties",
					TagUtils.getInstance().lookup(pageContext,
							propertyDefinitions, scope));
		} else {
			this.pageContext.getRequest()
					.setAttribute(
							"properties",
							IteratorUtils.toList(definition
									.propertyDefinitions(false)));
		}
		this.pageContext.getRequest().setAttribute(
				"validators",
				EnumerationUtils.toList(ValidatorParser.getParser()
						.validatorInfos()));
		try {
			TagUtils.getInstance().write(pageContext,
					this.getBodyContent().getString());
			this.pageContext.getRequest().setAttribute("com.fulong.form.name",
					id);
			try {
				if (categoryName != null) {
					NodeDefinition node = (NodeDefinition) TagUtils
							.getInstance().lookup(pageContext, categoryName,
									categoryProperty, scope);
					this.pageContext.getRequest().setAttribute("categoryID",
							node.getID());
				} else {
					this.pageContext.getRequest()
							.setAttribute("categoryID", "");
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			try {
				if (contentName != null && !contentName.equals("")) {
					Node content = (Node) TagUtils.getInstance().lookup(
							pageContext, contentName, contentProperty, scope);
					this.pageContext.getRequest().setAttribute("contentID",
							content.getID());
				} else {
					this.pageContext.getRequest().setAttribute("contentID", "");
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}

			this.pageContext.include("/cms/validators.jsp");
		} catch (IOException ex) {
			throw new JspException(ex);
		} catch (ServletException ex) {
			throw new JspException(ex);
		}
		return super.doEndTag();
	}
}
