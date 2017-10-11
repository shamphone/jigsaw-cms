/**
 * 
 */
package com.fulong.portlet.chart;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.jfree.chart.ChartUtilities;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fulong.longcon.chart.ChartManager;
import com.fulong.longcon.chart.ChartMessage;
import com.fulong.report.BaseServlet;


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
public class ChartPNGServlet extends BaseServlet {
	private ChartManager cache;
	private static final String CONTENT_TYPE= "image/png";
//	private static final String CONTENT_TYPE= "text/xml";
	/**
	 * 
	 */
	private static final long serialVersionUID = -4860445144755265827L;

	public void init() throws ServletException {
		super.init();	
		
		this.cache= (ChartManager)WebApplicationContextUtils.getWebApplicationContext(this.getServletContext()).getBean("chartManager");
		if(this.cache == null )
			throw new ServletException("Unable to find chart manager.");

	}

	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.dispatch(req, resp);
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.dispatch(req, resp);
	}

	protected void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = FilenameUtils.getBaseName(request.getRequestURI());
		if (id == null)
			throw new ServletException("Svn token should not be null.");
		ChartMessage message = this.cache.get(id);		
		if (message == null)
			throw new ServletException("Illegal Image token : " + id);
		response.setContentType(CONTENT_TYPE);
		ChartUtilities.writeChartAsPNG(response.getOutputStream(), message.getChart(), message.getWidth(), message.getHeight());		
		
	}

}
