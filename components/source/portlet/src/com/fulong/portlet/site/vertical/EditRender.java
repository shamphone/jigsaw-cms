package com.fulong.portlet.site.vertical;

import java.util.ArrayList;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.site.Channel;
import com.fulong.longcon.site.SiteTemplate;
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
 * @author Lixf
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

		NavigatorConfig config = new NavigatorConfig(request.getPreferences());
		SiteTemplate template = this.getCurrentSiteTemplate(request, response);
		request.setAttribute("channels",template.getChannels());
		request.setAttribute("preferences", request.getPreferences());
		ArrayList<Channel> selected = new ArrayList<Channel>();
		for (int i = 0; i < config.getChannelCount(); i++) {
			if (config.getChannelId(i) != null) {
				selected.add(template.getChannel(config.getChannelId(i)));
			}
		}
		request.setAttribute("selectedChannels", selected);
		return mapping.findForward("success");
	}

}
