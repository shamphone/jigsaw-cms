package com.fulong.process.activity.route;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.workflow.Activity;
import com.fulong.longcon.workflow.ProcessDefinition;
import com.fulong.process.ProcessBaseAction;
import com.fulong.process.form.RouteActivityForm;

/**
 * 
 * <p>
 * Title: Coolink流程模型管理系统--保存活动编辑结果
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

public class SaveAction extends ProcessBaseAction {

	protected ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		RouteActivityForm activityForm = (RouteActivityForm) form;
		String processID = activityForm.getProcessID();
		String activityName = activityForm.getName();
		String activityID = activityForm.getID();
		Activity activity = null;
		ProcessDefinition processDefinition = this.getWorkflowService(request)
				.getDefinition(processID);
		//若activityID存在，则为修改操作
		if (activityID != null) {
			activity = processDefinition.getActivity(activityID);
			activity.setName(activityName);
		}
		//若activity不存在，则为创建操作
		if (activity == null) {
				activity = processDefinition.createRouteActivity(null,activityName);
		}
		// To do 还应该为自动活动配置除名称以外的参数
		processDefinition.save();
		request.setAttribute("activityID", activity.getId());
		request.setAttribute("activityName", activity.getName());
		return mapping.findForward("success");
	}

}
