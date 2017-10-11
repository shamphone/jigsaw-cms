package com.fulong.portlet.date.weekCalendar;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;

import com.fulong.portlet.PortletAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

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
public class SaveAction extends PortletAction {
	/**
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            ActionRequest
	 * @param response
	 *            ActionResponse
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward action(ActionMapping mapping, ActionForm form, ActionRequest request, ActionResponse response)
			throws Exception {

		PortletPreferences modified = request.getPreferences();
		modified.store();
		return null;
	}

}
