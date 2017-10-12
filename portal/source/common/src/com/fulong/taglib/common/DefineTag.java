package com.fulong.taglib.common;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.util.MessageResources;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.Property;
import com.fulong.longcon.repository.PropertyType;
import com.fulong.longcon.repository.Value;
import com.fulong.longcon.repository.ValueFormatException;
import com.fulong.longcon.resource.Resource;
import com.fulong.taglib.SpringTagSupport;

/**
 * 在页面上定义一个node
 * <p>
 * Title: 龙驭电子商务系统
 * </p>
 * 
 * <p>
 * Description: 龙驭电子商务系统
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
 * @author Lixf
 * @version 2.0
 */

public class DefineTag extends SpringTagSupport {

	private static final long serialVersionUID = 697644748141451167L;

	/**
	 * Commons logging instance.
	 */
	private static final Log log = LogFactory.getLog(DefineTag.class);

	// ---------------------------------------------------- Protected variables

	/**
	 * The message resources for this package.
	 */
	protected static MessageResources messages = MessageResources
			.getMessageResources("org.apache.struts.taglib.bean.LocalStrings");

	/**
	 * The body content of this tag (if any).
	 */
	protected String body = null;

	// ------------------------------------------------------------- Properties

	/**
	 * The name of the scripting variable that will be exposed as a page scope
	 * attribute.
	 */
	protected String id = null;

