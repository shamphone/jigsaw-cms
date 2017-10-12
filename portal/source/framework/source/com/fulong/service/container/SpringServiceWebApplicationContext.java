/**
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2010-2015
 */
package com.fulong.service.container;

import java.io.IOException;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.parsing.PassThroughSourceExtractor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 * SpringServiceWebApplicationContext
 * 
 * @author <a href="lixf@fulong.com.cn">李雄峰</a>
 * @date 2010-6-26
 */
public class SpringServiceWebApplicationContext extends XmlWebApplicationContext {
	private DefaultListableBeanFactory beanFactory;
	
	public SpringServiceWebApplicationContext() {
		super();
	}

	public BeanDefinition getBeanDefinition(String name) {
		return this.beanFactory.getBeanDefinition(name);
	}

	protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws IOException {
		super.loadBeanDefinitions(beanFactory);
		this.beanFactory = beanFactory;
		beanFactory.setCacheBeanMetadata(true);
	}
	protected void initBeanDefinitionReader(XmlBeanDefinitionReader beanDefinitionReader) {
		beanDefinitionReader.setSourceExtractor(new PassThroughSourceExtractor());
	}
	
	
}
