package com.fulong.portlet.site.channelNavigation;

import javax.portlet.PortletPreferences;

import com.fulong.portlet.Constants;

/**
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
 * @author liuzijun
 * @version 1.0
 */
public class ChannelNavigationConfig implements Constants {
	private PortletPreferences preferences;
	public ChannelNavigationConfig(PortletPreferences preferences) {
		this.preferences = preferences;
	}

	public boolean isChannel() {
		return "true".equalsIgnoreCase(this.preferences.getValue("channel", "true"));
	}

	public String getCategory() {
		String value = this.preferences.getValue("category", "");
		if ((value == null) || (value.length() == 0)) {
			return null;
		}
		return value;
	}

	public String getContentCategory() {
		String value = this.preferences.getValue("contentCategory", "");
		if ((value == null) || (value.length() == 0)) {
			return null;
		}
		return value;
	}

	public String getField() {
		String value = this.preferences.getValue("fields", "");
		if ((value == null) || (value.length() == 0)) {
			return null;
		}
		return value;
	}

}
