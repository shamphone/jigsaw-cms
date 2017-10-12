package com.fulong.taglib.portal;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.fulong.portal.model.Preference;

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
public class PreferenceTag extends TagSupport {

	private static final long serialVersionUID = -3043695951205446767L;

	private Preference preference = null;
	public static final String PREFERENCE_TAG = "com.fulong.portal.preference";

	public int doStartTag() throws JspException {
		preference = new Preference();
		this.pageContext.setAttribute(PREFERENCE_TAG, this);
		return EVAL_BODY_INCLUDE;
	}

	public int doEndTag() throws JspException {
		PortletTag portletTag = (PortletTag) this.pageContext
				.getAttribute(PortletTag.PORTLET_TAG);
		if (portletTag != null) {
			portletTag.addPreference(preference);
		}
		this.pageContext.removeAttribute(PREFERENCE_TAG);
		return EVAL_PAGE;
	}

	public Preference getPreference() {
		return this.preference;
	}

	public void release() {
		this.preference = null;
	}

}
