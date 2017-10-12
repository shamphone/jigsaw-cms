/**
 * 
 */
package com.fulong.portlet.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
public class SiteLifeCircleFilter extends BasePortletFilter {
	
	public void doHttpFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		/*
		 * Site site = this.getCurrentSite(request, response);
		// 处理已关闭的网站
		
		if (site == null || site.getState().equals(State.STOP)) {
			RequestDispatcher dispatcher = request.getRequestDispatcher(this.siteStopPrompt);
			dispatcher.forward(request, response);
			return;
		}
		*/
		chain.doFilter(request, response);
	}

	public void setSiteStopPrompt(String siteStopPrompt) {
	}
}
