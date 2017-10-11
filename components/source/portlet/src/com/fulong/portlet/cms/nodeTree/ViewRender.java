package com.fulong.portlet.cms.nodeTree;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.Node;
import com.fulong.portlet.cms.ListContentPortletRender;

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
 * @author lichengzhao
 * @version 2.0
 */
public class ViewRender extends ListContentPortletRender {
	/**
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            RenderRequest
	 * @param response
	 *            RenderResponse
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward render(ActionMapping mapping, ActionForm form, RenderRequest request, RenderResponse response)
			throws Exception {
		PortletPreferences preferences = request.getPreferences();
		//片段的路径
		request.setAttribute("path", getClipPath(request, response, ""));
		
		//兼容原有设置
		if(request.getPreferences().getValue("isGotoPager", "false").equals("true"))
			request.getPreferences().setValue("show-pager", "isGotoPager");
		String nodeId = preferences.getValue("nodeId", "");
		Node node = null;
		if(preferences.getValue("contentType", "").equals("customnode")){
			node = this.getRepository().getNode(nodeId);
		}else{
			node = lookupNode(request, response);
		}
		if(node!=null){
			NodeTreeBuilder builder = new NodeTreeBuilder(node);
			request.setAttribute("tree", builder.build(preferences.getValue("field", "")));
		}
		
		request.setAttribute("preferences", preferences);
		request.setAttribute("contextName", "/"+request.getPreferences().getValue("contextName", ""));
		return mapping.findForward("success");
	}
}
