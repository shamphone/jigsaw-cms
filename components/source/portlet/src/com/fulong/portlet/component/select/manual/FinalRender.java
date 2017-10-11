package com.fulong.portlet.component.select.manual;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.Property;
import com.fulong.portlet.PortletRender;
import com.fulong.portlet.component.PortletConfig;

/**
 * 下拉框表单域占位符
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
 * @author lichengzhao
 * @version 3.1
 */
public class FinalRender extends PortletRender {
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
		Node node = this.lookupNode(request, response);
		PortletPreferences preferences = request.getPreferences();
		PortletConfig config = new PortletConfig(preferences);
		String echo = preferences.getValue("echo", "nodeEcho");
		if(echo.equals("nodeEcho")){  //用于编辑页回显
		if (node != null) {
			Property property = node.getProperty(config.getPropertyId());
			if (property != null &&property.getValue()!=null&&property.getValue().getString()!=null) {
				request.setAttribute("value", property.getValue().getString());
			} else {
				request.setAttribute("value", preferences.getValue("default-value", ""));
			}
		} else {
			request.setAttribute("value", preferences.getValue("default-value", ""));
		}
		} else{   //用于搜索页回显
			String oValue = request.getParameter(config.getPropertyId());
			if(oValue==null){
				oValue = "";
			}
			request.setAttribute("value", oValue);
		}
		request.setAttribute("preferences", preferences);
		return mapping.findForward("success");
	}
}
