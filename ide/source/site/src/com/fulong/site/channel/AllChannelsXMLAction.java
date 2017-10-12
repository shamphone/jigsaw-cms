/**
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2010-2015
 */
package com.fulong.site.channel;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.fulong.longcon.site.Channel;
import com.fulong.longcon.site.SiteFolder;
import com.fulong.longcon.site.SiteTemplate;
import com.fulong.site.SiteAjaxAction;

/**
 * ChannelsXMLAction
 * 
 * @author <a href="lixf@fulong.com.cn">李雄峰</a>
 * @date 2010-6-15
 */
public class AllChannelsXMLAction extends SiteAjaxAction {

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.fulong.site.SiteAjaxAction#renderXML(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public Document renderXML(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String templateName = request.getParameter("template");
		SiteTemplate template = this.getSiteFactory(request).getTemplate(templateName);
		String type = request.getParameter("type");
		Document document = this.createDocument();
		Element root = document.createElement("template");
		root.setAttribute("name", template.getName());
		document.appendChild(root);
		this.addChannel(document, root, template.getRootFolder(), type);
		return document;
	}

	private void addChannel(Document document, Element root, SiteFolder folder, String type) {

		for (Iterator<String> iterator = folder.getChannelNames(); iterator.hasNext();) {
			Channel child = folder.getChannel(iterator.next());
			if (child.getType().equalsIgnoreCase(type)) {
				Element element = document.createElement("channel");
				element.setAttribute("name", child.getName());
				element.setAttribute("displayName", child.getDisplayName());
				element.setAttribute("type", child.getType());
				element.setAttribute("path", child.getContextPath());
				root.appendChild(element);
			}
		}
		// 递归调用，将子文件夹的栏目添加进来；
		for (Iterator<String> iterator = folder.getChildNames(); iterator.hasNext();) {
			SiteFolder child = folder.getChild(iterator.next());
			addChannel(document, root, child, type);
		}
	}

}
