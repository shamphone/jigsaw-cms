/**
 * 
 */
package com.fulong.longcon.expression.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.Resource;

import com.fulong.longcon.expression.Calculator;
import com.fulong.longcon.expression.VariableManager;

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
public class SpringVariableManager  implements VariableManager,BeanFactoryAware {
	private BeanFactory parent;
	private XmlBeanFactory factory;
	private Resource path;
	
	 private static final Log log = LogFactory.getLog(SpringVariableManager.class);	

	public void setBeanFactory(BeanFactory factory) throws BeansException {
		this.parent = factory;

	}
	
	public void setPath(Resource path){
		this.path = path;
	}
	
	public void init (){

		this.factory= new XmlBeanFactory(this.path,parent);
		String[] names = factory.getBeanNamesForType(Calculator.class);
		for (int i = 0; i < names.length; i++){
			Calculator calc = null;
			BeanDefinition definition = factory.getBeanDefinition(names[i]);
			try{
				calc = (Calculator)this.factory.getBean(names[i]);
				log.info("calculator: "+calc.getClass().getName()+" ready. ");				
			}catch(Exception ex){
				log.error("Error in load calculator:" + definition.getBeanClassName(),ex);
			}	
		}	
		log.info("SpringVariableManager ready.");
		
	}
	public Calculator getCalculator(String variable) {
		Calculator calc=(Calculator)this.factory.getBean(variable);
		return calc;
	}

	public String getDisplayName(String variable) {
		return ((BasicCalculator)this.getCalculator(variable)).getName();
	}

	public String[] getVariables() {
		return this.factory.getBeanNamesForType(Calculator.class);
	}

}
