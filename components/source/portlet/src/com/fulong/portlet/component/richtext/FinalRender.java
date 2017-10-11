package com.fulong.portlet.component.richtext;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import com.fulong.longcon.repository.Node;
import com.fulong.portlet.component.PortletConfig;
import javax.portlet.PortletPreferences;
import com.fulong.portlet.PortletRender;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.longcon.repository.NodeDefinition;

/**
 * 多格式文本编辑器表单域占位符
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
 * @author lichengzhao
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
		Node node = this.lookupNode(request, response);
		PortletPreferences preferences = request.getPreferences();
		PortletConfig config = new PortletConfig(preferences);
		String definitionId = config.getCategoryID();
		NodeDefinition definition = this.getRepository().getDefinitionManager().getDefinition(definitionId);
		PropertyDefinition propertyDef = definition.getPropertyDefinition(config.getPropertyId());
		if (node != null && node.getProperty(config.getPropertyId()) != null) {
			String value = node.getProperty(config.getPropertyId()).getString();
			preferences.setValue("value", value);
		} else {
			if (propertyDef.getDefaultValue() != null && !propertyDef.getDefaultValue().equals("")) {
				preferences.setValue("value", propertyDef.getDefaultValue().getString());
			}
		}
		request.setAttribute("preferences", preferences);
		return mapping.findForward("success");
	}
}
