package com.fulong.common.util;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.collections.IteratorUtils;
import org.apache.struts.util.LabelValueBean;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
public class PortletParameterSet implements Cloneable, Serializable {

	private static final long serialVersionUID = 388536792712993885L;

	private Map<String, PortletParameter> parameters;

	@SuppressWarnings("unchecked")
	public PortletParameterSet() {
		parameters = Collections.synchronizedMap(new LinkedHashMap());
	}

	public void clear() {
		this.parameters.clear();
	}

	public void put(String name, String[] values) {
		if (name == null)
			throw new IllegalArgumentException("name should not be null");
		PortletParameter pref = new PortletParameter();
		pref.setName(name);
		pref.setValues(values);
		put(pref);
	}

	public void put(String name, String[] values, String ID) {
		if (name == null)
			throw new IllegalArgumentException("name should not be null");
		PortletParameter pref = new PortletParameter();
		pref.setName(name);
		pref.setValues(values);
		pref.setID(ID);
		put(pref);
	}

	public void put(String name, String[] values, int level) {
		if (name == null)
			throw new IllegalArgumentException("name should not be null");
		PortletParameter pref = new PortletParameter();
		pref.setName(name);
		pref.setValues(values);
		pref.setLevel(level);
		put(pref);
	}

	public PortletParameter get(String name) {
		return (PortletParameter) this.parameters.get(name);
	}

	public PortletParameter getParameter(String name) {
		return (PortletParameter) this.parameters.get(name);
	}

	public PortletParameter getParameter(int index) {
		if (index >= this.parameters.size())
			return null;
		String key = (String) IteratorUtils.toList(
				this.parameters.keySet().iterator()).get(index);
		return (PortletParameter) this.parameters.get(key);
	}

	public PortletParameterSet getRoot() {
		PortletParameterSet set = new PortletParameterSet();
		for (Iterator<String> names = this.parameters.keySet().iterator(); names
				.hasNext();) {
			String name = (String) names.next();
			PortletParameter param = (PortletParameter) this.parameters
					.get(name);
			if (param.getLevel() == 0)
				set.put(param);
		}
		return set;
	}

	public int getMaxLevel() {
		int level = 0;
		for (Iterator<String> names = this.parameters.keySet().iterator(); names
				.hasNext();) {
			String name = (String) names.next();
			PortletParameter current = (PortletParameter) this.parameters
					.get(name);
			if (current.getLevel() > level)
				level = current.getLevel();
		}
		return level;
	}

	public boolean isLeaf(String name) {
		PortletParameter current = (PortletParameter) this.parameters.get(name);
		if (current != null) {
			return current.getLevel() == this.getMaxLevel();
		}
		return false;
	}

	public PortletParameter getParent(String key) {
		if ((key == null) || key.length() == 0)
			return null;
		PortletParameter child = this.get(key);
		if (child == null)
			return null;
		String[] keys = (String[]) this.parameters.keySet().toArray(
				new String[this.parameters.size()]);
		if (child.getLevel() == 0)
			return null;
		int parentLevel = child.getLevel() - 1;
		boolean start = false;
		for (int i = keys.length - 1; i >= 0; i--) {
			PortletParameter parent = (PortletParameter) this.parameters
					.get(keys[i]);
			if (parent.getName().equals(key))
				start = true;
			else if (start && (parent.getLevel() == parentLevel))
				return parent;
		}
		return null;
	}

	public PortletParameterSet getParents(String key) {
		Vector<PortletParameter> vec = new Vector<PortletParameter>();
		PortletParameter parent = this.getParent(key);
		while (parent != null) {
			vec.add(0, parent);
			parent = this.getParent(parent.getName());
		}
		PortletParameterSet set = new PortletParameterSet();
		for (int i = 0; i < vec.size(); i++) {
			set.put((PortletParameter) vec.get(i));
		}
		return set;
	}

	/**
	 * 
	 * @param name
	 *            String
	 * @return ParameterSet
	 */
	public PortletParameterSet getSubLevel(String key) {
		if ((key == null) || key.length() == 0)
			return this.getRoot();
		PortletParameter root = this.get(key);
		if (root == null)
			return null;
		PortletParameterSet set = new PortletParameterSet();
		boolean start = false;
		boolean stop = false;
		int level = root.getLevel();
		for (Iterator<String> names = this.parameters.keySet().iterator(); names.hasNext() && !stop;) {
			String name = (String) names.next();
			PortletParameter current = (PortletParameter) this.parameters
					.get(name);
			if (name.equals(key))
				start = true;
			else if (start && (current.getLevel() <= level))
				stop = true;
			else if (start && (current.getLevel() == level + 1))
				set.put(current);
		}
		return set;
	}

