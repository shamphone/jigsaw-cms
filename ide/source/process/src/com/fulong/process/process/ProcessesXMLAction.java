/**
 * 
 */
package com.fulong.process.process;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.IteratorUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.fulong.longcon.workflow.ProcessDefinition;
import com.fulong.process.ProcessBaseXMLAction;

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
public class ProcessesXMLAction extends ProcessBaseXMLAction {

	/* 
	 * 渲染系统已有的流程定义列表。XML符合xpdl文档规范，格式如下：
	 * <WorkflowPrcesses>
	 *    <WorkflowProcess Id="" Name=""/>
	 *    <WorkflowProcess Id="" Name=""/>
	 *    。。。
	 * </WorkflowProcesses>
	 * @see com.fulong.common.AjaxAction#renderXML(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author lixf
	 * @lastmodified 2009-10-29下午01:01:49
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Document renderXML(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String module = request.getParameter("module");
		Document document = this.createDocument();
		Element root = document.createElement("WorkflowProcesses");
		document.appendChild(root);
		Collection<ProcessDefinition> definitions = new ArrayList<ProcessDefinition>();
		if(module!=null){
			definitions.addAll(IteratorUtils.toList(this.getWorkflowService(request).getModuleDefinitions(module)));
			definitions.addAll(IteratorUtils.toList(this.getWorkflowService(request).getModuleDefinitions("components")));
		}else{
			definitions.addAll(IteratorUtils.toList(this.getWorkflowService(request).definitions()));
		}
		for(Iterator<ProcessDefinition> iterator=definitions.iterator();iterator.hasNext();){
			ProcessDefinition process=iterator.next();
			Element element = document.createElement("WorkflowProcess");
			element.setAttribute("Id", process.getId());
			element.setAttribute("Name", process.getName());
			root.appendChild(element);
		}
		return document;
	}

}
