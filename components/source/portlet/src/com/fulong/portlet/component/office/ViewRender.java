package com.fulong.portlet.component.office;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.portlet.PortletRender;
import com.fulong.portlet.component.PortletConfig;


/**
 * <p>Title: Coolink平台协同管理系统</p>
 *
 * <p>Description: Coolink平台协同管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2010</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author luobin
 * @date 2010-8-18	
 * @version 1.0.1
 */
public class ViewRender extends PortletRender {

	public ActionForward render(ActionMapping mapping, ActionForm form, RenderRequest request, RenderResponse response)
			throws Exception {
		request.setAttribute("preferences", request.getPreferences());
		
		PortletPreferences preferences = request.getPreferences();
		PortletConfig config = new PortletConfig(preferences);
		String definitionId = config.getCategoryID();
		NodeDefinition definition = this.getRepository().getDefinitionManager().getDefinition(definitionId);
		PropertyDefinition propertyDef = definition.getPropertyDefinition(config.getPropertyId());
		if (propertyDef != null)
			request.setAttribute("propertyDef", propertyDef);
		
		return mapping.findForward("success");
	}
}
