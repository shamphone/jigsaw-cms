package com.fulong.portlet.cms.nodeDefinitionRepeater;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.portlet.cms.RepeaterEditRender;

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
public class EditRender extends RepeaterEditRender {
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
	public ActionForward editRender(NodeDefinition def, ActionMapping mapping, ActionForm form, RenderRequest request,
			RenderResponse response) throws Exception {
		String childrenPath = iniJspf(request, response,"child");
		request.setAttribute("childrenPath", childrenPath);
		String childrenClipPath = "/"+this.getCurrentSiteTemplate(request, response).getName()+ childrenPath;
		request.setAttribute("childrenClipPath", childrenClipPath);
		
		String parentPath = iniJspf(request, response,"parent");
		request.setAttribute("parentPath", parentPath);
		String parentClipPath = "/"+this.getCurrentSiteTemplate(request, response).getName()+ parentPath;
		request.setAttribute("parentClipPath", parentClipPath);	
		
		return mapping.findForward("success");

	}
}
