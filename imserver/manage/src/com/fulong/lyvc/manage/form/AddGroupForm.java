package com.fulong.lyvc.manage.form;

import com.fulong.lyvc.manage.base.BaseForm;

/**
 * AddGroupForm
 * 
 * 龙驭视频会议系统 v3.0
 * 
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 * 
 * @author lifx
 * 
 * 最后修改时间：2010-8-16
 */

public class AddGroupForm extends BaseForm {

	private static final long serialVersionUID = 3680213011412478020L;

	private String groupId; // 组的id
	private String groupName; // 组的名称
	private String groupDesc; // 组的描述

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupDesc() {
		return groupDesc;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

}
