/**
 * 
 */
package com.fulong.service;

/**
 *   
 * 服务执行日至
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
public interface ServiceLog {
	/**
	 * 执行信息
	 */
	public void info(Object object, String msg);
	/**
	 * 错误信息
	 */
	public void error(Object object, String msg);
}
