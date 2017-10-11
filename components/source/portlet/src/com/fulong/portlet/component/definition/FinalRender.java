package com.fulong.portlet.component.definition;

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
 * 下拉框表单域占位符
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
		PortletPreferences preferences = request.getPreferences();
		String definitionId = preferences.getValue("definitionId", null);
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
		return mapping.findForward("success");
	}
}
