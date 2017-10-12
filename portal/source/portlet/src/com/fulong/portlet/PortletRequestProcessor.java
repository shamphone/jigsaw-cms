package com.fulong.portlet;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.RequestProcessor;
import org.apache.struts.util.RequestUtils;

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
 * @author Lixf
 * @version 1.0
 */
public class PortletRequestProcessor extends RequestProcessor {
	public PortletRequestProcessor() {
		super();
	}

	public void action(ActionRequest arequest, ActionResponse aresponse) throws PortletException, IOException {
		HttpServletRequest request = (HttpServletRequest) arequest;
		HttpServletResponse response = (HttpServletResponse) aresponse;
		// Identify the path component we will use to select a mapping
		String path = processActionPath(arequest, aresponse);
		if (path == null) {
			return;
		}

		if (log.isDebugEnabled()) {
			log.debug("Processing a '" + request.getMethod() + "' for path '" + path + "'");
		}
		try {
			// Select a Locale for the current user if requested
			processLocale(request, response);

			// Set the content type and no-caching headers if requested
			processContent(request, response);
			processNoCache(request, response);

			// General purpose preprocessing hook
			if (!processPreprocess(request, response)) {
				return;
			}

			this.processCachedMessages(request, response);

			// Identify the mapping for this request
			ActionMapping mapping = processMapping(request, response, path);
			if (mapping == null) {
				return;
			}

			// Check for any role required to perform this action
			if (!processRoles(request, response, mapping)) {
				return;
			}

			// Process any ActionForm bean related to this request
			ActionForm form = processActionForm(request, response, mapping);
			processPopulate(request, response, form, mapping);
			if (!processValidate(request, response, form, mapping)) {
				return;
			}

			// Process a forward or include specified by this mapping
			if (!processForward(request, response, mapping)) {
				return;
			}

			if (!processInclude(request, response, mapping)) {
				return;
			}

			// Create or acquire the Action instance to process this request
			Action action = processActionCreate(request, response, mapping);
			if (action == null) {
				return;
			}

			// Call the Action instance itself
			ActionForward forward = processActionPerform(request, response, action, form, mapping);

			// Process the returned ActionForward instance
			processForwardConfig(request, response, forward);
		} catch (ServletException ex) {
			throw new PortletServletException(ex);
		}
	}

	public void render(RenderRequest arequest, RenderResponse aresponse) throws PortletException, IOException {
		HttpServletRequest request = (HttpServletRequest) arequest;
		HttpServletResponse response = (HttpServletResponse) aresponse;
		// Identify the path component we will use to select a mapping
		String path = processRenderPath(arequest, aresponse);
		if (path == null) {
			return;
		}

		if (log.isDebugEnabled()) {
			log.debug("Processing a '" + request.getMethod() + "' for path '" + path + "'");
		}
		try {
			// Select a Locale for the current user if requested
			processLocale(request, response);

			// Set the content type and no-caching headers if requested
			processContent(request, response);
			processNoCache(request, response);

			// General purpose preprocessing hook
			if (!processPreprocess(request, response)) {
				return;
			}

			this.processCachedMessages(request, response);

			// Identify the mapping for this request
			ActionMapping mapping = processMapping(request, response, path);
			if (mapping == null) {
				return;
			}

			// Check for any role required to perform this action
			if (!processRoles(request, response, mapping)) {
				return;
			}

			// Process any ActionForm bean related to this request
			ActionForm form = processActionForm(request, response, mapping);
			processPopulate(request, response, form, mapping);
			if (!processValidate(request, response, form, mapping)) {
				return;
			}

			// Process a forward or include specified by this mapping
			if (!processForward(request, response, mapping)) {
				return;
			}

			if (!processInclude(request, response, mapping)) {
				return;
			}

			// Create or acquire the Action instance to process this request
			Action action = processActionCreate(request, response, mapping);
			if (action == null) {
				return;
			}

			// Call the Action instance itself
			ActionForward forward = processActionPerform(request, response, action, form, mapping);

			// Process the returned ActionForward instance
			processForwardConfig(request, response, forward);
			
		} catch (ServletException ex) {
			throw new PortletServletException(ex);
		}

	}

