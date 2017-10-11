package com.fulong.portlet.button.delete.single;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.portlet.Constants;
import com.fulong.portlet.PortletRender;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

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
public class FinalRender extends PortletRender {
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
		request.setAttribute("node", this.lookupNode(request, response));
		NodeDefinition def = (NodeDefinition) request.getAttribute(Constants.REQUEST_NODEDEFINITION);
		String definition = null;
		if(def!=null)
			definition = def.getID();
		if (definition == null)
			definition = request.getPreferences().getValue("category-id", null);
		request.setAttribute("definition", definition);
		request.setAttribute("preferences", request.getPreferences());
		return mapping.findForward("success");
	}

}