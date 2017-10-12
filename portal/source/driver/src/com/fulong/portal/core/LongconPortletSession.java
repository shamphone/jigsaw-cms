 package com.fulong.portal.core;

import java.util.Enumeration;

import javax.portlet.PortletContext;
import javax.portlet.PortletSession;
import javax.servlet.http.HttpSession;
import javax.portlet.PortletConfig;
 /**
  *
  * <p>Title: Longcon Portal Driver</p>
  *
  * <p>Description: Longcon WebMaster</p>
  *
  * <p>Copyright: Copyright (c) 2005</p>
  *
  * <p>Company: Beijing Zhongke Fulong Computer Technology LTD.</p>
  *
  * @author Lixf
  * @version 1.0
  */

public class LongconPortletSession implements PortletSession,HttpSession {
    private final static int DEFAULT_SCOPE = PortletSession.PORTLET_SCOPE;

    private HttpSession httpSession;
    private PortletContext portletContext = null;
    private PortletConfig portletConig=null;
    public LongconPortletSession(PortletConfig portletConig,HttpSession httpSession) {
        this.httpSession = httpSession;
        this.portletConig=portletConig;
        this.portletContext=portletConig.getPortletContext();
    }

    public Object getAttribute(String name) {
        return this.getAttribute(name, DEFAULT_SCOPE);
    }

    public Enumeration<String> getAttributeNames() {
        return this.getAttributeNames(DEFAULT_SCOPE);
    }

    public long getCreationTime() throws IllegalStateException {
        return httpSession.getCreationTime();
    }

    public String getId() throws IllegalStateException {
        return httpSession.getId();
    }

    public long getLastAccessedTime() throws IllegalStateException {
        return httpSession.getLastAccessedTime();
    }

    public int getMaxInactiveInterval() {
        return httpSession.getMaxInactiveInterval();
    }

    public void invalidate() throws IllegalStateException {
        httpSession.invalidate();
    }

    public boolean isNew() throws IllegalStateException {
        return httpSession.isNew();
    }

    public void removeAttribute(String name) {
        this.removeAttribute(name, DEFAULT_SCOPE);
    }

    public void setAttribute(String name, Object value) {
        this.setAttribute(name, value, DEFAULT_SCOPE);
    }

    public void setMaxInactiveInterval(int interval) {
        httpSession.setMaxInactiveInterval(interval);
    }
    /**
     * 将全局属性名转为局部属性名
     * @param name
     * @return
     */
    private String makePortletAttributeName(String name){
    	return "javax.portlet.p." + this.portletConig.getPortletName() + name;
    }

    // --------------------------------------------------------------------------------------------

    // javax.portlet.PortletSession implementation ------------------------------------------------
    public Object getAttribute(String name, int scope) throws java.
            lang.IllegalStateException {
        if (name == null) {
            throw new IllegalArgumentException("name must not be null");
        }
        if (scope == PortletSession.APPLICATION_SCOPE) {
            return httpSession.getAttribute(name);
        } else {
            Object attribute = httpSession.getAttribute(this.makePortletAttributeName(name));
            if (attribute == null) {
                // not sure, if this should be done for all attributes or only javax.servlet.
                attribute = httpSession.getAttribute(name);
            }
            return attribute;
        }
    }

    @SuppressWarnings("unchecked")
	public Enumeration<String> getAttributeNames(int scope) {
            return httpSession.getAttributeNames();

    }

    public void removeAttribute(String name, int scope) throws java.lang.
            IllegalStateException {
        if (name == null) {
            throw new IllegalArgumentException("name must not be null");
        }
        if (scope == PortletSession.APPLICATION_SCOPE) {
            httpSession.removeAttribute(name);
        } else {
            httpSession.removeAttribute(this.makePortletAttributeName(name));
        }
    }

    public void setAttribute(String name, Object value,
                             int scope) throws IllegalStateException {
        if (name == null) {
            throw new IllegalArgumentException("name must not be null");
        }
        if (scope == PortletSession.APPLICATION_SCOPE) {
            httpSession.setAttribute(name, value);
        } else {
            httpSession.setAttribute(this.makePortletAttributeName(name), value);
        }
    }

    public PortletContext getPortletContext() {
        return getInternalPortletContext();
    }

    // --------------------------------------------------------------------------------------------

    // javax.servlet.http.HttpSession implementation ----------------------------------------------
    public javax.servlet.ServletContext getServletContext() {
        // TBD, open issue. it would be good if we could also implement the ServletContext interface at the PortletContextImpl
        return httpSession.getServletContext();
    }

    @SuppressWarnings("deprecation")
	public javax.servlet.http.HttpSessionContext getSessionContext() {
        return httpSession.getSessionContext();
    }

    public Object getValue(String name) {
        return this.getAttribute(name, DEFAULT_SCOPE);
    }

    public String[] getValueNames() {
        // TBD
        return null;
    }

    public void putValue(String name, Object value) {
        this.setAttribute(name, value, DEFAULT_SCOPE);
    }

    public void removeValue(String name) {
        this.removeAttribute(name, DEFAULT_SCOPE);
    }

    // --------------------------------------------------------------------------------------------

    // internal methods ---------------------------------------------------------------------------
    private synchronized PortletContext getInternalPortletContext() {
        if (this.portletContext == null) {
            throw new UnsupportedOperationException();
        }
        return this.portletContext;
    }
}
