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
import com.fulong.longcon.exchange.DefaultPropertyMap;
import com.fulong.longcon.exchange.DefaultXMLExporter;
import com.fulong.longcon.exchange.PropertyMap;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeIterator;

/**
 *   
 * 根据给定的节点ID和复合属性名称，获取所有的子节点的XML
 * 
 * 输入参数:
 *  ID: 父结点ID
 *  
 *  name：复合属性名称
 *  
 *  properties：即将返回的属性集合
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
public class ChildrenXMLAction extends AjaxAction{

	@Override
	public Document renderXML(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Node node = this.getRepository(request).getNode(request.getParameter("ID"));
		String name = request.getParameter("name");
		String [] properties = request.getParameterValues("properties");
		URL url = RequestUtils.serverURL(request);
		DefaultXMLExporter exporter=new DefaultXMLExporter(this.getRepository(request), url);
		exporter.setExportChildren(false);
		exporter.setExportPathes(false);
		exporter.setExportReferences(false);
		if(properties!=null){
			PropertyMap map = new DefaultPropertyMap(new String[0]);
			for(int i=0;i<properties.length;i++){
				map.addPropertyMap(properties[i], properties[i]);
			}
			exporter.setPropertyMap(map);
		}		
		NodeIterator<Node> childNodes = node.getNodes(name);
		while(childNodes.hasNext()){
			Node childNode = childNodes.nextNode();
			exporter.export(childNode);
		}
		return exporter.getDocument();		
	}

}
