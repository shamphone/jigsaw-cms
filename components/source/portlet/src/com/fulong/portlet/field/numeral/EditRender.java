package com.fulong.portlet.field.numeral;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.portlet.cms.ListContentPortletRender;
import com.fulong.portlet.field.string.PortletConfig;


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
 * @author liulei
 * @version 1.0
 */

public class EditRender extends ListContentPortletRender
{
	public ActionForward render(ActionMapping mapping, ActionForm form, 
			               RenderRequest request, RenderResponse response)throws Exception	                                                       
	{                                                 
		PortletConfig config = new PortletConfig(request.getPreferences());
		NodeDefinition category = lookUpDefinition(request);   //first
		request.getPreferences().setValue("category", category.getID());
		request.setAttribute("category", category);
		if(config.getField() != null) 
		{
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
			}
			request.setAttribute("propertyName",propertyName);
		}
		if(config.getContentID() != null)
		{
			request.setAttribute("content", this.getRepository().getNode(config.getContentID()));
		}
		
		request.setAttribute("numberFormatKeys", this.getPortletContext(request).getAttribute("numberFormatKeys"));
		request.setAttribute("numberFormats", this.getPortletContext(request).getAttribute("numberFormats"));
		return mapping.findForward("success");
	}
}
