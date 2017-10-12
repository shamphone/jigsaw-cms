package com.fulong.process.process;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.workflow.ProcessDefinition;
import com.fulong.process.ProcessBaseAction;
import com.fulong.process.form.ProcessForm;

/**
 * 
 * <p>Title: Coolink流程模型管理系统统--保存流程编辑结果</p>
 *
 * <p>Description: Coolink流程模型管理系统</p>
 *
 *
 * <p>Copyright: Copyright (c)北京中科辅龙计算机技术股份有限公司 2007</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author sunyuchao</a>
 * @version 1.0
 */


public class SaveAction extends ProcessBaseAction{

	protected ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ProcessForm processForm=(ProcessForm)form;
		String processID=processForm.getID();
		String processName=processForm.getName();
		String processDescription=processForm.getDiscription();
		ProcessDefinition process=this.getWorkflowService(request).getDefinition(processID);
		if(process==null){
			//process=this.getWorkflowService(request).create(processID);
		}
		process.setName(processName);
		process.setDescription(processDescription);
		process.save();
		request.setAttribute("processID", processID);
		request.setAttribute("processName", processName);
		return mapping.findForward("success");
	}

}
