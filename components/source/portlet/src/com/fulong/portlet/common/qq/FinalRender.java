package com.fulong.portlet.common.qq;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.Property;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.longcon.repository.Value;
import com.fulong.portlet.PortletRender;

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
 * @author mali
 * @version 1.0
 */
public class FinalRender extends PortletRender {
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
		String field = preferences.getValue("field", "");
		Node node = lookupNode(request, response);
		if (node == null) {
			return mapping.findForward(NODONE);
		}
		String categoryID = preferences.getValue("category", "");
		NodeDefinition def = this.getRepository().getDefinitionManager().getDefinition(categoryID);
		
		//处理引用/复合属性
		String refField = preferences.getValue("refField", null);
		if(refField != null){
			PropertyDefinition pd = def.getPropertyDefinition(refField);
			if (pd.getType() == 9) {
				Value contents = node.getProperty(refField).getValue();
				if(contents != null)
					node = this.getRepository().getNode(contents.getString());
				if (node == null) {
					return mapping.findForward(NODONE);
				}else{
					if(field!=null){
						field = field.substring(field.indexOf(".")+1);
						preferences.setValue("field", field);
					}
				}
			}
		}
		
		Property property = node.getProperty(field);
		if(property != null){
			String qqNum = property.getValues()[0].toString();
			request.setAttribute("qqNum", qqNum);
		}
		request.setAttribute("node", node);
		request.setAttribute("preferences", request.getPreferences());
		return mapping.findForward("success");

	}
}
