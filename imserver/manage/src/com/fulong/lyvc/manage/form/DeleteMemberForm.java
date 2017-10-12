package com.fulong.lyvc.manage.form;

import com.fulong.lyvc.manage.base.BaseForm;

/**
 * DeleteMemberForm
 *
 * 龙驭视频会议系统 v3.0
 *
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 *
 * @author lifx
 *
 * 最后修改时间：2010-8-16
 */

public class DeleteMemberForm extends BaseForm {

	private static final long serialVersionUID = -7989178880910245593L;
	
	private String groupId;			//组的id
	private String userId;			//用户id
	
	public String getGroupId() {
		return groupId;
	}
	
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
