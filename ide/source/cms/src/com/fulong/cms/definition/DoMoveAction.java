package com.fulong.cms.definition;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.cms.form.CategoryForm;
import com.fulong.longcon.repository.NodeDefinition;

/**
 * <p>
 * Title: 龙驭网站管理系统
 * </p>
 * 
 * <p>
 * Description: 龙驭网站管理系统
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
 * @author liuzijun
 * @version 1.0
 */
public class DoMoveAction extends DefinitionBaseAction {
	/**
	 * categoryPerform
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return ActionForward
	 * @throws Exception
	 * @todo Implement this com.fulong.cms.category.CategoryBaseAction method
	 */
	public ActionForward definitionPerform(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		NodeDefinition parentCategory = this.getRepository(request).getDefinitionManager().getDefinition(request.getParameter("parent"));
		String[] ids = request.getParameterValues("id");
		if(ids!=null)
		for (int i = 0; i < ids.length; i++) {
			NodeDefinition category = this.getRepository(request).getDefinitionManager().getDefinition(ids[i]);
			category.setSuperDefinition(parentCategory);
		}
		return null;

	}

}
