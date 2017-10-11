package com.fulong.portlet.field.highlighter;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.ChildNodeDefinition;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.longcon.site.Channel;
import com.fulong.portlet.cms.ListContentPortletRender;
/**
 * 
 *   
 * 
 * Coolink协同工作框架模型 
 *
 * Copyright: 2009-2011 北京中科辅龙计算机技术股份有限公司
 *
 * Company: 北京中科辅龙计算机技术股份有限公司
 *
 * @author lixf
 *
 * @version 2.0
 *
 */
public class ViewRender extends ListContentPortletRender {
	@SuppressWarnings("deprecation")
	public ActionForward render(ActionMapping mapping, ActionForm form, RenderRequest request, RenderResponse response)
			throws Exception {
		PortletConfig config = new PortletConfig(request.getPreferences());
		String propertyID = config.getField();
		String categoryId = request.getPreferences().getValue("category", null);
		NodeDefinition category = this.getRepository().getDefinitionManager().getDefinition(categoryId);
		
		if (category == null) {
			Channel channel = this.getCurrentChannel(request, response);
			if (channel == null) {
				return mapping.findForward("success");
			}
			category = channel.getBindingNode();
		}
		if (propertyID != null) {
			String[] temp = propertyID.split("#");
			if (temp.length > 1) {
				propertyID = temp[1];
				PropertyDefinition fixProperty = category.getPropertyDefinition(temp[0]);
				PropertyDefinition property = ((ChildNodeDefinition) fixProperty).getNodeDefinition()
						.getPropertyDefinition(temp[1]);
				request.setAttribute("property", property);
			} else {
				request.setAttribute("property", category.getPropertyDefinition(config.getField()));
				
			}
		}
		return mapping.findForward("success");
	}
}
