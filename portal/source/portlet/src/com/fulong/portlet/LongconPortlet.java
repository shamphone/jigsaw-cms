package com.fulong.portlet;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.config.ModuleConfig;

/**
 *　对Portlet做封装，使其支持Apache Struts。
 * <p>
 * Title: WebMaster Core Library
 * </p>
 * 
 * <p>
 * Description: Longcon WebMaster SV3
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Beijing Zhongke Fulong Computer Tech. LTD
 * </p>
 * 
 * @author Lixf,ligang
 * @version 1.0
 */
public abstract class LongconPortlet extends ActionServlet implements Portlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5887512416288326327L;
	private ModuleConfig moduleConfig;
	private ServletContext wrappedPortletContext;
	private ServletConfig wrappedPortletConfig;
	protected PortletConfig portletConfig;
	// protected HashMap actions = new HashMap();
	protected PortletRequestProcessor processor = null;
	protected Log log = LogFactory.getLog(this.getClass());

	public LongconPortlet() {
	}

	/**
	 * 整体初始化
	 */
	public void init(PortletConfig portletConfig) throws PortletException {
		this.portletConfig = portletConfig;
		this.wrappedPortletConfig = new PortletConfigWrapper(portletConfig);
		this.wrappedPortletContext = this.wrappedPortletConfig
				.getServletContext();
		this.config = portletConfig.getInitParameter("config-file");
		try {
			init();
			moduleConfig = (ModuleConfig) getServletContext().getAttribute(
					Globals.MODULE_KEY);
			initRequestProcessor(moduleConfig);
		} catch (ServletException ex) {
			throw new PortletServletException(ex);
		}
		initPortlet();
		portletConfig.getPortletContext().log(
				portletConfig.getPortletName() + " ready to server!");
	}

	/**
	 * 初始化Processor
	 * 
	 * @param config
	 * @return
	 * @throws PortletException
	 */
	protected synchronized PortletRequestProcessor initRequestProcessor(
			ModuleConfig config) throws PortletException {

		if (processor == null) {
			try {
				String className = PortletRequestProcessor.class.getName();
				processor = (PortletRequestProcessor) this.getClass()
						.getClassLoader().loadClass(className).newInstance();
				processor.init(this, config);
			} catch (Exception e) {
				throw new PortletException(
						"Cannot initialize RequestProcessor of class "
								+ config.getControllerConfig()
										.getProcessorClass() + ": " + e);
			}

			String key = Globals.REQUEST_PROCESSOR_KEY + config.getPrefix();

			getServletContext().setAttribute(key, processor);
		}

		return (processor);
	}

	/**
	 * 覆盖父类的实现，不做任何操作
	 * 
	 * @throws ServletException
	 */
	protected void initServlet() throws ServletException {

	}

	/**
	 * 为子类提供初始化入口
	 * 
	 * @throws PortletException
	 */
	protected void initPortlet() throws PortletException {

	}

	/**
	 * 禁止使用这个方法
	 * 
	 * @throws ServletException
	 */
	public final void init() throws ServletException {
		super.init();
	}

	public void processAction(ActionRequest request, ActionResponse response)
			throws PortletException, java.io.IOException {
		request.setAttribute(Globals.MODULE_KEY, this.moduleConfig);
		processor.action(request, response);
		request.removeAttribute(Globals.MODULE_KEY);

	}

	public void render(RenderRequest request, RenderResponse response)
			throws PortletException, IOException {
		request.setAttribute(Globals.MODULE_KEY, this.moduleConfig);
		processor.render(request, response);
		request.removeAttribute(Globals.MODULE_KEY);
	}

	/**
	 * 复写日志实现类,支持调试
	 * 
	 * @param msg
	 *            String
	 */
	public void log(String msg) {
		this.portletConfig.getPortletContext().log(msg);
		this.log.info(msg);
	}

	/**
	 * 复写日志实现类,支持调试
	 * 
	 * @param msg
	 *            String
	 */
	public void log(String msg, Throwable t) {
		this.portletConfig.getPortletContext().log(msg, t);
		this.log.error(msg, t);
	}

	protected PortletContext getPortletContext() {
		return this.portletConfig.getPortletContext();
	}

	protected PortletConfig getPortletConfig() {
		return this.portletConfig;
	}

	public ServletConfig getServletConfig() {
		return this.wrappedPortletConfig;
	}

	public ServletContext getServletContext() {
		return this.wrappedPortletContext;
	}

	public String getServletName() {
		return this.portletConfig.getPortletName();
	}
}
