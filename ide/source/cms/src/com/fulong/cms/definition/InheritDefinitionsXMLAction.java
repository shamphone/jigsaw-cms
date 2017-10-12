/**
 * 
 */
package com.fulong.cms.definition;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.fulong.cms.CMSAjaxAction;
import com.fulong.longcon.repository.NodeDefinition;

/**
 *   
 * 生成Definition和继承的definition的树
 * <definition id="" name="" >
 *     <definition id="" name="" />
 *     <definition id="" name="" />
 *     <definition id="" name="" />
 * </definition>
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
public class InheritDefinitionsXMLAction   extends CMSAjaxAction{
	public Document renderXML(HttpServletRequest request, HttpServletResponse response) throws Exception{
		NodeDefinition definition = this.getRepository(request).getDefinitionManager().getDefinition(request.getParameter("ID"));
		Document document = this.createDocument();
		Element root = document.createElement("definition");
		root.setAttribute("id", definition.getID());
		root.setAttribute("name", definition.getName());
		document.appendChild(root);
		for(Iterator<NodeDefinition> iterator = definition.getInheritDefinitions();iterator.hasNext();){
			NodeDefinition child = iterator.next();
			Element element = document.createElement("definition");
			element.setAttribute("id" , child.getID());
			element.setAttribute("name" , child.getName());
			element.setAttribute("children" , ""+child.getInheritDefinitions(false).getSize());
			root.appendChild(element);			
		}
		return document;
	}

}
