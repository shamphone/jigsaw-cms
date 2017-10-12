/**
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2010-2015
 */
package com.fulong.service.container;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.fulong.service.ServiceParameters;
import com.fulong.service.ServiceRequest;

/**
 * HttpServiceRequest
 * @author    <a href="lixf@fulong.com.cn">李雄峰</a>
 * @date      2010-6-13
 */
public class HttpServiceRequest extends HttpServletRequestWrapper implements ServiceRequest{
private  ServiceParameters parameters;
	public HttpServiceRequest(HttpServletRequest request, ServiceParameters parameters) {
		super(request);
		this.parameters=parameters;
	}
	@Override
	public ServiceParameters getServiceParameters() {
		return this.parameters;
	}

}
