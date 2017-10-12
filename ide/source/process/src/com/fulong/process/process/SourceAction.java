/**
 * 
 */
package com.fulong.process.process;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.process.ProcessBaseAction;

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
public class SourceAction  extends ProcessBaseAction{

	protected ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	String id = request.getParameter("id");
	String xml = this.getWorkflowService(request).getDefinition(id).getXML();
	response.setContentType("text/xml");
	response.setCharacterEncoding("UTF-8");
	response.getWriter().println(xml);
	return null;
	}

}

