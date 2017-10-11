package com.fulong.portlet.component.node.multiple;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.longcon.site.Channel;
import com.fulong.longcon.site.SiteTemplate;
import com.fulong.portlet.FormEditRender;

/**
 * 多文件上载表单域占位符
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
public class EditRender extends FormEditRender {
	/**
	 * execute
	 * 
	 * @param request
	 *            RenderRequest
	 * @param response
	 *            RenderResponse
	 * @throws Exception
	 */
	public ActionForward formRender(NodeDefinition definition, ActionMapping mapping, ActionForm form,
			RenderRequest request, RenderResponse response) throws Exception {
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
		request.setAttribute("properties", definition.propertyDefinitions());
		String propertyId = request.getPreferences().getValue("propertyId", null);
		String categoryId = request.getPreferences().getValue("category", NodeDefinition.NO_PROPERTIES_SCHEME);
		if (propertyId != null && propertyId.length() > 0 && definition.getPropertyDefinition(propertyId)!=null){
			request.setAttribute("property", definition.getPropertyDefinition(propertyId));
		}else if(definition.getPropertyDefinition(propertyId)==null && propertyId!=null){
			request.setAttribute("propertyDeleted",propertyId+"已被删除");
		}


		request.setAttribute("definition", definition.getID());
		NodeDefinition refDefinition = this.getRepository().getDefinitionManager().getDefinition(categoryId);
		request.setAttribute("category", refDefinition);
		String viewPropertyId = request.getPreferences().getValue("viewPropertyId", null);
		if (viewPropertyId != null && viewPropertyId.length() > 0) {
			request.setAttribute("viewProperty", refDefinition.getPropertyDefinition(viewPropertyId));
		}
		String orderfield = request.getPreferences().getValue("orderfield", null);
		if (orderfield != null && orderfield.length() > 0) {
			request.setAttribute("orderProperty", refDefinition.getPropertyDefinition(orderfield));
		}
		String[] properties = request.getPreferences().getValues("filterpatterns", new String[0]);
		List<PropertyDefinition> listTemp = new ArrayList<PropertyDefinition>();
		for(int i=0;i<properties.length;i++){
			PropertyDefinition prop = refDefinition.getPropertyDefinition(properties[i]);
			if(prop!=null){
				listTemp.add(prop);
			}
		}
		request.setAttribute("selectedProps", listTemp);
		String values[] = request.getPreferences().getValues("defaultValues", new String[0]);
		List<Node> list = new ArrayList<Node>();
		for (int i = 0; i < values.length; i++) {
			Node tmp = this.getRepository().getNode(values[i]);
			if (tmp != null)
				list.add(tmp);
		}
		request.setAttribute("values", list);
		request.setAttribute("preferences", request.getPreferences());
		return mapping.findForward("success");
	}
}
