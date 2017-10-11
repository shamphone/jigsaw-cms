package com.fulong.portlet.validator.property;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.portlet.FormEditRender;
/**
 *属性值验证占位符
 * <p>
 * Title: Coolink协同工作框架模型
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术有限公司 2009
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术有限公司
 * </p>
 * 
 * @author lixiang
 * @version 1.0
 */
public class EditRender extends FormEditRender {
	/**
	 * execute
	 * 
	 * @param request
	 *            RenderRequest
	 * @param response
	 *            RenderResponse
	 * @throws Exception
	 */
	public ActionForward formRender(NodeDefinition definition, ActionMapping mapping, ActionForm form,
			RenderRequest request, RenderResponse response) throws Exception {
		
		request.setAttribute("properties", definition.propertyDefinitions());
		String apropertyId = request.getPreferences().getValue("apropertyId", null);
		if (apropertyId != null && apropertyId.length() > 0)
			request.setAttribute("property", definition.getPropertyDefinition(apropertyId));
		request.setAttribute("definition", definition.getID());
		request.setAttribute("preferences", request.getPreferences());

		return mapping.findForward("success");
	}
}
