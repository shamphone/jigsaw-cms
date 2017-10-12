/**
 * 
 */
package com.fulong.sms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

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
public class SuspendAction extends ServiceBaseAction{

	@Override
	protected ActionForward doPerform(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Service service = this.getServiceManager(request).getService(request.getParameter("id"));
		if(!service.getState().equals(Service.State.RUNNABLE)){
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
		service.stop();
		request.setAttribute("service", service);
		return mapping.findForward("success");
	}

}
