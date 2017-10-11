package com.fulong.portlet.date.yearCalendar;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.portlet.cms.RepeaterEditRender;

/**
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
public class EditRender extends RepeaterEditRender {

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
	public ActionForward editRender(NodeDefinition def, ActionMapping mapping, ActionForm form, RenderRequest request,
			RenderResponse response) throws Exception {
		PortletPreferences preferences = request.getPreferences();
		String path = iniJspf(request, response);
		request.setAttribute("path", path);
		path = "/"+this.getCurrentSiteTemplate(request, response).getName()+ path;
		request.setAttribute("clipPath", path);
		
	    request.setAttribute("preferences", preferences);
		return mapping.findForward("success");

	}
}
