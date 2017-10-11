package com.fulong.portlet.site.channelNavigation;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.fulong.portlet.PortletRender;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import java.util.Vector;
import com.fulong.longcon.site.Channel;

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
public class FinalRender extends PortletRender {
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
	@SuppressWarnings("unchecked")
	public ActionForward render(ActionMapping mapping, ActionForm form, RenderRequest request, RenderResponse response)
			throws Exception {
		ChannelNavigationConfig config = new ChannelNavigationConfig(request.getPreferences());
		request.setAttribute("preferences", request.getPreferences());
		Vector channelList = new Vector();
		Channel channel = this.getCurrentChannel(request, response);
		if (channel != null) {
			channelList.add(channel);
			while (channel.getParent() != null) {
				channel = channel.getParent();
				channelList.add(0, channel);
			}
		}
		request.setAttribute("channels", channelList.iterator());
		request.setAttribute("size", channelList.size());
		return mapping.findForward("success");
	}
}
