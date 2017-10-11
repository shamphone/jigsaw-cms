package com.fulong.portlet.field.date;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.portlet.PortletRender;
import com.fulong.portlet.field.PortletConfig;

/**
 * 日期内容域占位符
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
public class EditRender extends PortletRender {

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
			String[] ids = config.getField().split("[\\.\\/]");
			String idstr = "";
			if(ids.length>0){
				for(int i=0;i<ids.length;i++){
					idstr = idstr+ids[i];
					PropertyDefinition property2 = category.getPropertyDefinition(idstr);
					propertyName.append(property2.getName());
					if(i!=ids.length-1){
						idstr+=".";
						propertyName.append(".");
					}
				}
			}else{
				propertyName.append(config.getField()+"已被删除");
			}
			request.setAttribute("propertyName", propertyName);
		}
		if (config.getContentID() != null)
			request.setAttribute("content", this.getRepository().getNode(config.getContentID()));

		request.setAttribute("dateFormatKeys", this.getPortletContext(request).getAttribute("dateFormatKeys"));
		request.setAttribute("timeFormatKeys", this.getPortletContext(request).getAttribute("timeFormatKeys"));
		request.setAttribute("dateFormats", this.getPortletContext(request).getAttribute("dateFormats"));
		request.setAttribute("timeFormats", this.getPortletContext(request).getAttribute("timeFormats"));
		return mapping.findForward("success");
	}
}
