/**
 * 
 */
package com.fulong.service.content;

import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.RequestEntity;

import com.fulong.longcon.exchange.DefaultPropertyMap;
import com.fulong.longcon.exchange.DefaultXMLExporter;
import com.fulong.longcon.exchange.XMLExporter;
import com.fulong.longcon.repository.Node;
import com.fulong.service.NodeService;
import com.fulong.service.ServiceException;
import com.fulong.service.ServiceParameters;

/**
 * 
 * 发送内容到远程网站；
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
public class SendNodesService extends NodeService {


	@Override
	public void doProcess(Node node, ServiceParameters parameters, HttpServletRequest request) throws Exception {
		String remoteURL = parameters.getValue("remoteURL");
		String remoteId = parameters.getValue("remoteCategoryID");
		String localURL = parameters.getValue("localURL");
		XMLExporter exporter = new DefaultXMLExporter(node.getRepository(), new URL(localURL));
		if(parameters.getValues("mapping")!=null && parameters.getValues("mapping").length>0 ){
			DefaultPropertyMap map = new DefaultPropertyMap(parameters.getValues("mapping"));
			exporter.setPropertyMap(map);
		}
		exporter.export(node);
		String nodeURL = remoteURL + "/xml/" + remoteId + "/" + node.getID() + ".xml";
		HttpClient client = new HttpClient();
		PutMethod method = new PutMethod(nodeURL);
		// Create a method instance.
		try {
			// Execute the method.
			RequestEntity entity = new InputStreamRequestEntity(exporter.getStream());
			method.setRequestEntity(entity);			
			int statusCode = client.executeMethod(method);
			//操作成功
			if (statusCode >300 ) {
				throw new ServiceException(method.getStatusText());
			}
		} finally {
			// Release the connection.
			method.releaseConnection();
		}

	}

}
