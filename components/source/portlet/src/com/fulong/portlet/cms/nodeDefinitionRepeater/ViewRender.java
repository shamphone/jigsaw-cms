package com.fulong.portlet.cms.nodeDefinitionRepeater;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fulong.portlet.cms.ListContentPortletRender;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.NodeDefinitionTreeBuilder;
import javax.portlet.PortletPreferences;

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
		
		//片段的路径
		request.setAttribute("path", getClipPath(request, response, "child"));

		PortletPreferences preferences = request.getPreferences();
		String categoryId = preferences.getValue("category", NodeDefinition.NO_PROPERTIES_SCHEME);
		NodeDefinition nodeDefinition = this.getRepository().getDefinitionManager().getDefinition(categoryId);
		if(preferences.getValue("defSource", "customerSelect").equals("getFromURL")){
			nodeDefinition = (NodeDefinition)request.getAttribute("com.fulong.longcon.nodeDefinition");
		}
		if(nodeDefinition!=null){
			NodeDefinitionTreeBuilder builder = new NodeDefinitionTreeBuilder(nodeDefinition);
			Boolean showroot = preferences.getValue("showrootmsg", "").equalsIgnoreCase("on");
			//显示一级子节点的个数
			String num = preferences.getValue("num", "");
			request.setAttribute("tree", builder.build(showroot,num));
		}
		request.setAttribute("preferences", preferences);
		request.setAttribute("contextName", "/"+request.getPreferences().getValue("contextName", ""));
		return mapping.findForward("success");

	}

}
