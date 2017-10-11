package com.fulong.portlet.validator.property;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.Property;
import com.fulong.longcon.repository.Value;
import com.fulong.portlet.PortletRender;

/**
 * 属性值校验占位符Final状态
 * 
 * <p>
 * Title: Coolink协同工作框架模型
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
 * @author Lixiang
 * @version 1.0
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
		PortletPreferences preferences = request.getPreferences();
		Node node = lookupNode(request, response);
		if (node == null)
		{
			return mapping.findForward(NODONE);
		}
		Property property = node.getProperty(preferences.getValue("apropertyId", ""));
		if (property != null)
		{
			Value value = property.getValue();
			request.setAttribute("value", value.getString());
		}
		request.setAttribute("preferences", request.getPreferences());
		return mapping.findForward("success");
	}

}
