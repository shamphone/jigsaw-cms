package com.fulong.common.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * <p>Title: WebMaster Core Library</p>
 *
 * <p>Description: Longcon WebMaster SV3</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Beijing Zhongke Fulong Computer Tech. LTD</p>
 *
 * @author Lixf
 *
 * @version 1.0
 */

public class CharacterEncodingFilter
    implements Filter {
  protected String encoding = null;
  protected FilterConfig filterConfig = null;
    private static Log log = LogFactory.getLog(CharacterEncodingFilter.class);

  public void init(FilterConfig filterConfig) throws ServletException {
    this.filterConfig = filterConfig;
    this.encoding = filterConfig.getInitParameter("encoding");
    log.trace("Init Character Encoding filter for "+this.encoding+" successfully.");
  }

  public void doFilter(ServletRequest req, ServletResponse resp,
                       FilterChain chain) throws IOException,
      ServletException {
	  HttpServletRequest request = (HttpServletRequest)req;
    if ( (request.getCharacterEncoding() == null)) {
      String encoding = selectEncoding(request);
      if (encoding != null) {
        request.setCharacterEncoding(encoding);
      }
    }
    
    //添加对第三方Cookie的支持
    HttpServletResponse response = (HttpServletResponse)resp;
    response.setHeader("P3P","CP=CAO PSA OUR");
    //将Context Path放在Cookie中以便客户端脚本处理
    Cookie cookie = new Cookie("contextPath", request.getContextPath());
    cookie.setMaxAge(-1);
    cookie.setPath("/");
    response.addCookie(cookie);
    chain.doFilter(request, response);
  }

  public void destroy() {
    this.encoding = null;
    this.filterConfig = null;
  }

  protected String selectEncoding(ServletRequest request) {
    return (this.encoding);
  }

}
