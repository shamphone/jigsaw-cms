/**
 * 
 */
package com.fulong.portlet.filter;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.Property;
import com.fulong.longcon.workflow.Activity;
import com.fulong.portlet.ServletNodeWorkItem;

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
public class LoginFilter extends BasePortletFilter {
	private Log log = LogFactory.getLog(LoginFilter.class);
	public void doHttpFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

		// 调用登入流程
		Activity activity = (Activity) request.getSession(true).getAttribute("com.fulong.login.activity");
		if ((activity != null) && (request.getUserPrincipal() != null)) {
			log.info(request.getUserPrincipal().toString()+" Login.");
			Node user = (Node) request.getUserPrincipal();
			Property property = user.getProperty("lastLoginDate");
			if(property!=null)
				property.setValue(Calendar.getInstance());
			property = user.getProperty("lastLoginIP");
			if(property!=null)
				property.setValue(request.getRemoteAddr());
			property = user.getProperty("loginCount");
			if(property!=null)
				property.setValue(property.getLong()+1);
			try {				
				activity.execute(new ServletNodeWorkItem(user , request, response));
			} catch (Exception e) {
				throw new ServletException(e);
			}
			request.getSession(true).removeAttribute("com.fulong.login.activity");
		}
		chain.doFilter(request, response);
	}
}
