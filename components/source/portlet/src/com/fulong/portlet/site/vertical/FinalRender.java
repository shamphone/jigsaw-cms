package com.fulong.portlet.site.vertical;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.fulong.longcon.site.SiteTemplate;
import com.fulong.portlet.PortletRender;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fulong.longcon.site.Channel;
import java.util.Iterator;

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
public class FinalRender extends PortletRender {

	/**
	 * execute
	 * 
	 * @param request
	 *            RenderRequest
	 * @param response
	 *            RenderResponse
	 * @throws Exception
	 * @todo Implement this com.fulong.portlet.PortletRender method
	 */
	public ActionForward render(ActionMapping mapping, ActionForm form, RenderRequest request, RenderResponse response)
			throws Exception {
		NavigatorConfig config = new NavigatorConfig(request.getPreferences());
		SiteTemplate template = this.getCurrentSiteTemplate(request, response);
		request.setAttribute("channels", getAllChannels(template));
		request.setAttribute("preferences", request.getPreferences());
		ArrayList<Channel> channels = new ArrayList<Channel>();
		for (int i = 0; i < config.getChannelCount(); i++) {
			if (config.getChannelId(i) != null) {
				channels.add(template.getChannel(config.getChannelId(i)));
			}
		}
		Channel selected = getSelected(this.getCurrentChannel(request, response), channels);
		if (selected == null) {
			selected = this.getCurrentChannel(request, response);
		}
		request.setAttribute("selected", selected);

		request.setAttribute("channels", channels);
		return mapping.findForward("success");

	}

	private Channel getSelected(Channel selected, ArrayList<Channel> channels) {
		if (selected == null) {
			return null;
		}
		Iterator<Channel> it = channels.iterator();
		while (it.hasNext()) {
			if (((Channel) it.next()).equals(selected)) {
				return selected;
			}
		}
		return getSelected(selected.getParent(), channels);
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
