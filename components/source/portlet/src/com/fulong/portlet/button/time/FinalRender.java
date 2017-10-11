package com.fulong.portlet.button.time;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

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
	protected final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	protected final SimpleDateFormat SIMPLEFORMATTER = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * execute
	 * 
	 * @param request
	 *            RenderRequest
	 * @param response
	 *            RenderResponse
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward render(ActionMapping mapping, ActionForm form, RenderRequest request, RenderResponse response)
			throws Exception {
		PortletPreferences preferences = request.getPreferences();
		String defaultMode = preferences.getValue("defaultMode", "none");
		String date = null;
		Calendar calendar = Calendar.getInstance();
		String offsetS = preferences.getValue("offset", "0");
		int offset = 0;
		try {
			offset = Integer.parseInt(offsetS);
		} catch (NumberFormatException ex) {
		}
		int unit = Integer.parseInt(preferences.getValue("unit", Calendar.DAY_OF_YEAR + ""));
		if ("now".equals(defaultMode)) {
			calendar.add(unit, offset);
			date = FORMATTER.format(calendar.getTime());
		} else if ("par".equals(defaultMode)) {
			String sd = request.getParameter(preferences.getValue("propertyId", ""));
			if (sd != null) {
				try {
					calendar.setTime(FORMATTER.parse(sd));
				} catch (Exception ex) {
					try {
						calendar.setTime(SIMPLEFORMATTER.parse(sd));
					} catch (Exception e) {
					}
				}
			}
			calendar.add(unit, offset);
			date = FORMATTER.format(calendar.getTime());
		} else if ("fix".equals(defaultMode)) {
			date = FORMATTER.format(preferences.getValue("defaultValue", null));
		}
		request.setAttribute("date", date);
		Map map = ((HttpServletRequest) request).getParameterMap();
		String properyId = preferences.getValue("propertyId", null);
		if (map.containsKey(properyId)) {
			map.remove(properyId);
		}
		StringBuffer queryString = new StringBuffer();
		queryString.append("?");
		if (!map.isEmpty()) {
			Iterator it = map.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				String[] values = (String[]) map.get(key);
				for (int i = 0; i < values.length; i++) {
					queryString.append(key + "=" + values[i]);
					queryString.append("&");
				}
			}
		}
		queryString.append(properyId + "=");
		request.setAttribute("queryString", queryString.toString());
		request.setAttribute("preferences", request.getPreferences());
		return mapping.findForward("success");
	}

}
