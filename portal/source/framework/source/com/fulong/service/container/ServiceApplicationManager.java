/**
 * 
 */
package com.fulong.service.container;

import com.fulong.longcon.workflow.Application;
import com.fulong.longcon.workflow.ApplicationManager;
import com.fulong.service.Service;
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
public class ServiceApplicationManager implements ApplicationManager {
	private ServiceManager manager;
	
	public ServiceApplicationManager(){
		
	}
	
	
	public void setServiceManager(ServiceManager manager){
		this.manager=manager;
	}
	
	
	public Application getApplication(String ID) {
		Service service = manager.getService(ID);
		if(service!=null)
			return new ServiceApplication(service);
		return null;
	}

}
