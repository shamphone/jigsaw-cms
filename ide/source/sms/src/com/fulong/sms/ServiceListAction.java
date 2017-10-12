package com.fulong.sms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 服务列表
 * @author liuzijun
 *
 */
public class ServiceListAction extends ServiceBaseAction {

	/* (non-Javadoc)
	 * @see com.fulong.service.ServiceBaseAction#doPerform(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("categoryID");
		request.setAttribute("category", this.getServiceManager(request).getServiceContext(id));
		request.setAttribute("serviceManager", this.getServiceManager(request));
		return mapping.findForward("success");
	}

}
