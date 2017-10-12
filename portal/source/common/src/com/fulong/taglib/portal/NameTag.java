package com.fulong.taglib.portal;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

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
 * @author 李雄锋
 * @version 1.0
 */
public class NameTag extends BodyTagSupport {

	private static final long serialVersionUID = -2391209167932258539L;

	public int doEndTag() throws JspException {
		PreferenceTag prefTag = (PreferenceTag) this.pageContext
				.getAttribute(PreferenceTag.PREFERENCE_TAG);
		if (prefTag != null) {
			prefTag.getPreference().setName(bodyContent.getString().trim());
		}
		
		return EVAL_PAGE;
	}

}
