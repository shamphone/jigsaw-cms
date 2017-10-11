package com.fulong.portlet.cms.list;

import java.util.Collections;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.portlet.PortletRender;

/**
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
 * @author Lixf
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
		ContentListConfig config = new ContentListConfig(request.getPreferences());
		request.setAttribute("preferences", request.getPreferences());
		request.setAttribute("channel.types", Collections.list(this.getSiteFactory().channelTypes()));

		NodeDefinition definition = null;
		if (config.getCategory() != null) {
			definition = this.getRepository().getDefinitionManager().getDefinition(config.getCategory());
		}
		if ((definition == null) && (request.getParameter("definition") != null)) {
			definition = this.getRepository().getDefinitionManager().getDefinition(request.getParameter("definition"));
		}
		if (definition == null) {
			definition = this.getRepository().getDefinitionManager().getDefinition(NodeDefinition.NO_PROPERTIES_SCHEME);
		}

		request.setAttribute("category", definition);

		String[] fields = config.getBlockFields();
		for (int i = 0; i < fields.length; i++) {
			request.setAttribute("property" + i, definition.getPropertyDefinition(fields[i]));
		}
		return mapping.findForward("success");

	}
}
