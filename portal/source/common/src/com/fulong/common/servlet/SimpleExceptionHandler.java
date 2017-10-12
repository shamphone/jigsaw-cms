/**
 * 
 */
package com.fulong.common.servlet;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ExceptionHandler;
import org.apache.struts.config.ExceptionConfig;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.ModuleUtils;

/**
 *   
 * 
 * Coolink协同工作框架模型 
 *
 * Copyright: 2009-2011 北京中科辅龙计算机技术股份有限公司
 *
 * Company: 北京中科辅龙计算机技术股份有限公司
 *
 * @author lixf
 *
 * @version 2.0
 *
 */
public class SimpleExceptionHandler extends ExceptionHandler {
	 private static final Log LOG = LogFactory.getLog(SimpleExceptionHandler.class);
	  /**
     * <p> Handle the Exception. Return the ActionForward instance (if any)
     * returned by the called ExceptionHandler. </p>
     *
     * @param ex           The exception to handle
     * @param ae           The ExceptionConfig corresponding to the exception
     * @param mapping      The ActionMapping we are processing
     * @param formInstance The ActionForm we are processing
     * @param request      The servlet request we are processing
     * @param response     The servlet response we are creating
     * @return The <code>ActionForward</code> instance (if any) returned by
     *         the called <code>ExceptionHandler</code>.
     * @throws ServletException if a servlet exception occurs
     * @since Struts 1.1
     */
    public ActionForward execute(Exception ex, ExceptionConfig ae,
        ActionMapping mapping, ActionForm formInstance,
        HttpServletRequest request, HttpServletResponse response)
        throws ServletException {
        LOG.debug("ExceptionHandler executing for exception " + ex);
        String message = "";
        MessageResources resources= this.retrieveMessageResources(request, ae.getBundle());
        if(resources!=null)
        	message = resources.getMessage(request.getLocale(), ex.getMessage());

        this.logException(ex);
        try {
            message = URLEncoder.encode(message,"UTF-8");
            response.setHeader("message",message);
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, message);
		} catch (IOException e) {
			 LOG.error("Error in handle exception " + ex.getMessage()+".", e);	
			return null;
		}
        return null;
    }
	
    public MessageResources retrieveMessageResources(
    		HttpServletRequest request,
            String bundle)
            throws ServletException {

        MessageResources resources = null;

        if (bundle == null) {
            bundle = Globals.MESSAGES_KEY;
        }

        if (resources == null) {
            resources =
                    (MessageResources) request.getAttribute(
                            bundle);
        }

        if (resources == null) {
            ModuleConfig moduleConfig =ModuleUtils.getInstance().getModuleConfig(request);
            resources =
                    (MessageResources) request.getAttribute(
                            bundle + moduleConfig.getPrefix());
        }


        if (resources == null)
        	return null;
        return resources;
    }
}
