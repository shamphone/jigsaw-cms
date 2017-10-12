package com.fulong.process.visual;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.workflow.ProcessDefinition;
import com.fulong.process.ProcessBaseAction;

/**
 * 
 * <p>Title: Coolink平台协同管理系统</p>
 *
 * <p>Description: Coolink平台协同管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2009</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author liuzijun
 * @version 3.1
 */
public class ProcessAction extends ProcessBaseAction {

	/* (non-Javadoc)
	 * @see com.fulong.process.ProcessBaseAction#doPerform(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String processID = request.getParameter("processID");		
		if(processID!=null&&!processID.equals("")){
			ProcessDefinition processDefinition=this.getWorkflowService(request).getDefinition(processID);
			if(processDefinition!=null){
				String graphicValue = processDefinition.getXML();
				request.setAttribute("graphicValue", graphicValue);
				request.setAttribute("process", processDefinition);
			}else{
				request.setAttribute("newProcessID", processID);
			}
		}else{
			for(int i=0;i<100;i++){
				String newProcessID = "newProcess" + i;
				ProcessDefinition oldProcessDefinition=this.getWorkflowService(request).getDefinition(newProcessID);
				if(oldProcessDefinition==null){
					request.setAttribute("newProcessID", newProcessID);
					break;
				}
			}
		}
	
		return mapping.findForward("success");
	}

}
