package com.fulong.lyvc.manage.form;

import com.fulong.lyvc.manage.base.BaseForm;

/**
 * SendBulletinForm
 *
 * 龙驭视频会议系统 v3.0
 *
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 *
 * @author lifx
 *
 * 最后修改时间：2010-8-17
 */

public class SendBulletinForm extends BaseForm {

	private static final long serialVersionUID = -4130049961933829867L;

	private String title;		//公告标题
	private String content;		//公告内容
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
}
