package com.fulong.portlet.site.vertical;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.fulong.longcon.site.Channel;
import com.fulong.longcon.site.SiteTemplate;
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
 * @author Lixf
 * @version 1.0
 */
public class PreviewRender extends PortletRender {

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
		request.setAttribute("channels", getAllChannels(template));
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
	/**
	 * 获取网站模板中所有的栏目,按照先序遍历
	 * 
	 * @param template
	 *            SiteTemplate
	 * @return List
	 */
	public static List<Channel> getAllChannels(SiteTemplate template) {
		Vector<Channel> channels = new Vector<Channel>();
		recursiveAddChannels(template.getRootChannel(), channels);
		return channels;
	}

	/**
	 * 先序遍历所有栏目。
	 * 
	 * @param root
	 *            Channel
	 * @param channels
	 *            List
	 */
	private static void recursiveAddChannels(Channel root, List<Channel> channels) {
		channels.add(root);
		for (Iterator<Channel> children = root.getChildren(); children.hasNext();) {
			recursiveAddChannels(children.next(), channels);
		}
	}
}
