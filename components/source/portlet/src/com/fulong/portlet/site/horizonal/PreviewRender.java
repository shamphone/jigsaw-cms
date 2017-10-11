package com.fulong.portlet.site.horizonal;

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
