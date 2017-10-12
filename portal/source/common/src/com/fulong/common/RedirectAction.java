/**
 * 
 */
package com.fulong.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 
 * 获取指定地址的页面代码
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
public class RedirectAction extends BaseAction {
	@SuppressWarnings("deprecation")
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpClient client = new HttpClient();
		String url = request.getParameter("url");
		HttpMethod method = new GetMethod(url);
		// Create a method instance.
		String timeout=request.getParameter("timeout");
		if(timeout!=null)
			client.setTimeout(Integer.parseInt(timeout));
		try {
			// Execute the method.
			int statusCode = client.executeMethod(method);
			if(statusCode != HttpServletResponse.SC_OK){
				response.sendError(statusCode, method.getStatusText());
				return null;
			}
			Header[] headers= method.getResponseHeaders();
			for(int i=0;i<headers.length;i++){				
				response.addHeader(headers[i].getName(), headers[i].getValue());
			}
			String body = method.getResponseBodyAsString();
			response.getWriter().write(body);
			response.flushBuffer();
			return null;
		} finally {
			// Release the connection.
			method.releaseConnection();
		}
	}
}
