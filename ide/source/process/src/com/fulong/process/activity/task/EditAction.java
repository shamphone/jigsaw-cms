package com.fulong.process.activity.task;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.process.ProcessBaseAction;

/**
 * 
 * <p>
 * Title: Coolink流程模型管理系统--编辑活动
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

public class EditAction extends ProcessBaseAction {

	protected ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String module = request.getParameter("module");
		if(module!=null&&!module.equals("components")){
			request.setAttribute("template",this.getSiteFactory(request).getTemplate(module));
		}
		request.setAttribute("manager",this.getServiceManager(request));
		
		return mapping.findForward("success");
	}
}
