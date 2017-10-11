package com.fulong.portlet.field.child;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.ChildNodeDefinition;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.portlet.cms.RepeaterEditRender;

/**
 * 
 * <p>
 * Title: 龙驭门户引擎
 * </p>
 * 
 * <p>
 * Description: 龙驭网站内容管理系统核心引擎
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
 * @author jiangqi
 * @version 1.0
 */
public class EditRender extends RepeaterEditRender {
	public ActionForward editRender(NodeDefinition def, ActionMapping mapping, ActionForm form, RenderRequest request,
			RenderResponse response) throws Exception {

		PortletPreferences preferences = request.getPreferences();
		String path = iniJspf(request, response);
		request.setAttribute("path", path);
		path = "/"+this.getCurrentSiteTemplate(request, response).getName()+ path;
		request.setAttribute("clipPath", path);
		
		NodeDefinition category = this.lookUpDefinition(request);
		String comField = preferences.getValue("comField", null);
		if (comField != null) {
			ChildNodeDefinition pd = (ChildNodeDefinition) def.getPropertyDefinition(comField);
			request.setAttribute("property", pd);
			StringBuffer propertyName = new StringBuffer();
			if(pd==null){
				propertyName.append(comField+"已被删除");
			}else{
				String idstr="";
				if(preferences.getValue("comField", "").split("[\\.\\/]").length>0){
					String ids[] = preferences.getValue("comField", "").split("[\\.\\/]");
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
		}
		return mapping.findForward("success");

	}

}
