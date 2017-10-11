package com.fulong.portlet.button.forward;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.portlet.Constants;
import com.fulong.portlet.PortletRender;

/**
 * <p>
 * Title: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Description: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术有限公司 2006
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术有限公司
 * </p>
 * 
 * @author jiangqi
 * @version 2.0
 */
public class FinalRender extends PortletRender {
	protected final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
		request.setAttribute("preferences", request.getPreferences());
		PortletPreferences preferences = request.getPreferences();
		String propertyId = preferences.getValue("propertyId", null);
		if (propertyId != null) {
			Object o = request.getAttribute(Constants.REQUEST_PARAMETER);
			if (o instanceof Date) {
				request.setAttribute("paramet", FORMATTER.format(o));
			} else {
				request.setAttribute("paramet", o);
			}
		} else {
			request.setAttribute("paramet", this.getServletRequest(request).getQueryString());
		}
		return mapping.findForward("success");
	}

}