	public String getId() {
		return (this.id);
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * The name of the bean owning the property to be exposed.
	 */
	protected String name = null;

	public String getName() {
		return (this.name);
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * The name of the property to be retrieved.
	 */
	protected String property = null;

	public String getProperty() {
		return (this.property);
	}

	public void setProperty(String property) {
		this.property = property;
	}

	/**
	 * The scope within which to search for the specified bean.
	 */
	protected String scope = null;

	public String getScope() {
		return (this.scope);
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	/**
	 * The scope within which the newly defined bean will be creatd.
	 */
	protected String toScope = null;

	public String getToScope() {
		return (this.toScope);
	}

	public void setToScope(String toScope) {
		this.toScope = toScope;
	}

	/**
	 * The fully qualified Java class name of the value to be exposed.
	 */
	protected String type = null;

	public String getType() {
		return (this.type);
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * The (String) value to which the defined bean will be set.
	 */
	protected String value = null;
	private String propertyName;
	private boolean ignore = false;

	public String getValue() {
		return (this.value);
	}

	public String getPropertyName() {
		return propertyName;
	}

	public boolean isIgnore() {
		return ignore;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public void setIgnore(boolean ignore) {
		this.ignore = ignore;
	}

	private String formatStr;

	public String getFormat() {
		return formatStr;
	}

	public void setFormat(String format) {
		this.formatStr = format;
	}

	private String seperator = " ";

	public String getSeperator() {
		return seperator;
	}

	public void setSeperator(String seperator) {
		this.seperator = seperator;
	}

	// --------------------------------------------------------- Public Methods

	/**
	 * Check if we need to evaluate the body of the tag
	 * 
	 * @exception JspException
	 *                if a JSP exception has occurred
	 */
	@SuppressWarnings("deprecation")
	public int doStartTag() throws JspException {

		return (EVAL_BODY_TAG);

	}

	/**
	 * Save the body content of this tag (if any), or throw a JspException if
	 * the value was already defined.
	 * 
	 * @exception JspException
	 *                if value was defined by an attribute
	 */
	public int doAfterBody() throws JspException {

		if (bodyContent != null) {
			body = bodyContent.getString();
			if (body != null) {
				body = body.trim();
			}
			if (body.length() < 1) {
				body = null;
			}
		}
		return (SKIP_BODY);

	}

	/**
	 * Retrieve the required property and expose it as a scripting variable.
	 * 
	 * @exception JspException
	 *                if a JSP exception has occurred
	 */
	public int doEndTag() throws JspException {

		// Enforce restriction on ways to declare the new value
		int n = 0;
		if (this.body != null) {
			n++;
		}
		if (this.name != null) {
			n++;
		}
		if (this.value != null) {
			n++;
		}
		if (n > 1) {
			JspException e = new JspException(messages.getMessage(
					"define.value", id));
			TagUtils.getInstance().saveException(pageContext, e);
			throw e;
		}

		// Retrieve the required property value
		Object value = this.value;
		if ((value == null) && (name != null)) {
			value = TagUtils.getInstance().lookup(pageContext, name, property,
					scope);
			Property property = ((Node) value).getProperty(propertyName);
			if (property != null) {
				if (type != null) {
					if (type.equals("user")) {
						try {
							value = this.getPassportProvider().getUser(
									property.getString());
						} catch (ValueFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} else {
					value = this.formatProperty(property);
				}
			}
		}

		if ((value == null) && (body != null)) {
			value = body;
		}
		if (!ignore && (value == null)) {
			JspException e = new JspException(messages.getMessage(
					"define.null", id));
			TagUtils.getInstance().saveException(pageContext, e);
			throw e;
		}

		// Expose this value as a scripting variable
		int inScope = PageContext.PAGE_SCOPE;
		try {
			if (toScope != null) {
				inScope = TagUtils.getInstance().getScope(toScope);
			}
		} catch (JspException e) {
			log.warn("toScope was invalid name so we default to PAGE_SCOPE", e);
		}

		pageContext.setAttribute(id, value, inScope);

		// Continue processing this page
		return (EVAL_PAGE);

	}

	/**
	 * Release all allocated resources.
	 */
	public void release() {

		super.release();
		body = null;
		id = null;
		name = null;
		property = null;
		scope = null;
		toScope = "page";
		type = null;
		value = null;

	}

	protected String formatProperty(Property property) throws JspException {
		try {
			if (property.getValue() == null) {
				return "";
			}
		} catch (Exception ex) {
		}
		if (property.getValues().length == 0) {
			return "";
		}
		Value[] values = property.getValues();
		StringBuffer output = new StringBuffer("");
		if ((this.formatStr == null) || (this.formatStr.length() == 0)) {
			for (int i = 0; i < values.length; i++) {
				if (i > 1) {
					output.append(this.seperator);
				}
				if (values[i].getString() != null) {
					output.append(values[i].getString());
				}
			}
			return output.toString();
		}

		int propertyType = property.getDefinition().getType();
		String contextPath = ((HttpServletRequest) this.pageContext
				.getRequest()).getContextPath();
		for (int i = 0; i < values.length; i++) {
			if (values[i].getString() == null) {
				output.append("");
			} else {
				if (i > 1) {
					output.append(this.seperator);
				}
				switch (propertyType) {
				case PropertyType.DATE:
					Calendar date = values[i].getDate();
					SimpleDateFormat format = new SimpleDateFormat(
							this.formatStr);
					output.append(format.format(date.getTime()));
					break;
				case PropertyType.DOUBLE:
					DecimalFormat dformat = new DecimalFormat(this.formatStr);
					output.append(dformat.format(values[i].getDouble()));
					break;
				case PropertyType.LONG:
					DecimalFormat lformat = new DecimalFormat(this.formatStr);
					output.append(lformat.format(values[i].getDouble()));
					break;
				case PropertyType.REFERENCE: // 引用属性,目前仅处理resource；
					Resource resource = this.getResourceManager().getResource(
							values[i].getString());
					if (resource != null) {
						String path = contextPath + resource.getPath();
						output.append(this.formatStr.replaceAll("#", path));
					} else {
						Node content = this.getRepository().getNode(
								values[i].getString());
						if (content != null) {
							output.append(this.formatStr.replaceAll("#",
									content.getProperty("title").getString()));
						}
					}
					break;
				default:
					String value = values[i].getString();
					/*
					 * if (property.getDefinition().getEnumEntry() != null) {
					 * Entry entry = this.getDictManager().getRootEntry().
					 * getEntry(property.getDefinition().getEnumEntry()); if
					 * (entry != null) { entry = entry.getEntry(value); } if
					 * (entry != null) { value = entry.getLabel(); } }
					 */
					if (value == null) {
						output.append("");
					} else {
						output.append(this.formatStr.replaceAll("#", value));
					}
					break;
				}
			}
		}
		return output.toString();
	}

}
