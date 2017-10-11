package com.fulong.portlet.field.highlighter;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.portlet.cms.ListContentPortletRender;
/**
 * 
 *   
 * 
 * Coolink协同工作框架模型 
 *
 * Copyright: 2009-2011 北京中科辅龙计算机技术股份有限公司
 *
 * Company: 北京中科辅龙计算机技术股份有限公司
 *
 * @author lixf
 *
 * @version 2.0
 *
 */
public class EditRender extends ListContentPortletRender {
	public ActionForward render(ActionMapping mapping, ActionForm form, RenderRequest request, RenderResponse response)
			throws Exception {
		PortletConfig config = new PortletConfig(request.getPreferences());
		NodeDefinition category = lookUpDefinition(request);
		request.getPreferences().setValue("category", category.getID());
		request.setAttribute("category", category);
		if (config.getField() != null) {
			PropertyDefinition property = category.getPropertyDefinition(config.getField());
			request.setAttribute("property", property);
			StringBuffer propertyName = new StringBuffer();
			if(property!=null){
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
			}else{
				propertyName.append(config.getField()+"已被删除");
			}
			request.setAttribute("propertyName",propertyName);
		}
		if (config.getContentID() != null) {
			request.setAttribute("content", this.getRepository().getNode(config.getContentID()));
		}
		return mapping.findForward("success");
	}
}
