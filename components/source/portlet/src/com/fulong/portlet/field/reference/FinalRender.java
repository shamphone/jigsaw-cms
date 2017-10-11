package com.fulong.portlet.field.reference;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.fulong.longcon.repository.Node;
import com.fulong.portlet.PortletRender;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

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
		//片段的路径
		request.setAttribute("path", getClipPath(request, response, ""));
		if (node == null) {
			return mapping.findForward(NODONE);
		}
		PortletPreferences preferences = request.getPreferences();
		String refField = preferences.getValue("refField", null);
		if (refField != null) {
			if(node.getProperty(refField)!=null){
				request.setAttribute("nodes", node.getProperty(refField).getValues());
			}
		}
		request.setAttribute("preferences", request.getPreferences());
		return mapping.findForward("success");

	}

}
