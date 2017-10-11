package com.fulong.portlet.component.node.multiple;

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
import com.fulong.longcon.repository.Property;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.longcon.repository.Value;
import com.fulong.longcon.repository.value.ReferenceValue;
import com.fulong.portlet.PortletRender;
import com.fulong.portlet.component.PortletConfig;

/**
 * 多个内容选择表单域占位符
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
		Node node = this.lookupNode(request, response);
		PortletPreferences preferences = request.getPreferences();
		PortletConfig config = new PortletConfig(preferences);
		String echo = preferences.getValue("echo", "nodeEcho");
		Node nodes[] = new Node[0];
		if(echo.equals("nodeEcho")){//用于编辑页回显
			if (node != null) {
				Property property = node.getProperty(config.getPropertyId());
				if (property != null) {
					Value values[] = property.getValues();
					List<Node> list = new ArrayList<Node>();
					for (int i = 0; i < values.length; i++) {
						Node tmp = ((ReferenceValue) values[i]).getReference();
						if (tmp != null)
							list.add(tmp);
					}
					nodes = list.toArray(new Node[list.size()]);
				}

			} else {
				String sValues[] = request.getPreferences().getValues("defaultValues", new String[0]);
				if(sValues!=null){
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
					nodes = list.toArray(new Node[list.size()]);
				}
			}
		}else{//用于搜索页回显
			String sValues[] = request.getParameterValues(config.getPropertyId());
			List<Node> list = new ArrayList<Node>();
			if(sValues!=null){
				for (int i = 0; i < sValues.length; i++) {
					if (sValues[i].equals("user")) {
						list.add((Node) request.getUserPrincipal());
					} else {
						Node tmp = this.getRepository().getNode(sValues[i]);
						if (tmp != null)
							list.add(tmp);
					}
				}
				nodes = list.toArray(new Node[list.size()]);
			}
		}
		String categoryId = request.getPreferences().getValue("category", NodeDefinition.NO_PROPERTIES_SCHEME);
		NodeDefinition refDefinition = this.getRepository().getDefinitionManager().getDefinition(categoryId);
		String[] properties = request.getPreferences().getValues("filterpatterns", new String[0]);
		List<PropertyDefinition> listTemp = new ArrayList<PropertyDefinition>();
		for(int i=0;i<properties.length;i++){
			PropertyDefinition prop = refDefinition.getPropertyDefinition(properties[i]);
			if(prop!=null){
				listTemp.add(prop);
			}
		}
		String viewPropertyId = request.getPreferences().getValue("viewPropertyId", null);
		if (viewPropertyId != null && viewPropertyId.length() > 0) {
			request.setAttribute("viewPropertyId", viewPropertyId);
			request.setAttribute("viewProperty", refDefinition.getPropertyDefinition(viewPropertyId));
		}
		request.setAttribute("selectedProps", listTemp);
		request.setAttribute("preferences", preferences);
		request.setAttribute("values", nodes);
		return mapping.findForward("success");
	}
}
