package com.fulong.portal.model.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortalContext;
import javax.portlet.Portlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.Rule;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.fulong.portal.core.LongconPortletConfig;
import com.fulong.portal.core.LongconPortletContext;
import com.fulong.portal.model.ParameterConfig;
import com.fulong.portal.model.PortletCategory;
import com.fulong.portal.model.PortletContainer;
import com.fulong.portal.model.PortletDefinition;
import com.fulong.portal.model.PortletWindow;
import com.fulong.portal.model.Preference;

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
public class PortletContainerImpl implements PortletContainer {
	private Map<String, PortletDefinition> definitions;
	private Map<String, PortletConfig> configs;
	private Map<String, Portlet> portlets;
	private Map<String, PortletCategory> categories;
	private PortalContext portalContext = null;
	private String name;
	private static final Log log = LogFactory
			.getLog(PortletContainerImpl.class);

	public PortletContainerImpl() {
		this.definitions = Collections.synchronizedMap(new LinkedHashMap<String, PortletDefinition>());
		this.configs = Collections.synchronizedMap(new LinkedHashMap<String, PortletConfig>());
		this.portlets = Collections.synchronizedMap(new LinkedHashMap<String, Portlet>());
		this.categories = Collections.synchronizedMap(new LinkedHashMap<String, PortletCategory>());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @param context
	 *            ServletContext
	 * @throws ServletException
	 */
	public void registerPortlets(ServletContext context, String configFile) throws ServletException {
		PortletContext portletContext = new LongconPortletContext(this, context);
		Digester digester = this.getDigester(portletContext);
		try {
			digester.parse(context.getRealPath(configFile));
		} catch (SAXException ex) {
			throw new ServletException(ex);
		} catch (IOException ex) {
			throw new ServletException(ex);
		}
	}

	private Digester getDigester(PortletContext context) {
		Digester digester = new Digester();
		digester.setNamespaceAware(true);
		digester.setValidating(false);
		digester.setUseContextClassLoader(false);

		digester.clear();
		digester.push(this);
		digester.addRule("longcon-portlets/portlet-category", new AddPortletCategoryRule(context));
		digester.addBeanPropertySetter("longcon-portlets/portlet-category/name", "name");
		digester.addCallMethod("longcon-portlets/portlet-category/display-name", "setDisplayName", 2);
		digester.addCallParam("longcon-portlets/portlet-category/display-name", 0, "lang");
		digester.addCallParam("longcon-portlets/portlet-category/display-name", 1);
		digester.addCallMethod("longcon-portlets/portlet-category/portlet-config", "addPortletConfig", 1);
		digester.addCallParam("longcon-portlets/portlet-category/portlet-config", 0);
		digester.addSetNext("longcon-portlets/portlet-category", "addCategory");
		return digester;
	}

	public void addCategory(PortletCategoryImpl category) {
		this.categories.put(category.getName(), category);
		for (Iterator<PortletDefinition> iterator = category.getPortletDeginitions().iterator(); iterator.hasNext();) {
			PortletDefinition definition = (PortletDefinition) iterator.next();
			this.definitions.put(definition.getName(), definition);
		}
	}

	/**
     *
     */
	public void setPortalContext(PortalContext context) {
		this.portalContext = context;
	}

	/**
     *
     */
	public void shutdown() {
		Iterator<Portlet> iterator = this.portlets.values().iterator();
		while (iterator.hasNext()) {
			((Portlet) iterator.next()).destroy();
		}
	}

	public PortletDefinition getPortletDefinition(String name) {
		return this.definitions.get(name);
	}

	public Collection<PortletDefinition> getPortletDefinitions(String category) {
		return this.categories.get(category).getPortletDeginitions();
	}

	public Collection<PortletCategory> getPortletCategories() {
		return this.categories.values();
	}

	public PortletWindow createWindow(String ID, String name) {
		PortletDefinition definition = this.getPortletDefinition(name);
		if (definition == null)
			return null;
		return new PortletWindowImpl(ID, (Portlet) this.portlets.get(definition
				.getName()), (PortletConfig) this.configs.get(definition
				.getName()), definition);
	}

	public Portlet getPortlet(String name) {
		return (Portlet) this.portlets.get(name);

	}

	public PortletConfig getPortletConfig(String name) {
		return (PortletConfig) this.configs.get(name);

	}

	public PortalContext getPortalContext() {
		return this.portalContext;
	}

	/**
	 * public String getProperty(String name){ return
	 * this.envParams.getProperty(name); }
	 * 
	 * public Enumeration getPropertyNames(){ return this.envParams.keys(); }
	 * 
	 * public String getPortletHeader() { return
	 * envParams.getProperty("longcon.portlet.header"); }
	 * 
	 * public String getPortletHeader(PortletMode mode){ return
	 * envParams.getProperty("longcon.portlet.header."+mode.toString()); }
	 * 
	 * public String getPortletFooter() { return
	 * envParams.getProperty("longcon.portlet.footer"); } public final String
	 * getPortletFooter(PortletMode mode){ return
	 * envParams.getProperty("longcon.portlet.footer."+mode.toString()); }
	 * 
	 * public final String getPageHeader() { return
	 * envParams.getProperty("longcon.page.header"); }
	 * 
	 * public final String getPageFooter() { return
	 * envParams.getProperty("longcon.page.footer"); }
	 **/
	class AddPortletCategoryRule extends Rule {

		private PortletContext context;

		public AddPortletCategoryRule(PortletContext context) {
			super();
			this.context = context;
		}

		public void begin(Attributes attributes) throws Exception {
			PortletCategory category = new PortletCategoryImpl(this.context);
			digester.push(category);
		}

		public void end() throws Exception {
			digester.pop();
		}
	};

	public class PortletCategoryImpl implements PortletCategory {
		private Map<String, PortletDefinition> portletDefinitions;
		private Map<Locale, String> displayNames;
		private String name;
		// private PortletContainer container;
		private PortletContext context;

		public PortletCategoryImpl(PortletContext context) {
			this.displayNames = Collections
					.synchronizedMap(new LinkedHashMap<Locale, String>());
			this.portletDefinitions = Collections
					.synchronizedMap(new LinkedHashMap<String, PortletDefinition>());
			this.context = context;
		}

		/**
		 * 
		 * @param locale
		 *            Locale
		 * @return String
		 */
		public String getDisplayName(Locale locale) {
			return (String) this.displayNames.get(locale);
		}

		/**
		 * 
		 * @param locale
		 *            Locale
		 * @return String
		 */
		public String getDisplayName(String locale) {
			return (String) this.displayNames.get(new Locale(locale));
		}

		/**
		 * 
		 * @param locale
		 *            String
		 * @param name
		 *            String
		 */
		public void setDisplayName(String locale, String name) {
			this.displayNames.put(new Locale(locale), name);
		}

		/**
		 * 
		 * @return String
		 */
		public String getName() {
			return this.name;
		}

		/**
		 * 
		 * @param name
		 *            String
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * 
		 * @return Iterator
		 */
		public Collection<PortletDefinition> getPortletDeginitions() {
			return this.portletDefinitions.values();
		}

		/**
		 * 
		 * @param category
		 *            String
		 * @return Digester
		 */
		private Digester getPortletDigester() {
			Digester digester = new Digester();
			digester.setNamespaceAware(true);
			digester.setValidating(false);
			digester.setUseContextClassLoader(true);

			digester.clear();
			digester.push(this);
			digester.addObjectCreate("portlet-app/portlet",
					PortletDefinitionImpl.class);
			digester.addBeanPropertySetter("portlet-app/portlet/portlet-name",
					"name");
			digester.addBeanPropertySetter("portlet-app/portlet/portlet-class",
					"className");
			// rules for description;
			digester.addCallMethod("portlet-app/portlet/description",
					"addDescription", 2);
			digester.addCallParam("portlet-app/portlet/description", 0, "lang");
			digester.addCallParam("portlet-app/portlet/description", 1);

			// rules for display-name;
			digester.addCallMethod("portlet-app/portlet/display-name",
					"addDisplayName", 2);
			digester
					.addCallParam("portlet-app/portlet/display-name", 0, "lang");
			digester.addCallParam("portlet-app/portlet/display-name", 1);
			// rules for resources;
			digester.addBeanPropertySetter(
					"portlet-app/portlet/resource-bundle", "resourceBundle");

			digester.addBeanPropertySetter("expiration-cache",
					"expirationCache");

			// rules for params;
			digester.addObjectCreate("portlet-app/portlet/init-param",
					ParameterConfig.class);
			digester.addBeanPropertySetter(
					"portlet-app/portlet/init-param/name", "name");
			digester.addCallMethod("portlet-app/portlet/init-param/value",
					"addValue", 1);
			digester.addCallParam("portlet-app/portlet/init-param/value", 0);
			digester.addSetNext("portlet-app/portlet/init-param",
					"addInitParameterConfig");
			// rules for preferences;
			digester.addObjectCreate(
					"portlet-app/portlet/portlet-preferences/preference",
					Preference.class);
			digester.addBeanPropertySetter(
					"portlet-app/portlet/portlet-preferences/preference/name",
					"name");
			digester.addCallMethod(
					"portlet-app/portlet/portlet-preferences/preference/value",
					"addValue", 1);
			digester.addCallParam(
					"portlet-app/portlet/portlet-preferences/preference/value",
					0);
			digester.addSetNext(
					"portlet-app/portlet/portlet-preferences/preference",
					"addPreferences");

			digester.addSetNext("portlet-app/portlet", "addPortletDefinition");
			return digester;
		}

		public void addPortletConfig(String definitionFile) throws IOException,
				SAXException {
			Digester digester = this.getPortletDigester();
			digester.parse(this.context.getRealPath(definitionFile));

		}

		public void addPortletDefinition(PortletDefinition definition) {
			try {
				this.preparePortlets(this.context, definition);
				log.info("[" + this.context.getPortletContextName()
						+ "] Portlet " + definition.getName() + " ("
						+ definition.getClassName() + ") init successfully!");
			} catch (Exception ex) {
				log.error("[" + this.context.getPortletContextName()
						+ "] Error in config portlet " + definition.getName()
						+ " (" + definition.getClassName() + "):"
						+ ex.getMessage(), ex);
			}
		}

		/**
		 * 初始化Portlet
		 * 
		 * @param portletContext
		 * @param definition
		 * @throws InstantiationException
		 * @throws IllegalAccessException
		 * @throws ClassNotFoundException
		 * @throws PortletException
		 */
		protected synchronized void preparePortlets(
				PortletContext portletContext, PortletDefinition definition)
				throws InstantiationException, IllegalAccessException,
				ClassNotFoundException, PortletException {
			String className = definition.getClassName();
			Portlet portlet = (Portlet) Thread.currentThread()
					.getContextClassLoader().loadClass(className).newInstance();

			// Portlet portlet = (Portlet)
			// Class.forName(className).newInstance();
			LongconPortletConfig portletConfig = new LongconPortletConfig(
					portletContext, definition);
			portlet.init(portletConfig);
			this.portletDefinitions.put(definition.getName(), definition);
			portlets.put(definition.getName(), portlet);
			configs.put(definition.getName(), portletConfig);
			definitions.put(definition.getName(), definition);
		}
	}

}
