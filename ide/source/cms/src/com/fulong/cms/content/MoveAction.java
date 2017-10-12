package com.fulong.cms.content;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.Node;
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
public class MoveAction extends ContentBaseAction {

	public ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		NodeDefinition cc = null;
		String[] ids = request.getParameterValues("contentID");
		String definition = request.getParameter("definition");
		for (int i = 0; i < ids.length; i++) {
			Node content = this.getRepository(request).getNode(ids[i]);
			if (definition != null) {
					cc = this.getRepository(request).getDefinitionManager().getDefinition(definition);
					content.setDefinition(cc);
				}
			}
		return null;
	}

}