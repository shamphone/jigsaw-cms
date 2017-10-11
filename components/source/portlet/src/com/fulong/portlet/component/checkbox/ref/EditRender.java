package com.fulong.portlet.component.checkbox.ref;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.portlet.FormEditRender;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.portlet.PortletPreferencesForm;

/**
 * 复选框表单域占位符
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
		String referenceId = request.getPreferences().getValue("referenceId", null);
		if (referenceId != null && referenceId.length() > 0) {
			PropertyDefinition reference = definition.getPropertyDefinition(referenceId);
			if (reference != null) {
				request.setAttribute("reference", reference);
				String propertyId = request.getPreferences().getValue("propertyId", null);
				if (propertyId != null && propertyId.length() > 0){
					request.setAttribute("property", reference.getReferenceDefinition().getPropertyDefinition(
							propertyId));
				}else if(reference.getReferenceDefinition().getPropertyDefinition(propertyId)==null){
					request.setAttribute("propertyDeleted",propertyId+"已被删除");
				}
			}
		}
		request.setAttribute("definition", definition.getID());
		PortletPreferencesForm aForm = (PortletPreferencesForm) form;
		aForm.setPreference("definitionId", definition.getID());
		return mapping.findForward("success");
	}
}
