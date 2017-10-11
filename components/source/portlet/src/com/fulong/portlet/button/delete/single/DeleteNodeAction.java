package com.fulong.portlet.button.delete.single;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;

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
 * @author lixf
 * @version 2.0
 */
public class DeleteNodeAction extends PortletAction {
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
		PortletPreferences preferences = request.getPreferences();
		String method = preferences.getValue("method", "deleteNode");
		String IDs[] = request.getParameterValues("node");
		for (int i = 0; IDs != null && i < IDs.length; i++) {
			Node node = this.getRepository().getNode(IDs[i]);
			if (node != null) {
				if (method.equals("deleteNode")) {
		    		//执行活动，下面为范例代码，未和具体数据相关联		
		        	String processId=request.getPreferences().getValue("process", "blank");
		    		String activityId = request.getPreferences().getValue("activity", "begin");
		    		ProcessDefinition definition = this.getWorkflowService().getDefinition(processId);
		    		if(definition!=null){
		    			Activity activity = definition.getActivity(activityId);
		    			if(activity!=null){
		    				activity.execute(new PortletNodeWorkItem(node, request, response));
		    			}
		    		}
					this.getRepository().delete(node);
//					NodeEvent event = new NodeEvent(node, NodeEvent.NODE_DELETING);
//					this.getServiceManager().dispatch(event);
				} else if (method.equals("deleteLink")) {
					String definitionID = request.getParameter("definition");
					NodeDefinition definition = this.getRepository().getDefinitionManager().getDefinition(definitionID);
					node.removeMixin(definition);
				}

			}
		}
		String forwardSelf = request.getPreferences().getValue("forwardSelf", "false");
		if(forwardSelf.equals("false")){
			String path = request.getPreferences().getValue("channel", "");
			if(path!=null&&!path.equals("")){
				response.sendRedirect(path);
			}else{
				this.redirect(request, response, "index.jsp");
			}
		}else{
			response.sendRedirect(request.getParameter("selfURL"));
		}
		
		return null;

	}

}
