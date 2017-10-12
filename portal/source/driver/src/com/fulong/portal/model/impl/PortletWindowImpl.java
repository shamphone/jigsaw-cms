package com.fulong.portal.model.impl;

import com.fulong.portal.model.PortletDefinition;
import com.fulong.portal.model.PortletWindow;
import com.fulong.portal.model.Preference;
import com.fulong.portal.model.PreferenceSet;
import javax.portlet.Portlet;
import javax.portlet.PortletConfig;

/**
 * <p>
 * Title: Longcon Portal
 * </p>
 * 
 * <p>
 * Description: Longcon Portal Driver
 * </p>
 * 
 * <p>
 * Copyright: Beijing Zhongke Fulong Computer Tech Co.LTD
 * </p>
 * 
 * <p>
 * Company: Beijing Zhongke Fulong Computer Tech Co.LTD
 * </p>
 * 
 * @author Lixf
 * @version 1.0
 */
public class PortletWindowImpl implements PortletWindow {
	private String title;
	private String portletId;
	private String description;
	private PreferenceSet preferences;
	private PortletDefinition definition;
	private Portlet portlet;
	private PortletConfig config;

	public PortletWindowImpl(String id, Portlet portlet, PortletConfig config, PortletDefinition entity) {
		this.portletId = id;
		this.definition = entity;
		this.preferences = new PreferenceSet();
		this.portlet = portlet;
		this.config = config;
	}

	public String getId() {
		return this.portletId;
	}

	public String getType() {
		return this.definition.getName();
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public PreferenceSet getTemplatePreferenceSet() {

		return preferences;
	}

	public void setTemplatePreferenceSet(PreferenceSet set) {
		this.preferences = set;
	}

	public PortletDefinition getDefinition() {
		return this.definition;
	}

	public PreferenceSet getDefinedPreferenceSet() {

		return this.getDefinition().getPreferenceSet();
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void addTemplatePreference(Preference preference) {
		this.preferences.put(preference);
	}

	public String toString() {
		return this.toHTML();
	}

	public String toHTML() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<fulong:portlet id=\"" + this.portletId + "\" type=\""
				+ this.definition.getName() + "\">");
		buffer.append(this.preferences.toHTML());
		buffer.append("</fulong:portlet>");
		return buffer.toString();
	}

	public String toXML() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<fulong:portlet id=\"" + this.portletId + "\" type=\""
				+ this.definition.getName() + "\">");
		buffer.append(this.preferences.toXML());
		buffer.append("</fulong:portlet>");
		return buffer.toString();
	}

	public Portlet getPortlet() {
		return this.portlet;
	}

	public PortletConfig getPortletConfig() {
		return this.config;
	}

}
