package com.fulong.portlet.component.select.cascade;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.longcon.repository.Property;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.longcon.repository.Query;
import com.fulong.longcon.repository.Value;
import com.fulong.longcon.repository.value.ReferenceValue;
import com.fulong.portlet.PortletRender;

/**
 * 下拉框表单域占位符
 * 
 * <p>
 * Title: 龙驭门户引擎
 * </p>
 * 
 * <p>
 * Description: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * @author lichengzhao
 * @version 3.1
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

	@SuppressWarnings("unchecked")
	public ActionForward render(ActionMapping mapping, ActionForm form, RenderRequest request, RenderResponse response)
			throws Exception {
		Node node = this.lookupNode(request, response);
		PortletPreferences preferences = request.getPreferences();
		String definitionId = preferences.getValue("definitionId", null);
		String referenceId = preferences.getValue("referenceId", null);
		String echo = preferences.getValue("echo", "nodeEcho");

		NodeDefinition definition = this.getRepository().getDefinitionManager().getDefinition(definitionId);
		if(definition!=null){
			PropertyDefinition propertyDef = definition.getPropertyDefinition(referenceId);
			NodeDefinition reference = propertyDef.getReferenceDefinition();

			Query query = this.getRepository().getQueryManager().createQuery(reference, Query.SQL);
			query.filterByParent(this.getPassportProvider().getDefaultOrganization(), false);
			NodeIterator nodes = query.nodes();
			request.setAttribute("nodes", nodes);

			Node defaultValue = null;
			if(echo.equals("nodeEcho")){  //用于编辑页回显
			if (node != null) {
				Property property = node.getProperty(referenceId);
				if (property != null) {
					Value value = property.getValue();
					if(value!=null){
						Node tmp = ((ReferenceValue) value).getReference();
						if (tmp != null)
							defaultValue = tmp;
					}				
				}
			} else {
				String sValue = request.getPreferences().getValue("defaultValue", "");
				if (sValue.equals("user")) {
					defaultValue = (Node) request.getUserPrincipal();
				} else {
					if(!sValue.equals("")){
						Node tmp = this.getRepository().getNode(sValue);
						if (tmp != null)
							defaultValue = tmp;
					}
				}
			}
			request.setAttribute("value", defaultValue);
			}else{   //用于搜索页回显
				String referenceID = request.getParameter(preferences.getValue("referenceId", ""));
				Node oRefNode = this.getRepository().getNode(referenceID);
				if(oRefNode!=null){
					request.setAttribute("value", oRefNode);
				}
			}
		}
		request.setAttribute("preferences", preferences);
		return mapping.findForward("success");
	}
}
