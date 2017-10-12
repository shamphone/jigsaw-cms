package com.fulong.portal.core;

import javax.portlet.PortletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.jsp.PageContext;

import com.fulong.portal.model.PortletContainer;
import com.fulong.portal.model.PortletWindow;

/**
 *
 * <p>Title: Longcon Portal</p>
 *
 * <p>Description: Longcon Portal Driver</p>
 *
 * <p>Copyright: Beijing Zhongke Fulong Computer Tech Co.LTD</p>
 *
 * <p>Company: Beijing Zhongke Fulong Computer Tech Co.LTD</p>
 *
 * @author Lixf
 * @version 1.0
 */


public class LongconPortletResponse extends HttpServletResponseWrapper implements
        PortletResponse {
    protected PortletContainer container;
    protected PortletWindow portletWindow;
    protected PageContext pageContext;

    public LongconPortletResponse(PageContext pageContext,
                                  PortletWindow portletWindow,
                                  PortletContainer container) {
        super((HttpServletResponse)pageContext.getResponse());
        this.portletWindow = portletWindow;
        this.pageContext=pageContext;
        this.container = container;
    }

    public void addProperty(String key, String value) {
        /**@todo Implement this javax.portlet.PortletResponse method*/
        throw new java.lang.UnsupportedOperationException(
                "Method addProperty() not yet implemented.");
    }

    public void setProperty(String key, String value) {
        /**@todo Implement this javax.portlet.PortletResponse method*/
        throw new java.lang.UnsupportedOperationException(
                "Method setProperty() not yet implemented.");
    }

}
