/**
 * 
 */
package com.fulong.service.container;

import java.io.ByteArrayInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.fulong.longcon.workflow.xml.XMLParameters;
import com.fulong.service.Service;

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
public class EditAction extends ContainerBaseAction{
	public ActionForward execute(ActionMapping mapping, ActionForm aform, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ServiceForm form = (ServiceForm)aform;
		//ProcessDefinition definition = getWorkflowService().getDefinition(form.getProcessID());
		//TaskActivity activity = (TaskActivity)definition.getActivity(form.getActivityID());
		//ServiceParameterAdapter parameters = new ServiceParameterAdapter(activity.getParameters());
		String serviceParam = request.getParameter("serviceParam");
		if(serviceParam==null||serviceParam.equals("")){
			serviceParam = "<ActualParameters></ActualParameters>";
		}
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		db = dbf.newDocumentBuilder();
		Document document = db.parse(new ByteArrayInputStream(serviceParam.getBytes("UTF-8")));
		NodeList nodes = document.getElementsByTagName("ActualParameters");
		Element params = (Element) nodes.item(0);
		XMLParameters xmlParameters = new XMLParameters(params);
		ServiceParameterAdapter parameters = new ServiceParameterAdapter(xmlParameters);
		Service service = this.getServiceManager().getService(form.getServiceID());		
		HttpServiceRequest servRequest = new HttpServiceRequest(request,parameters);
		HttpServiceResponse servResponse = new HttpServiceResponse(response);		
		request.setAttribute(ServiceForm.class.getName(), form);
		request.setAttribute(Service.class.getName(), this.getServiceManager().getServiceConfig(form.getServiceID()).getName());
		service.onEdit(servRequest,servResponse);
		return null;
	}
	
}
