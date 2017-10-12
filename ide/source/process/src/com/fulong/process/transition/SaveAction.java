package com.fulong.process.transition;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.workflow.ProcessDefinition;
import com.fulong.longcon.workflow.Transition;
import com.fulong.process.ProcessBaseAction;
import com.fulong.process.form.TransitionForm;

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
			TransitionForm transitionForm=(TransitionForm)form;
			String processID = transitionForm.getProcessID();
			String transitionID = transitionForm.getID();
			String transitionName = transitionForm.getName();
			String startActivityID = transitionForm.getStartActivityID();
			String endActivityID = transitionForm.getEndActivityID();
			String condition = transitionForm.getCondition();
			ProcessDefinition processDefinition = this.getWorkflowService(request)
					.getDefinition(processID);
			Transition transition = null;
			
			//若transitionID存在，则为修改操作 
			if (transitionID != null) {
				transition=processDefinition.getTransition(transitionID);
			}
			//若transition不存在，则为创建操作 
			if (transition == null) {
				transition = processDefinition.createTransition(null,
						processDefinition.getActivity(startActivityID),
						processDefinition.getActivity(endActivityID));
			}
			transition.setName(transitionName);
			transition.setFrom(processDefinition.getActivity(startActivityID));
			transition.setTo(processDefinition.getActivity(endActivityID));
			transition.setCondition(condition);
			processDefinition.save();
		return mapping.findForward("success");
	}

}
