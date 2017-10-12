/**
 * 
 */
package com.fulong.security;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.common.util.ListRangeIterator;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.Repository;
import com.fulong.longcon.repository.Value;
import com.fulong.longcon.repository.ValueUtils;

/**
 *   
 * 
 * Coolink协同工作框架模型 
 *
 * Copyright: 2009-2011 北京中科辅龙计算机技术股份有限公司
 *
 * Company: 北京中科辅龙计算机技术股份有限公司
 *
 * @author liuzijun
 *
 * @version 2.0
 *
 */
public class RoleAction extends SecurityBaseAction{

	
	protected ActionForward doPerform(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Repository repository = this.getRepository(request);
		String roleID = request.getParameter("ID");		
		if(roleID!=null){
			Node role = repository.getNode(roleID);
			if(role!=null){
				//未完，有bug，当成员节点被删除，组中的成员引用还在，被系统缓存起来了，待解决
				Value[] contents = role.getProperty("member").getValues();  //组的成员member属性是引用属性，组中的成员是改组的引用节点
				Node[] nodes = ValueUtils.toNodeArray(contents);
				List<Node> list = new ArrayList<Node>();
				Collections.addAll(list, nodes);
				ListRangeIterator<Node> it = new ListRangeIterator<Node>(list);
				it.skip(this.getPager(request).getFromIndex());
				this.setPager(request, it.getSize());
				request.setAttribute("members", it);
			}
			request.setAttribute("roleID", roleID);
		}
		return mapping.findForward("success");
	}
}