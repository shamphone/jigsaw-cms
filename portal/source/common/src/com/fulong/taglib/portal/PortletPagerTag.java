package com.fulong.taglib.portal;

import java.io.IOException;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.taglib.TagUtils;

import com.fulong.common.util.Pager;
import com.fulong.portal.core.ServletResponseWrapperInclude;
import com.fulong.portlet.Constants;

/**
 * 分页， 由name、property、scope确定一个com.fulong.common.util.Pager对象 由pattern确定分页模式
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
public class PortletPagerTag extends TagSupport {

	private static final long serialVersionUID = 1803220598745398481L;

	private String pattern;
	private String name;
	private String property;
	private String scope;
	private String isLink;

	public PortletPagerTag() {
	}

	public int doEndTag() throws JspException {
		PortletRequest renderRequest = (PortletRequest) pageContext.getRequest().getAttribute(Constants.PORTLET_REQUEST);
		if (renderRequest == null) {
			renderRequest = (PortletRequest) pageContext.getRequest();
		}
		
		RenderResponse renderResponse = (RenderResponse) pageContext.getRequest().getAttribute(Constants.PORTLET_RESPONSE);
		if (renderResponse == null) {
			renderResponse = (RenderResponse) pageContext.getResponse();
		}
		
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest().getAttribute(Constants.PORTLET_REQUEST);
		if (request == null) {
			request = (HttpServletRequest) pageContext.getRequest();
		}
		
		Pager pager = (Pager) TagUtils.getInstance().lookup(pageContext, name, property, scope);
		renderRequest.setAttribute("portlet.tag.pager", pager);
		int pageNo = pager.getPageNo();
		long pageCount = pager.getPageCount();
		PortletURL gotoPage = renderResponse.createRenderURL();
		gotoPage.setParameter("pageNo", "null");
		renderRequest.setAttribute("portlet.tag.URLPattern", gotoPage.toString());
		
		if (pageNo > 0) {
			PortletURL prev = renderResponse.createRenderURL();
			prev.setParameter("pageNo", "null");
			prev.setParameter("pageNo", "" + (pageNo - 1));
			renderRequest.setAttribute("portlet.tag.prev", prev.toString());
			PortletURL first = renderResponse.createRenderURL();
			first.setParameter("pageNo", "null");
			first.setParameter("pageNo", "0");
			renderRequest.setAttribute("portlet.tag.first", first.toString());
		}
		
		if ((pageNo < pager.getPageCount() - 1) && (pager.getPageCount() > 1)) {
			PortletURL last = renderResponse.createRenderURL();
			last.setParameter("pageNo", "null");
			last.setParameter("pageNo", "" + (((int) pageCount) - 1));
			renderRequest.setAttribute("portlet.tag.last", last.toString());
			PortletURL next = renderResponse.createRenderURL();
			next.setParameter("pageNo", "null");
			next.setParameter("pageNo", "" + (pageNo + 1));
			renderRequest.setAttribute("portlet.tag.next", next.toString());
		}
		
		if ((pattern == null) || pattern.length() == 0) {
			pattern = "default";
		}
		
		String path = "";
		if (isLink != null && isLink.equals("false")) {
			path = "/" + "pager.nolink.jsp";
		} else {
			path = "/" + "pager." + pattern + ".jsp";
		}
		
		try {
			ServletContext context = pageContext.getServletContext().getContext("/portal");
			RequestDispatcher dispatcher = context.getRequestDispatcher(path);
			dispatcher.include(pageContext.getRequest(), new ServletResponseWrapperInclude(pageContext.getResponse(), pageContext.getOut()));
		} catch (IOException ex) {
			throw new JspException(ex);
		} catch (ServletException ex) {
			throw new JspException(ex);
		}
		
		renderRequest.removeAttribute("portlet.tag.first");
		renderRequest.removeAttribute("portlet.tag.last");
		renderRequest.removeAttribute("portlet.tag.pager");
		renderRequest.removeAttribute("portlet.tag.prev");
		renderRequest.removeAttribute("portlet.tag.next");
		renderRequest.removeAttribute("portlet.tag.URLPattern");
		
		return EVAL_PAGE;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
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

	public void setIsLink(String isLink) {
		this.isLink = isLink;
	}

	public String getPattern() {
		return pattern;
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

	public String getIsLink() {
		return isLink;
	}

	public void release() {
		super.release();
		this.name = null;
		this.scope = null;
		this.property = null;
		this.pattern = null;
		this.isLink = null;

	}
}
