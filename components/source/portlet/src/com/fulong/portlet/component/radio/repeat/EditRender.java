package com.fulong.portlet.component.radio.repeat;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.portlet.FormEditRender;

/**
 * 单选框表单域占位符
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
 * Copyright: 北京中科辅龙计算机技术有限公司
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
	 * 
	 */
	public ActionForward formRender(NodeDefinition definition, ActionMapping mapping, ActionForm form,
			RenderRequest request, RenderResponse response) throws Exception {
		PortletPreferences preferences = request.getPreferences();		
		request.setAttribute("properties", definition.propertyDefinitions());
		request.setAttribute("nameDefinition",definition);
		// 控件的name字段，和Form表单榜定的definition相关。
		String name = preferences.getValue("name", "");
		if(name.length()>0){
			PropertyDefinition property = definition.getPropertyDefinition(name);
			request.setAttribute("name", property);			
		}
		// radio控件value属性榜定的definition,这个definition和表单榜定的definition是不一样的,一般来说这个
		//Definition来自重复器。
		NodeDefinition category = lookUpDefinition(request);
		preferences.setValue("valueDefinition", category.getID());
		request.setAttribute("valueDefinition", category);
		String field=preferences.getValue("value", "");
		if(field.length()>0){
			PropertyDefinition property = category.getPropertyDefinition(field);
			request.setAttribute("value", property);
		}
		String userID = request.getPreferences().getValue("userID", null);
		request.setAttribute("userID", userID);
		if (name != null) {
			PropertyDefinition pd = definition.getPropertyDefinition(name);
			if (pd != null) {
			if (pd.getType() == 9) {
					request.setAttribute("property", 0);
				}
			else {
				request.setAttribute("property", 1);
			}
		}
			else {
				request.setAttribute("property", 1);
			}
		}
		return mapping.findForward("success");
	}
}
