package com.fulong.portlet.date.calendar;

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
public class FinalRender extends PortletRender {

	public ActionForward render(ActionMapping mapping, ActionForm form, RenderRequest request, RenderResponse response)
			throws Exception {
		PortletPreferences preferences = request.getPreferences();
		String timeSource = preferences.getValue("timeSource", "");
		Calendar calendar = null;
		calendar = Calendar.getInstance();
		if (timeSource.equals("url")){
			String str = request.getParameter("dateParameter");
			if (str != null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
			Date par = sdf.parse(str);
			calendar.setTime(par);
			}
		} else {
			Date par = (Date)request.getAttribute("com.fulong.longcon.dateParameter");
			if (par != null) {
				calendar.setTime(par);
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		request.setAttribute("linkDate", sdf.format(calendar.getTime()));
		if (calendar != null) {
			String str = preferences.getValue("dateFormat", "");
			if(str.equals("shortDate")||str.equals("fullDate")) {
				request.setAttribute("date", "");
			} else {
			SimpleDateFormat dateFormatter = new SimpleDateFormat(preferences.getValue("dateFormat", ""));
			//输出星期格式只显示大写数字
			if (str.equals("E")) {
				request.setAttribute("date", dateFormatter.format(calendar.getTime()).toString().substring(2));
			} 
			else {
			request.setAttribute("date", dateFormatter.format(calendar.getTime()));
			}
			}
		}
		request.setAttribute("preferences", preferences);
		return mapping.findForward("success");
	}
}
