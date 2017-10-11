package com.fulong.portlet.site.vertical;

import javax.portlet.PortletPreferences;

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
 * @author Lixf
 * @version 1.0
 */
public class NavigatorConfig {
	private PortletPreferences preferences;
	private String[] channels;

	public NavigatorConfig(PortletPreferences preferences) {
		this.preferences = preferences;
		this.channels = this.preferences.getValues("channels", new String[0]);
	}

	public int getChannelCount() {
		return this.channels.length;
	}

	public String getChannelId(int index) {
		if (index < this.channels.length) {
			if ((channels[index] == null) || (channels[index].length() == 0)) {
				return null;
			}
			return channels[index];
		}
		return null;
	}

	public String getSeperator() {
		return this.preferences.getValue("seperator", "");
	}
}
