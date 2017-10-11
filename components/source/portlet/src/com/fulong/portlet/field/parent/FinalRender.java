package com.fulong.portlet.field.parent;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.Node;
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
		Node node = lookupNode(request, response);
		/*String contentType = preferences.getValue("contentType", "");
		
		String contentId = null;
		if(contentType.equalsIgnoreCase("default")){
			node = this.getRepository().getNode(request.getParameter("contentId"));
		}else if(contentType.equals("user")){
			node = this.getCurrentUser(request, response);
		}else if(contentType.equals("site")){
			node = this.getCurrentSite(request, response).getOwner();
		}*/
		if (node == null) {
			return mapping.findForward(NODONE);
		}
		//片段的路径
		request.setAttribute("path", getClipPath(request, response, ""));
		request.setAttribute("nodeparent", node.getParent());
		request.setAttribute("preferences", request.getPreferences());
		return mapping.findForward("success");

	}

}
