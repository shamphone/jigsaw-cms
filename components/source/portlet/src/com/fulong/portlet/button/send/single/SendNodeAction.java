package com.fulong.portlet.button.send.single;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.workflow.Activity;
import com.fulong.longcon.workflow.ProcessDefinition;
import com.fulong.portlet.PortletAction;
import com.fulong.portlet.PortletNodeWorkItem;

/**
 * <p>
 * Title: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Description: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术有限公司 2006
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术有限公司
 * </p>
 * 
 * @author jiangqi
 * @version 2.0
 */
public class SendNodeAction extends PortletAction {
	/**
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            ActionRequest
	 * @param response
	 *            ActionResponse
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward action(ActionMapping mapping, ActionForm form, ActionRequest request, ActionResponse response)
			throws Exception {
		this.getRepository().getDefinitionManager().getDefinition("definitionID");
		String IDs[] = request.getParameterValues("node");
		for (int i = 0; IDs != null && i < IDs.length; i++) {
			Node node = this.getRepository().getNode(IDs[i]);
			if (node != null) {
				for (int j = 0; j < request.getParameterValues("categorys").length; j++) {
					NodeDefinition target = this.getRepository().getDefinitionManager().getDefinition(
							request.getParameterValues("categorys")[j]);
					node.addMixinDefinition(target);
				}
			}
			String processId=request.getPreferences().getValue("process", "blank");
    		String activityId = request.getPreferences().getValue("activity", "begin");
    		ProcessDefinition definition = this.getWorkflowService().getDefinition(processId);
    		if(definition!=null){
    			Activity activity = definition.getActivity(activityId);
    			if(activity!=null){
    				activity.execute(new PortletNodeWorkItem(node, request, response));
    			}
    		}
		}
		this.redirect(request, response, request.getPreferences().getValue("channel", "index.jsp"));
		return null;

	}

}
