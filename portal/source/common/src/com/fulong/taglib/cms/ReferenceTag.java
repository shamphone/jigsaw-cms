package com.fulong.taglib.cms;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.logic.ConditionalTagBase;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.Property;
import com.fulong.longcon.repository.PropertyIterator;

/**
 * 判断一个内容是否被投递
 * <p>
 * Title: 龙驭内容管理系统
 * </p>
 * 
 * <p>
 * Description: 龙驭内容管理系统
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
 * @author liuzijun
 * @version 2.0
 */
public class ReferenceTag extends ConditionalTagBase {

	// ------------------------------------------------------ Protected Methods

	private static final long serialVersionUID = 923061121631686708L;

	/**
	 * Evaluate the condition that is being tested by this particular tag, and
	 * return <code>true</code> if the nested body content of this tag should be
	 * evaluated, or <code>false</code> if it should be skipped. This method
	 * must be implemented by concrete subclasses.
	 * 
	 * @exception JspException
	 *                if a JSP exception occurs
	 */
	protected boolean condition() throws JspException {

		return (condition(false));

	}

	/**
	 * Evaluate the condition that is being tested by this particular tag, and
	 * return <code>true</code> if the nested body content of this tag should be
	 * evaluated, or <code>false</code> if it should be skipped. This method
	 * must be implemented by concrete subclasses.
	 * 
	 * @param desired
	 *            Desired outcome for a true result
	 * 
	 * @exception JspException
	 *                if a JSP exception occurs
	 */
	protected boolean condition(boolean desired) throws JspException {
		if (this.name == null) {
			JspException e = new JspException(messages
					.getMessage("empty.noNameAttribute"));
			TagUtils.getInstance().saveException(pageContext, e);
			throw e;
		}

		Node content = (Node) TagUtils.getInstance().lookup(pageContext, name, scope);
		boolean empty = true;
		if (content == null) {
			empty = true;
			return (empty == desired);
		}
		PropertyIterator<?> references = content.getReferences();
		if (this.property != null) {
			if (references.hasNext()) {
				while (references.hasNext()) {
					Property reference = (Property) references.next();
					if (reference.getParent().getID().equals(this.property)) {
						empty = false;
						return (empty == desired);
					}
				}
				empty = true;
				return (empty == desired);
			} else {
				empty = true;
				return (empty == desired);
			}
		} else {
			if (references.hasNext()) {
				empty = false;
				return (empty == desired);
			} else {
				empty = true;
				return (empty == desired);
			}
		}
	}
}
