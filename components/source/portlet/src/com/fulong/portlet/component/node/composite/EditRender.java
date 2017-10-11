package com.fulong.portlet.component.node.composite;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.portlet.FormEditRender;

/**
 * 复合节点选择占位符
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
 * @author liuzijun
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
		request.setAttribute("properties", definition.propertyDefinitions());
		String propertyId = request.getPreferences().getValue("propertyId", null);
		String categoryId = request.getPreferences().getValue("category", NodeDefinition.NO_PROPERTIES_SCHEME);
		if (propertyId != null && propertyId.length() > 0 && definition.getPropertyDefinition(propertyId)!=null){
			request.setAttribute("property", definition.getPropertyDefinition(propertyId));
		}else if(definition.getPropertyDefinition(propertyId)==null && propertyId!=null){
			request.setAttribute("propertyDeleted",propertyId+"已被删除");
		}

		request.setAttribute("definition", definition.getID());
		NodeDefinition refDefinition = this.getRepository().getDefinitionManager().getDefinition(categoryId);
		request.setAttribute("category", refDefinition);
		String viewPropertyId = request.getPreferences().getValue("viewPropertyId", null);
		if (viewPropertyId != null && viewPropertyId.length() > 0) {
			request.setAttribute("viewProperty", refDefinition.getPropertyDefinition(viewPropertyId));
		}
		String values[] = request.getPreferences().getValues("defaultValues", new String[0]);
		List<Node> list = new ArrayList<Node>();
		for (int i = 0; i < values.length; i++) {
			Node tmp = this.getRepository().getNode(values[i]);
			if (tmp != null)
				list.add(tmp);
		}
		request.setAttribute("values", list);
		request.setAttribute("preferences", request.getPreferences());
		return mapping.findForward("success");
	}
}
