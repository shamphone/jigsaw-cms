/**
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2010-2015
 */
package com.fulong.taglib.site;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.fulong.portal.core.ServletResponseWrapperInclude;

/**
 * InsertTag
 * @author    <a href="lixf@fulong.com.cn">李雄峰</a>
 * @date      2010-6-10
 */
public class InsertTag extends org.apache.struts.taglib.tiles.InsertTag {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7929485615834419452L;
	private String contextName;
	
	public void setContextName(String contextName) {
		this.contextName = contextName;
	}

	@Override
	protected void doInclude(String page) throws ServletException, IOException {
		ServletContext context = this.getRootContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher(page);
		if(this.contextName!=null&&!this.contextName.equals("")){
			dispatcher = this.pageContext.getServletContext().getContext(this.contextName).getRequestDispatcher(page);
		}
		dispatcher.include(this.pageContext.getRequest(),new ServletResponseWrapperInclude(this.pageContext.getResponse(),this.pageContext.getOut()));
	}
	
	private ServletContext getRootContext() {
		HttpServletRequest request = (HttpServletRequest)this.pageContext.getRequest();
		while(request instanceof HttpServletRequestWrapper){
			request =(HttpServletRequest)((HttpServletRequestWrapper)request).getRequest();
		}
		return request.getSession().getServletContext();
	}

}
