package com.fulong.portal.core;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Enumeration;
import java.util.Set;

import javax.portlet.PortletContext;
import javax.portlet.PortletRequestDispatcher;
import javax.servlet.ServletContext;

import com.fulong.portal.model.PortletContainer;

/**
 * 
 * <p>
 * Title: Longcon Portal
 * </p>
 * 
 * <p>
 * Description: Longcon Portal Driver
 * </p>
 * 
 * <p>
 * Copyright: Beijing Zhongke Fulong Computer Tech Co.LTD
 * </p>
 * 
 * <p>
 * Company: Beijing Zhongke Fulong Computer Tech Co.LTD
 * </p>
 * 
 * @author Lixf
 * @version 1.0
 */

public class LongconPortletContext implements PortletContext {
	private ServletContext servletContext;
	private PortletContainer container;

	public LongconPortletContext(PortletContainer container,
			ServletContext servletContext) {
		this.container = container;
		this.servletContext = servletContext;
	}

	// PortletContext implementation
	// ------------------------------------------------
	public String getServerInfo() {
		StringBuffer sb = new StringBuffer(getPortletContainerName()).append(
				"/").append(this.getMajorVersion()).append(".").append(
				this.getMinorVersion());
		return sb.toString();

	}

	public String getPortletContainerName() {
		return this.container.getPortalContext().getPortalInfo();
	}

	public PortletRequestDispatcher getRequestDispatcher(String path) {
		try {
			return new LongconPortletRequestDispatcher(this.servletContext,
					path);
		} catch (Exception e) {
			// need to catch exception because of tomcat 4.x bug
			// tomcat throws an exception instead of return null
			// if the path was not found
			return null;
		}

	}

	public PortletRequestDispatcher getNamedDispatcher(String name) {
		throw new UnsupportedOperationException("getNamedDispatcher");
	}

	public InputStream getResourceAsStream(String path) {
		return servletContext.getResourceAsStream(path);
	}

	public int getMajorVersion() {
		return Integer.parseInt(container.getPortalContext().getProperty(
				"javax.portlet.version.major"));
	}

	public int getMinorVersion() {
		return Integer.parseInt(container.getPortalContext().getProperty(
				"javax.portlet.version.minor"));
	}

	public String getMimeType(String file) {
		return servletContext.getMimeType(file);
	}

	public String getRealPath(String path) {
		return servletContext.getRealPath(path);
	}

	@SuppressWarnings("unchecked")
	public Set getResourcePaths(String path) {
		return servletContext.getResourcePaths(path);
	}

	public java.net.URL getResource(String path)
			throws java.net.MalformedURLException {
		if (path == null || !path.startsWith("/")) {
			throw new MalformedURLException("path must start with a '/'");
		}
		return servletContext.getResource(path);
	}

	public Object getAttribute(java.lang.String name) {
		if (name == null) {
			throw new IllegalArgumentException("Attribute name == null");
		}

		return servletContext.getAttribute(name);
	}

	@SuppressWarnings("unchecked")
	public Enumeration getAttributeNames() {
		return servletContext.getAttributeNames();
	}

	public java.lang.String getInitParameter(java.lang.String name) {
		if (name == null) {
			throw new IllegalArgumentException("Parameter name == null");
		}

		return servletContext.getInitParameter(name);
	}

	@SuppressWarnings("unchecked")
	public Enumeration getInitParameterNames() {
		return servletContext.getInitParameterNames();
	}

	public void log(java.lang.String msg) {
		servletContext.log(msg);
	}

	public void log(java.lang.String message, java.lang.Throwable throwable) {
		servletContext.log(message, throwable);
	}

	public void removeAttribute(java.lang.String name) {
		if (name == null) {
			throw new IllegalArgumentException("Attribute name == null");
		}

		servletContext.removeAttribute(name);
	}

	public void setAttribute(java.lang.String name, java.lang.Object object) {
		if (name == null) {
			throw new IllegalArgumentException("Attribute name == null");
		}

		servletContext.setAttribute(name, object);
	}

	public String getPortletContextName() {
		return this.servletContext.getServletContextName();
	}

}
