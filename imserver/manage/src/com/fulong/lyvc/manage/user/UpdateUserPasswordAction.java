package com.fulong.lyvc.manage.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.fulong.lyvc.ConferenceManager;
import com.fulong.lyvc.User;
import com.fulong.lyvc.manage.base.BaseAction;
import com.fulong.lyvc.manage.form.SetUserPasswordForm;

/**
 * UpdateUserPasswordAction
 *
 * 龙驭视频会议系统 v3.0
 *
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 *
 * @author lifx
 *
 * 最后修改时间：2010-8-16
 */

public class UpdateUserPasswordAction extends BaseAction {

	/**
	 * 
	 * 修改某个公共联系人的登录密码
	 */
	protected ActionForward doPerform(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.setDomain(request);

		SetUserPasswordForm form = (SetUserPasswordForm) actionForm;
		
		String userId = form.getUserId();
		String newPassword = form.getNewPassword();
		
		ConferenceManager manager = this.getConferenceManager();
		
		if(userId != null && !userId.equals("")) {
			User user = manager.getUser(userId);
			if(user != null) {
				user.setPassword(newPassword);
			}
		}
		else {
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("user.notSelected"));
			this.addErrors(request, messages);
			
			return mapping.getInputForward();
		}
		
		//从session中删除保存的参数
		request.getSession().removeAttribute("groups");
		request.getSession().removeAttribute("users");
		
		return mapping.findForward("success");
	}
	
}
