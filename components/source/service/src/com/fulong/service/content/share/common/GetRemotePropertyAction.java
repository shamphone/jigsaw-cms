package com.fulong.service.content.share.common;

import java.io.ByteArrayInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.fulong.common.DomUtils;
import com.fulong.common.util.ParameterSet;
import com.fulong.service.ServiceAjaxAction;

/**
 * 
 *   
 * 
 * Coolink协同工作框架模型 
 *
 * Copyright: 2009-2011 北京中科辅龙计算机技术股份有限公司
 *
 * Company: 北京中科辅龙计算机技术股份有限公司
 *
 * @author sunyuchao
 *
 * @version 1.0
 *
 */
public class GetRemotePropertyAction extends ServiceAjaxAction {

	@Override
	public Document renderXML(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String remoteDomain=request.getParameter("remoteDomain");
		String remoteCategoryID=request.getParameter("remoteCategoryID");
		
		String currentRemoteURL = "http://" +remoteDomain
				+ "/service/content/common/getProperty.do?categoryID="
				+ remoteCategoryID;

		HttpClient client = new HttpClient();
		client.getHostConfiguration().setHost(remoteDomain,
				80, "http");
		PostMethod post = new PostMethod(currentRemoteURL);
		post.addRequestHeader("Content-Type",
				"text/html,charset=UTF-8 ");

		int statusCode1 = client.executeMethod(post);
		String xml=null;
		Document document=null;
		ParameterSet set = new ParameterSet();
		if (statusCode1 == HttpStatus.SC_OK) {
			xml=post.getResponseBodyAsString();
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			document = dbf.newDocumentBuilder().parse(new
	                ByteArrayInputStream(xml.
	                                     getBytes("UTF-8")));
			Element element=document.getDocumentElement();
			NodeList nodeList=element.getElementsByTagName("parameter");
			for(int i=0;i<nodeList.getLength();i++){
				Element node=(Element) nodeList.item(i);
				NodeList remotePropIDs=node.getElementsByTagName("name");
				String remotePropID=null;
				if (remotePropIDs.getLength() > 0) {
					Element nameElem = (Element) remotePropIDs.item(0);
					remotePropID = DomUtils.getValue(nameElem);
					
				}
				NodeList remotePropNames=node.getElementsByTagName("value");
				String remotePropName=null;
				if (remotePropNames.getLength() > 0) {
					Element nameElem = (Element) remotePropNames.item(0);
					remotePropName = DomUtils.getValue(nameElem);
				}
				set.put(remotePropID, remotePropName);
			}
		}
        
        response.setHeader("Cache-Control", "no-cache, must-revalidate");
        return set.toDocument();
	}
    
}
