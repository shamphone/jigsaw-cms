package com.fulong.portlet.button.logout;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.site.Channel;
import com.fulong.portlet.PortletRender;

/**
 * 保存按钮Final状态
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
	 * execute
	 * 
	 * @param request
	 *            RenderRequest
	 * @param response
	 *            RenderResponse
	 * @throws Exception
	 */
	public ActionForward render(ActionMapping mapping, ActionForm form, RenderRequest request, RenderResponse response)
			throws Exception {
		String channelPath = request.getPreferences().getValue("channel", "");
		if(channelPath!=null&&!channelPath.equals("")&&channelPath.indexOf("/")>=0){
			Channel channel = this.parseChannel(channelPath);
			request.setAttribute("channel", channel);
			String[] channelPaths = channelPath.split("/");
			request.setAttribute("logoutChannelURL", "/"+channelPath.substring(channelPath.indexOf(channelPaths[2])));
		}
		request.setAttribute("preferences", request.getPreferences());
		return mapping.findForward("success");
	}

}
