package com.fulong.portlet.field.download.single;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import javax.portlet.PortletPreferences;
import com.fulong.portlet.cms.ListContentPortletRender;

/**
 * 下载内容域占位符
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
public class ViewRender extends ListContentPortletRender {

	public ActionForward render(ActionMapping mapping, ActionForm form, RenderRequest request, RenderResponse response)
			throws Exception {

		PortletPreferences preferences = request.getPreferences();
		String text = preferences.getValue("text", null);
		if (text != null)
			preferences.setValue("text", this.antiFilter(text));

		request.setAttribute("preferences", preferences);
		return mapping.findForward("success");
	}
}
