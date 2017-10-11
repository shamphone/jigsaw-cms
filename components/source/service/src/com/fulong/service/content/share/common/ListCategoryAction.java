package com.fulong.service.content.share.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.common.util.Tree;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.NodeDefinitionTreeBuilder;
import com.fulong.service.ServiceBaseAction;

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
 * @author sunyuchao
 * 
 * @version 1.0
 * 
 */

public class ListCategoryAction extends ServiceBaseAction {
	
	public ActionForward doPerform(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Tree tree = new NodeDefinitionTreeBuilder(this.getRepository().getDefinitionManager().getDefinition(NodeDefinition.NO_PROPERTIES_SCHEME)).build();
		if (tree != null) {
			request.setAttribute("categoryTree", tree);
		}
		
		request.setAttribute("fromURL", request.getParameter("fromURL"));
		
		return mapping.findForward("success");
	}
}
