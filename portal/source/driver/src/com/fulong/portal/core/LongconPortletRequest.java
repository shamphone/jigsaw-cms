package com.fulong.portal.core;

import java.util.Enumeration;
import java.util.Vector;

import javax.portlet.PortalContext;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import javax.portlet.WindowState;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

import com.fulong.portal.model.PortletContainer;
import com.fulong.portal.model.PortletWindow;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

/**
 *
 * <p>Title: 龙驭门户引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author 李雄锋
 * @version 1.0
 */
public abstract class LongconPortletRequest extends HttpServletRequestWrapper implements
        PortletRequest {
    protected PortletSession portletSession;
    protected PortletWindow portletWindow;
    protected PortletPreferences preferences;
    protected PageContext pageContext;
    protected PortletContainer container;
    private Map<String, Object> attributes;
    public LongconPortletRequest(PageContext pageContext,
                                 PortletWindow portletWindow,
                                 PortletContainer container) {
        super((HttpServletRequest) pageContext.getRequest());
        this.portletSession = null;
        this.portletWindow = portletWindow;
        this.preferences = new LongconPortletPreferences(portletWindow);
        this.pageContext = pageContext;
        this.container = container;
        this.attributes = new HashMap<String, Object>();
    }

    /**
     * 获取所在页面的PageContext；
     * @return PageContext
     */
    public PageContext getPageContext() {
        return this.pageContext;
    }

    /**
     * 获取PortletSession；
     * @return PortletSession
     */
    @Override
    public PortletSession getPortletSession() {
        return getPortletSession(true);
    }

    /**
     *
     * @param create boolean 如果不存在，则创建
     * @return PortletSession
     */
    @Override
    public PortletSession getPortletSession(boolean create) {
    	    	
    	if(portletSession!=null)
    		return portletSession;
    	
        HttpSession httpSession = this.getHttpRequest().getSession(false);
        //如果httpSession不存在而portletSession存在，则发生异常，把portletSession清空；
        if ((portletSession != null) && (httpSession == null)) {
            portletSession = null;
        } else if (httpSession != null) {
            create = true;
        }

        if (create && portletSession == null) {
            httpSession = this.getHttpRequest().getSession(create);
            if (httpSession != null) {
                portletSession = new LongconPortletSession(
                        this.portletWindow.getPortletConfig(),
                        httpSession);
            }
        }

        return portletSession;
    }

    /**
     *
     * @param state WindowState
     * @return boolean
     */
    @Override
    public boolean isWindowStateAllowed(WindowState state) {
        /**@todo Implement this javax.portlet.PortletRequest method*/
        throw new java.lang.UnsupportedOperationException(
                "Method isWindowStateAllowed() not yet implemented.");
    }

    /**
     *
     * @param mode PortletMode
     * @return boolean
     */
    @Override
    public boolean isPortletModeAllowed(PortletMode mode) {
        /**@todo Implement this javax.portlet.PortletRequest method*/
        throw new java.lang.UnsupportedOperationException(
                "Method isPortletModeAllowed() not yet implemented.");
    }

    /**
     * 获取当前占位符模式，缺省模式为final；
     * @return PortletMode
     */
    @Override
    public PortletMode getPortletMode() {
        String mode = this.getParameter(Constants.REQUEST_PORTLET_MODE);
        if (mode == null)
            mode = this.getParameter(Constants.REQUEST_PAGE_MODE);
        if (mode == null)
            return Constants.PORTLET_MODE_FINAL;
        else
            return new PortletMode(mode);
    }

    /**
     * 获取当前窗口模式，缺省模式为normal;
     * @return WindowState
     */
    @Override
    public WindowState getWindowState() {
        String state = this.getParameter(Constants.REQUEST_WINDOW_STATE);
        if (state == null)
            return WindowState.NORMAL;
        else
            return new WindowState(state);
    }

    /**
     *
     * @return PortletPreferences
     */
    @Override
    public PortletPreferences getPreferences() {
        return this.preferences;
    }

    /**
     *
     * @return PortalContext
     */
    @Override 
    public PortalContext getPortalContext() {
        return this.container.getPortalContext();
    }

    /**
     *
     * @return String
     */
    public String getResponseContentType() {
        return this.pageContext.getResponse().getContentType();

    }

    /**
     *
     * @return HttpServletRequest
     */
    public HttpServletRequest getHttpRequest() {
        return (HttpServletRequest)this.getRequest();
    }

    /**
     *
     * @param name String
     * @return Object
     */
    public Object getAttribute(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Attribute name == null");
        }

        Object attribute = this.attributes.get(name);
        if (attribute == null) {
            attribute = this.getRequest().getAttribute(name);
        }

        return attribute;

    }

    /**
     *
     * @param name String
     * @param obj Object
     */
    public void setAttribute(String name, Object obj) {
        if (name == null) {
            throw new IllegalArgumentException("Attribute name == null.");
        }
        if (obj == null) {
            this.attributes.remove(name);
            return;
        }
        this.attributes.put(name, obj);
    }

    @SuppressWarnings("unchecked")
	public Enumeration<String> getAttributeNames() {
        Vector<String> portletAttributes = new Vector<String>();

        portletAttributes.addAll(this.attributes.keySet());

        portletAttributes.addAll(Collections.list(this.getRequest().getAttributeNames()));

        return portletAttributes.elements();
    }

    public void removeAttribute(String name) {
        this.attributes.remove(name);
    }

    public Enumeration<String> getResponseContentTypes() {
        throw new java.lang.UnsupportedOperationException("Method getResponseContentTypes() not yet implemented.");
    }

    /**
     *
     * @param name String
     * @return String
     */
    public String getProperty(String name) {
        return this.getHttpRequest().getHeader(name);
    }

    /**
     *
     * @param name String
     * @return Enumeration
     */
    @SuppressWarnings("unchecked")
	public Enumeration<String> getProperties(String name) {
        return this.getHttpRequest().getHeaders(name);
    }

    /**
     *
     * @return Enumeration
     */
    @SuppressWarnings("unchecked")
	public Enumeration<String> getPropertyNames() {
        return this.getHttpRequest().getHeaderNames();
    }

	@Override 
	public HttpSession getSession() {
		 return (HttpSession)this.getPortletSession();
	}

	@Override
	public HttpSession getSession(boolean create) {
		return (HttpSession)this.getPortletSession(create);
	}
    
    


}
