/**
 * 
 */
package com.fulong.service;

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
public interface ServiceConfig {
	
	public ServiceContext getServiceContext();
	
	/**
	 * 唯一标识符
	 * @return
	 */
	public String getId();
	/**
	 * 服务名称
	 * @return
	 */
	public String getName();

	/**
	 * 服务描述
	 * @return
	 */
	public String getDescription();

}
