package com.fulong.common;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>Title: Coolink平台协同管理系统</p>
 *
 * <p>Description: Coolink平台协同管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2010</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author luobin
 * @date 2010-12-10	
 * @version 1.0.1
 */
public class WebDavServlet extends HttpServlet {

	private static final long serialVersionUID = -1041996772219615594L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String path = req.getRequestURI();
		path = path.substring("/portal/xml".length());
		RequestDispatcher dispatcher = this.getServletContext().getContext("/xml").getRequestDispatcher(path);
		dispatcher.forward(req, resp);
	}
	
}
