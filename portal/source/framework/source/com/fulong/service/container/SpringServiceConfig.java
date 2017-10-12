/**
 * 
 */
package com.fulong.service.container;

import java.util.Enumeration;
import java.util.Vector;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.config.BeanDefinition;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.fulong.service.ServiceContext;
import com.fulong.service.ServiceConfig;

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
public class SpringServiceConfig implements ServiceConfig,ServletConfig {
	private BeanDefinition definition;
	private ServiceContext context;

	public SpringServiceConfig(ServiceContext category,BeanDefinition definition) {
		this.definition = definition;
		this.context= category;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fulong.service.ServiceConfig#getDescription()
	 */
	public String getDescription() {
		Element element = (Element) this.definition.getSource();
		NodeList nodes = element.getElementsByTagName("description");
		if (nodes.getLength() == 0)
			return null;
		return nodes.item(0).getTextContent();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fulong.service.ServiceConfig#getId()
	 */
	public String getId() {
		Element element = (Element) this.definition.getSource();		
		return element.getAttribute("id");		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fulong.service.ServiceConfig#getName()
	 */
	public String getName() {
		Element element = (Element) this.definition.getSource();
		return element.getAttribute("name");
	}
	
	public String getClassName(){
		return this.definition.getBeanClassName();
	}

	
	@Override
	public ServiceContext getServiceContext() {		
		return this.context;
	}

	@Override
	public String getInitParameter(String arg0) {
		Element element = (Element) this.definition.getSource();		
		NodeList list = element.getElementsByTagName("property");
		for(int i=0;i<list.getLength();i++){
			Element property = (Element)list.item(i);
			if(property.getAttribute("name").equals(arg0))
				return property.getTextContent();
		}
		return null;
	}

	@Override
	public Enumeration<String> getInitParameterNames() {		
		Element element = (Element) this.definition.getSource();
		Vector<String> names = new Vector<String>();
		NodeList list = element.getElementsByTagName("property");
		for(int i=0;i<list.getLength();i++){
			Element property = (Element)list.item(i);
			names.add(property.getAttribute("name"));
		}
		return names.elements();
	}

	@Override
	public ServletContext getServletContext() {
		return (ServletContext)this.getServiceContext();
	}

	@Override
	public String getServletName() {		
		return this.getName();
	}

}
