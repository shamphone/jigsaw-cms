package com.fulong.portlet.date.forward;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.portlet.PortletRender;

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
		PortletPreferences preferences = request.getPreferences();
		Calendar calendar = null;
		calendar = Calendar.getInstance();
		String strTemp = request.getParameter("dateParameter");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		if (strTemp != null){
			Date par = sdf.parse(strTemp);
			calendar.setTime(par);
			}

		String str = preferences.getValue("channelWay", "");
		//今天
		if (str.equals("today")) {
			if (calendar != null) {
				request.setAttribute("date", sdf.format(new Date()));
			}
		//昨天
		} else if (str.equals("yesterday")) {
			if (calendar != null) {
				calendar.add(Calendar.DATE, -1);
				request.setAttribute("date", sdf.format(calendar.getTime()));
			}
		//明天
		} else if (str.equals("tomorrow")) {
			if (calendar != null) {
				calendar.add(Calendar.DATE, 1);
				request.setAttribute("date", sdf.format(calendar.getTime()));
			}
		//上一周
		} else if (str.equals("previousWeek")) {
			if (calendar != null) {
				calendar.add(Calendar.WEEK_OF_YEAR, -1);
				request.setAttribute("date", sdf.format(calendar.getTime()));
			}
		//下一周
		} else if (str.equals("lastWeek")) {
			if (calendar != null) {
				calendar.add(Calendar.WEEK_OF_YEAR, 1);
				request.setAttribute("date", sdf.format(calendar.getTime()));
			}
		//上一月
		} else if (str.equals("previousMonth")) {
			if (calendar != null) {
				calendar.add(Calendar.MONTH, -1);
				request.setAttribute("date", sdf.format(calendar.getTime()));
			}
		//下一月
		} else if (str.equals("lastMonth")) {
			if (calendar != null) {
				calendar.add(Calendar.MONTH, 1);
				request.setAttribute("date", sdf.format(calendar.getTime()));
			}
		//上一年
		} else if (str.equals("previousYear")) {
			if (calendar != null) {
				calendar.add(Calendar.YEAR, -1);
				request.setAttribute("date", sdf.format(calendar.getTime()));
			}
		//下一年
		} else if (str.equals("lastYear")) {
			if (calendar != null) {
				calendar.add(Calendar.YEAR, 1);
				request.setAttribute("date", sdf.format(calendar.getTime()));
			}
		}
		request.setAttribute("preferences", request.getPreferences());
		return mapping.findForward("success");
	}

}
