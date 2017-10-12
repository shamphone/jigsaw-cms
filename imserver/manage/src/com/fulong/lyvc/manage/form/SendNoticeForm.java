package com.fulong.lyvc.manage.form;

import com.fulong.lyvc.manage.base.BaseForm;

/**
 * SendConferenceNoticeForm
 *
 * 龙驭视频会议系统 v3.0
 *
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 *
 * @author lifx
 *
 * 最后修改时间：2010-9-2
 */

public class SendNoticeForm extends BaseForm {

	private static final long serialVersionUID = 2469129479164261181L;

	private String conferenceId; 	// 会议id
	private String title; 			// 通知标题
	private String content; 		// 通知描述
	
	public String getConferenceId() {
		return conferenceId;
	}
	
	public void setConferenceId(String conferenceId) {
		this.conferenceId = conferenceId;
	}
	
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
