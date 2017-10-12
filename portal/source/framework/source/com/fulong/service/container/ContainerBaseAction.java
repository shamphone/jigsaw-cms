/**
 * 
 */
package com.fulong.service.container;

import com.fulong.common.BaseAction;
import com.fulong.longcon.repository.Repository;
import com.fulong.longcon.workflow.WorkflowService;
import com.fulong.service.ServiceManager;

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
public class ContainerBaseAction extends BaseAction {
	/**
	 * 
	 * @return WorkflowService
	 */
	protected WorkflowService getWorkflowService() {
		return (WorkflowService) this.getBeanFactory().getBean("workflow");
	}

	/**
	 * 
	 * @return ServiceManager
	 */
	protected ServiceManager getServiceManager() {
		return (ServiceManager) getBeanFactory().getBean("serviceManager");
	}

	protected Repository getRepository() {
		return (Repository) getBeanFactory().getBean("repository", Repository.class);
	}
}
