package com.fulong.cms.content;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <p>Title: WebMaster Core Library</p>
 *
 * <p>Description: Longcon WebMaster SV3</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Beijing Zhongke Fulong Computer Tech. LTD</p>
 *
 * @author sunyuchao
 * @version 1.0
 */
public class AjaxDeleteContentAction extends BatchAction {

	public ActionForward doExecute(Node[] nodes, NodeDefinition definion,
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Node node = this.getRepository(request).getNode(request.getParameter(
        "contentID"));
		if (node != null) {
			this.getRepository(request).delete(node);
		}
		return null;
	}
}
