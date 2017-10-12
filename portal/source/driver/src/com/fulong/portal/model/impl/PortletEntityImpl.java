package com.fulong.portal.model.impl;

import java.util.Iterator;

import com.fulong.portal.model.PortletEntity;
import com.fulong.portal.model.Preference;
import com.fulong.portal.model.PreferenceSet;

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
public class PortletEntityImpl implements PortletEntity {
	private PreferenceSet preferences;
	private String id;
	private String type;

	public PortletEntityImpl() {
		this.preferences = new PreferenceSet();
	}

	public String getId() {
		return id;
	}

	public PreferenceSet getPreferenceSet() {
		return preferences;
	}

	@SuppressWarnings("unchecked")
	public Iterator getPreferences() {
		return this.preferences.getPreferences();
	}

	public String getType() {
		return type;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void addPreference(Preference preference) {
		this.preferences.put(preference);
	}

	public void setType(String type) {
		this.type = type;
	}

}
