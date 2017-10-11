package com.fulong.portlet.effects.transit;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.Property;
import com.fulong.portlet.PortletRender;

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
 * Copyright: 北京中科辅龙计算机技术有限公司 2009
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术有限公司
 * </p>
 * 
 * @author liuzijun
 * @version 3.1
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
		request.setAttribute("preferences", request.getPreferences());
		PortletPreferences preferences = request.getPreferences();
		Node oNode = this.lookupNode(request, response);
		String propertyValue = preferences.getValue("propertyValue", null);
		String propertyName = preferences.getValue("propertyName", "");
		String isContentIDForValue = preferences.getValue("contentIDForValue", "false");
		String isContentIDForName = preferences.getValue("contentIDForName", "false");
		if(!isContentIDForName.equals("false")){
			propertyName = "contentId";
		}
		request.setAttribute("propName", propertyName);
		String value = "";
		if(!isContentIDForValue.equals("false")){
			if(oNode!=null){
				value = oNode.getID();
			}
		}else{
			if(propertyValue!=null){
				if(oNode!=null){
					Property prop = oNode.getProperty(propertyValue);
					if(prop!=null){
						value = prop.getString();
					}
				}
			}
		}
		request.setAttribute("value", value);
		return mapping.findForward("success");
	}

}
