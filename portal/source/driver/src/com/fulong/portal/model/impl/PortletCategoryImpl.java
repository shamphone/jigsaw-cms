package com.fulong.portal.model.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;

import com.fulong.portal.model.ParameterConfig;
import com.fulong.portal.model.PortletCategory;
import com.fulong.portal.model.PortletDefinition;
import com.fulong.portal.model.Preference;

/**
 * <p>Title: 龙驭门户引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author 李雄锋
 * @version 1.0
 */
public class PortletCategoryImpl implements PortletCategory {
    private Map<String, PortletDefinition> definitions;
    private Map<Locale, String> displayName;
    private String name;
    private ServletContext context;

    @SuppressWarnings("unchecked")
	public PortletCategoryImpl(ServletContext context) {
        this.displayName = Collections.synchronizedMap(new LinkedHashMap());
        this.definitions = Collections.synchronizedMap(new LinkedHashMap());
        this.context = context;
    }

    /**
     *
     * @param locale Locale
     * @return String
     */
    public String getDisplayName(Locale locale) {
        return (String)this.displayName.get(locale);
    }


    /**
     *
     * @param locale Locale
     * @return String
     */
    public String getDisplayName(String locale) {
        return (String)this.displayName.get(new Locale(locale));
    }
    /**
     *
     * @param locale String
     * @param name String
     */
    public void setDisplayName(String locale, String name){
        this.displayName.put(new Locale(locale),name);
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
     * @param name String
     */
    public void setName(String name) {
          this.name = name;
    }
    /**
     *
     * @return Iterator
     */
    public Collection<PortletDefinition> getPortletDeginitions() {
        return this.definitions.values();
    }

    /**
     *
     * @param category String
     * @return Digester
     */
    private Digester getPortletDigester() {
        Digester digester = new Digester();
        digester.setNamespaceAware(true);
        digester.setValidating(false);
        digester.setUseContextClassLoader(true);

        digester.clear();
        digester.push(this);
        digester.addObjectCreate("portlet-app/portlet", PortletDefinitionImpl.class);
        digester.addBeanPropertySetter("portlet-app/portlet/portlet-name",
                                       "name");
        digester.addBeanPropertySetter("portlet-app/portlet/portlet-class",
                                       "className");
        //rules for description;
        digester.addCallMethod("portlet-app/portlet/description",
                               "addDescription", 2);
        digester.addCallParam("portlet-app/portlet/description", 0, "lang");
        digester.addCallParam("portlet-app/portlet/description", 1);

        //rules for display-name;
        digester.addCallMethod("portlet-app/portlet/display-name",
                               "addDisplayName", 2);
        digester.addCallParam("portlet-app/portlet/display-name", 0, "lang");
        digester.addCallParam("portlet-app/portlet/display-name", 1);
        //rules for resources;
        digester.addBeanPropertySetter("portlet-app/portlet/resource-bundle",
                                       "resourceBundle");

        digester.addBeanPropertySetter("expiration-cache", "expirationCache");

        //rules for params;
        digester.addObjectCreate("portlet-app/portlet/init-param",
                                 ParameterConfig.class);
        digester.addBeanPropertySetter("portlet-app/portlet/init-param/name",
                                       "name");
        digester.addCallMethod("portlet-app/portlet/init-param/value",
                               "addValue", 1);
        digester.addCallParam("portlet-app/portlet/init-param/value", 0);
        digester.addSetNext("portlet-app/portlet/init-param",
                            "addInitParameterConfig");
        //rules for preferences;
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
                "portlet-app/portlet/portlet-preferences/preference/value", 0);
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
        definitions.put(definition.getName(), definition);

    }

}
