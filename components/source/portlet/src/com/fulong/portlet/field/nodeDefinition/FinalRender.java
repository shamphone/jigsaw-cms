package com.fulong.portlet.field.nodeDefinition;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.portlet.Constants;
import com.fulong.portlet.PortletRender;

/**
 * 
 * <p>
 * Title: WebMaster Core Library
 * </p>
 * 
 * <p>
 * Description: Longcon WebMaster SV3
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Beijing Zhongke Fulong Computer Tech. LTD
 * </p>
 * 
 * @author jiangqi
 * @version 1.0
 */
public class FinalRender extends PortletRender {

	/**
	 * execute
	 * 
	 * @param request
	 *            RenderRequest
	 * @param response
	 *            RenderResponse
	 * @throws Exception
	 */
	public ActionForward render(ActionMapping mapping, ActionForm form, RenderRequest request, RenderResponse response)
			throws Exception {
		if (request.getAttribute(Constants.REQUEST_NODEDEFINITION) == null) {
			Node node = this.lookupNode(request, response);
			if (node != null) {
				request.setAttribute(Constants.REQUEST_NODEDEFINITION, node.getDefinition());
			}else{
				if(request.getPreferences().getValue("contentType", "").equals("default")){
					if(request.getParameter("definition")!=null&&!request.getParameter("definition").equals("")){
						NodeDefinition def = this.getRepository().getDefinitionManager().getDefinition(request.getParameter("definition"));
						if(def!=null){
							request.setAttribute(Constants.REQUEST_NODEDEFINITION, def);
						}
					}
				}
			}
		}
		request.setAttribute("preferences", request.getPreferences());
		return mapping.findForward("success");

	}

}
