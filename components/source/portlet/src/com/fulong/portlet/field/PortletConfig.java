package com.fulong.portlet.field;

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
 * @author lichengzhao
 * @version 3.1
 */
public class PortletConfig {
	private PortletPreferences preferences;

	public PortletConfig(PortletPreferences preferences) {
		this.preferences = preferences;
	}

	public String getCategoryID() {
		String id = this.preferences.getValue("category-id", "");
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

	public String getContentType() {
		String type = this.preferences.getValue("contentType", "");
		if (type.length() == 0) {
			return null;
		}
		return type;
	}

	public String getField() {
		String field = this.preferences.getValue("field", "");
		if (field.length() == 0) {
			return null;
		}
		return field;
	}
}
