package com.fulong.portal.core;

import java.io.IOException;
import java.io.OutputStream;

import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;
import javax.servlet.jsp.PageContext;

import com.fulong.portal.model.PortletContainer;
import com.fulong.portal.model.PortletWindow;

/**
 * 
 * <p>
 * Title: Longcon Portal Driver
 * </p>
 * 
 * <p>
 * Description: Longcon WebMaster
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Beijing Zhongke Fulong Computer Technology LTD.
 * </p>
 * 
 * @author Lixf
 * @version 1.0
 */

public class LongconRenderResponse extends LongconPortletResponse implements RenderResponse {
	
	public LongconRenderResponse(PageContext context, PortletWindow window, PortletContainer container) {
		super(context, window, container);
	}

	/**
	 * 
	 * @return PortletURL
	 */

	public PortletURL createRenderURL() {
		return new LongconPortletURL(this.pageContext, this.portletWindow, false);

	}

	/**
	 * 
	 * @return PortletURL
	 */
	public PortletURL createActionURL() {
		return new LongconPortletURL(this.pageContext, this.portletWindow, true);
	}

	/**
	 * 
	 * @return String
	 */
	public String getNamespace() {
		return NamespaceMapper.encode(this.portletWindow.getId(), "");
	}

	/**
	 * 
	 * @param title
	 *            String
	 */
	public void setTitle(String title) {
		portletWindow.setTitle(title);
	}

	/**
	 * 
	 * @return OutputStream
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	public OutputStream getPortletOutputStream() throws IOException, IllegalStateException {
		return this.getResponse().getOutputStream();
	}

}
