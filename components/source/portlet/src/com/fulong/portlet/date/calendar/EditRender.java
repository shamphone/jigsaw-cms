package com.fulong.portlet.date.calendar;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.site.Channel;
import com.fulong.longcon.site.SiteTemplate;
import com.fulong.portlet.PortletRender;

/**
 * 日期域占位符
 * 
 * Title: Coolink平台协同服务管理系统
 * </p>
 * 
 * <p>
 * Description: Coolink平台协同服务管理系统
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术股份有限公司 2009
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * @author lixiang
 * @version 3.1
 */
public class EditRender extends PortletRender {

	public ActionForward render(ActionMapping mapping, ActionForm form, RenderRequest request, RenderResponse response)
			throws Exception {
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
		request.setAttribute("preferences", request.getPreferences());
		request.setAttribute("dateFormatKeys", this.getPortletContext(request).getAttribute("dFormatKeys"));
		request.setAttribute("dateFormats", this.getPortletContext(request).getAttribute("dFormats"));
		return mapping.findForward("success");
	}
}
