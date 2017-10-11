package com.fulong.portlet.component.select.common;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.portlet.FormEditRender;
import com.fulong.portlet.PortletPreferencesForm;

/**
 * 下拉框表单域占位符
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
		String propertyId = request.getPreferences().getValue("propertyId", null);
		if (propertyId != null && propertyId.length() > 0) {
			request.setAttribute("property", definition.getPropertyDefinition(propertyId));
		}
       String categoryId = request.getPreferences().getValue("category", "");
       NodeDefinition category = this.getRepository().getDefinitionManager().getDefinition(categoryId);
       request.setAttribute("category", category);
       request.setAttribute("preferences", request.getPreferences());

		String orderProperty = request.getPreferences().getValue("order-field", null);
		if ((orderProperty != null) && (orderProperty.length() > 0)) {
			request.setAttribute("orderProperty", category.getPropertyDefinition(orderProperty));
		}
		
     	request.setAttribute("definition", definition);
		PortletPreferencesForm aForm = (PortletPreferencesForm) form;
		aForm.setPreference("definitionId", definition.getID());
		return mapping.findForward("success");
	}
}
