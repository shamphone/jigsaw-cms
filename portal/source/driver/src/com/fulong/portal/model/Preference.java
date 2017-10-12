package com.fulong.portal.model;

import java.util.Vector;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import org.apache.struts.taglib.TagUtils;

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

public class Preference implements Serializable {

	private static final long serialVersionUID = 2427148044633830379L;

	private String name;
	private Collection<String> values = new Vector<String>();
	private boolean readOnly;

	/**
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @return boolean
	 */
	public boolean isReadOnly() {
		return readOnly;
	}

	/**
	 * 
	 * @param name
	 *            String
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @param readOnly
	 *            boolean
	 */
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	/**
	 * 
	 * @param value
	 *            String
	 */
	public void addValue(String value) {
		if (value == null)
			value = "";
		this.values.add(value);
	}

	/**
	 * 
	 * @param values
	 *            String[]
	 */
	public void setValues(String[] values) {
		for (int i = 0; i < values.length; i++)
			this.addValue(values[i]);
	}

	/**
	 * 
	 * @return String[]
	 */
	public String[] getValues() {
		return (String[]) values.toArray(new String[values.size()]);
	}

	public String toString() {
		return this.toHTML();
	}

	public String toHTML() {
		StringBuffer buffer = new StringBuffer("<fulong:preference>");
		buffer.append("<fulong:name>").append(this.name).append(
				"</fulong:name>");
		for (Iterator<String> iterator = this.values.iterator(); iterator.hasNext();) {
			buffer.append("<fulong:value>");
			buffer.append(iterator.next().toString());
			buffer.append("</fulong:value>");
		}
		buffer.append("</fulong:preference>");
		return buffer.toString();

	}

	public String toXML() {
		StringBuffer buffer = new StringBuffer("<fulong:preference>");
		buffer.append("<fulong:name>").append(this.name).append(
				"</fulong:name>");
		for (Iterator<String> iterator = this.values.iterator(); iterator.hasNext();) {
			buffer.append("<fulong:value>");
			buffer.append(TagUtils.getInstance().filter(
					iterator.next().toString()));
			buffer.append("</fulong:value>");
		}
		buffer.append("</fulong:preference>");
		return buffer.toString();

	}

}
