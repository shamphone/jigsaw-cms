/* 
 * ========================================================================
 * 
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * ========================================================================
 */
package com.fulong.service.container;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;



/**
 * Abstract wrapper around <code>ServletConfig</code> which overrides the
 * <code>getServletContext()</code> method to return our own wrapper around
 * <code>ServletContext</code>. This class provides a common implementation 
 * of the wrapper for the different servlet API.
 *
 * @version $Id: AbstractServletConfigWrapper.java 292559 2005-09-29 21:36:43Z kenney $
 */
public abstract class AbstractServletConfigWrapper
    implements ServletConfig
{
    /**
     * The original servlet config object.
     */
    protected ServletConfig originalConfig;

    /**
     * List of parameters set using the <code>setInitParameter()</code> method.
     */
    protected Hashtable<String, String> initParameters;

    /**
     * Simulated name of the servlet.
     */
    protected String servletName;

    /**
     * @param theOriginalConfig the original servlet config object
     */
    public AbstractServletConfigWrapper(ServletConfig theOriginalConfig)
    {
        this.originalConfig = theOriginalConfig;
        this.initParameters = new Hashtable<String, String>();
    }

  

    /**
     * Sets a parameter as if it were set in the <code>web.xml</code> file.
     *
     * @param theName the parameter's name
     * @param theValue the parameter's value
     */
    public void setInitParameter(String theName, String theValue)
    {
        this.initParameters.put(theName, theValue);
    }

    /**
     * Sets the servlet name. That will be the value returned by the
     * <code>getServletName()</code> method.
     *
     * @param theServletName the servlet's name
     */
    public void setServletName(String theServletName)
    {
        this.servletName = theServletName;
    }

    /**
     * @return the original unmodified config object
     * @since 1.5
     */
    public ServletConfig getOriginalConfig()
    {
        return this.originalConfig;
    }

    //--Overridden methods ----------------------------------------------------

    /**
     * @return our own wrapped servlet context object
     */
    public ServletContext getServletContext()
    {
        return  this.originalConfig.getServletContext();
    }

    /**
     * @param theName the name of the parameter's value to return
     * @return the value of the parameter, looking for it first in the list of
     *         parameters set using the <code>setInitParameter()</code> method
     *         and then in those set in <code>web.xml</code>.
     */
    public String getInitParameter(String theName)
    {
        // Look first in the list of parameters set using the
        // setInitParameter() method.
        String value = this.initParameters.get(theName);

        if (value == null)
        {
            value = this.originalConfig.getInitParameter(theName);
        }

        return value;
    }

    /**
     * @return the union of the parameters defined in the Redirector
     *         <code>web.xml</code> file and the one set using the
     *         <code>setInitParameter()</code> method.
     */
    @SuppressWarnings("unchecked")
	public Enumeration<String> getInitParameterNames()
    {
        Vector<String> names = new Vector<String>();

        // Add parameters that were added using setInitParameter()
        Enumeration<String> en = this.initParameters.keys();

        while (en.hasMoreElements())
        {
            String value = en.nextElement();

            names.add(value);
        }

        // Add parameters from web.xml
        en = originalConfig.getInitParameterNames();

        while (en.hasMoreElements())
        {
            String value = en.nextElement();

            // Do not add parameters that have been overriden by calling
            // the setInitParameter() method.
            if (!names.contains(value))
            {
                names.add(value);
            }
        }

        return names.elements();
    }

    /**
     * @return the simulated servlet's name if defined or the redirector
     *         servlet's name
     */
    public String getServletName()
    {
        if (this.servletName != null)
        {
            return this.servletName;
        }

        return this.originalConfig.getServletName();
    }
}
