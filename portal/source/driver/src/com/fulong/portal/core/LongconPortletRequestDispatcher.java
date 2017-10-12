package com.fulong.portal.core;

import java.io.IOException;

import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

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

public class LongconPortletRequestDispatcher implements PortletRequestDispatcher, RequestDispatcher {
	private String path;
	private ServletContext context;

	public LongconPortletRequestDispatcher(ServletContext context, String path) {
		this.path = path.replaceAll("\\\\", "/");
		this.context = context;
	}

	/*
	 * 注意这里对include的处理和对forward的处理是不一样的。include处理的path是来自使用占位符的web上下文，而
	 * forward处理的path是来自占位符本身的上下文。后者主要使用在LongconPortlet中。
	 * 
	 * @see
	 * javax.portlet.PortletRequestDispatcher#include(javax.portlet.RenderRequest
	 * , javax.portlet.RenderResponse)
	 */
	@Override
	public void include(RenderRequest request, RenderResponse response) throws PortletException, IOException {
		LongconRenderRequest req = (LongconRenderRequest) request;
		try {
			RequestDispatcher rd = this.getRootContext(request).getContext("/components").getRequestDispatcher(path);
			rd.include((ServletRequest) request, new RenderResponseWrapperIncluded(response, req.getPageContext().getOut()));
		} catch (ServletException ex) {
			throw new LongconPortletException(ex);
		}
	}

	/**
	 * Convert a possibly relative resource path into a context-relative
	 * resource path that starts with a '/'.
	 * 
	 * @param request
	 *            The servlet request we are processing
	 * @param relativePath
	 *            The possibly relative resource path
	 */
	public static String getContextRelativePath(ServletRequest request, String relativePath) {

		if (relativePath.startsWith("/"))
			return (relativePath);
		if (!(request instanceof HttpServletRequest))
			return (relativePath);
		HttpServletRequest hrequest = (HttpServletRequest) request;
		String uri = (String) request.getAttribute("javax.servlet.include.servlet_path");
		if (uri != null) {
			String pathInfo = (String) request.getAttribute("javax.servlet.include.path_info");
			if (pathInfo == null) {
				if (uri.lastIndexOf('/') >= 0)
					uri = uri.substring(0, uri.lastIndexOf('/'));
			}
		} else {
			uri = hrequest.getServletPath();
			if (uri.lastIndexOf('/') >= 0)
				uri = uri.substring(0, uri.lastIndexOf('/'));
		}
		return uri + '/' + relativePath;

	}

	public void forward(ServletRequest request, ServletResponse response) throws IOException, ServletException {
		RequestDispatcher rd = this.context.getRequestDispatcher(path);
		rd.include(request, response);
	}

	public void include(ServletRequest request, ServletResponse response) throws IOException, ServletException {
		try {
			this.include((RenderRequest) request, (RenderResponse) response);
		} catch (PortletException ex) {
			throw new ServletException(ex.getCause());
		}

	}

	/**
	 * 获取用户发出请求的context;
	 * 
	 * @return
	 */
	private ServletContext getRootContext(RenderRequest renderRequest) {
		HttpServletRequest request = (HttpServletRequest) renderRequest;
		while (request instanceof HttpServletRequestWrapper) {
			request = (HttpServletRequest) ((HttpServletRequestWrapper) request).getRequest();
		}
		return request.getSession().getServletContext();
	}
}