	public void put(String name, String value) {
		if (name == null)
			throw new IllegalArgumentException("name should not be null");
		this.put(name, new String[] { value });
	}

	public void put(String name, String value, String ID) {
		if (name == null)
			throw new IllegalArgumentException("name should not be null");
		this.put(name, new String[] { value }, ID);
	}

	public void put(String name, String value, int level) {
		if (name == null)
			throw new IllegalArgumentException("name should not be null");
		this.put(name, new String[] { value }, level);
	}

	public void add(String name, String value) {
		if (name == null)
			throw new IllegalArgumentException("name should not be null");

		PortletParameter param = (PortletParameter) this.parameters.get(name);
		if (param == null)
			this.put(name, value);
		else
			param.addValue(value);

	}

	public void put(PortletParameter parameter) {
		if (parameter.getName() == null)
			throw new IllegalArgumentException("name should not be null.");
		parameters.put(parameter.getName(), parameter);
	}

	public void putAll(PortletParameterSet set) {
		if (set != null) {
			this.parameters.putAll(set.parameters);
		}
	}

	@SuppressWarnings("unchecked")
	public void putAll(Map options) {
		Iterator keys = options.keySet().iterator();
		while (keys.hasNext()) {
			PortletParameter option = new PortletParameter();
			String oname = (String) keys.next();
			String ovalue = (String) options.get(oname);
			option.setName(oname);
			option.addValue(ovalue);
			put(option);
		}
	}

	public String[] getValues(String name) {
		PortletParameter parameter = (PortletParameter) this.parameters.get(name);
		if (parameter != null) {
			return parameter.getValues();
		}
		
		return null;
	}

	public String getID(String name) {
		PortletParameter parameter = (PortletParameter) this.parameters.get(name);
		if (parameter != null) {
			return parameter.getID();
		}
		return null;
	}

	public String getValue(String name) {
		String[] values = this.getValues(name);
		if ((values == null) || (values.length == 0))
			return null;
		
		return values[0];
	}

	@SuppressWarnings("unchecked")
	public Iterator getNames() {
		return names();
	}

	public Iterator<String> names() {
		return this.parameters.keySet().iterator();
	}

	public Iterator<PortletParameter> iterator() {
		return this.parameters.values().iterator();
	}

	public Map<String, PortletParameter> getMap() {
		return this.parameters;
	}

	public int size() {
		return this.parameters.size();
	}

	public int getSize() {
		return this.parameters.size();
	}

	public void remove(String name) {
		this.parameters.remove(name);
	}

	public Document toDocument() throws ParserConfigurationException {
		Document document;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		DocumentBuilder db = null;

		db = dbf.newDocumentBuilder();

		document = db.newDocument();
		Element root = document.createElement("parameters");
		document.appendChild(root);

		Iterator<String> iterator = this.parameters.keySet().iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			String[] values = this.getValues(key);
			Element parameter = document.createElement("parameter");
			root.appendChild(parameter);
			Element name = document.createElement("name");
			name.appendChild(document.createTextNode(key));
			parameter.appendChild(name);
			Element level = document.createElement("level");
			level.appendChild(document.createTextNode("" + level));
			parameter.appendChild(level);
			for (int i = 0; i < values.length; i++) {
				Element value = document.createElement("value");
				value.appendChild(document.createTextNode(values[i]));
				parameter.appendChild(value);
			}
			Element nodeID = document.createElement("ID");
			nodeID.appendChild(document.createTextNode("" + this.getID(key)));
			parameter.appendChild(nodeID);
		}
		return document;

	}

	public String toXML() throws IOException, ParserConfigurationException {
		Document document = this.toDocument();
		OutputFormat format = new OutputFormat(document, "UTF-8", true);

		Writer writer = new StringWriter();
		XMLSerializer xs = new XMLSerializer(writer, format);
		xs.serialize(document);
		writer.close();
		return writer.toString();

	}

	public LabelValueBean[] toLabelValueBean() {
		LabelValueBean[] bean = new LabelValueBean[this.parameters.size()];
		int index = 0;
		Iterator<String> names = this.parameters.keySet().iterator();
		while (names.hasNext()) {
			String name = (String) names.next();
			String value = this.getValue(name);
			bean[index++] = new LabelValueBean(value, name);
		}
		return bean;
	}
}
