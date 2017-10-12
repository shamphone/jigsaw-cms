/**
 * 
 */
package com.fulong.cms;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
public abstract class CMSAjaxAction extends CMSBaseAction {
	public abstract Document renderXML(HttpServletRequest request, HttpServletResponse response) throws Exception;

	/**
	 * doPerform
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return ActionForward
	 * @throws Exception
	 * @todo Implement this com.fulong.cms.CMSBaseAction method
	 */

	@Override
	protected ActionForward doPerform(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		response.setContentType("text/xml");
		response.setHeader("Content-Type", "text/xml; charset=UTF-8");
		Writer writer = response.getWriter();
		Document document = this.renderXML(request, response);
		if (document == null) {
			return null;
		}
		OutputFormat format = new OutputFormat(document, "UTF-8", true);
		XMLSerializer xs = new XMLSerializer(writer, format);
		xs.serialize(document);
		writer.close();
		return null;
	}

	protected Document renderEmptyDocument(String status) throws ParserConfigurationException {
		Document document;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		DocumentBuilder db = dbf.newDocumentBuilder();

		document = db.newDocument();
		Element root = document.createElement("status");

		root.appendChild(document.createTextNode(status));
		document.appendChild(root);
		return document;
	}
	
	protected Document createDocument() throws ParserConfigurationException{
		Document document;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		DocumentBuilder db = dbf.newDocumentBuilder();

		document = db.newDocument();
		return document;
 
	}

}
