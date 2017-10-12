package com.fulong.taglib.common;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.taglib.TagUtils;

import com.fulong.common.util.Pager;

/**
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
 * @author Lixf
 * @version 1.0
 */
public class PagerTag extends TagSupport {
	
	private static final long serialVersionUID = -593119011771313917L;
	
	private String pageNo;
	private String pageSize;
	private String count;
	private String name;
	private String property;
	private String scope;
	private String pattern;
	private String target;

	public PagerTag() {
		this.pageNo = null;
		this.pageSize = null;
		this.count = null;
		this.name = null;
		this.property = null;
		this.scope = null;
		this.target = "";
	}

	public String getPageNo() {
		return pageNo;
	}

	public String getPageSize() {
		return pageSize;
	}

	public String getCount() {
		return count;
	}

	public String getName() {
		return name;
	}

	public String getProperty() {
		return property;
	}

	public String getScope() {
		return scope;
	}

	public String getPattern() {

		return pattern;
	}

	public String getTarget() {
		return target;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public void setPattern(String pattern) {

		this.pattern = pattern;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	@SuppressWarnings("null")
	public int doStartTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) this.pageContext
				.getRequest();
		Pager pager = null;
		if ((this.name != null) && this.name.length() > 0)
			pager = (Pager) TagUtils.getInstance().lookup(pageContext, name,
					property, scope);
		if ((pager == null) && (this.pageNo != null)
				&& (this.pageNo.length() > 0)) {
			int pageNo = 0;
			int pageSize = 20;
			long count = 0;
			if ((this.pageNo != null) && (this.pageNo.length() > 0))
				pageNo = Integer.parseInt(this.pageNo);
			if ((this.pageSize != null) && (this.pageSize.length() > 0))
				pageSize = Integer.parseInt(this.pageSize);
			if ((this.count != null) && (this.count.length() > 0))
				count = Integer.parseInt(this.count);
			pager.setCount(count);
			pager.setPageNo(pageNo);
			pager.setPageSize(pageSize);
		}
		if (pager == null)
			pager = (Pager) request.getAttribute(Pager.BEAN_PAGER);
		request.setAttribute(Pager.BEAN_PAGER, pager);
		int pageNo = pager.getPageNo();
		String href = this.calcRequestURL();

		request.setAttribute("portlet.tag.URLPattern", this.calcHref(href,
				"XXX", "" + pager.getPageSize()));

		if (pageNo > 0) {
			request.setAttribute("portlet.tag.prev", this.calcHref(href, pager
					.getPageNo() - 1, pager.getPageSize()));
			request.setAttribute("portlet.tag.first", this.calcHref(href, 0,
					pager.getPageSize()));

		}
		if ((pageNo < pager.getPageCount() - 1) && (pager.getPageCount() > 1)) {
			request.setAttribute("portlet.tag.last", this.calcHref(href, pager
					.getPageCount() - 1, pager.getPageSize()));
			request.setAttribute("portlet.tag.next", this.calcHref(href, pager
					.getPageNo() + 1, pager.getPageSize()));
		}
		request.setAttribute("portlet.tag.target", this.getTarget());
		if ((this.pattern == null) || (this.pattern.length() == 0))
			pattern = "default";
		String path = "/common/pager/" + pattern + ".jsp";
		try {
			pageContext.include(path, false);
		} catch (IOException ex) {
			throw new JspException(ex);
		} catch (ServletException ex) {
			throw new JspException(ex);
		}
		request.removeAttribute("portlet.tag.first");
		request.removeAttribute("portlet.tag.last");
		request.removeAttribute("portlet.tag.prev");
		request.removeAttribute("portlet.tag.next");
		request.removeAttribute("portlet.tag.URLPattern");
		return EVAL_PAGE;
	}

	private String calcHref(String href, long pageNo, long pageSize)
			throws JspException {
		return this.calcHref(href, "" + pageNo, "" + pageSize);
	}

	@SuppressWarnings("unchecked")
	private String calcHref(String href, String pageNo, String pageSize)
			throws JspException {
		HttpServletRequest request = (HttpServletRequest) this.pageContext
				.getRequest();
		Map<String, Object> parameters = new HashMap<String, Object>(request.getParameterMap());
		parameters.put("pageNo", "" + pageNo);
		parameters.put("pageSize", "" + pageSize);
		try {
			return TagUtils.getInstance().computeURLWithCharEncoding(
					pageContext, null, href, null, null, null, parameters,
					null, true, false);
		} catch (MalformedURLException ex) {
			throw new JspException(ex);
		}

	}

	private String calcRequestURL() {
		HttpServletRequest request = (HttpServletRequest) this.pageContext
				.getRequest();
		while (request instanceof HttpServletRequestWrapper) {
			request = (HttpServletRequest) ((HttpServletRequestWrapper) request)
					.getRequest();
		}
		return request.getRequestURI();
	}

	public void release() {
		super.release();
		this.pageNo = null;
		this.pageSize = null;
		this.count = null;
		this.name = null;
		this.property = null;
		this.scope = null;
	}

}
