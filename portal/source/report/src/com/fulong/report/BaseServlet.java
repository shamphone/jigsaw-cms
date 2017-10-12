package com.fulong.report;

import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fulong.report.ReportManager;


/**
 * <p>Title: 龙驭网站管理系统</p>
 *
 * <p>Description: 龙驭网站管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author lixf
 * @version 1.0
 */
public class BaseServlet  extends com.fulong.common.BaseServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7608519302724277549L;
	protected String imagePath="/report-images";
    protected static String REPORT_MANAGER_BEAN_NAME="reportManager";
    protected ReportManager reports;
    public void init() throws ServletException {
    	super.init();
        this.reports = (ReportManager)(WebApplicationContextUtils.getWebApplicationContext(this.getServletContext()).getBean(REPORT_MANAGER_BEAN_NAME));

    }

  

    protected String getRelativePath(HttpServletRequest request) {

        // Are we being processed by a RequestDispatcher.include()?
        if (request.getAttribute("javax.servlet.include.request_uri") != null) {
            String result = (String)
                request.getAttribute(
                    "javax.servlet.include.path_info");
            if (result == null)
                result = (String)
                    request.getAttribute(
                        "javax.servlet.include.servlet_path");
            if ( (result == null) || (result.equals("")))
                result = "/";
            return (result);
        }

        String result = null;
        try {
            result = java.net.URLDecoder.decode(request.getRequestURI(),
                                                "UTF-8");
            String contextPath = java.net.URLDecoder.decode(request.
                getContextPath(),
                "UTF-8");
            if (contextPath != null)
                result = result.substring(contextPath.length());
        }
        catch (UnsupportedEncodingException ex) {
            result = request.getRequestURI();
            String contextPath = request.getContextPath();
            if (contextPath != null)
                result = result.substring(contextPath.length());
        }

        return (result);

    }

}
