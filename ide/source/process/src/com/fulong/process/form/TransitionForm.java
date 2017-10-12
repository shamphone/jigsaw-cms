package com.fulong.process.form;

import org.apache.struts.validator.ValidatorForm;

public class TransitionForm extends ValidatorForm {

	private static final long serialVersionUID = -7180063317185189562L;
	private String processID;
	private String ID;
	private String name;
	private String startActivityID;
	private String endActivityID;
	private String condition;
	
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
	public String getStartActivityID() {
		return startActivityID;
	}
	public void setStartActivityID(String startActivityID) {
		this.startActivityID = startActivityID;
	}
	public String getEndActivityID() {
		return endActivityID;
	}
	public void setEndActivityID(String endActivityID) {
		this.endActivityID = endActivityID;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	
}
