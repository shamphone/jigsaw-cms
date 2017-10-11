package com.fulong.portlet.component.text.simple;

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
 * 文本框表单域占位符
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
		PortletPreferences preferences = request.getPreferences();
		NodeDefinition category = this.getRepository().getDefinitionManager().getDefinition(preferences.getValue("category-id", ""));
		String propertyId = request.getPreferences().getValue("propertyId", null);
		if (propertyId != null && propertyId.length() > 0)
			request.setAttribute("property", definition.getPropertyDefinition(propertyId));
			StringBuffer propertyName = new StringBuffer();
			if(definition.getPropertyDefinition(propertyId)==null && propertyId!=null){
				propertyName.append(propertyId+"已被删除");
			}else{
				String idstr="";
				if(preferences.getValue("propertyId", "").split("[\\.\\/]").length>0){
					String ids[] = preferences.getValue("propertyId", "").split("[\\.\\/]");
					for(int i=0;i<ids.length;i++){
						if(ids[i]!="" && ids[i].length()>0){
							idstr = idstr+ids[i];
							PropertyDefinition property2 = category.getPropertyDefinition(idstr);
							propertyName.append(property2.getName());
							if(i!=ids.length-1){
								idstr+=".";
								propertyName.append(".");
							}
						}
					}
				}
			}
		request.setAttribute("propertyName",propertyName);
		request.setAttribute("definition", definition.getID());
		return mapping.findForward("success");
	}
}
