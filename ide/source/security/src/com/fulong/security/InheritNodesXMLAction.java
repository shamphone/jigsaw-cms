package com.fulong.security;

import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.util.RequestUtils;
import org.w3c.dom.Document;

import com.fulong.longcon.exchange.DefaultXMLExporter;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeIterator;

/**
 * 
 * <p>Title: Coolink平台协同管理系统</p>
 *
 * <p>Description: Coolink平台协同管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2009</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author liuzijun
 * @version 3.1
 */
public class InheritNodesXMLAction extends SecurityAjaxAction {

	/* (non-Javadoc)
	 * @see com.fulong.security.SecurityAjaxAction#renderXML(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public Document renderXML(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Node node = this.getRepository(request).getNode(request.getParameter("ID"));
		URL url = RequestUtils.serverURL(request);
		DefaultXMLExporter exporter=new DefaultXMLExporter(this.getRepository(request), url);
		if(node!=null){
			NodeIterator<Node> childNodes = node.getNodes(request.getParameter("childGroup"));
			while(childNodes.hasNext()){
				Node childNode = childNodes.nextNode();
				exporter.export(childNode);
			}
		}
		return exporter.getDocument();
	}
}
