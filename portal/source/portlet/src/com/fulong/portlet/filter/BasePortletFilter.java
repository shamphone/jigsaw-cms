/**
 * 
 */
package com.fulong.portlet.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fulong.longcon.repository.Repository;
import com.fulong.longcon.site.Channel;
import com.fulong.longcon.site.Site;
import com.fulong.longcon.site.SiteFactory;
import com.fulong.longcon.site.SiteTemplate;

/**
 * 
 * 
 * Coolink协同工作框架模型
 * 
 * Copyright: 2009-2011 北京中科辅龙计算机技术股份有限公司
 * 
 * Company: 北京中科辅龙计算机技术股份有限公司
 * 
 * @author lixf
 * 
 * @version 2.0
 * 
 */
public class BasePortletFilter implements Filter {
	protected SiteFactory siteFactory;

	protected Repository repository;

	public void setRepository(Repository repository) {
		this.repository = repository;
	}

	public void doHttpFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		chain.doFilter(request, response);
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		this.doHttpFilter((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse, chain);
	}

	/**
	 * 获取当前请求的网站
	 * 
	 * @param request
	 *            PortletRequest
	 * @param response
	 *            PortletResponse
	 * @return Site
	 * @throws Exception
	 */
	protected Site getCurrentSite(HttpServletRequest request, HttpServletResponse response) {
		Site site = (Site) request.getAttribute(Site.class.getName());
		if (site == null) {
			String siteId = request.getParameter("siteId");
			if (siteId == null) {
				siteId = request.getServerName();
			}
			site = this.getSiteFactory().getSite(siteId);
			if (site != null) {
				request.setAttribute(Site.class.getName(), site);
			}
		}
		return site;
	}

	/**
	 * 获取当前网站模板
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return SiteTemplate
	 * @throws Exception
	 */
	protected SiteTemplate getCurrentSiteTemplate(HttpServletRequest request, HttpServletResponse response) {
		SiteTemplate template = (SiteTemplate) request.getAttribute(SiteTemplate.class.getName());
		if (template == null) {
			HttpServletRequest req = (HttpServletRequest) request;
			String[] pathes = req.getServletPath().split("/");
			template = this.getSiteFactory().getTemplate(pathes[2]);
			if (template != null) {
				request.setAttribute(SiteTemplate.class.getName(), template);
			}
		}
		return template;
	}

	/**
	 * 获取当前栏目
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return Channel
	 * @throws Exception
	 */
	protected Channel getCurrentChannel(HttpServletRequest request, HttpServletResponse response) {
		Channel channel = (Channel) request.getAttribute(Channel.class.getName());
		if (channel == null) {
			HttpServletRequest req = (HttpServletRequest) request;
			if(req.getContextPath().split("/").length<1){
				return null;
			}
			String contextName = req.getContextPath().split("/")[1];
			if (this.getSiteFactory().getTemplate(contextName) == null) {
				return null;
			}			
			channel = this.getSiteFactory().getTemplate(contextName).getChannel(req.getServletPath());
			if (channel == null) {
				return null;
			}
			if (channel != null) {
				request.setAttribute(Channel.class.getName(), channel);
			}
		}
		return channel;
	}

	public SiteFactory getSiteFactory() {
		return siteFactory;
	}

	public void setSiteFactory(SiteFactory siteFactory) {
		this.siteFactory = siteFactory;
	}

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
