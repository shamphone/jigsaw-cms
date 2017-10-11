package com.fulong.service.content.share.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.security.User;
import com.fulong.service.ServiceBaseAction;
import com.fulong.service.container.ServiceForm;

/**
 * 
 *   
 * 
 * Coolink协同工作框架模型 
 *
 * Copyright: 2009-2011 北京中科辅龙计算机技术股份有限公司
 *
 * Company: 北京中科辅龙计算机技术股份有限公司
 *
 * @author sunyuchao
 *
 * @version 1.0
 *
 */

public class InsertSiteAction extends ServiceBaseAction {
	protected ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ServiceForm sform = (ServiceForm) form;
		User user = this.getPassportProvider().getUser("1000000000000");

		request.setAttribute("srcUsername", user.getUsername());
		request.setAttribute("srcPassword", user.getPassword());
		request.setAttribute("categoryID", sform.getValue("localCategoryID"));
		request.setAttribute("URL", sform.getValue("remoteURL"));
		request.setAttribute("remoteSiteName", sform.getValue("remoteSiteName"));
		request.setAttribute("processID", sform.getProcessID());
		request.setAttribute("activityID", sform.getActivityID());
		request.setAttribute("serviceID", sform.getServiceID());
		return mapping.findForward("success");
	}
}
