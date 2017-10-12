/*
 * $Id: EmptyTag.java 54929 2004-10-16 16:38:42Z germuska $
 *
 * Copyright 1999-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fulong.taglib.common;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.TagUtils;

import com.fulong.longcon.repository.Node;
import com.fulong.taglib.ConditionalTagBase;

/**
 * 判断一个节点的值是否为空
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
public class EmptyTag extends ConditionalTagBase {

	// ------------------------------------------------------ Protected Methods

	private static final long serialVersionUID = 8521026919927554615L;

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

		return (condition(true));

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
	@SuppressWarnings("unchecked")
	protected boolean condition(boolean desired) throws JspException {
		if (this.name == null) {
			JspException e = new JspException(messages
					.getMessage("empty.noNameAttribute"));
			TagUtils.getInstance().saveException(pageContext, e);
			throw e;
		}

		Object value = null;
		if (this.property == null) {
			value = TagUtils.getInstance().lookup(pageContext, name, scope);
		} else {
			value = TagUtils.getInstance().lookup(pageContext, name, property,
					scope);
		}
		Object variable = null;
		boolean empty = true;
		try {
			variable = ((Node) value).getProperty(propertyName).getValue()
					.getString();
		} catch (Exception ex) {
			empty = true;
			return (empty == desired);
		}
		if (variable == null) {
			empty = true;
		} else if (variable instanceof String) {
			String strValue = (String) variable;
			empty = (strValue.length() < 1);

		} else if (variable instanceof Collection) {
			Collection collValue = (Collection) variable;
			empty = collValue.isEmpty();

		} else if (variable instanceof Map) {
			Map mapValue = (Map) variable;
			empty = mapValue.isEmpty();
		} else if (variable.getClass().isArray()) {
			empty = Array.getLength(variable) == 0;
		} else {
			empty = false;
		}

		return (empty == desired);
	}

}
