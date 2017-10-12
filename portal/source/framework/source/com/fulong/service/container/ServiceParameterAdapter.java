/**
 * 
 */
package com.fulong.service.container;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Enumeration;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.fulong.longcon.workflow.Parameters;
import com.fulong.longcon.workflow.xml.XMLParameters;
import com.fulong.service.ServiceParameters;

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
public class ServiceParameterAdapter implements ServiceParameters {
	private Parameters original;
	
	public ServiceParameterAdapter(String xml) throws ParserConfigurationException, SAXException, IOException{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.parse(new ByteArrayInputStream(xml.getBytes("UTF-8")));
		this.original = new XMLParameters(document.getDocumentElement());
	}

	public ServiceParameterAdapter(Parameters original) {

		this.original = original;
	}

	/**
 * 
 */
	public String getValue(String name) {
		return this.original.getValue(name);
	}

	public String[] getValues(String name) {
		return this.original.getValues(name);
	}

	public void setValue(String name, String value) {
		this.original.setValue(name, value);

	}

	public void setValues(String name, String[] value) {
		this.original.setValues(name, value);	}
	
	public Enumeration<String> getNames(){
		return this.original.getNames();
	}

}
