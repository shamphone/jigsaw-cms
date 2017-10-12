package com.fulong.cms.manage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.cms.CMSBaseAction;
import com.fulong.common.util.Tree;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.NodeDefinitionTreeBuilder;

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
 * @author lixf
 * @version 1.0
 */
public class LeftAction extends CMSBaseAction {

	public ActionForward doPerform(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		NodeDefinition root = this.getRepository(request).getDefinitionManager().getDefinition(
				NodeDefinition.NO_PROPERTIES_SCHEME);
		NodeDefinitionTreeBuilder builder = new NodeDefinitionTreeBuilder(root);
		Tree tree = null;
		tree = builder.build();
		request.setAttribute("categoryTree", tree);
		return mapping.findForward("success");
	}
}
