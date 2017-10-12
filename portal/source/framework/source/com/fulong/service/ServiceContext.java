/**
 * 
 */
package com.fulong.service;

import java.util.Collection;

/**
 *   
 * 服务类别
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
public interface ServiceContext {
	
	
	public String getName();
	/**
	 * 该类别下的所有服务
	 * @return
	 */
	public Collection<ServiceConfig> getServiceConfigs();
	/**
	 * 获取特定ID的配置
	 * @param ID
	 * @return
	 */
	public ServiceConfig getServiceConfig(String ID);
}
