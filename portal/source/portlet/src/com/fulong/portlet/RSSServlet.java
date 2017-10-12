package com.fulong.portlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * Title: WebMaster Core Library
 * </p>
 * 
 * <p>
 * Description: Longcon WebMaster SV3
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Beijing Zhongke Fulong Computer Tech. LTD
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class RSSServlet {
	protected void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		/*
		 * String[] servletPath = request.getServletPath().split("/"); if
		 * (servletPath.length != 4) { log.error("Unknown servlet path " +
		 * request.getServletPath()); this.getServletContext()
		 * .getRequestDispatcher("/noPage.jsp") .forward(request, response);
		 * return; } String siteId = servletPath[1]; String repositoryId =
		 * servletPath[2]; String rssId = servletPath[3].substring(0,
		 * servletPath[3].length() - ".rss".length());
		 * 
		 * Site site = this.factory.findByRoot(siteId); NativeRepository
		 * repository = (NativeRepository) site .getContentStore()
		 * .getRepository(repositoryId); StringBuffer serverName = new
		 * StringBuffer(); serverName.append("http://" +
		 * request.getServerName()); if (request.getServerPort() != 80)
		 * serverName.append(":" + request.getServerPort()); Document document =
		 * repository .getRSSChannel(rssId) .render(serverName.toString());
		 * response.setContentType("text/xml");
		 * response.setHeader("Content-Type", "text/xml; charset=UTF-8"); Writer
		 * writer = response.getWriter();
		 * 
		 * OutputFormat format = new OutputFormat(document, "UTF-8", true);
		 * XMLSerializer xs = new XMLSerializer(writer, format);
		 * xs.serialize(document); writer.close();
		 */

	}

}
