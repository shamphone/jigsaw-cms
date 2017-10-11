package com.fulong.portlet.effects.transit;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.longcon.site.Channel;
import com.fulong.longcon.site.SiteTemplate;
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

		PortletPreferences preferences = request.getPreferences();
		String definitionId1 = preferences.getValue("definitionId1", null);
		NodeDefinition category1 = null;
		if (definitionId1 != null)
			category1 = this.getRepository().getDefinitionManager().getDefinition(definitionId1);

		if (category1 == null)
			category1 = this.getRepository().getDefinitionManager().getDefinition("no-properties-scheme");
		request.setAttribute("category1", category1);
		preferences.setValue("definitionId1", category1.getID());

		String propertyValue = preferences.getValue("propertyValue", null);
		if (propertyValue != null) {
			PropertyDefinition property = category1.getPropertyDefinition(propertyValue);
			request.setAttribute("property", property);
		}
		
		String definitionId2 = preferences.getValue("definitionId2", null);
		NodeDefinition category2 = null;
		if (definitionId2 != null)
			category2 = this.getRepository().getDefinitionManager().getDefinition(definitionId2);

		if (category2 == null)
			category2 = this.getRepository().getDefinitionManager().getDefinition("no-properties-scheme");
		request.setAttribute("category2", category2);
		preferences.setValue("definitionId2", category2.getID());
		String propertyName = preferences.getValue("propertyName", null);
		if (propertyName != null) {
			PropertyDefinition property2 = category2.getPropertyDefinition(propertyName);
			request.setAttribute("property2", property2);
		}

		return mapping.findForward("success");
	}
}
