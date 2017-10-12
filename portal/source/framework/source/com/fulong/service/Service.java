/**
 * 
 */
package com.fulong.service;

import javax.servlet.http.HttpServletRequest;



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
public interface Service {
	public enum State{
		NEW,RUNNABLE,TERMINATED;
	}
	/**
	 * 初始化
	 */
	public void init(ServiceConfig config);	
	/**
	 * 停止服务
	 */
	public void stop();
	
	/**
	 * 激活服务
	 */
	public void activate();
	/**
	 * 当前状态
	 * @return
	 */
	public State getState();	
	
	/**
	 * 编辑服务参数
	 * @param context
	 */
	public void onEdit(ServiceRequest request, ServiceResponse response) throws Exception;
	
	/**
	 * 执行服务
	 * @param event
	 * @throws Exception
	 */
	public void process(ServiceObject event,ServiceParameters parameters,HttpServletRequest request) throws Exception;
}
