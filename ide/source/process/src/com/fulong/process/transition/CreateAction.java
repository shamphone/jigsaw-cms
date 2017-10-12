package com.fulong.process.transition;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.workflow.ProcessDefinition;
import com.fulong.process.ProcessBaseAction;

/**
 * 
 * <p>
 * Title: Coolink流程模型管理系统--创建转移
 * </p>
 * 
 * <p>
 * Description: Coolink流程模型管理系统
 * </p>
 * 
 * 
 * <p>
 * Copyright: Copyright (c)北京中科辅龙计算机技术股份有限公司 2007
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * @author sunyuchao</a>
 * @version 1.0
 */

public class CreateAction extends ProcessBaseAction {

	protected ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			String processID = request.getParameter("processID");
			ProcessDefinition processDefinition=this.getWorkflowService(request).getDefinition(processID);
			request.setAttribute("activities",processDefinition.getActivities());
			request.setAttribute("processID", processID);
		return mapping.findForward("success");
	}

}
