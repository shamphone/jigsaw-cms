package com.fulong.taglib.common;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.struts.taglib.TagUtils;
import org.apache.struts.util.MessageResources;

/**
 * 提供类似for(int {id}={offset};{id}<{(name,property,scope)};{id}++){}的功能
 * 其中id是被暴露到pageContext的Integer； offset由输入参数决定 name、property、scope组合确定循环的最大值
 * 
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
public class ForTag extends BodyTagSupport {

	private static final long serialVersionUID = 5180626760926411121L;

	/**
	 * The number of elements we have already rendered.
	 */
	protected int lengthCount = 0;

	/**
	 * The actual length value (calculated in the start tag).
	 */
	protected int lengthValue = 0;

	/**
	 * The message resources for this package.
	 */
	protected static MessageResources messages = MessageResources
			.getMessageResources("org.apache.struts.taglib.logic.LocalStrings");

	/**
	 * The actual offset value (calculated in the start tag).
	 */
	protected int offsetValue = 0;

	/**
	 * Has this tag instance been started?
	 */
	protected boolean started = false;

	/**
	 * The name of the scripting variable to be exposed.
	 */
	protected String id = null;

	public String getId() {
		return (this.id);
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * <p>
	 * Return the zero-relative index of the current iteration through the loop.
	 * If you specify an <code>offset</code>, the first iteration through the
	 * loop will have that value; otherwise, the first iteration will return
	 * zero.
	 * </p>
	 * 
	 * <p>
	 * This property is read-only, and gives nested custom tags access to this
	 * information. Therefore, it is <strong>only</strong> valid in between
	 * calls to <code>doStartTag()</code> and <code>doEndTag()</code>.
	 * </p>
	 */
	public int getIndex() {
		if (started)
			return (offsetValue + lengthCount - 1);
		else
			return (0);
	}

	/**
	 * The name of the collection or owning bean.
	 */
	protected String name = null;

	public String getName() {
		return (this.name);
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * The starting offset (zero relative).
	 */
	protected String offset = null;

	public String getOffset() {
		return (this.offset);
	}

	public void setOffset(String offset) {
		this.offset = offset;
	}

	/**
	 * The property name containing the collection.
	 */
	protected String property = null;

	public String getProperty() {
		return (this.property);
	}

	public void setProperty(String property) {
		this.property = property;
	}

	/**
	 * The scope of the bean specified by the name property, if any.
	 */
	protected String scope = null;

	public String getScope() {
		return (this.scope);
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	/**
	 * The Java class of each exposed element of the collection.
	 */
	protected String type = null;

	public String getType() {
		return (this.type);
	}

	public void setType(String type) {
		this.type = type;
	}

	// --------------------------------------------------------- Public Methods

	/**
	 * Construct an iterator for the specified collection, and begin looping
	 * through the body once per element.
	 * 
	 * @exception JspException
	 *                if a JSP exception has occurred
	 */
	@SuppressWarnings("deprecation")
	public int doStartTag() throws JspException {
		// Calculate the starting offset
		if (offset == null) {
			offsetValue = 0;
		} else {
			try {
				offsetValue = Integer.parseInt(offset);
			} catch (NumberFormatException e) {
				Integer offsetObject = (Integer) TagUtils.getInstance().lookup(
						pageContext, offset, null);
				if (offsetObject == null) {
					offsetValue = 0;
				} else {
					offsetValue = offsetObject.intValue();
				}
			}
		}
		if (offsetValue < 0) {
			offsetValue = 0;
		}
		Object length = TagUtils.getInstance().lookup(pageContext, name,
				property, scope);
		if (length == null)
			lengthValue = 0;
		else if (length instanceof Integer)
			lengthValue = ((Integer) length).intValue();
		else
			lengthValue = Integer.parseInt(length.toString());

		lengthCount = offsetValue;

		// Store the first value and evaluate, or skip the body if none
		if (lengthCount < lengthValue) {
			pageContext.setAttribute(id, new Integer(lengthCount));
			lengthCount++;
			started = true;
			return (EVAL_BODY_TAG);
		} else {
			return (SKIP_BODY);
		}

	}

	/**
	 * Make the next collection element available and loop, or finish the
	 * iterations if there are no more elements.
	 * 
	 * @exception JspException
	 *                if a JSP exception has occurred
	 */
	@SuppressWarnings("deprecation")
	public int doAfterBody() throws JspException {

		// Render the output from this iteration to the output stream
		if (bodyContent != null) {
			TagUtils.getInstance().writePrevious(pageContext,
					bodyContent.getString());
			bodyContent.clearBody();
		}

		// Decide whether to iterate or quit
		if ((lengthValue > 0) && (lengthCount >= lengthValue)) {
			pageContext.removeAttribute(id);
			return (SKIP_BODY);
		}
		pageContext.setAttribute(id, new Integer(lengthCount));
		lengthCount++;

		return (EVAL_BODY_TAG);
	}

	/**
	 * Clean up after processing this enumeration.
	 * 
	 * @exception JspException
	 *                if a JSP exception has occurred
	 */
	public int doEndTag() throws JspException {

		// Clean up our started state
		started = false;

		// Continue processing this page
		return (EVAL_PAGE);

	}

	/**
	 * Release all allocated resources.
	 */
	public void release() {

		super.release();

		lengthCount = 0;
		lengthValue = 0;
		offsetValue = 0;

		id = null;
		name = null;
		offset = null;
		property = null;
		scope = null;
		started = false;

	}

}
