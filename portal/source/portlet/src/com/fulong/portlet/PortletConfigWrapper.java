package com.fulong.portlet;

import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.portlet.PortletConfig;

/**
 * <p>
 * Title: 龙驭门户引擎
 * </p>
 * 
 * <p>
 * Description: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * @author Lixf
 * @version 1.0
 */
public class PortletConfigWrapper implements ServletConfig {
	private PortletConfig config;
	private ServletContext context;

	public PortletConfigWrapper(PortletConfig config) {
		this.config = config;
		this.context = new PortletContextWrapper(config.getPortletContext());
	}

	/**
	 * Returns a <code>String</code> containing the value of the named
	 * initialization parameter, or <code>null</code> if the parameter does not
	 * exist.
	 * 
	 * @param name
	 *            a <code>String</code> specifying the name of the
	 *            initialization parameter
	 * @return a <code>String</code> containing the value of the initialization
	 *         parameter
	 */
	public String getInitParameter(String name) {
		return config.getInitParameter(name);
	}

	/**
	 * Returns the names of the servlet's initialization parameters as an
	 * <code>Enumeration</code> of <code>String</code> objects, or an empty
	 * <code>Enumeration</code> if the servlet has no initialization parameters.
	 * 
	 * @return an <code>Enumeration</code> of <code>String</code> objects
	 *         containing the names of the servlet's initialization parameters
	 */
	@SuppressWarnings("unchecked")
	public Enumeration getInitParameterNames() {
		return this.config.getInitParameterNames();
	}

	/**
	 * Returns a reference to the {@link ServletContext} in which the caller is
	 * executing.
	 * 
	 * @return a {@link ServletContext} object, used by the caller to interact
	 *         with its servlet container
	 */
	public ServletContext getServletContext() {
		return this.context;
	}

	/**
	 * Returns the name of this servlet instance.
	 * 
	 * @return the name of the servlet instance
	 */
	public String getServletName() {
		return this.config.getPortletName();
	}
}
