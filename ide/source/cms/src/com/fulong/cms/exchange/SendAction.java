/**
 * 
 */
package com.fulong.cms.exchange;

import java.net.URL;
import java.util.Calendar;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.cms.CMSBaseAction;
import com.fulong.longcon.exchange.DefaultXMLExporter;
import com.fulong.longcon.exchange.XMLExporter;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.longcon.repository.Query;

/**
 *   
 * 发送内容到远程
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
public class SendAction   extends CMSBaseAction {

	/* 
	 * 
	 * @see com.fulong.cms.CMSBaseAction#doPerform(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author lixf
	 * @lastmodified 2009-9-27下午02:42:01
	 */
	@Override
	protected ActionForward doPerform(ActionMapping mapping, ActionForm aform, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("ID");
		NodeDefinition definition=this.getRepository(request).getDefinitionManager().getDefinition(id);
		PropertyDefinition property = definition.getPropertyDefinition("URL");
		String url = property.getDefaultValue().getString();
		property = definition.getPropertyDefinition("remoteId");
		String remoteId =property.getDefaultValue().getString();
		property = definition.getPropertyDefinition("localId");
		String localId = property.getDefaultValue().getString();
		property = definition.getPropertyDefinition("exchangeTime");
	//	Date lastTime = property.getDefaultValue().getDate().getTime();
		
	
		NodeDefinition localDefinition = this.getRepository(request).getDefinitionManager().getDefinition(localId);
		Query query = this.getRepository(request).getQueryManager().createQuery(localDefinition, Query.SQL);
		Iterator<Node> nodes = query.nodes();

		while(nodes.hasNext()){
			Node node = nodes.next();
			node.addMixinDefinition(definition);
			node.setProperty("URL", "");
			node.setProperty("remoteId", node.getID());
			node.setProperty("exchangeTime", Calendar.getInstance());
			
			XMLExporter exporter = new DefaultXMLExporter(this.getRepository(request), new URL(url));
			exporter.export(node);
			String nodeURL = url +"/xml/"+remoteId +"/"+node.getID()+".xml";
			HttpClient client = new HttpClient();
			PutMethod method = new PutMethod(nodeURL);
			// Create a method instance.
			try {
				// Execute the method.	
				RequestEntity entity = new InputStreamRequestEntity(exporter.getStream());
				method.setRequestEntity(entity);
				int statusCode = client.executeMethod(method);
				if(statusCode != HttpServletResponse.SC_OK){
					response.sendError(statusCode, method.getStatusText());
					return null;
				}				
			} finally {
				// Release the connection.
				method.releaseConnection();
			}			
		}		
		return mapping.findForward("success");
	}

}
