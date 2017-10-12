package com.fulong.cms.definition;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;

import com.fulong.cms.AjaxAction;
import com.fulong.common.util.ParameterSet;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.NodeDefinitionIterator;

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
 * @author sunyuchao
 * @version 1.0
 */
public class GetChildNodeDefinitionAction extends AjaxAction {

	/**
	 * definitionPerform
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
	 */
	public Document renderXML(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("categoryID");
		NodeDefinition def = this.getRepository(request).getDefinitionManager()
				.getDefinition(id);
		ParameterSet set = new ParameterSet();
		NodeDefinitionIterator it = def.getInheritDefinitions();
			while (it.hasNext()) {
				NodeDefinition nd = (NodeDefinition)it.next();
				set.put(nd.getID(), nd.getName());
			}
		response.setHeader("Cache-Control", "no-cache, must-revalidate");
		return set.toDocument();
	}
}
