package com.fulong.portal.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fulong.portal.core.Constants;
import com.fulong.portal.model.PortletContainer;

/**
 * 门户引擎，这是基于Filter实现的引擎。过滤器实现如下功能：
 * 1. 在系统启动时，根据配置文件中指定的文件地址来加载所有占位符。JSR168规定所有占位符需要在portlet.xml中配置。由于本系统的占位符数量较多，所以对每个占位符可以分别配置，并在Web.xml文件中对过滤器配置指定文件地址。
 * 2. 对用户请求进行拦截，将请求(HttpServletRequest)和响应(HttpServletResponse)封装成门户的请求（PortalServletRequest）和响应（PortalActionResponse）。
 * 3. 系统关闭时，卸载占位符。
 * 未实现功能：
 * 1.　对最小化状态的处理。
 *
 * <p>Title: 龙驭门户引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author Lixf
 * @version 1.0
 */
public class PortalFilter implements Filter {
//    private static String contentType = "text/html;charset=UTF-8";
    public final static String PAGE_MODE = "javax.portlet.page.mode";
    private String encoding = "UTF-8";
    private PortletContainer container;
    private ServletContext servletContext;
    private static Log log = LogFactory.getLog(PortalFilter.class);
    private String[] patterns;
    private PathMatcher resolver;
    /**
    * Called by the web container to indicate to a filter that it is being placed into service.
    *
    * @param filterConfig FilterConfig
    * @throws ServletException
    */
   public void init(FilterConfig filterConfig) throws ServletException {
       String encoding = filterConfig.getInitParameter("encoding");
       if (encoding != null)
           this.encoding = encoding;
       String strPatterns =  filterConfig.getInitParameter("patterns");
       if(strPatterns!=null)
    	   this.patterns = filterConfig.getInitParameter("patterns").split(";");
       this.resolver = new AntPathMatcher();
       this.servletContext = filterConfig.getServletContext();
       //load portlet container;
       this.container = (PortletContainer)(WebApplicationContextUtils.getWebApplicationContext(this.getServletContext()).getBean("portletContainer"));
       String file = filterConfig.getInitParameter("portlet-configs");
       if(file!=null)
    	   container.registerPortlets(servletContext, file);
       servletContext.setAttribute(Constants.PORTLET_CONTAINER,
                                   this.container);
       log("Longcon portal driver ready!");
   }
   /**
     * Called by the web container to indicate to a filter that it is being taken out of service.
     *
     */
    public void destroy() {
        log("Shutting down portlet container. . .");
        this.container.shutdown();
        this.getServletContext().removeAttribute(Constants.PORTLET_CONTAINER);
        System.gc();
    }

    private ServletContext getServletContext() {
        return this.servletContext;
    }

    /**
     *　将用户请求进行预处理，转换成门户的请求和响应。
     * @param request ServletRequest
     * @param response ServletResponse
     * @param chain FilterChain
     * @throws IOException
     * @throws ServletException
     */
    public void doFilter(ServletRequest req, ServletResponse resp,
                         FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) req;
        if (!this.isFilterPath(httpReq.getRequestURI().toLowerCase())) {
            chain.doFilter(req, resp);
            return;
        }

        if (req.getCharacterEncoding() == null)
            req.setCharacterEncoding(this.encoding);
        PortalServletRequest request = new PortalServletRequest((
                HttpServletRequest) req);
        String jsp = request.getAggregationPath();
        req.setAttribute(Constants.LONGCON_PAGE, jsp);
            if (request.isAction()) {
                resp = new PortalActionResponse((HttpServletResponse) resp);
                chain.doFilter(request, resp);
            } else {
                chain.doFilter(request, resp);
            }
        request.removeAttribute(Constants.LONGCON_PAGE);
    }

    private boolean isFilterPath(String path) {
        for (int i = 0; i < this.patterns.length; i++) {
            if (this.patterns[i].length() > 0) {
                if (resolver.match(this.patterns[i], path))
                    return true;
            }
        }
        return false;
    }


    /**
     * 复写日志实现类,支持调试
     * @param msg String
     */
    public void log(String msg) {

        log.info(msg);
    }

    /**
     * 复写日志实现类,支持调试
     * @param msg String
     */
    public void log(String msg, Throwable t) {

        log.error(msg, t);
    }
}
