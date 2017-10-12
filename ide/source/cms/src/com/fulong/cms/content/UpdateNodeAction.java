package com.fulong.cms.content;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.Node;

/**
 * 
 * <p>Title: Coolink平台协同管理系统</p>
 *
 * <p>Description: Coolink平台协同管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2009</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author liuzijun
 * @version 3.1
 */
public class UpdateNodeAction extends ContentBaseAction {

	public ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String nodeID = request.getParameter("nodeID");
		String prop = request.getParameter("prop");
		String proID = request.getParameter("proID");
		Node node = this.getRepository(request).getNode(nodeID);
		if(node!=null){
			if(node.getProperty(proID)!=null){
				node.setProperty(proID, prop);
			}
		}				
		request.setAttribute("oNode", node);
		request.setAttribute("proID", proID);
		return mapping.findForward("success");
	}

}
