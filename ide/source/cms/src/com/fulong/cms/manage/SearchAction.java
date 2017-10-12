package com.fulong.cms.manage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.IteratorUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.cms.CMSBaseAction;
import com.fulong.longcon.repository.NodeDefinition;
/**
 * 
 *   
 * 
 * Coolink协同工作框架模型 
 *
 * Copyright: 2009-2011 北京中科辅龙计算机技术股份有限公司
 *
 * Company: 北京中科辅龙计算机技术股份有限公司
 *
 * @author lixf
 *
 * @version 2.0
 *
 */
public class SearchAction extends CMSBaseAction {

	protected ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		String categoryId = request.getParameter("categoryID");
		if(categoryId == null )
			categoryId="no-properties-scheme";
		
		NodeDefinition definition = this.getRepository(request)
		.getDefinitionManager()
		.getDefinition(categoryId);			
		
		request.setAttribute("propertyIDs", request.getParameterValues("displayColumns"));
		request.setAttribute("properties",IteratorUtils.toList(definition.propertyDefinitions()));
		
		request.setAttribute("definition", definition);
		
		return mapping.findForward("success");
	}
}