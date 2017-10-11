package com.fulong.portlet.button.copy.multiple;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.RepositoryException;
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
public class CopyNodeAction extends PortletAction {
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
		//支持多分类复制 by mali 2010-7-9
		String IDs[] = request.getParameterValues("node");
		List<NodeDefinition> targets = new ArrayList<NodeDefinition>();
		String categorys[] = request.getParameterValues("category");
		for(int i = 0; categorys != null && i < categorys.length; i++){
			targets.add(this.getRepository().getDefinitionManager().getDefinition(
					categorys[i]));
		}
		String processId=request.getPreferences().getValue("process", "blank");
		String activityId = request.getPreferences().getValue("activity", "begin");
		for (int j = 0; IDs != null && j < IDs.length; j++) {
			Node node = this.getRepository().getNode(IDs[j]);
			Node newNode = null;
			if (node != null) {
				for(Iterator<NodeDefinition> i = targets.iterator(); i.hasNext();){
					try {
						newNode = node.clone();
						newNode.setDefinition(i.next());
					} catch (RepositoryException ex) {
	
					}
				}
			}
			ProcessDefinition definition = this.getWorkflowService().getDefinition(processId);
			if(definition!=null){
				Activity activity = definition.getActivity(activityId);
				if(activity!=null){
					activity.execute(new PortletNodeWorkItem(newNode, request, response));
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
