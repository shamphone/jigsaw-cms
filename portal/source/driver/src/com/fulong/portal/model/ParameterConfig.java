package com.fulong.portal.model;

import java.util.Vector;
import java.io.Serializable;

/**
 * 
 * <p>
 * Title: Longcon Portal Driver
 * </p>
 * 
 * <p>
 * Description: Longcon WebMaster
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Beijing Zhongke Fulong Computer Technology LTD.
 * </p>
 * 
 * @author Lixf
 * @version 1.0
 */
public class ParameterConfig implements Serializable {

	private static final long serialVersionUID = -635382256947597216L;

	private String name;
	private Vector<String> values;

	public ParameterConfig() {
		values = new Vector<String>();
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void addValue(String value) {
		this.values.add(value);
	}

	public String[] getValues() {
		return values.toArray(new String[values.size()]);
	}

	public String getValue() {
		if (values.size() == 0) {
			return null;
		}
		return values.get(0);
	}
}
