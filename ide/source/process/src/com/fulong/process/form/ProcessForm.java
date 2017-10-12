package com.fulong.process.form;

import org.apache.struts.validator.ValidatorForm;

public class ProcessForm extends ValidatorForm {

	private static final long serialVersionUID = -7234302393618547626L;
	private String name;
	private String discription;
	private String ID;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDiscription() {
		return discription;
	}
	public void setDiscription(String discription) {
		this.discription = discription;
	}
	public String getID() {
		return ID;
	}
	public void setID(String id) {
		ID = id;
	}
	
	

}
