/**
 * 
 */
package com.fulong.cms.form;

import org.apache.struts.action.ActionForm;

/**
 *   
 * 
 * Coolink协同工作框架模型 
 *
 * Copyright: 2009-2011 北京中科辅龙计算机技术股份有限公司
 *
 * Company: 北京中科辅龙计算机技术股份有限公司
 *
 * @author lixf
 *
 * @version 2.0
 *
 */
public class ExchangeForm extends ActionForm{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1317106021377416722L;
	private String remoteURL;
	private String remoteDefinition;
	private String localDefinition;
	private String mapping;
	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemoteURL() {
		return remoteURL;
	}
	public void setRemoteURL(String remoteURL) {
		this.remoteURL = remoteURL;
	}
	public String getRemoteDefinition() {
		return remoteDefinition;
	}
	public void setRemoteDefinition(String remoteDefinition) {
		this.remoteDefinition = remoteDefinition;
	}
	public String getLocalDefinition() {
		return localDefinition;
	}
	public void setLocalDefinition(String localDefinition) {
		this.localDefinition = localDefinition;
	}
	public String getMapping() {
		return mapping;
	}
	public void setMapping(String mapping) {
		this.mapping = mapping;
	}
	
}
