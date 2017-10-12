/**
 * 
 */
package com.fulong.service.container;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fulong.service.Service;
import com.fulong.service.ServiceConfig;
import com.fulong.service.ServiceContext;
import com.fulong.service.ServiceManager;

/**
 * 
 * <p>
 * Title: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Description: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术股份有限公司 2006
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * @author lixf
 * 
 * @version 2.0
 */
public class SpringServiceManager implements ServiceManager {
	
	public Map<String, ServiceContext> categories;
	private Map<String, Service> services;
	
	private static final Log log = LogFactory.getLog(SpringServiceManager.class);

	public SpringServiceManager() {		
		this.categories = new HashMap<String, ServiceContext>();
		this.services = new HashMap<String, Service>();
	}

	/*
	 * 获取指定的服务。 从beanFactory中加载
	 * 
	 * @see com.fulong.service.ServiceManager#getService(java.lang.String)
	 */
	public Service getService(String ID) {
		return (Service) this.services.get(ID);
	}

	/**
	 * 获取所有服务
	 * @return
	 */
	public Collection<Service> getServices() {
		return this.services.values();
	}




	public void init() throws IOException {
		log.info("SpringServiceManager ready.");
	}

	/*
	 * 从Web应用中加载所配置的所有服务
	 * 
	 * @see com.fulong.service.ServiceManager#load(javax.servlet.ServletContext)
	 */
	public void load(ServletContext servletContext) {
		File folder = new File(servletContext.getRealPath("/WEB-INF/service"));
		final String postfix = ".services.xml";
		if (folder.exists()) {
			File[] files = folder.listFiles(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return name.endsWith(postfix);
				}
			});
			if (files != null)
				for (int i = 0; i < files.length; i++) {
					try {
						this.load("/WEB-INF/service/"+files[i].getName(), servletContext);
						log.info("[" + servletContext.getServletContextName() + "]Load services from " + files[i].getPath());
					} catch (Exception ex) {
						log.error("[" + servletContext.getServletContextName() + "]Error in load services from file " + files[i].getPath(), ex);
					}
				}
		}
	}

	/**
	 * 加载模块中的指定文件里定义的服务
	 * @param file
	 * @param module
	 * @throws IOException
	 */
	private void load(String location, ServletContext module) throws IOException {
		SpringServiceContext context = new SpringServiceContext(module, location);
		context.init();
		this.categories.put(context.getId(), context);
		this.services.putAll(context.getServices());
		log.info(" Service Category " + context.getName() + " loaded.");
	}

	@Override
	public Collection<ServiceContext> getModuleServiceContexts(String contextPath) {
		List<ServiceContext> contexts = new ArrayList<ServiceContext>();
		for (ServiceContext category : this.categories.values()) {
			SpringServiceContext context = (SpringServiceContext) category;
			if (context.getContextPath().equals(contextPath)){
				contexts.add(context);
			}
		}
		return contexts;
	}

	public Collection<ServiceContext> getServiceCategories() {
		return this.categories.values();
	}
	@Override
	public Collection<ServiceContext> getServiceContexts(){
		return this.categories.values();
	}
	public ServiceContext getServiceCategory(String id) {
		return this.categories.get(id);
	}
	
	@Override
	public ServiceContext getServiceContext(String id) {
		return this.categories.get(id);
	}
	
	@Override
	public ServiceConfig getServiceConfig(String ID) {
		for (ServiceContext category : this.categories.values()) {
			ServiceConfig config = category.getServiceConfig(ID);
			if (config != null)
				return config;
		}
		return null;
	}

}
