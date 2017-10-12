package com.fulong.cms.form;

import org.apache.struts.action.ActionForm;

/**
 * 
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

public class PropertyForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1849848134167570436L;
	
	private String name;
	private String ID;
	private String type;
	private String maxLength = "1";
	private String minLength ="1";
	private String tips;
	private String definitionId;
	private String referenceType;
	private String dataNum;
	
	

	public String getDataNum() {
		return dataNum;
	}

	public void setDataNum(String dataNum) {
		this.dataNum = dataNum;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public String getMaxLength() {
		return maxLength;
	}

	public String getMinLength() {
		return minLength;
	}

	public String getTips() {
		return tips;
	}

	public String getDefinitionId() {
		return definitionId;
	}


	public String getReferenceType() {
		return referenceType;
	}

	public String getID() {
		return ID;
	}



	public void setName(String name) {
		this.name = name;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setMaxLength(String maxLength) {
		this.maxLength = maxLength;
	}

	public void setMinLength(String minLength) {
		this.minLength = minLength;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	public void setDefinitionId(String definitionId) {
		this.definitionId = definitionId;
	}


	public void setReferenceType(String referenceType) {
		this.referenceType = referenceType;
	}

	public void setID(String ID) {
		this.ID = ID;
	}



}
