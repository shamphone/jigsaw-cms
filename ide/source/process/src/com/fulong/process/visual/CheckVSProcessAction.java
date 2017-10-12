package com.fulong.process.visual;

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
public class CheckVSProcessAction extends ProcessBaseAction {

	/* (non-Javadoc)
	 * @see com.fulong.process.ProcessBaseAction#doPerform(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		boolean ok = false;
		String processID = request.getParameter("processID");
		ProcessDefinition processDefinition=this.getWorkflowService(request).getDefinition(processID);
		if(processDefinition!=null){
			ok = true;
		}
		
		Writer writer = response.getWriter();
		response.setContentType("text/xml");
        response.setHeader("Content-Type", "text/xml; charset=UTF-8");
        
        if(ok){
        	writer.append("true");
        }else{
        	writer.append("false");
        }        
        writer.close();
		return null;
	}

}
