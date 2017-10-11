package com.fulong.portlet.component.node.selection;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.NodeDefinitionTreeBuilder;
import com.fulong.portlet.PortletRender;

/**
 * 内容选择表单域占位符
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
 * @author jiangqi
 * @version 3.1
 */
public class FinalRender extends PortletRender {
	public ActionForward render(ActionMapping mapping, ActionForm form, RenderRequest request, RenderResponse response)
			throws Exception {
		PortletPreferences preferences = request.getPreferences();

		String definitionId = preferences.getValue("category", NodeDefinition.NO_PROPERTIES_SCHEME);
		NodeDefinition definition = this.getRepository().getDefinitionManager().getDefinition(definitionId);
		NodeDefinitionTreeBuilder builder = new NodeDefinitionTreeBuilder(definition);
		request.setAttribute("tree", builder.build());
		request.setAttribute("preferences", preferences);
		NodeDefinition cDefinition = null;
		if (request.getParameter("definition") != null) {
			cDefinition = this.getRepository().getDefinitionManager().getDefinition(request.getParameter("definition"));
		}
		if (cDefinition == null) {
			if (preferences.getValue("defaultType", "default").equals("user")) {
				cDefinition = ((Node) request.getUserPrincipal()).getDefinition();
			} else {
				cDefinition = this.getRepository().getDefinitionManager().getDefinition(
						preferences.getValue("category", null));
			}
		}
		if (cDefinition == null)
			cDefinition = definition;
		request.setAttribute("definition", cDefinition);
		String nodeP = preferences.getValue("propertyId", null);
		Node cNode = null;
		if (nodeP != null) {
			cNode = this.getRepository().getNode(request.getParameter(nodeP));
		}
		if (cNode == null) {
			cNode = (Node) this.getCurrentUser(request, response);
		}
		request.setAttribute("cNode", cNode);
		return mapping.findForward("success");
	}
}
