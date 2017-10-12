package com.fulong.portal.core;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;

import com.fulong.portal.model.ParameterConfig;
import com.fulong.portal.model.PortletDefinition;

/**
 *
 * <p>Title: Longcon Portal Driver</p>
 *
 * <p>Description: Longcon WebMaster</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Beijing Zhongke Fulong Computer Technology LTD.</p>
 *
 * @author Lixf
 * @version 1.0
 */
public class LongconPortletConfig
    implements PortletConfig {
//  PortletWindow window;
  private PortletDefinition definition;
  private PortletContext context;

  public LongconPortletConfig(PortletContext context,PortletDefinition definition) {
    this.context = context;
    this.definition = definition;

  }

  /**
   * Returns a String containing the value of the named initialization
   * parameter, or null if the parameter does not exist.
   *
   * @param name a <code>String</code> specifying the name of the
   *   initialization parameter
   * @return a <code>String</code> containing the value of the initialization
   *   parameter
   * @todo Implement this javax.portlet.PortletConfig method
   */
  public String getInitParameter(String name) {

    ParameterConfig config = definition.getInitParameterConfig(name);
    if (config == null) {
      return null;
    }
    return config.getValue();
  }

  /**
   * Returns the names of the portlet initialization parameters as an
   * <code>Enumeration</code> of String objects, or an empty
   * <code>Enumeration</code> if the portlet has no initialization parameters.
   *
   * @return an <code>Enumeration</code> of <code>String</code> objects
   *   containing the names of the portlet initialization parameters, or an
   *   empty <code>Enumeration</code> if the portlet has no initialization
   *   parameters.
   * @todo Implement this javax.portlet.PortletConfig method
   */
  @SuppressWarnings("unchecked")
public Enumeration getInitParameterNames() {
    return definition.getInitParameterNames();
  }

  /**
   * Returns the <code>PortletContext</code> of the portlet application the
   * portlet is in.
   *
   * @return a <code>PortletContext</code> object, used by the caller to
   *   interact with its portlet container
   * @todo Implement this javax.portlet.PortletConfig method
   */
  public PortletContext getPortletContext() {
    return this.context;
  }

  /**
   * Returns the name of the portlet.
   *
   * @return the portlet name
   */
  public String getPortletName() {
    return definition.getName();
  }

  public String getDisplayName(String language) {
    Locale locale=Locale.getDefault();
    if (language != null)
      locale = new Locale(language);
    return definition.getDisplayName(locale);
  }

  public String getDescription(String language) {
    Locale locale=Locale.getDefault();
 if (language != null)
   locale = new Locale(language);
    return definition.getDescription(locale);
  }

  /**
   * Gets the resource bundle for the given locale based on the resource bundle
   * defined in the deployment descriptor with <code>resource-bundle</code> tag
   * or the inlined resources defined in the deployment descriptor.
   *
   * @param locale the locale for which to retrieve the resource bundle
   * @return the resource bundle for the given locale
   * @todo Implement this javax.portlet.PortletConfig method
   */
  public ResourceBundle getResourceBundle(Locale locale) {
    return ResourceBundle.getBundle(definition.getResourceBundle(), locale);
  }

}
