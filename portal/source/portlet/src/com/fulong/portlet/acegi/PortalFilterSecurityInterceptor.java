/**
 * 
 */
package com.fulong.portlet.acegi;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.security.intercept.web.FilterSecurityInterceptor;

/**
 * 
 * <p>
 * Title: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Description: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术股份有限公司 2006
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * @author lixf
 * 
 * @version 2.0
 */
public class PortalFilterSecurityInterceptor extends FilterSecurityInterceptor {
	


	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain chain) throws IOException, ServletException {
	
		super.doFilter(servletRequest, servletResponse, chain);
	}
}
