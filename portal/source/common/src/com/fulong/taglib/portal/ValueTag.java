package com.fulong.taglib.portal;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.BodyContent;

/**
 * 
 * <p>
 * Title: 龙驭门户引擎
 * </p>
 * 
 * <p>
 * Description: 龙驭网站内容管理系统核心引擎
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
 * @author Lixf
 * @version 1.0
 */
public class ValueTag extends BodyTagSupport {

	private static final long serialVersionUID = 3473538434715491876L;

	public int doEndTag() throws JspException {
		PreferenceTag prefTag = (PreferenceTag) this.pageContext
				.getAttribute(PreferenceTag.PREFERENCE_TAG);
		if (prefTag != null) {
			BodyContent value = this.getBodyContent();
			if (value != null)
				prefTag.getPreference().addValue(value.getString().trim());
			else
				prefTag.getPreference().addValue("");
		}
		return EVAL_PAGE;
	}
}
