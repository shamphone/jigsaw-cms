package com.fulong.security.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

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
public class CreateAction extends SecurityBaseAction {

	/* 
	 * @param mapping 参见配置文件security-config.xml
	 * @param form 参见UserForm
	 * @param request 包含如下参数：
	 *     1. nodeID：为所选择的组的ID，选择该组则在此组下建立用户，可以为空，为空则在根组下建立用户
	 * @param response 无
	 * @return 返回到create.jsp
	 * @throws Exception
	 * @see com.fulong.security.SecurityBaseAction#doPerform(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 *  Administrator 2009-10-29
	 */
	protected ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Repository repository = this.getRepository(request);
		UserForm userform = (UserForm)form;
		String nodeID = request.getParameter("nodeID");
		if(nodeID!=null&&!nodeID.equals("")){
			NodeDefinition definition = repository.getDefinitionManager().getDefinition(nodeID);
			if(definition!=null){
				request.setAttribute("definition", definition);
			}
		}
		request.setAttribute("nodeID", nodeID);
		userform.setNodeID(nodeID);
		return mapping.findForward("success");
	}

}
