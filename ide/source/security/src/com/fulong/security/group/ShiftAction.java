package com.fulong.security.group;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.security.SecurityBaseAction;

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
public class ShiftAction extends SecurityBaseAction {

	/* 
	 * @param mapping 参见配置文件security-config.xml
	 * @param form 无
	 * @param request 包含如下参数：
	 *     1. nodeID：为所选择的组的ID，选择该组则可对此组进行转移，不可为空
	 * @param response 无
	 * @return 返回到shift.jsp
	 * @throws Exception
	 * @see com.fulong.security.SecurityBaseAction#doPerform(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 *  Administrator 2009-10-29
	 */
	protected ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String nodeID = request.getParameter("nodeID");
		Node group = this.getRepository(request).getNode(nodeID);
		request.setAttribute("groupID", nodeID);
		Node rootGroup = this.getRepository(request).getNode("root-group");
		List<Node> list = new ArrayList<Node>();
		if(rootGroup!=null&&group!=null){
			list.add(rootGroup);
			getChildrenWithoutNode(rootGroup,group,list);
		}
		request.setAttribute("groups", list);
		return mapping.findForward("success");
	}
	
	protected void getChildrenWithoutNode(Node parent,Node withoutNode,List<Node> list){
		 NodeIterator<Node> nodes = parent.getNodes("childGroup");
		 while(nodes.hasNext()){
			 Node child = nodes.nextNode();
			 if(child!=withoutNode){
				 list.add(child);
				 getChildrenWithoutNode(child,withoutNode,list);
			 }
		 }
	}
}
