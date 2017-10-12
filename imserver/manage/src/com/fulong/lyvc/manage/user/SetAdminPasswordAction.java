package com.fulong.lyvc.manage.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.lyvc.manage.base.BaseAction;

public class SetAdminPasswordAction extends BaseAction {
	
	/**
	 * 
	 * 初始化setAdminPassword.jsp页面的部分内容
	 */
	protected ActionForward doPerform(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		return mapping.findForward("success");
	}

}
