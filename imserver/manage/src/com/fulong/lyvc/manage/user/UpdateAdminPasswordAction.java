package com.fulong.lyvc.manage.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.lyvc.ConferenceManager;
import com.fulong.lyvc.User;
import com.fulong.lyvc.manage.base.BaseAction;
import com.fulong.lyvc.manage.form.SetAdminPasswordForm;

public class UpdateAdminPasswordAction extends BaseAction {

	/**
	 * 
	 * 修改管理员的登录密码
	 */
	protected ActionForward doPerform(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.setDomain(request);

		SetAdminPasswordForm form = (SetAdminPasswordForm) actionForm;
		
		String newPassword = form.getNewPassword();
		
		ConferenceManager manager = this.getConferenceManager();
		User creator = manager.getUser(manager.getLeaserId());
		
		if(creator != null)
			creator.setPassword(newPassword);
		
		return mapping.findForward("success");
	}

}
