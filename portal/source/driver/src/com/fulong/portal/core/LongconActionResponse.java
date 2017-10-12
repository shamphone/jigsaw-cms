package com.fulong.portal.core;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.portlet.ActionResponse;
import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;
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

public class LongconActionResponse extends LongconPortletResponse implements ActionResponse {
	boolean redirectAllowed = true;

	private boolean redirected = false;
	private String redirectLocation = null;
	private WindowState windowState = null;
	private PortletMode portletMode = null;
	private HashMap<String, String[]> renderParams = new HashMap<String, String[]>();

	public LongconActionResponse(PageContext context, PortletWindow window, PortletContainer container) {
		super(context, window, container);
	}

	/**
	 * 
	 * @param windowState
	 *            WindowState
	 * @throws WindowStateException
	 */
	public void setWindowState(WindowState windowState)
			throws WindowStateException {
		this.windowState = windowState;
		this.redirectAllowed = false;
	}

	/**
	 * 
	 * @param portletMode
	 *            PortletMode
	 * @throws PortletModeException
	 */
	public void setPortletMode(PortletMode portletMode)
			throws PortletModeException {
		this.portletMode = portletMode;
		this.redirectAllowed = false;
	}

	/**
	 * 
	 * @param location
	 *            String
	 * @throws IOException
	 */
	public void sendRedirect(String location) throws java.io.IOException {
		if (!this.redirectAllowed)
			throw new IllegalStateException("Redirect is not allowed here.");
		if (location != null) {
			redirectLocation = this.encodeRedirectURL(location);
			redirected = true;
		}
	}

	/**
	 * 
	 * @param parameters
	 *            Map
	 */
	@SuppressWarnings("unchecked")
	public void setRenderParameters(Map parameters) {
		if (redirected) {
			throw new IllegalStateException(
					"Can't invoke setRenderParameters() after sendRedirect() has been called");
		}
		if (parameters == null) {
			throw new IllegalArgumentException(
					"Render parameters must not be null.");
		}
		for (Iterator iter = parameters.entrySet().iterator(); iter.hasNext();) {
			Map.Entry entry = (Map.Entry) iter.next();
			if (!(entry.getKey() instanceof String)) {
				throw new IllegalArgumentException(
						"Key must not be null and of type java.lang.String.");
			}
			if (!(entry.getValue() instanceof String[])) {
				throw new IllegalArgumentException(
						"Value must not be null and of type java.lang.String[].");
			}
		}

		this.renderParams = new HashMap<String, String[]>(parameters);

	}

	/**
	 * 
	 * @param key
	 *            String
	 * @param value
	 *            String
	 */
	public void setRenderParameter(String key, String value) {
		if (redirected) {
			throw new IllegalStateException(
					"Can't invoke setRenderParameter() after sendRedirect() has been called");
		}

		if ((key == null) || (value == null)) {
			throw new IllegalArgumentException(
					"Render parameter key or value must not be null.");
		}

		this.renderParams.put(key, new String[] { value });

	}

	/**
	 * 
	 * @param key
	 *            String
	 * @param values
	 *            String[]
	 */
	public void setRenderParameter(String key, String[] values) {
		if (redirected) {
			throw new IllegalStateException(
					"Can't invoke setRenderParameter() after sendRedirect() has been called");
		}

		if (key == null || values == null || values.length == 0) {
			throw new IllegalArgumentException(
					"Render parameter key or value must not be null or values be an empty array.");
		}

		this.renderParams.put(key, values);

	}

	// --------------------------------------------------------------------------------------------

	// org.apache.pluto.core.InternalActionResponse implementation
	// --------------------------------

	public String getRedirectLocation() {
		return redirectLocation;
	}

	public boolean isRedirected() {
		return this.redirected;
	}

	public PortletMode getPortletMode() {
		return portletMode;
	}

	public WindowState getWindowState() {
		return windowState;
	}

	public Map<String, String[]> getRenderParameterMap() {
		return this.renderParams;
	}
}
