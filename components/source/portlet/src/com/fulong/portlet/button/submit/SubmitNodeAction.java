package com.fulong.portlet.button.submit;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.expression.FilterParser;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.RepositoryException;
import com.fulong.longcon.workflow.Activity;
import com.fulong.longcon.workflow.ProcessDefinition;
import com.fulong.portlet.PortletNodeWorkItem;
import com.fulong.portlet.button.ButtionPortletAction;

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
 * @author lixf
 * @version 2.0
 */
public class SubmitNodeAction extends ButtionPortletAction {
	protected final Log log = LogFactory.getLog(this.getClass());

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
		PortletConfig config = new PortletConfig(request.getPreferences());
		Node node = this.lookupNode(request, response);
		if (node == null) {
			return mapping.findForward("noNode");
		}

		String values[] = config.getDefaultValues();
		for (int i = 0; i < values.length; i++) {
			FilterParser parser = this.getFilterParser(request, response);
			parser.parser(values[i]);			
			node.setProperty(parser.getPropertyDefinition(), parser.getValue());

		}

		/**
		 * 内容操作
		 */
		List<NodeDefinition> copyCategorys = this.lookupDefinitions(request, "copyTo");
		for (int i = 0; i < copyCategorys.size(); i++) {
			Node newNode = node.clone();
			newNode.setDefinition(copyCategorys.get(i));
		}
		List<NodeDefinition> moveCategorys = this.lookupDefinitions(request, "moveTo");
		for (int i = 0; i < moveCategorys.size(); i++) {
			Node newNode = node.clone();
			newNode.setDefinition(copyCategorys.get(i));
			this.getRepository().delete(node);
		}
		List<NodeDefinition> recommendCategorys = this.lookupDefinitions(request, "recommendTo");
		for (int i = 0; i < recommendCategorys.size(); i++) {
			try {
				node.addMixinDefinition(copyCategorys.get(i));
			} catch (RepositoryException ex) {

			}
		}
		//执行活动，下面为范例代码，未和具体数据相关联		
		ProcessDefinition definition = this.getWorkflowService().getDefinition("basicRegister");
		Activity activity = definition.getActivity("register");
		activity.execute(new PortletNodeWorkItem(node, request, response));
		
		String path = request.getPreferences().getValue("channel", "");
		if(path!=null&&!path.equals("")){
			response.sendRedirect(path+"?contentId="+node.getID());
		}else{
			this.redirect(request, response, "index.jsp", node.getID());
		}
		return null;
	}
}
