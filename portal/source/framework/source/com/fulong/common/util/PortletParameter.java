package com.fulong.common.util;

import java.util.Vector;
import java.util.Arrays;

/**
 * <p>
 * Title: WebMaster Core Library
 * </p>
 * 
 * <p>
 * Description: Coolink
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * 
 * <p>
 * Company: Beijing Zhongke Fulong Computer Tech. LTD
 * </p>
 * 
 * @author liuzijun
 * @version 1.0
 */
public class PortletParameter {
	private String name;
	private String ID;
	private Vector<String> values;
	private int level;

	public PortletParameter() {
		values = new Vector<String>();
		name = null;
		ID = null;
		level = 0;
	}

	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int upgrade() {
		if (this.level > 0)
			this.level--;
		return this.level;
	}

	public int degrade() {
		this.level++;
		return this.level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name == null)
			throw new IllegalArgumentException("name should not be null");
		this.name = name;
	}

	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		if (ID == null)
			throw new IllegalArgumentException("ID should not be null");
		this.ID = ID;
	}

	public void addValue(String value) {
		this.values.add(value);
	}

	public void setValues(String[] values) {
		if (values == null)
			this.values.clear();
		else
			this.values = new Vector<String>(Arrays.asList(values));
	}

	public void removeValue(String value) {
		this.values.remove(value);
	}

	public String[] getValues() {
		return (String[]) values.toArray(new String[values.size()]);
	}

	public String getValue() {
		if (values.size() == 0)
			return null;
		return getValues()[0];
	}

}
