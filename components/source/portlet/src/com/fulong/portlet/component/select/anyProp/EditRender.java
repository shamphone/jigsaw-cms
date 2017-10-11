package com.fulong.portlet.component.select.anyProp;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.PropertyDefinition;
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
        String referenceId = request.getPreferences().getValue("referenceId", null);
		String categoryId = request.getPreferences().getValue("category", null);
		NodeDefinition category = null;
		if (categoryId != null) {
			category = this.getRepository().getDefinitionManager().getDefinition(categoryId);
		}
		if ((category == null) && (request.getParameter("definition") != null)) {
			category = this.getRepository().getDefinitionManager().getDefinition(request.getParameter("definition"));
		}
		if (category == null) {
			category = this.getRepository().getDefinitionManager().getDefinition("no-properties-scheme");
		}
		request.getPreferences().setValue("category", category.getID());
		request.setAttribute("category", category);
        if (referenceId != null && referenceId.length() > 0) {
            PropertyDefinition reference = definition.getPropertyDefinition(referenceId);
            if (reference != null) {
                request.setAttribute("reference", reference);
                
            }
        }
        if(category!= null){
        	String propertyId = request.getPreferences().getValue("propertyId", null);
            if (propertyId != null && propertyId.length() > 0) {
                request.setAttribute("property", category.getPropertyDefinition(propertyId));
            }
            String optionValue = request.getPreferences().getValue("optionValue", null);
            if (optionValue != null && optionValue.length() > 0) {
                request.setAttribute("value", category.getPropertyDefinition(optionValue));
            }
        }        
        request.setAttribute("definition", definition.getID());
        PortletPreferencesForm aForm = (PortletPreferencesForm) form;
        aForm.setPreference("definitionId", definition.getID());
		request.setAttribute("preferences", request.getPreferences());
		String userID = request.getPreferences().getValue("userID", null);
		request.setAttribute("userID", userID);
		if (referenceId != null) {
			PropertyDefinition pd = definition.getPropertyDefinition(referenceId);
			if (pd != null) {
			if (pd.getType() == 9) {
					request.setAttribute("propertyType", 0);
				}
			else {
				request.setAttribute("propertyType", 1);
			}
		}
			else {
				request.setAttribute("propertyType", 1);
			}
		}
		return mapping.findForward("success");
	}
}
