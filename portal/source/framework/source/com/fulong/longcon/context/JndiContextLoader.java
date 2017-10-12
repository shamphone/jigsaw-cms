/**
 * 
 */
package com.fulong.longcon.context;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;

import javax.servlet.ServletContext;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.AbstractRefreshableConfigApplicationContext;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.fulong.longcon.site.SiteFactory;
import com.fulong.longcon.workflow.WorkflowService;
import com.fulong.service.ServiceManager;

/**
 * <p>
 * Title: JndiContextLoader
 * </p>
 * <p>
 * Description: Coolink协同工作支撑平台
 * </p>
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * @author lixf
 * @date 2010-5-21
 */
public class JndiContextLoader extends ContextLoader {
	public static final String PARENT_CONTEXT_JNDI_NAME = "parentContextJndiName";
	public static final String[] excludesTemp = { "ide", "portal",
			"components", "root", "monitor" ,"cache","imserver","xml","resources","webdav"};

	private ServletContext context;
	private BeanFactory parent;
	private String name;

	/**
	 * 系统启动；在对应的Webapp启动时加载这个Template;
	 */
	@Override
	public WebApplicationContext initWebApplicationContext(
			ServletContext servletContext) throws IllegalStateException,
			BeansException {
		WebApplicationContext context = super
				.initWebApplicationContext(servletContext);
		this.context = servletContext;
		this.name = this.parseContextName(servletContext);
		// 加载服务；
		this.processServices(servletContext);
		// 加载工作流；
		this.processWorkflows(servletContext);

		// 注册网站模板
		if (!ArrayUtils.contains(excludesTemp, this.name)) {

			this.processTemplate(servletContext);
		}

		// this.context.setAttribute(SiteTemplate.class.getName(), this);

		return context;
	}

	protected void processTemplate(ServletContext servletContext) {
		SiteFactory factory = (SiteFactory) this.parent.getBean("siteFactory");
		try {
			String templateName = this.parseContextName(servletContext);
			if (templateName != null && !templateName.equals("")
					&& !templateName.startsWith(".")) {
				factory.registerTemplate(templateName, new File(this.context
						.getRealPath("")));
			}

		} catch (IOException e) {
			throw new IllegalStateException(e);
		}

	}

	/**
	 * 加载服务
	 * 
	 * @param servletContext
	 * @throws IOException
	 */
	protected void processServices(ServletContext servletContext) {
		ServiceManager service = (ServiceManager) this.parent
				.getBean("serviceManager");
		try {
			service.load(servletContext);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * 加载工作流
	 * 
	 * @param servletContext
	 */
	protected void processWorkflows(ServletContext servletContext) {
		WorkflowService service = (WorkflowService) this.parent
				.getBean("workflow");
		try {
			service.load(servletContext);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * 加载公用设置
	 */
	@Override
	protected ApplicationContext loadParentContext(ServletContext servletContext)
			throws BeansException {
		String jndiName = servletContext
				.getInitParameter(PARENT_CONTEXT_JNDI_NAME);
		if (jndiName == null)
			jndiName = "java:comp/env/coolink/root";
		JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
		bean.setJndiName(jndiName);
		try {
			bean.afterPropertiesSet();
		} catch (Exception e) {
			throw new FatalBeanException(e.getMessage(), e);
		}
		AbstractRefreshableConfigApplicationContext parent = (AbstractRefreshableConfigApplicationContext) bean
				.getObject();
		if (!parent.isActive())
			parent.refresh();
		this.parent = parent;
		return parent;
	}

	public ServletContext getServletContext() {
		return this.context;
	}

	/**
	 * 处理template name,从加载的webapp中获取；
	 * 
	 * @param context
	 * @return
	 */
	private String parseContextName(ServletContext context)
			throws IllegalStateException {
		try {
			Method method = context.getClass().getMethod("getContextPath");
			if (method != null) {
				// 仅用于JEE 1.5/tomcat6.0以上版本
				/*
				 * String name = context.getContextPath(); name =
				 * name.substring(1); return name;
				 */
				String name = (String) method.invoke(context);
				if (name == null || name.length() == 0)
					return "";
				return name.substring(1);
			}
		} catch (Throwable ex) {
			// just ignore this exception;
		}

		// 适用于所有版本，但性能较差
		String name = "";
		try {
			name = context.getResource("/index.jsp").getFile();
		} catch (MalformedURLException e) {
			throw new IllegalStateException("No index.jsp found, ["
					+ context.getServletContextName()
					+ "] is not a coolink template.");
		}
		String[] splits = name.split("[\\\\/]");
		return splits[splits.length - 2];

	}

	@Override
	public void closeWebApplicationContext(ServletContext servletContext) {
		super.closeWebApplicationContext(servletContext);
		if (this.name.equals("portal")&&this.parent instanceof ConfigurableApplicationContext) {
			((ConfigurableApplicationContext) this.parent).close();
		}
	}
	
}
