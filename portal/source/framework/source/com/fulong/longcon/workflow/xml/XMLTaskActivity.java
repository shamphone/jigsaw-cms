/**
 * 
 */
package com.fulong.longcon.workflow.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.fulong.common.DomUtils;
import com.fulong.longcon.workflow.Application;
import com.fulong.longcon.workflow.Parameters;
import com.fulong.longcon.workflow.ProcessDefinition;
import com.fulong.longcon.workflow.TaskActivity;
import com.fulong.longcon.workflow.WorkItem;
import com.fulong.longcon.workflow.WorkflowException;
import com.fulong.longcon.workflow.WorkflowService;

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
public class XMLTaskActivity extends XMLImpActivity implements TaskActivity {
	private Element application;
	private Document document;
	private XMLParameters parameters;
	
	

	public XMLTaskActivity(Element element, ProcessDefinition definition,WorkflowService service) {
		super(element, definition, service);
		document = this.element.getOwnerDocument();
		this.application = DomUtils.getElement(element, "Activity/Implementation/Task/TaskApplication");
		if (application == null)
			throw new WorkflowException("This is not a task activity.");
		NodeList nodes = application.getElementsByTagName("ActualParameters");
		Element params = null;
		if (nodes.getLength() > 0)
			params = (Element) nodes.item(0);
		else {
			params = document.createElement("ActualParameters");
			this.application.appendChild(params);
		}
		this.parameters = new XMLParameters(params);
		
	}

	public Parameters getParameters() {
		return this.parameters;
	}

	public String getAppId() {
		return application.getAttribute("Id");

	}

	public void setAppId(String id) {
		application.setAttribute("id", id);
	}

	/**
	 * 执行完成之后，进入后续活动
	 */
	protected void doExecute(WorkItem node) throws Exception {
		Application app=this.service.getApplicationManager().getApplication(getAppId());
		app.execute(node, getParameters());
	}

}
