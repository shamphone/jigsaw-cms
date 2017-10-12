package com.fulong.lyvc.manage.form;

import com.fulong.lyvc.manage.base.BaseForm;

public class SetAdminPasswordForm extends BaseForm {

	private static final long serialVersionUID = 1565891581233531980L;

	private String newPassword;			//新密码
	private String confirmPassword;		//验证密码
	
	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
}
