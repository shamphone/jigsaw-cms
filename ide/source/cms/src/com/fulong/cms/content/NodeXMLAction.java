/**
 * 
 */
package com.fulong.cms.content;

import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.util.RequestUtils;
import org.w3c.dom.Document;

import com.fulong.cms.AjaxAction;
import com.fulong.longcon.exchange.DefaultXMLExporter;
import com.fulong.longcon.repository.Node;

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
public class NodeXMLAction extends AjaxAction{

	@Override
	public Document renderXML(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Node node = this.getRepository(request).getNode(request.getParameter("ID"));
		URL url = RequestUtils.serverURL(request);
		DefaultXMLExporter exporter=new DefaultXMLExporter(this.getRepository(request), url);
		exporter.setExportChildren(!"false".equals(request.getParameter("children")));
		exporter.setExportPathes(request.getParameter("pathes")!=null);
		exporter.setExportReferences(request.getParameter("reference")!=null);
		exporter.export(node);
		return exporter.getDocument();		
	}

}
