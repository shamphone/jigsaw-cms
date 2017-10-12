package com.fulong.taglib.portal;

import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletSecurityException;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import com.fulong.portal.core.Constants;

/**
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

public class ActionURLTag extends URLTag {

	private static final long serialVersionUID = -5669265108629398123L;

	public int doStartTag() throws JspException {
		if (var != null) {
			pageContext.removeAttribute(var, PageContext.PAGE_SCOPE);
		}
		RenderResponse renderResponse = (RenderResponse) pageContext
				.getRequest().getAttribute(Constants.PORTLET_RESPONSE);

		if (renderResponse != null) {
			setUrl(renderResponse.createActionURL());
			if (portletMode != null) {
				try {
					url.setPortletMode(new PortletMode(portletMode
							.toUpperCase()));
				} catch (PortletModeException e) {
					throw new JspException(e);
				}
			}
			if (windowState != null) {
				try {
					url.setWindowState((WindowState) TEI.definedWindowStates
							.get(windowState.toUpperCase()));
				} catch (WindowStateException e) {
					throw new JspException(e);
				}
			}
			if (secure != null) {
				try {
					url.setSecure(getSecureBoolean());
				} catch (PortletSecurityException e) {
					throw new JspException(e);
				}
			}
		}
		return EVAL_PAGE;
	}
}
