/**
 * 
 */
package com.fulong.service.container;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import com.fulong.service.Service;
import com.fulong.service.ServiceConfig;
import com.fulong.service.ServiceContext;

/**
 * 
 * 服务运行的上下文环境
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

public class SpringServiceContext extends ServletContextWrapper implements ServiceContext, ServletContext {

	private Map<String, ServiceConfig> configs;
	private SpringServiceWebApplicationContext beanFactory;
	private ServiceCategoryInfo category;
	private String location;
	private static final Log log = LogFactory.getLog(SpringServiceContext.class);

	public SpringServiceContext(ServletContext servletContext, String location) throws BeansException {
		super(servletContext);
		this.configs = new HashMap<String, ServiceConfig>();
		this.location = location;

	}

	/**
	 * 初始化
	 */
	public void init() {
		this.beanFactory = new SpringServiceWebApplicationContext();
		beanFactory.setParent(this.loadParentContext(this.originalContext));
		beanFactory.setServletContext(this.originalContext);
		beanFactory.setConfigLocation(location);
		this.beanFactory.refresh();
		String[] names = beanFactory.getBeanNamesForType(Service.class);
		for (int i = 0; i < names.length; i++) {
			BeanDefinition definition = beanFactory.getBeanDefinition(names[i]);
			try {
				ServiceConfig config = new SpringServiceConfig(this, definition);
				this.configs.put(names[i], config);
				Service service = (Service) beanFactory.getBean(names[i]);
				service.init(config);
				log.info("service " + config.getName() + " ready.");
			} catch (Exception ex) {
				log.error("Error in start service:" + definition.getBeanClassName(), ex);
			}
		}
		try {
			this.category = (ServiceCategoryInfo) beanFactory.getBean(ServiceCategoryInfo.BeanName);
			this.category.setUid(FilenameUtils.getBaseName(this.location));
		} catch (Exception ex) {
			this.category = new ServiceCategoryInfo();
			this.category.setUid(FilenameUtils.getBaseName(this.location));
			this.category.setName(FilenameUtils.getBaseName(this.location));
			this.category.setDescription(FilenameUtils.getBaseName(this.location));
		}
	}

	@SuppressWarnings("unchecked")
	public Map<String, Service> getServices() {
		return this.beanFactory.getBeansOfType(Service.class, false, true);
	}

	/**
	 * 唯一标识
	 * 
	 * @return
	 */
	public String getId() {
		return this.category.getUid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fulong.service.ServiceCategory#getName()
	 */
	public String getName() {
		return category.getName();
	}

	/**
	 * @return
	 */
	public String getDescription() {
		return category.getDescription();
	}

	/**
	 * 获取指定的服务配置
	 */
	public ServiceConfig getServiceConfig(String ID) {
		return this.configs.get(ID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fulong.service.ServiceCategory#getServiceConfigs()
	 */
	public Collection<ServiceConfig> getServiceConfigs() {
		return this.configs.values();
	}

	protected ApplicationContext loadParentContext(ServletContext servletContext) throws BeansException {

		return (ApplicationContext) servletContext.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
	}

}
