package com.fulong.portlet.component.text.fuzzySearchProp;

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
 * 
 * <p>
 * Title: coolink协同工作支撑平台
 * </p>
 * 
 * <p>
 * Description: coolink协同工作支撑平台
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
 * @author liuzijun
 * @version 1.0
 */
public class EditRender extends FormEditRender {
	public ActionForward formRender(NodeDefinition definition, ActionMapping mapping, ActionForm form,
			RenderRequest request, RenderResponse response) throws Exception {
		request.setAttribute("properties", definition.propertyDefinitions());
		PortletPreferences preferences = request.getPreferences();
		NodeDefinition category = this.getRepository().getDefinitionManager().getDefinition(preferences.getValue("category-id", ""));
		String propertyId = request.getPreferences().getValue("propertyId", null);
		if (propertyId != null && propertyId.length() > 0){
			if(propertyId.indexOf("coolinkKeyword_")!=-1){
				propertyId = propertyId.substring(15);
			}
			request.setAttribute("property", definition.getPropertyDefinition(propertyId));
		}
			
			StringBuffer propertyName = new StringBuffer();
			if(definition.getPropertyDefinition(propertyId)==null && propertyId!=null){
				propertyName.append(propertyId+"已被删除");
			}else{
				String idstr="";
				if(propertyId!=null&&!propertyId.equals("")&&propertyId.split("[\\.\\/]").length>0){
					String ids[] = propertyId.split("[\\.\\/]");
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
		request.setAttribute("preferences", request.getPreferences());
		return mapping.findForward("success");
	}
}
