package com.fulong.portlet.cms.nodeDefinitionRepeater;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.fulong.portlet.cms.ListContentPortletRender;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import javax.portlet.PortletPreferences;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.NodeDefinitionIterator;

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
public class FinalRender extends ListContentPortletRender {

	/**
	 * execute
	 * 
	 * @param request
	 *            RenderRequest
	 * @param response
	 *            RenderResponse
	 * @throws Exception
	 */
	public ActionForward render(ActionMapping mapping, ActionForm form, RenderRequest request, RenderResponse response)
			throws Exception {
		
		//片段的路径
		request.setAttribute("childrenPath", getClipPath(request, response, "child"));
		request.setAttribute("parentPath", getClipPath(request, response, "parent"));
		
		PortletPreferences preferences = request.getPreferences();
		String categoryId = preferences.getValue("category", NodeDefinition.NO_PROPERTIES_SCHEME);
		NodeDefinition nodeDefinition = this.getRepository().getDefinitionManager().getDefinition(categoryId);
		if(preferences.getValue("defSource", "customerSelect").equals("getFromURL")){
			nodeDefinition = (NodeDefinition)request.getAttribute("com.fulong.longcon.nodeDefinition");
			if(nodeDefinition==null){
				nodeDefinition = this.getRepository().getDefinitionManager().getDefinition(request.getParameter("definition"));
			}
		}
		//是否显示根节点
		Boolean showrootnode = preferences.getValue("showrootmsg", "").equalsIgnoreCase("on");
		if(nodeDefinition!=null){
			if(showrootnode){
				request.setAttribute("rootDef", nodeDefinition);
			}
			NodeDefinitionIterator defs = nodeDefinition.getInheritDefinitions();
			request.setAttribute("defs", defs);
		}
		
		//显示一级子节点的个数
		request.setAttribute("preferences", preferences);
		return mapping.findForward("success");

	}

}
