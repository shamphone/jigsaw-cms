package com.fulong.portal.model.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

import com.fulong.portal.model.ParameterConfig;
import com.fulong.portal.model.PortletDefinition;
import com.fulong.portal.model.Preference;
import com.fulong.portal.model.PreferenceSet;
import com.fulong.portal.model.SecurityRoleRefConfig;
import com.fulong.portal.utils.Enumerator;

import org.apache.struts.util.MessageResourcesFactory;
import org.apache.struts.util.MessageResources;

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
 * @author 李雄锋
 * @version 1.0
 */
public class PortletDefinitionImpl implements Serializable, PortletDefinition {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6483218834431752555L;
	private String id;
	private String className;
	private String expirationCache;
	private String name;
	private Map<String, String> descriptions;
	private Collection<Locale> languages;
	@SuppressWarnings("unchecked")
	private Map initParameters;
	private Collection<SecurityRoleRefConfig> roleRefs;
	private PreferenceSet preferences;
	private Map<String, String> displayNames;
	private String resourceBundle;
	private MessageResources messages;

	public PortletDefinitionImpl() {
		descriptions = new Hashtable<String, String>();
		languages = new Vector<Locale>();
		initParameters = new Hashtable<Object, ParameterConfig>();
		roleRefs = new Vector<SecurityRoleRefConfig>();
		preferences = new PreferenceSet();
		displayNames = new Hashtable<String, String>();

	}

	/**
	 * Returns the identifier of this portlet as object id. The return value
	 * cannot be NULL.
	 * 
	 * @return the object identifier
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Returns the class name
	 * 
	 * @return the class name
	 */
	public String getClassName() {
		return this.className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * Returns the administrative name The return value cannot be NULL.
	 * 
	 * @return the administrative name
	 */
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String formatLanguage(String language) {
		Locale locale = Locale.getDefault();
		if (language != null)
			locale = new Locale(language);
		return locale.getLanguage();
	}

	/**
	 * Returns the description for the given locale The return value may be
	 * NULL.
	 * 
	 * @return the description
	 */
	public void addDescription(String lang, String description) {
		this.descriptions.put(this.formatLanguage(lang), description);
	}

	public String getDescription(Locale locale) {
		return (String) this.descriptions.get(locale.getLanguage());
	}

	public String getDescription(String language) {
		return (String) this.descriptions.get(this.formatLanguage(language));
	}

	/**
	 * Returns all resource information of this portlet
	 * 
	 * @return the portlet resources
	 */
	@SuppressWarnings("unchecked")
	public Enumeration getLanguages() {
		return new Enumerator(languages);
	}

	public void addLanguage(Locale language) {
		languages.add(language);
	}

	/**
	 * Returns all parameters of this portlet The return value cannot be NULL.
	 * 
	 * @return the parameter set
	 */
	@SuppressWarnings("unchecked")
	public Enumeration getInitParameterNames() {
		return new Enumerator(initParameters.keySet());
	}

	@SuppressWarnings("unchecked")
	public ParameterConfig[] getInitParameterConfigs() {
		Collection<ParameterConfig> values = this.initParameters.values();

		return (ParameterConfig[]) values.toArray(new ParameterConfig[values.size()]);
	}

	public ParameterConfig getInitParameterConfig(String name) {
		return (ParameterConfig) this.initParameters.get(name);
	}

	@SuppressWarnings("unchecked")
	public void addInitParameterConfig(ParameterConfig config) {
		this.initParameters.put(config.getName(), config);
	}

	/**
	 * Returns all SecurityRoleRefs of this portlet
	 * 
	 * @return the SecurityRoleRef set
	 */
	public SecurityRoleRefConfig[] getInitSecurityRoleRefs() {
		return (SecurityRoleRefConfig[]) this.roleRefs
				.toArray(new SecurityRoleRefConfig[roleRefs.size()]);
	}

	public void addInitSecurityRoleRefs(SecurityRoleRefConfig ref) {
		this.roleRefs.add(ref);
	}

	public void addPreferences(Preference config) {
		preferences.put(config);
	}

	public PreferenceSet getPreferenceSet() {
		return this.preferences;
	}

	/**
	 * Returns the display name of this portlet for the given locale. The return
	 * value may be NULL.
	 * 
	 * @return display name for the given locale
	 */
	public String getDisplayName(Locale locale) {
		if (locale == null)
			locale = Locale.getDefault();
		return (String) displayNames.get(locale.getLanguage());
	}

	public String getDisplayName(String language) {
		return (String) displayNames.get(this.formatLanguage(language));
	}

	public void addDisplayName(String language, String name) {
		displayNames.put(this.formatLanguage(language), name);
	}

	/**
	 * Returns duration (in seconds) of expiration cache
	 * 
	 * @return duration of expiration cache
	 */
	public String getExpirationCache() {
		return this.expirationCache;
	}

	public String getResourceBundle() {
		return resourceBundle;
	}

	public void setExpirationCache(String expirationCache) {
		this.expirationCache = expirationCache;
	}

	public void setResourceBundle(String resourceBundle) {
		this.resourceBundle = resourceBundle;
		this.messages = MessageResourcesFactory.createFactory()
				.createResources(resourceBundle);
	}

	public MessageResources getMessageResources() {
		return this.messages;
	}

}
