/**
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2010-2015
 */
package com.fulong.site.channel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.fulong.longcon.site.Channel;
import com.fulong.site.SiteAjaxAction;

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
 * @date 2010-6-24	
 * @version 1.0.1
 */
public class RefreshChannelAction extends SiteAjaxAction {

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.fulong.site.SiteAjaxAction#renderXML(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public Document renderXML(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String path = request.getParameter("path");
		Channel channel = this.parseChannel(path,request);
		Document document = this.createDocument();
		Element root = document.createElement("template");
		Element element = document.createElement("channel");
		element.setAttribute("displayName", channel.getDisplayName());
		element.setAttribute("isPublished", ""+channel.isPublished());
		element.setAttribute("type", channel.getType());
		root.appendChild(element);
		document.appendChild(root);
		return document;
	}
}
