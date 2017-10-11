package com.fulong.portlet.cms.list;

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
	@SuppressWarnings("unchecked")
	public ActionForward render(ActionMapping mapping, ActionForm form, RenderRequest request, RenderResponse response)
			throws Exception {

		ContentListConfig config = new ContentListConfig(request.getPreferences());
		if (config.getCategory() == null) {
			return mapping.findForward("no.content");
		}

		request.setAttribute("preferences", request.getPreferences());
		// 获取内容列表
		NodeDefinition def = this.getRepository().getDefinitionManager().getDefinition(config.getCategory());
		RangeIterator contents = this.getContents(request, response, def, config.getFilterPatterns(), config
				.getOrderField(), config.getOrderValue(), config.isGlobal(), config.isRecursive(), Query.SQL);

		request.setAttribute("contents", RangeIteratorUtils.toSizedList(contents, config.getSize()));

		return mapping.findForward("success");
	}
}
