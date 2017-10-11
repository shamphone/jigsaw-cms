package com.fulong.portlet.button.forward;

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
 * Copyright: 北京中科辅龙计算机技术有限公司 2006
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术有限公司
 * </p>
 * 
 * @author jiangqi
 * @version 2.0
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
		if(channelPath!=null&&!channelPath.equals("")&&channelPath.indexOf("/")>=0){
			template = this.parseTemplate(channelPath);
			channel = this.parseChannel(channelPath);
		}else{
			template = this.getCurrentSiteTemplate(request, response);
		}
		request.setAttribute("channel", channel);
		request.setAttribute("siteTemplate", template);

		PortletPreferences preferences = request.getPreferences();
		String definitionId = preferences.getValue("definitionId", null);
		NodeDefinition category = null;
		if (definitionId != null)
			category = this.getRepository().getDefinitionManager().getDefinition(definitionId);

		if ((category == null) && (request.getParameter("definition") != null))
			category = this.getRepository().getDefinitionManager().getDefinition(request.getParameter("definition"));

		if (category == null)
			category = this.getRepository().getDefinitionManager().getDefinition("no-properties-scheme");
		request.setAttribute("category", category);
		preferences.setValue("definitionId", category.getID());

		String propertyId = preferences.getValue("propertyId", null);
		if(category.getPropertyDefinition(propertyId)==null && propertyId!= null){
			request.setAttribute("propertyName1", propertyId+"已被删除");
		}else if (propertyId != null && propertyId.length() > 0){
			PropertyDefinition property = category.getPropertyDefinition(propertyId);
			request.setAttribute("property", property);
		}
		String propertyId2 = preferences.getValue("propertyId2", null);
		if(category.getPropertyDefinition(propertyId2)==null && propertyId2!=null){
			request.setAttribute("propertyName2", propertyId2+"已被删除");
		}else if (propertyId2 != null && propertyId2.length() > 0){
			PropertyDefinition property = category.getPropertyDefinition(propertyId2);
			request.setAttribute("property", property);
		}

		return mapping.findForward("success");
	}
}
