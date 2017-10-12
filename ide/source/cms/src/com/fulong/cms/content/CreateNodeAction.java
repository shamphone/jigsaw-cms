package com.fulong.cms.content;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;

/**
 * 用于创建复合属性节点
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
public class CreateNodeAction extends BatchAction {

	/* (non-Javadoc)
	 * @see com.fulong.cms.dialog.DialogBaseAction#dialogPerform(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */

	public ActionForward doExecute(Node[] contents,
    		NodeDefinition category,
                                   ActionMapping mapping,
                                   ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response) throws
            Exception {
		String parentID = request.getParameter("parentID");
		String fixPropID = request.getParameter("fixPropID");
		String proID = request.getParameter("proID");
		String childDef = request.getParameter("childDef");
		request.setAttribute("parentID", parentID);
		request.setAttribute("fixPropID", fixPropID);
		request.setAttribute("proID", proID);
		request.setAttribute("childDef", childDef);
		return mapping.findForward("success");
	}

}
