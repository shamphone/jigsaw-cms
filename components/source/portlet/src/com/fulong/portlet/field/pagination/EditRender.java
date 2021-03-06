package com.fulong.portlet.field.pagination;

import java.util.Random;

import javax.portlet.PortletPreferences;
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
 * 分页内容域占位符
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
		PortletPreferences preferences = request.getPreferences();
		PortletConfig config = new PortletConfig(preferences);
		NodeDefinition category = lookUpDefinition(request);
		preferences.setValue("category", category.getID());
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
			request.setAttribute("propertyName", propertyName);
		}
		if (config.getContentID() != null)
			request.setAttribute("content", this.getRepository().getNode(config.getContentID()));

		if (preferences.getValue("identifier", null) == null)
			preferences.setValue("identifier", System.currentTimeMillis() + "" + new Random().nextLong());

		if (preferences.getValue("separator", null) != null && preferences.getValue("separator", null).length() > 0)
			preferences.setValue("separator", this.antiFilter(preferences.getValue("separator", null)));

		return mapping.findForward("success");
	}
}
