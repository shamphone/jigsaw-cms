package com.fulong.portlet.site.horizonal;

import java.util.ArrayList;

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
	public ActionForward render(ActionMapping mapping, ActionForm form, RenderRequest request, RenderResponse response)
			throws Exception {

		NavigatorConfig config = new NavigatorConfig(request.getPreferences());
		SiteTemplate template = this.getCurrentSiteTemplate(request, response);
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
}
