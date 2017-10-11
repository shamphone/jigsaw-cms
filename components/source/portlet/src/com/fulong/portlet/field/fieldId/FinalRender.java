package com.fulong.portlet.field.fieldId;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.Node;
import com.fulong.portlet.PortletRender;

/**
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
 * @author lixiang
 * @version 1.0
 */
public class FinalRender extends PortletRender {

	public ActionForward render(ActionMapping mapping, ActionForm form,
			RenderRequest request, RenderResponse response) throws Exception {
		Node node = lookupNode(request, response);
		if (node == null) {
			return mapping.findForward(NODONE);
		}
		request.setAttribute("preferences", request.getPreferences());
		request.setAttribute("node", node);
		return mapping.findForward("success");
	}
}
