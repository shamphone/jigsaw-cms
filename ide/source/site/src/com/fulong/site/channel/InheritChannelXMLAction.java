package com.fulong.site.channel;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.IteratorUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.fulong.longcon.site.Channel;
import com.fulong.longcon.site.SiteFolder;
import com.fulong.longcon.site.SiteTemplate;
import com.fulong.site.SiteAjaxAction;

/**
 * <p>Title: Coolink平台协同管理系统</p>
 *
 * <p>Description: Coolink平台协同管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2009</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author liuzijun
 * @version 1.0.1
 */
public class InheritChannelXMLAction extends SiteAjaxAction {

	/* (non-Javadoc)
	 * @see com.fulong.site.SiteAjaxAction#renderXML(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public Document renderXML(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String templateName = request.getParameter("template");
		SiteTemplate template = this.getSiteFactory(request).getTemplate(templateName);
		String folderName = request.getParameter("folder");		
		SiteFolder folder ;
		if(folderName==null)
			folder = template.getRootFolder();
		else
			folder = template.getFolder(folderName);
		Document document = this.createDocument();
		Element root = document.createElement("folder");
		root.setAttribute("name", folder.getName());
		root.setAttribute("displayName", folder.getDisplayName());
		root.setAttribute("path", folder.getContextPath());
		document.appendChild(root);		
		for(Iterator<String> iterator = folder.getChannelNames();iterator.hasNext();){
			Channel child = folder.getChannel(iterator.next());
			Element element = document.createElement("channel");
			element.setAttribute("name" , child.getName());
			element.setAttribute("displayName" , child.getDisplayName());
			element.setAttribute("type" , child.getType());
			element.setAttribute("path" , child.getContextPath());
			root.appendChild(element);
		}
		for(Iterator<String> iterator = folder.getChildNames();iterator.hasNext();){
			SiteFolder child = folder.getChild(iterator.next());
			Element element = document.createElement("folder");
			element.setAttribute("name" , child.getName());
			element.setAttribute("displayName" , child.getDisplayName());
			element.setAttribute("path" , child.getContextPath());
			element.setAttribute("channelsNum" , ""+IteratorUtils.toArray(child.getChannelNames()).length);
			element.setAttribute("childrenNum", ""+IteratorUtils.toArray(child.getChildNames()).length);
			root.appendChild(element);
		}
		return document;
	}

}
