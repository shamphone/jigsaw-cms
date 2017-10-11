package com.fulong.portlet.date.calendar;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fulong.portlet.PortletRender;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.portlet.PortletPreferences;

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
public class ViewRender extends PortletRender {

	public ActionForward render(ActionMapping mapping, ActionForm form, RenderRequest request, RenderResponse response)
			throws Exception {
		PortletPreferences preferences = request.getPreferences();
		Calendar calendar = Calendar.getInstance();
		calendar.set(2000, 0, 31);
		//输出星期格式只显示大写数字
		String str = preferences.getValue("dateFormat", "");
		if(str.equals("shortDate")||str.equals("fullDate")) {
			request.setAttribute("date", "");
		} else {
		if (str.equals("E")) {
			SimpleDateFormat dateFormatter = new SimpleDateFormat(preferences.getValue("dateFormat", ""));
			request.setAttribute("date", dateFormatter.format(calendar.getTime()).toString().substring(2));
		} else {
		SimpleDateFormat dateFormatter = new SimpleDateFormat(preferences.getValue("dateFormat", ""));
		request.setAttribute("date", dateFormatter.format(calendar.getTime()));
		}
		}
		request.setAttribute("preferences", preferences);
		return mapping.findForward("success");
	}
}
