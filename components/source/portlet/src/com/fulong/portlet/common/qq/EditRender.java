package com.fulong.portlet.common.qq;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.portlet.PortletRender;
import com.fulong.portlet.field.string.PortletConfig;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

/**
 * 在线服务站位符，使用QQ提供服务
 * <p>
 * Title: WebMaster Core Library
 * </p>
 * 
 * <p>
 * Description: Longcon WebMaster SV3
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Beijing Zhongke Fulong Computer Tech. LTD
 * </p>
 * 
 * @author maweishan
 * @version 1.0
 */
public class EditRender extends PortletRender {

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
		PortletConfig config = new PortletConfig(preferences);
		NodeDefinition category = lookUpDefinition(request);
		preferences.setValue("category", category.getID());
		request.setAttribute("category", category);
		if (config.getField() != null) {
			PropertyDefinition property = category.getPropertyDefinition(preferences.getValue("refField", ""));
			StringBuffer propertyName = new StringBuffer();
			if(property==null && preferences.getValue("field", null)!=null){
				propertyName = propertyName.append(preferences.getValue("field", null)+"已被删除");
			}else{
				String idstr="";
				if(config.getField().split("[\\.\\/]").length>0){
					String ids[] = config.getField().split("[\\.\\/]");
					for(int i=0;i<ids.length;i++){
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
			request.setAttribute("propertyName",propertyName);
			request.setAttribute("property", property);
		}
		if (config.getContentID() != null) {
			request.setAttribute("content", this.getRepository().getNode(config.getContentID()));
		}
		return mapping.findForward("success");
	}
}
