package com.fulong.portlet.cms.grid;

import java.util.Collections;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.portlet.PortletRender;

/**
 * <p>
 * Title: WebMaster Core Library
 * </p>
 * 
 * <p>
 * Description: Longcon WebMaster SV3
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Beijing Zhongke Fulong Computer Tech. LTD
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

		ContentGridConfig config = new ContentGridConfig(request.getPreferences());
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

		String[] fields = config.getColumnFields();
		for (int i = 0; i < fields.length; i++) {
			request.setAttribute("property" + i, definition.getPropertyDefinition(fields[i]));

		}
		return mapping.findForward("success");

	}
}
