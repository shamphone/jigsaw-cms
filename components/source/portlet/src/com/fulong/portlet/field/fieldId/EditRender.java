package com.fulong.portlet.field.fieldId;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.site.Channel;
import com.fulong.longcon.site.SiteTemplate;
import com.fulong.portlet.cms.ListContentPortletRender;

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
 * @author Lixiang
 * @version 1.0
 */
public class EditRender extends ListContentPortletRender {
	public ActionForward render(ActionMapping mapping, ActionForm form, RenderRequest request, RenderResponse response)
			throws Exception {
		PortletPreferences preferences = request.getPreferences();
		PortletConfig config = new PortletConfig(preferences);

		SiteTemplate template = null;
		Channel channel = null;
		String channelPath = request.getPreferences().getValue("channel", "");
		if(channelPath==null||channelPath.equals("")){
			template = this.getCurrentSiteTemplate(request, response);
		}else{
			template = this.parseTemplate(channelPath);
			channel = this.parseChannel(channelPath);
		}
		request.setAttribute("channel", channel);
		request.setAttribute("siteTemplate", template);

		if (config.getContentID() != null) {
			request.setAttribute("content", this.getRepository().getNode(config.getContentID()));
		}
		String type = preferences.getValue("type", null); 
		if (type != null)
			request.setAttribute("type", type);
		return mapping.findForward("success");
	}
}
