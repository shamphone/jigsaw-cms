/**
 * 
 */
package com.fulong.longcon.workflow;

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
public interface TaskActivity extends ImpActivity {
	/**
	 * 调用的服务的ID;
	 * @return
	 */
	public String getAppId();
	/**
	 * 获取参数设置
	 * @return
	 */
	public Parameters getParameters();
	

}
