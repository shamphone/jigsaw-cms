package com.fulong.portlet.cms.grid;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.common.util.RangeIterator;
import com.fulong.common.util.RangeIteratorUtils;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.Query;
import com.fulong.portlet.cms.ListContentPortletRender;

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
public class ViewRender extends ListContentPortletRender {
	/**
	 * execute
	 * 
	 * @param request
	 *            RenderRequest
	 * @param response
	 *            RenderResponse
	 * @throws Exception
	 * @todo Implement this com.fulong.portlet.PortletRender method
	 */
	@SuppressWarnings("unchecked")
	public ActionForward render(ActionMapping mapping, ActionForm form, RenderRequest request, RenderResponse response) throws Exception {

		ContentGridConfig config = new ContentGridConfig(request.getPreferences());
		request.setAttribute("preferences", request.getPreferences());
		NodeDefinition def = this.getRepository().getDefinitionManager().getDefinition(config.getCategory());

		RangeIterator iterator = this.getContents(request, response, def, config.getFilterPatterns(), config
				.getOrderField(), config.getOrderValue(), config.isGlobal(), config.isRecursive(), Query.SQL);

		if (iterator.getSize() == 0) {
			return mapping.findForward("no.content");
		} else {
			request.setAttribute("contents", RangeIteratorUtils.toSizedList(iterator, config.getSize()));
			return mapping.findForward("success");

		}
	}
}
