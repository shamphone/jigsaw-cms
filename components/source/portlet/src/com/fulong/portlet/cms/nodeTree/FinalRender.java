package com.fulong.portlet.cms.nodeTree;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.fulong.portlet.cms.ListContentPortletRender;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import javax.portlet.PortletPreferences;

import com.fulong.longcon.repository.Node;
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
public class FinalRender extends ListContentPortletRender {

	public ActionForward render(ActionMapping mapping, ActionForm form, RenderRequest request, RenderResponse response)
			throws Exception {
		PortletPreferences preferences = request.getPreferences();
		String nodeId = preferences.getValue("nodeId", "");
		
		Node node = null;
		
		if(preferences.getValue("contentType", "").equals("customnode")){
			node = this.getRepository().getNode(nodeId);
		}else{
			node = lookupNode(request, response);
		}
		String field = preferences.getValue("field", "");
		String path = this.getClipPath(request, response, "");
		
		request.setAttribute("path", path);
		
		NodeTreeBuilder builder = new NodeTreeBuilder(node);
		request.setAttribute("tree", builder.build(field));
		request.setAttribute("preferences", preferences);
		return mapping.findForward("success");
	}
}
