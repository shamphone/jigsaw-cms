package com.fulong.portal.core;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.portlet.PortalContext;

/**
 * 
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
 * @author 李雄锋
 * @version 1.0
 */

public class LongconPortalContext implements PortalContext {
	private Properties properties;
	private String portalInfo;
	private List<String> supportedPortletModes;
	private List<String> supportedWindowStates;

	public LongconPortalContext() {
	}

	public String getProperty(String name) {
		return this.properties.getProperty(name);
	}

	public Enumeration<?> getPropertyNames() {
		return this.properties.propertyNames();
	}

	public Enumeration<String> getSupportedPortletModes() {
		return Collections.enumeration(this.supportedPortletModes);
	}

	public Enumeration<String> getSupportedWindowStates() {
		return Collections.enumeration(this.supportedWindowStates);
	}

	public String getPortalInfo() {
		return this.portalInfo;
	}

	public void setPortalInfo(String info) {
		this.portalInfo = info;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public void setPortletModeList(List<String> modes) {
		this.supportedPortletModes = modes;
	}

	public void setWindowStateList(List<String> states) {
		this.supportedWindowStates = states;
	}

}
