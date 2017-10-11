package com.fulong.portlet.cms.nodeTree;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.portlet.cms.RepeaterEditRender;
import com.fulong.portlet.field.string.PortletConfig;

/**
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
 * @version 2.0
 */
public class EditRender extends RepeaterEditRender {
	/**
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            RenderRequest
	 * @param response
	 *            RenderResponse
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward editRender(NodeDefinition def, ActionMapping mapping, ActionForm form, RenderRequest request,
			RenderResponse response) throws Exception {
		PortletPreferences preferences = request.getPreferences();
		PortletConfig config = new PortletConfig(preferences);
		NodeDefinition category = this.getRepository().getDefinitionManager().getDefinition(preferences.getValue("categoryId", ""));
		if (config.getField() != null) {
			PropertyDefinition property = category.getPropertyDefinition(config.getField());
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
			request.setAttribute("property", property);
		}
		
		String path = iniJspf(request, response);
		request.setAttribute("path", path);
		
		path = "/"+this.getCurrentSiteTemplate(request, response).getName()+ path;
		request.setAttribute("clipPath", path);
		
		String parentPath = iniJspf(request, response,"parent");
		request.setAttribute("parentPath", parentPath);
		
		String parentClipPath = "/"+this.getCurrentSiteTemplate(request, response).getName()+ parentPath;
		request.setAttribute("preferences",request.getPreferences());
		request.setAttribute("parentClipPath", parentClipPath);	
		return mapping.findForward("success");
	}
}
