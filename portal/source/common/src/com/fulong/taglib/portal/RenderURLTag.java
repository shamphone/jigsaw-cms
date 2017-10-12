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
 * Title: WebMaster Core Library
 * </p>
 * 
 * <p>
 * Description: Longcon WebMaster SV3
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Beijing Zhongke Fulong Computer Tech. LTD
 * </p>
 * 
 * @author lixf
 * @version 1.0
 */

public class RenderURLTag extends URLTag {
	
	private static final long serialVersionUID = 3552932414337291718L;

	public int doStartTag() throws JspException {
		if (var != null) {
			pageContext.removeAttribute(var, PageContext.PAGE_SCOPE);
		}
		RenderResponse renderResponse = (RenderResponse) pageContext
				.getRequest().getAttribute(Constants.PORTLET_RESPONSE);
		if (renderResponse != null) {
			setUrl(renderResponse.createRenderURL());
			if (portletMode != null) {
				try {
					// url.setPortletMode((PortletMode) TEI.portletModes.get(
					// portletMode.toUpperCase()));
					url.setPortletMode(new PortletMode(portletMode));
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
