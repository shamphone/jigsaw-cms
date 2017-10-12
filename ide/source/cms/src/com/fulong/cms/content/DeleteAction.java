package com.fulong.cms.content;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

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
 * @author liulei
 * 
 * @version 2.0
 * 
 */
public class DeleteAction extends ContentBaseAction {
	/**
	 * contentPerform
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
	public ActionForward doExecute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String[] contentIDs = request.getParameterValues("contentID");
		/**
		 * liulei modified in 2009-12-11 改进目的：主要处理在删除推荐节点和被推荐节点的问题。
		 * 改进描述：首先需要判断该节点是不是type为0，
		 * 随后，如果type为0，需要初始化NODE并调用removeMixin函数；如果type为1
		 * ，则调用repository的delete方法。
		 */
		String categoryID = request.getParameter("categoryID");
		for (int i = 0; i < contentIDs.length; i++) {
			Node node = this.getRepository(request).getNode(contentIDs[i]);
			if (node.getDefinition().isNodeType(categoryID)) {
				this.getRepository(request).delete(node);
			} else {
				int type = node.getNodeType(categoryID);
				if (type == 0) // 推荐节点删除
				{
					NodeDefinition definition = this.getRepository(request)
					.getDefinitionManager().getDefinition(categoryID);
					node.removeMixin(definition);
				} else if (type == 1) // 主分类节点删除
				{
					this.getRepository(request).delete(node);
				}
			}
		}
		return null;
	}
}
