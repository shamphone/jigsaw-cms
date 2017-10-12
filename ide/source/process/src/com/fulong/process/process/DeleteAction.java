package com.fulong.process.process;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.workflow.ProcessDefinition;
import com.fulong.process.ProcessBaseAction;

/**
 * 
 * <p>Title: Coolink流程模型管理系统--删除流程</p>
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

public class DeleteAction extends ProcessBaseAction{

	protected ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String processID=request.getParameter("processID");
		ProcessDefinition processDefinition=this.getWorkflowService(request).getDefinition(processID);
		
		Writer writer = response.getWriter();
		response.setContentType("text/xml");
        response.setHeader("Content-Type", "text/xml; charset=UTF-8");
        writer.append(processID);
        writer.close();
		
		this.getWorkflowService(request).delete(processDefinition);
		
		return null;
	}

}
