package com.fulong.security.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

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
 * @author lixiang
 * @version 3.1
 */
public class ResetPasswordAction extends SecurityBaseAction {

	/* 
	 * @param mapping 参见配置文件security-config.xml
	 * @param form 无
	 * @param request 包含如下参数：
	 *     1. userID：为所选择要修改密码的用户ID
	 * @param response 无
	 * @return 返回到resetPassword.jsp
	 * @throws Exception
	 * @see com.fulong.security.SecurityBaseAction#doPerform(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 *  Administrator 2009-10-29
	 */
	protected ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String[] userIDs = request.getParameterValues("userID");
		request.setAttribute("IDs", userIDs);
		return mapping.findForward("success");
	}

}