	/**
	 * 
	 * @param request
	 *            ActionRequest
	 * @param response
	 *            ActionResponse
	 * @return String
	 * @throws IOException
	 */
	protected String processActionPath(ActionRequest request, ActionResponse response) throws IOException {

		String name = request.getParameter(Constants.KEY_ACTION_NAME);
		return "/" + name;

	}

	  /* (non-Javadoc)
	 * @see org.apache.struts.action.RequestProcessor#processActionCreate(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.apache.struts.action.ActionMapping)
	 * 
	 */
	@SuppressWarnings("unchecked")
	protected Action processActionCreate(HttpServletRequest request,
		        HttpServletResponse response, ActionMapping mapping)
		        throws IOException {
		        // Acquire the Action instance we will be using (if there is one)
		        String className = mapping.getType();

		        if (log.isDebugEnabled()) {
		            log.debug(" Looking for Action instance for class " + className);
		        }

		        // If there were a mapping property indicating whether
		        // an Action were a singleton or not ([true]),
		        // could we just instantiate and return a new instance here?
		        Action instance;

		        synchronized (actions) {
		            // Return any existing Action instance of this class
		            instance = (Action) actions.get(className);

		            if (instance != null) {
		                if (log.isTraceEnabled()) {
		                    log.trace("  Returning existing Action instance");
		                }

		                return (instance);
		            }

		            // Create and return a new Action instance
		            if (log.isTraceEnabled()) {
		                log.trace("  Creating new Action instance");
		            }

		            try {
		            	
		                instance = (Action) this.servlet.getClass()
		                .getClassLoader()
		                .loadClass(className).newInstance();

		                // Maybe we should propagate this exception
		                // instead of returning null.
		            } catch (Exception e) {
		                log.error(getInternal().getMessage("actionCreate",
		                        mapping.getPath()), e);

		                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
		                    getInternal().getMessage("actionCreate", mapping.getPath()));

		                return (null);
		            }

		            actions.put(className, instance);
		        }

		        if (instance.getServlet() == null) {
		            instance.setServlet(this.servlet);
		        }

		        return (instance);
		    }
	/**
	 * 
	 * @param request
	 *            ActionRequest
	 * @param response
	 *            ActionResponse
	 * @return String
	 * @throws IOException
	 */
	protected String processRenderPath(RenderRequest arequest, RenderResponse aresponse) throws IOException {

		return "/" + arequest.getPortletMode().toString();

	}

    /**
     * <p>Retrieve and return the <code>ActionForm</code> associated with
     * this mapping, creating and retaining one if necessary. If there is no
     * <code>ActionForm</code> associated with this mapping, return
     * <code>null</code>.</p>
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param mapping The mapping we are using
     */
    protected ActionForm processActionForm(HttpServletRequest request,
                                           HttpServletResponse response,
                                           ActionMapping mapping) {

        // Create (if necessary) a form bean to use
        ActionForm instance = RequestUtils.createActionForm
            (request, mapping, moduleConfig, servlet);
        if (instance == null) {
            return (null);
        }

        // Store the new instance in the appropriate scope
        if (log.isDebugEnabled()) {
            log.debug(" Storing ActionForm bean instance in scope '" +
                mapping.getScope() + "' under attribute key '" +
                mapping.getAttribute() + "'");
        }
        if ("session".equals(mapping.getScope())) {
        	 HttpSession session = request.getSession();
             session.setAttribute(mapping.getAttribute(), instance);
        } else {
            request.setAttribute(mapping.getAttribute(), instance);           
        }
        
    	if (instance instanceof PortletPreferencesForm) {
			((PortletPreferencesForm) instance).bind(((PortletRequest) request).getPreferences());
		}        
        return (instance);

    }
   		
}
