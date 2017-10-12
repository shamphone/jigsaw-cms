package com.fulong.process.form;

import org.apache.struts.validator.ValidatorForm;

public class TaskActivityForm extends ValidatorForm {

	private static final long serialVersionUID = 7307197543172448818L;
	private String processID;
	private String ID;
	private String name;
	private String service;
	
	public String getProcessID() {
		return processID;
	}
	public void setProcessID(String processID) {
		this.processID = processID;
	}
	public String getID() {
		return ID;
	}
	public void setID(String id) {
		ID = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	
}
