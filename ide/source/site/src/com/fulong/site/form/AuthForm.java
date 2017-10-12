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
public class AuthForm  extends ValidatorForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3307850549439774383L;
	private boolean secure;
	private String[] definitions;
	private String channelID;
	private String templateID;
	public String getChannelID() {
		return channelID;
	}
	public void setChannelID(String channelID) {
		this.channelID = channelID;
	}
	public String getTemplateID() {
		return templateID;
	}
	public void setTemplateID(String templateID) {
		this.templateID = templateID;
	}
	public boolean isSecure() {
		return secure;
	}
	public void setSecure(boolean secure) {
		this.secure = secure;
	}
	public String[] getPrincipals() {
		return definitions;
	}
	public void setPrincipals(String[] definitions) {
		this.definitions = definitions;
	}
}
