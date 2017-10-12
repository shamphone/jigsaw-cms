package com.fulong.common.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

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
 * @author jiangqi
 *
 * @version 1.0
 */

public class FileFilter implements Filter {
    protected String[] patterns = null;
    private PathMatcher resolver;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.resolver=new AntPathMatcher();
        this.patterns = filterConfig.getInitParameter("patterns").split(";");
    }

    public void doFilter(ServletRequest req, ServletResponse resp,
                         FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) req;
        if (!this.isFilterPath(httpReq.getRequestURI().toLowerCase())) {
            chain.doFilter(req, resp);
            return;
        }else{
            resp.getWriter().println("access error");
        }

    }

    private boolean isFilterPath(String path) {
        for (int i = 0; i < this.patterns.length; i++) {
            if (this.patterns[i].length() > 0) {
                if (resolver.match(this.patterns[i], path)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void destroy() {
        System.gc();
    }


}
