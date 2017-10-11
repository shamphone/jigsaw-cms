package com.fulong.portlet.site.channelNavigation;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.site.ChannelTreeBuilder;
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
 * @author liuzijun
 * @version 1.0
 */
public class EditRender extends PortletRender {

	/**
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            RenderRequest
	 * @param response
	 *            RenderResponse
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward render(ActionMapping mapping, ActionForm form, RenderRequest request, RenderResponse response)
			throws Exception {
		PortletPreferences preferences = request.getPreferences();
		request.setAttribute("preferences", preferences);
		ChannelTreeBuilder channelBuilder = new ChannelTreeBuilder(this.getCurrentSiteTemplate(request, response));
		request.setAttribute("channels", channelBuilder.build().getNodes());
		request.setAttribute("currentPath", "sites/" + this.getCurrentSiteTemplate(request, response).getName());
		String seperator = preferences.getValue("seperator", null);
		if (seperator != null)
			preferences.setValue("seperator", this.antiFilter(seperator));
		return mapping.findForward("success");
	}
}
