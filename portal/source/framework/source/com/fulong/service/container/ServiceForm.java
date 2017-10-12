/**
 * 
 */
package com.fulong.service.container;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;

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
public class ServiceForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -416585183337017265L;

	private String processID;
	private String activityID;
	private String serviceID;
	private Map<String, String[]> values;

	public ServiceForm() {
		values = new Hashtable<String, String[]>();
	}

	public String getProcessID() {
		return processID;
	}
	
	public Collection<String> getNames(){
		return this.values.keySet();
	}

	public void setProcessID(String processID) {
		this.processID = processID;
	}

	public String getActivityID() {
		return activityID;
	}

	public void setActivityID(String activityID) {
		this.activityID = activityID;
	}

	public String getServiceID() {
		return serviceID;
	}

	public void setServiceID(String serviceID) {
		this.serviceID = serviceID;
	}

	public String getValue(String name) {
		String[] values = this.values.get(name);
		if ((values == null) || (values.length == 0))
			return null;
		return values[0];
	}

	public String[] getValues(String name) {
		return this.values.get(name);
	}

	public void setValue(String name, String value) {
		if (value != null)
			this.values.put(name, new String[] { value });
		else
			this.values.remove(name);
	}

	public void setValues(String name, String[] values) {
		if (values != null)
			this.values.put(name, values);
		else
			this.values.remove(name);
	}

}
