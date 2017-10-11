package com.fulong.portlet.field.date;

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
 * 日期内容域占位符
 * 
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
 * @author lichengzhao
 * @version 3.1
 */
public class ViewRender extends PortletRender {

	public ActionForward render(ActionMapping mapping, ActionForm form, RenderRequest request, RenderResponse response)
			throws Exception {
		PortletPreferences preferences = request.getPreferences();
		Calendar calendar = Calendar.getInstance();
		calendar.set(2000, 0, 31);
		SimpleDateFormat dateFormatter = new SimpleDateFormat(preferences.getValue("dateFormat", ""));
		SimpleDateFormat timeFormatter = new SimpleDateFormat(preferences.getValue("timeFormat", ""));
		request.setAttribute("date", dateFormatter.format(calendar.getTime()));
		request.setAttribute("time", timeFormatter.format(calendar.getTime()));

		request.setAttribute("preferences", preferences);
		return mapping.findForward("success");
	}
}
