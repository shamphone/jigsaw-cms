package com.fulong.security.user;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.longcon.repository.Query;
import com.fulong.longcon.repository.Repository;
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
public class JoinAction extends SecurityBaseAction {

	/* 
	 * @param mapping 参见配置文件security-config.xml
	 * @param form 无
	 * @param request 包含如下参数：
	 *     1. userID：为所选择要转译的用户ID
	 * @param response 无
	 * @return 返回到join.jsp
	 * @throws Exception
	 * @see com.fulong.security.SecurityBaseAction#doPerform(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 *  Administrator 2009-10-29
	 */
	protected ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String[] userIDs = request.getParameterValues("userID");
		request.setAttribute("userIDs", userIDs);
		Repository repository = this.getRepository(request);
		NodeDefinition definition = repository.getDefinitionManager().getDefinition("group-scheme");
		List<Node> list = new ArrayList<Node>();
		Node owner = null;
		owner = this.getCurrentSite(request, response).getOwner();
		if(definition!=null){
			Query query = repository.getQueryManager().createQuery(definition, Query.SQL);
			NodeIterator<Node> groups = query.nodes();  //获得组大纲下的所有组
			if(owner!=null){
				while(groups.hasNext()){
					Node group = groups.nextNode();
					if(group.getParent()==owner){    //父节点为当前域名的所属机构的组，也就是一级的组
						if(!group.getID().equals("root-group")){
							list.add(group);
						}
						getChildGroups(group,list);  //递归获取每个一级组下的子组
					}
				}
			}
		}
		request.setAttribute("groups", list);
		return mapping.findForward("success");
	}

	
	protected void getChildGroups(Node parent,List<Node> list){
		if(parent!=null&&list!=null){
			NodeIterator<Node> childGroups = parent.getNodes("childGroup");
			while(childGroups!=null&&childGroups.hasNext()){
				Node childGroup = childGroups.nextNode();
				list.add(childGroup);
				getChildGroups(childGroup,list);
			}
		}
	}
}
