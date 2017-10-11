package com.fulong.portlet.component.radio.ref;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import com.fulong.longcon.repository.NodeDefinition;
import javax.portlet.PortletPreferences;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.longcon.repository.Query;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.longcon.repository.Node;
import com.fulong.portlet.PortletRender;
import com.fulong.longcon.repository.Property;

/**
 * 选项按钮表单域占位符
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

	public ActionForward render(ActionMapping mapping, ActionForm form, RenderRequest request, RenderResponse response)
			throws Exception {
		Node node = this.lookupNode(request, response);
		PortletPreferences preferences = request.getPreferences();
		String definitionId = preferences.getValue("definitionId", null);
		String referenceId = preferences.getValue("referenceId", null);

		NodeDefinition definition = this.getRepository().getDefinitionManager().getDefinition(definitionId);
		PropertyDefinition propertyDef = definition.getPropertyDefinition(referenceId);
		NodeDefinition reference = propertyDef.getReferenceDefinition();

		Query query = this.getRepository().getQueryManager().createQuery(reference, Query.SQL);
		NodeIterator<?> nodes = query.nodes();

		request.setAttribute("nodes", nodes);

		if (node != null) {
			Property property = node.getProperty(referenceId);
			if (property != null) {
				preferences.setValue("value", property.getString());
			}
		} else {
			String value = request.getPreferences().getValue("defaultValue", null);
			Node defaultValue = null;
			if (value != null) {
				defaultValue = this.getRepository().getNode(value);
				preferences.setValue("value", defaultValue.getID());
			}
		}
		request.setAttribute("preferences", preferences);
		request.setAttribute("node", node);
		return mapping.findForward("success");
	}
}
