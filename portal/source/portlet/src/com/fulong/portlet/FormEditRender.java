package com.fulong.portlet;

import javax.portlet.RenderRequest;

import com.fulong.longcon.repository.NodeDefinition;
import javax.portlet.RenderResponse;
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
 * 此基类适用于必须放置在表单中的占位符，基类方法统一获取definition，如果获取不到直接跳转到noForm页面
 * 
 * @author jiangqi
 * @version 2.0
 */
public abstract class FormEditRender extends PortletRender {

	public abstract ActionForward formRender(NodeDefinition definition, ActionMapping mapping, ActionForm form,
			RenderRequest request, RenderResponse response) throws Exception;

	public ActionForward render(ActionMapping mapping, ActionForm form, RenderRequest request, RenderResponse response)
			throws Exception {
		String definitionId = request.getParameter("formDefinition");
		if (definitionId == null || definitionId.equals("")) {
			return mapping.findForward("noForm");
		}
		NodeDefinition definition = this.getRepository().getDefinitionManager().getDefinition(definitionId);
		if (definition == null || definition.equals("")) {
			return mapping.findForward("noForm");
		}
		String nodeType = request.getParameter("node");
		if (nodeType != null) {
			request.getPreferences().setValue("contentType", nodeType);
		}
		return formRender(definition, mapping, form, request, response);
	}

}
