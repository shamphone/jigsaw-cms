/**
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2010-2015
 */
package com.fulong.service.container;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import com.fulong.service.ServiceResponse;

/**
 * HttpServiceResponse
 * @author    <a href="lixf@fulong.com.cn">李雄峰</a>
 * @date      2010-6-13
 */
public class HttpServiceResponse extends HttpServletResponseWrapper implements ServiceResponse {

	public HttpServiceResponse(HttpServletResponse response) {
		super(response);
		
	}

}
