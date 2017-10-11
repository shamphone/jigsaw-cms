package com.fulong.portlet.component.select.ref;

import java.util.ArrayList;
import java.util.List;

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

		NodeDefinition definition = this.getRepository().getDefinitionManager().getDefinition(definitionId);
		PropertyDefinition propertyDef = definition.getPropertyDefinition(referenceId);
		NodeDefinition reference = propertyDef.getReferenceDefinition();

		Query query = this.getRepository().getQueryManager().createQuery(reference, Query.SQL);
		NodeIterator nodes = query.nodes();

		request.setAttribute("nodes", nodes);

		Node defaultValues[] = null;
		if (node != null) {
			Property property = node.getProperty(referenceId);
			if (property != null) {
				Value values[] = property.getValues();
				List<Node> list = new ArrayList<Node>();
				for (int i = 0; i < values.length; i++) {
					Node tmp = ((ReferenceValue) values[i]).getReference();
					if (tmp != null)
						list.add(tmp);
				}
				defaultValues = list.toArray(new Node[list.size()]);
			}
		} else {
			String sValues[] = request.getPreferences().getValues("defaultValues", new String[0]);
			List<Node> list = new ArrayList<Node>();
			for (int i = 0; i < sValues.length; i++) {
				if (sValues[i].equals("user")) {
					list.add((Node) request.getUserPrincipal());
				} else {
					Node tmp = this.getRepository().getNode(sValues[i]);
					if (tmp != null)
						list.add(tmp);
				}
			}
			defaultValues = list.toArray(new Node[list.size()]);
		}
		request.setAttribute("value", defaultValues);
		request.setAttribute("preferences", preferences);
		return mapping.findForward("success");
	}
}
