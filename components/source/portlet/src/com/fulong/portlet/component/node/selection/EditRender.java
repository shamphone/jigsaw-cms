package com.fulong.portlet.component.node.selection;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.portlet.FormEditRender;

/**
 * 内容选择表单域占位符
 * 
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
 * @version 3.1
 */
public class EditRender extends FormEditRender {
	/**
	 * execute
	 * 
	 * @param request
	 *            RenderRequest
	 * @param response
	 *            RenderResponse
	 * @throws Exception
	 */
	public ActionForward formRender(NodeDefinition definition, ActionMapping mapping, ActionForm form,
			RenderRequest request, RenderResponse response) throws Exception {
		request.setAttribute("definition", definition.getID());

		request.setAttribute("properties", definition.propertyDefinitions());
		String propertyId = request.getPreferences().getValue("propertyId", null);
		String categoryId = request.getPreferences().getValue("category", NodeDefinition.NO_PROPERTIES_SCHEME);
		if (propertyId != null && propertyId.length() > 0 && definition.getPropertyDefinition(propertyId)!=null){
			request.setAttribute("property", definition.getPropertyDefinition(propertyId));
		}else if(definition.getPropertyDefinition(propertyId)==null && propertyId!=null){
			request.setAttribute("propertyDeleted",propertyId+"已被删除");
		}
		NodeDefinition refDefinition = this.getRepository().getDefinitionManager().getDefinition(categoryId);
		request.setAttribute("category", refDefinition);

		String defaultCategoryId = request.getPreferences().getValue("defaultCategory",
				NodeDefinition.NO_PROPERTIES_SCHEME);
		NodeDefinition defaultCategory = this.getRepository().getDefinitionManager().getDefinition(defaultCategoryId);
		request.setAttribute("defaultCategory", defaultCategory);

		String viewPropertyId = request.getPreferences().getValue("viewPropertyId", null);
		if (viewPropertyId != null && viewPropertyId.length() > 0) {
			request.setAttribute("viewProperty", refDefinition.getPropertyDefinition(viewPropertyId));
		}
		return mapping.findForward("success");
	}
}
