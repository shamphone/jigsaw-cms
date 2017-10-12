/**
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2010-2015
 */
package com.fulong.service;

import javax.servlet.ServletRequest;

/**
 * ServiceRequest
 * @author    <a href="lixf@fulong.com.cn">李雄峰</a>
 * @date      2010-6-12
 */
public interface ServiceRequest extends ServletRequest {
	/**
	 * 获取当前服务的配置参数
	 * @return
	 */
	public ServiceParameters getServiceParameters();
}
