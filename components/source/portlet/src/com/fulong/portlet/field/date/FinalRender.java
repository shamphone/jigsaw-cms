package com.fulong.portlet.field.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.Node;
import com.fulong.portlet.PortletRender;
import com.fulong.portlet.field.PortletConfig;

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
public class FinalRender extends PortletRender {

	public ActionForward render(ActionMapping mapping, ActionForm form, RenderRequest request, RenderResponse response)
			throws Exception {
		PortletPreferences preferences = request.getPreferences();
		PortletConfig config = new PortletConfig(preferences);
		Calendar calendar = null;
		String contentType = request.getPreferences().getValue("contentType", null);
		if (contentType.equals("parameter")) {
			Date par = (Date) request.getAttribute("com.fulong.longcon.parameter");
			if (par != null) {
				calendar = Calendar.getInstance();
				calendar.setTime(par);
			}
		} else {
			Node node = lookupNode(request, response);
			if (node == null) {
				return mapping.findForward(NODONE);
			}
			if (node.getProperty(config.getField()) != null) {
				calendar = node.getProperty(config.getField()).getDate();
			}
		}
		if (calendar != null) {
			// 默认值为“”表示不显示
			SimpleDateFormat dateFormatter = new SimpleDateFormat(preferences.getValue("dateFormat", ""));
			SimpleDateFormat timeFormatter = new SimpleDateFormat(preferences.getValue("timeFormat", ""));
			request.setAttribute("date", dateFormatter.format(calendar.getTime()));
			request.setAttribute("time", timeFormatter.format(calendar.getTime()));
		}
		request.setAttribute("preferences", preferences);
		return mapping.findForward("success");
	}
}
