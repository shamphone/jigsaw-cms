package com.fulong.portlet.effects.imageWeave;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.fulong.portlet.PortletRender;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;


/**
 * <p>Title: Coolink平台协同管理系统</p>
 *
 * <p>Description: Coolink平台协同管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2010</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author luobin
 * @date 2010-9-6	
 * @version 1.0.1
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
		PortletPreferences preferences = request.getPreferences();
		request.setAttribute("preferences", preferences);
		String charWidthStr = preferences.getValue("charWidth", null);
		int charWidth = 13;
		if(charWidthStr!=null){
			charWidth = Integer.parseInt(charWidthStr);
		}
		request.setAttribute("charWidth", charWidth);
		String heightStr = preferences.getValue("height", null);
		int height = 20;
		if(heightStr!=null){
			height = Integer.parseInt(heightStr);
		}
		request.setAttribute("height", height);
		return mapping.findForward("success");
	}

}
