package com.fulong.security.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.Repository;
import com.fulong.security.SecurityBaseAction;
import com.fulong.security.form.UserForm;

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
public class EditAction extends SecurityBaseAction {

	/* 
	 * @param mapping 参见配置文件security-config.xml
	 * @param form 参见UserForm
	 * @param request 包含如下参数：
	 *     1. nodeID：为所选择的用户所在组的ID
	 *     2. userID：为所选择要修改的用户ID
	 * @param response 无
	 * @return 返回到edit.jsp
	 * @throws Exception
	 * @see com.fulong.security.SecurityBaseAction#doPerform(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 *  Administrator 2009-10-29
	 */
	protected ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Repository repository = this.getRepository(request);
		String nodeID = request.getParameter("nodeID");
		UserForm userform = (UserForm)form;
		if(nodeID!=null&&!nodeID.equals("")){
			NodeDefinition definition = repository.getDefinitionManager().getDefinition(nodeID);
			if(definition!=null){
				request.setAttribute("definition", definition);
			}
		}
		request.setAttribute("nodeID", nodeID);
		String userID = request.getParameter("userID");
		if(userID!=null&&!userID.equals("")){
			Node user = repository.getNode(userID);
			if(user!=null){
				if(user.getProperty("user-commonname")!=null){
					userform.setCommonname(user.getProperty("user-commonname").getString());
				}
				NodeDefinition[] roleDefs = user.getMixinDefinitions();
				if(roleDefs!=null){
					String[] roleDefIDs = new String[roleDefs.length];
					for(int i=0;i<roleDefs.length;i++){
						roleDefIDs[i]=roleDefs[i].getID();
					}
					userform.setRoleDefs(roleDefIDs);
				}
				request.setAttribute("user", user);
			}
		}
		return mapping.findForward("success");
	}

}
