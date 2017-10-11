package com.fulong.portlet.button.submit;

import javax.portlet.PortletPreferences;

/**
 * 内容域占位符参数解析,参数说明参见ContentField类
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
 * @author Lixf
 * @version 1.0
 */
public class PortletConfig {
	private PortletPreferences preferences;

	public PortletConfig(PortletPreferences preferences) {
		this.preferences = preferences;
	}

	public String getType() {
		return this.preferences.getValue("type", null);
	}

	public String getCategoryID() {
		String id = this.preferences.getValue("category", "");
		if (id.length() == 0) {
			return null;
		}
		return id;
	}

	public String getContentID() {
		String id = this.preferences.getValue("content-id", "");
		if (id.length() == 0) {
			return null;
		}
		return id;
	}

	public String[] getDefaultValues() {
		return this.preferences.getValues("default-values", new String[0]);
	}

	public boolean isCreate() {
		return "true".equalsIgnoreCase(this.preferences.getValue("create", "true"));
	}
	
	public boolean isCreateFixNode() {
		return "true".equalsIgnoreCase(this.preferences.getValue("createFixNode", "true"));
	}

}
