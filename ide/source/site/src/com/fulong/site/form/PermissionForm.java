package com.fulong.site.form;

import org.apache.struts.validator.ValidatorForm;

/**
 * <p>Title: 龙驭网站管理系统</p>
 *
 * <p>Description: 龙驭网站管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author lixf
 * @version 1.0
 */
public class PermissionForm extends ValidatorForm {
	private String principalId;
	private String templateId;
	private String[] channelIds;
	public void setChannelIds(String[] channelIds) {
		this.channelIds = channelIds;
	}
	public String[] getChannelIds() {
		return channelIds;
	}
	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}
	public String getPrincipalId() {
		return principalId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getTemplateId() {
		return templateId;
	}
}
