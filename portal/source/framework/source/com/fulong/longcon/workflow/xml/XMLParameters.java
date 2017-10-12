/**
 * 
 */
package com.fulong.longcon.workflow.xml;

import java.util.Enumeration;
import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.fulong.common.DomUtils;
import com.fulong.longcon.workflow.Parameters;

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
public class XMLParameters implements Parameters {
	
	private Element element;
	private Document document ;
	public XMLParameters(Element element){
		this.element=element;
		this.document = this.element.getOwnerDocument();
	}

	public String getValue(String name) {
		String[] values = this.getValues(name);
		if (values == null)
			return null;
		if (values.length == 0)
			return null;
		return values[0];
	}

	public void setValue(String name, String value) {
		if (value != null)
			this.setValues(name, new String[] { value });
		else
			this.removeParameter(name);
	}

	private void addParameter(String name, String[] values) {
		Element param = null;
		NodeList nodes = element.getElementsByTagName("ActualParameter");
		for (int i = 0; i < nodes.getLength() && (param == null); i++) {
			Element node = (Element) nodes.item(i);
			NodeList nameElems = node.getElementsByTagName("name");
			if (nameElems.getLength() > 0) {
				Element nameElem = (Element) nameElems.item(0);
				String paramName = DomUtils.getValue(nameElem);
				if (paramName.equalsIgnoreCase(name))
					param = node;
			}
		}
		if (param == null) {
			param = document.createElement("ActualParameter");
			element.appendChild(param);
		} else {
			while (param.hasChildNodes())
				param.removeChild(param.getFirstChild());
		}
		Element nameElem = document.createElement("name");
		param.appendChild(nameElem);
		nameElem.appendChild(document.createTextNode(name));
		for (int i = 0; i < values.length; i++) {
			Element valueElem = document.createElement("value");
			param.appendChild(valueElem);
			valueElem.appendChild(document.createTextNode(values[i]));

		}

	}

	private void removeParameter(String name) {
		NodeList nodes = DomUtils.getElements(this.element, "ActualParameters/ActualParameter");
		if (nodes == null)
			return;
		for (int i = 0; i < nodes.getLength(); i++) {
			Element param = (Element) nodes.item(i);
			NodeList nameElems = param.getElementsByTagName("name");
			if (nameElems.getLength() > 0) {
				Element nameElem = (Element) nameElems.item(0);
				String paramName = DomUtils.getValue(nameElem);
				if (paramName.equalsIgnoreCase(name)) {
					param.getParentNode().removeChild(param);
					return;
				}
			}
		}
	}

	public String[] getValues(String name) {
		NodeList nodes = DomUtils.getElements(this.element, "ActualParameters/ActualParameter");
		if (nodes == null)
			return null;
		for (int i = 0; i < nodes.getLength(); i++) {
			Element node = (Element) nodes.item(i);
			NodeList nameElems = node.getElementsByTagName("name");
			if (nameElems.getLength() > 0) {
				Element nameElem = (Element) nameElems.item(0);
				String paramName = DomUtils.getValue(nameElem);
				if(paramName!=null)
				if (paramName.equalsIgnoreCase(name)) {
					NodeList valueElems = node.getElementsByTagName("value");
					String[] result = new String[valueElems.getLength()];
					for (int v = 0; v < valueElems.getLength(); v++)
						result[v] = DomUtils.getValue((Element) valueElems.item(v));
					return result;
				}
			}
		}
		return null;
	}

	public void setValues(String name, String[] values) {
		if ((values != null) && (values.length > 0))
			this.addParameter(name, values);
		else
			this.removeParameter(name);

	}

	public void clear() {
		while(this.element.hasChildNodes())
		this.element.removeChild(this.element.getFirstChild());
		
	}

	public Enumeration<String> getNames() {
		Vector<String> names = new Vector<String>();	
		NodeList nodes = DomUtils.getElements(this.element, "ActualParameters/ActualParameter");		
		if (nodes == null)
			return null;
		for (int i = 0; i < nodes.getLength(); i++) {
			Element node = (Element) nodes.item(i);
			NodeList nameElems = node.getElementsByTagName("name");
			if (nameElems.getLength() > 0) {
				Element nameElem = (Element) nameElems.item(0);
				String paramName = DomUtils.getValue(nameElem);
				names.add(paramName);
			}
		}
		return names.elements();
	}

}
