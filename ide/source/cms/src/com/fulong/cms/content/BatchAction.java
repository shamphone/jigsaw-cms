package com.fulong.cms.content;

import java.util.ArrayList;

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

public abstract class BatchAction extends ContentBaseAction {

	public ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String[] contentIDs = request.getParameterValues("contentID");
		String categoryId = request.getParameter("categoryID");
		ArrayList<Node> contents = new ArrayList<Node>();
		NodeDefinition category = this.getRepository(request).getDefinitionManager().getDefinition(categoryId);
		if (contentIDs != null) {
			for (int i = 0; i < contentIDs.length; i++) {
				Node node = this.getRepository(request).getNode(contentIDs[i]);
				if (node != null) {
					contents.add(node);
				}
			}
		}
		return doExecute((Node[]) contents.toArray(new Node[contents.size()]), category, mapping, form, request, response);
	}

	/**
	 * 
	 * @param nodes
	 *            Node[]
	 * @param definion
	 *            NodeDefinition
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return String
	 * @throws Exception
	 */
	public abstract ActionForward doExecute(Node[] nodes, NodeDefinition definition, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
