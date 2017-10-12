package com.fulong.portlet;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.portlet.PortletContext;

/**
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
public class PortletContextWrapper implements ServletContext {
	private PortletContext context;

	public PortletContextWrapper(PortletContext context) {
		this.context = context;
	}

	/**
	 * Returns the servlet container attribute with the given name, or
	 * <code>null</code> if there is no attribute by that name.
	 * 
	 * @param name
	 *            a <code>String</code> specifying the name of the attribute
	 * @return an <code>Object</code> containing the value of the attribute, or
	 *         <code>null</code> if no attribute exists matching the given name
	 */
	public Object getAttribute(String name) {
		return this.context.getAttribute(name);
	}

	/**
	 * Returns an <code>Enumeration</code> containing the attribute names
	 * available within this servlet context.
	 * 
	 * @return an <code>Enumeration</code> of attribute names
	 */
	@SuppressWarnings("unchecked")
	public Enumeration getAttributeNames() {
		return this.context.getAttributeNames();
	}

	/**
	 * Returns a <code>ServletContext</code> object that corresponds to a
	 * specified URL on the server.
	 * 
	 * @param uripath
	 *            a <code>String</code> specifying the context path of another
	 *            web application in the container.
	 * @return the <code>ServletContext</code> object that corresponds to the
	 *         named URL, or null if either none exists or the container wishes
	 *         to restrict this access.
	 */
	public ServletContext getContext(String uripath) {
		return null;
	}

	/**
	 * Returns a <code>String</code> containing the value of the named
	 * context-wide initialization parameter, or <code>null</code> if the
	 * parameter does not exist.
	 * 
	 * @param name
	 *            a <code>String</code> containing the name of the parameter
	 *            whose value is requested
	 * @return a <code>String</code> containing at least the servlet container
	 *         name and version number
	 */
	public String getInitParameter(String name) {
		return this.context.getInitParameter(name);
	}

	/**
	 * Returns the names of the context's initialization parameters as an
	 * <code>Enumeration</code> of <code>String</code> objects, or an empty
	 * <code>Enumeration</code> if the context has no initialization parameters.
	 * 
	 * @return an <code>Enumeration</code> of <code>String</code> objects
	 *         containing the names of the context's initialization parameters
	 */
	@SuppressWarnings("unchecked")
	public Enumeration getInitParameterNames() {
		return this.context.getInitParameterNames();
	}

	/**
	 * Returns the major version of the Java Servlet API that this servlet
	 * container supports.
	 * 
	 * @return 2
	 */
	public int getMajorVersion() {
		return this.context.getMajorVersion();
	}

	/**
	 * Returns the MIME type of the specified file, or <code>null</code> if the
	 * MIME type is not known.
	 * 
	 * @param file
	 *            a <code>String</code> specifying the name of a file
	 * @return a <code>String</code> specifying the file's MIME type
	 */
	public String getMimeType(String file) {
		return this.context.getMimeType(file);
	}

	/**
	 * Returns the minor version of the Servlet API that this servlet container
	 * supports.
	 * 
	 * @return 4
	 */
	public int getMinorVersion() {
		return this.context.getMinorVersion();
	}

	/**
	 * Returns a {@link RequestDispatcher} object that acts as a wrapper for the
	 * named servlet.
	 * 
	 * @param name
	 *            a <code>String</code> specifying the name of a servlet to wrap
	 * @return a <code>RequestDispatcher</code> object that acts as a wrapper
	 *         for the named servlet, or <code>null</code> if the
	 *         <code>ServletContext</code> cannot return a
	 *         <code>RequestDispatcher</code>
	 */
	public RequestDispatcher getNamedDispatcher(String name) {
		return (RequestDispatcher) this.context.getNamedDispatcher(name);
	}

	/**
	 * Returns a <code>String</code> containing the real path for a given
	 * virtual path.
	 * 
	 * @param path
	 *            a <code>String</code> specifying a virtual path
	 * @return a <code>String</code> specifying the real path, or null if the
	 *         translation cannot be performed
	 */
	public String getRealPath(String path) {
		return this.context.getRealPath(path);
	}

	/**
	 * Returns a {@link RequestDispatcher} object that acts as a wrapper for the
	 * resource located at the given path.
	 * 
	 * @param path
	 *            a <code>String</code> specifying the pathname to the resource
	 * @return a <code>RequestDispatcher</code> object that acts as a wrapper
	 *         for the resource at the specified path, or <code>null</code> if
	 *         the <code>ServletContext</code> cannot return a
	 *         <code>RequestDispatcher</code>
	 */
	public RequestDispatcher getRequestDispatcher(String path) {
		return (RequestDispatcher) this.context.getRequestDispatcher(path);
	}

	/**
	 * Returns a URL to the resource that is mapped to a specified path.
	 * 
	 * @param path
	 *            a <code>String</code> specifying the path to the resource
	 * @return the resource located at the named path, or <code>null</code> if
	 *         there is no resource at that path
	 * @throws MalformedURLException
	 *             if the pathname is not given in the correct form
	 */
	public URL getResource(String path) throws MalformedURLException {
		return this.context.getResource(path);
	}

	/**
	 * Returns the resource located at the named path as an
	 * <code>InputStream</code> object.
	 * 
	 * @param path
	 *            a <code>String</code> specifying the path to the resource
	 * @return the <code>InputStream</code> returned to the servlet, or
	 *         <code>null</code> if no resource exists at the specified path
	 */
	public InputStream getResourceAsStream(String path) {
		return this.context.getResourceAsStream(path);
	}

	/**
	 * Returns a directory-like listing of all the paths to resources within the
	 * web application whose longest sub-path matches the supplied path
	 * argument.
	 * 
	 * @param path
	 *            the partial path used to match the resources, which must start
	 *            with a
	 * @return a Set containing the directory listing, or null if there are no
	 *         resources in the web application whose path begins with the
	 *         supplied path.
	 */
	@SuppressWarnings("unchecked")
	public Set getResourcePaths(String path) {
		return this.context.getResourcePaths(path);
	}

	/**
	 * Returns the name and version of the servlet container on which the
	 * servlet is running.
	 * 
	 * @return a <code>String</code> containing at least the servlet container
	 *         name and version number
	 */
	public String getServerInfo() {
		return this.context.getServerInfo();
	}

	/**
	 * @deprecated As of Java Servlet API 2.1, with no direct replacement.
	 * 
	 * @param name
	 *            String
	 * @throws ServletException
	 * @return Servlet
	 */
	public Servlet getServlet(String name) throws ServletException {
		return null;
	}

	/**
	 * Returns the name of this web application corresponding to this
	 * ServletContext as specified in the deployment descriptor for this web
	 * application by the display-name element.
	 * 
	 * @return The name of the web application or null if no name has been
	 *         declared in the deployment descriptor.
	 */
	public String getServletContextName() {
		return this.context.getPortletContextName();
	}

	/**
	 * @deprecated As of Java Servlet API 2.1, with no replacement.
	 * 
	 * @return Enumeration
	 */
	@SuppressWarnings("unchecked")
	public Enumeration getServletNames() {
		return null;
	}

	/**
	 * @deprecated As of Java Servlet API 2.0, with no replacement.
	 * 
	 * @return Enumeration
	 */
	@SuppressWarnings("unchecked")
	public Enumeration getServlets() {
		return null;
	}

	/**
	 * Writes the specified message to a servlet log file, usually an event log.
	 * 
	 * @param msg
	 *            a <code>String</code> specifying the message to be written to
	 *            the log file
	 */
	public void log(String msg) {
		this.context.log(msg);
	}

	/**
	 * Writes an explanatory message and a stack trace for a given
	 * <code>Throwable</code> exception to the servlet log file.
	 * 
	 * @param message
	 *            a <code>String</code> that describes the error or exception
	 * @param throwable
	 *            the <code>Throwable</code> error or exception
	 */
	public void log(String message, Throwable throwable) {
		this.context.log(message, throwable);
	}

	/**
	 * @deprecated As of Java Servlet API 2.1, use
	 *             {@link #log(String message, Throwable throwable)} instead.
	 * 
	 * @param exception
	 *            Exception
	 * @param msg
	 *            String
	 */
	public void log(Exception exception, String msg) {
		this.context.log(msg, exception);
	}

	/**
	 * Removes the attribute with the given name from the servlet context.
	 * 
	 * @param name
	 *            a <code>String</code> specifying the name of the attribute to
	 *            be removed
	 */
	public void removeAttribute(String name) {
		this.context.removeAttribute(name);
	}

	/**
	 * Binds an object to a given attribute name in this servlet context.
	 * 
	 * @param name
	 *            a <code>String</code> specifying the name of the attribute
	 * @param object
	 *            an <code>Object</code> representing the attribute to be bound
	 */
	public void setAttribute(String name, Object object) {
		this.context.setAttribute(name, object);
	}

	public String getContextPath() {
		// TODO Auto-generated method stub
		return null;
	}
}
