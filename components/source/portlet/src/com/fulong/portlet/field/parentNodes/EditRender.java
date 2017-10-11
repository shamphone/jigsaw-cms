package com.fulong.portlet.field.parentNodes;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.ChildNodeDefinition;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.longcon.site.Channel;
import com.fulong.longcon.site.SiteTemplate;
import com.fulong.portlet.PortletRender;
import com.fulong.portlet.field.string.PortletConfig;

/**
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
 * @author liuzijun
 * @version 1.0
 */
public class EditRender extends PortletRender {

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
	public ActionForward render(ActionMapping mapping, ActionForm form, RenderRequest request, RenderResponse response)
			throws Exception {
		PortletPreferences preferences = request.getPreferences();
		PortletConfig config = new PortletConfig(preferences);
		NodeDefinition category = lookUpDefinition(request);
		preferences.setValue("category", category.getID());
		request.setAttribute("category", category);
		
		SiteTemplate template = null;
		Channel channel = null;
		String channelPath = request.getPreferences().getValue("channel", "");
		if(channelPath==null||channelPath.equals("")){
			template = this.getCurrentSiteTemplate(request, response);
		}else{
			template = this.parseTemplate(channelPath);
			channel = this.parseChannel(channelPath);
		}
		request.setAttribute("channel", channel);
		request.setAttribute("siteTemplate", template);
		
		if(preferences.getValue("showField", null)!=null){
			PropertyDefinition property = category.getPropertyDefinition(preferences.getValue("showField", null));
			request.setAttribute("property1", property);
			StringBuffer propertyName = new StringBuffer();
			String idstr="";
			if(preferences.getValue("showField", "").split("[\\.\\/]").length>0){
				String ids[] = preferences.getValue("showField", "").split("[\\.\\/]");
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
			request.setAttribute("property1Name",propertyName);
		}
		String refField = preferences.getValue("refField", null);
		if (refField != null) {
			PropertyDefinition pd = category.getPropertyDefinition(refField);
			if (pd.getType() == 9) {
					request.setAttribute("property", pd);
				}
			if (pd.getType() == 0) {
				ChildNodeDefinition fh = (ChildNodeDefinition)pd;
				request.setAttribute("property", fh);
			}
		}
		
		if (config.getField() != null) {
			PropertyDefinition property = category.getPropertyDefinition(config.getField());
			request.setAttribute("property2", property);
		}
		if (config.getContentID() != null) {
			request.setAttribute("content", this.getRepository().getNode(config.getContentID()));
		}
		
		String seperator = preferences.getValue("seperator", null);
		if (seperator != null)
			preferences.setValue("seperator", this.antiFilter(seperator));
		
		return mapping.findForward("success");
	}
}
