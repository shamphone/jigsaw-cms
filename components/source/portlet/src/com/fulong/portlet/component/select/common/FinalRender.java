package com.fulong.portlet.component.select.common;

import java.util.Iterator;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.expression.FilterParser;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.longcon.repository.Property;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.longcon.repository.Query;
import com.fulong.portlet.Constants;
import com.fulong.portlet.PortletRender;
import com.fulong.portlet.cms.list.ContentListConfig;
import com.fulong.portlet.component.PortletConfig;

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
 * @author lixiang
 * @version 1.0
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
		ContentListConfig contentListConfig = new ContentListConfig(request.getPreferences());

	    String categoryId = request.getPreferences().getValue("category", "");
	    NodeDefinition categoryDef = this.getRepository().getDefinitionManager().getDefinition(categoryId);

		Query query = this.getRepository().getQueryManager().createQuery(categoryDef, Query.SQL);
		
		PortletConfig config = new PortletConfig(preferences);
		if (node != null) {
			Property property = node.getProperty(config.getPropertyId());
			if (property != null &&property.getValue()!=null&&property.getValue().getString()!=null) {
				request.setAttribute("value", property.getValue().getString());
			} else {
				request.setAttribute("value", preferences.getValue("default-value", ""));
			}
		} else {
			request.setAttribute("value", preferences.getValue("default-value", ""));
		}
		
		NodeDefinition def = (NodeDefinition) request.getAttribute(Constants.REQUEST_NODEDEFINITION);
		if (def == null) {
			def = lookUpDefinition(request);
		}
		if (request.getPreferences().getValue("filterByParamet", "on").equals("on")) {
			Iterator<PropertyDefinition> properties = def.propertyDefinitions();
			while (properties.hasNext()) {
				PropertyDefinition propertyDefinition = (PropertyDefinition) properties.next();
				if (request.getParameter(propertyDefinition.getID()) != null) {
					String pars[] = request.getParameterValues(propertyDefinition.getID());
					if (pars.length > 1) {
						if(propertyDefinition.getType()==1){   //处理多值属性的过滤
							query.filterByKeywords(pars);
						}else{
							if (pars[0].length() > 1) {
								query.filterByFromValue(propertyDefinition.getID(),  this.getRepository().getValueFactory().createValue(pars[0], propertyDefinition.getType()));
							}
							if (pars[1].length() > 1) {
								query.filterByToValue(propertyDefinition.getID(),  this.getRepository().getValueFactory().createValue(pars[1], propertyDefinition.getType()));

							}
						}						
					} else if (pars.length == 1 && pars[0].length() > 0) {
						query.filterByProperty(propertyDefinition.getID(), pars[0].trim());
					}
				}
			}
			if (request.getParameter("keyword") != null && request.getParameter("keyword").length() > 0) {
				query.filterByKeywords(request.getParameter("keyword"));
			}
			if (request.getParameter("conditions") != null && request.getParameter("conditions").length() > 0) {
				String conditions[] = request.getParameterValues("conditions");
				for (int i = 0; i < conditions.length; i++) {
					FilterParser parser = this.getFilterParser(request, response);
					parser.parser(conditions[i]);
					parser.addToQuery(query);

				}
			}
		}
		for(int i=0;i<contentListConfig.getFilterPatterns().length;i++){
			FilterParser parser = this.getFilterParser(request, response);
			parser.parser(contentListConfig.getFilterPatterns()[i]);
			parser.addToQuery(query);
		}
		String orderBy= contentListConfig.getOrderField();
		if (orderBy != null) 
			query.sortByProperty(orderBy, contentListConfig.getOrderValue());
		
		boolean global =  contentListConfig.isGlobal();
		if (!global) {
			if (this.getCurrentSite(request, response) != null) {
				if (this.getCurrentSite(request, response).getOwner() != null) {
					query.filterByParent((Node) this.getCurrentSite(request, response).getOwner(), false);
				}
			}
		}		
		NodeIterator<?> nodes = query.nodes();

		request.setAttribute("nodes", nodes);
		
		request.setAttribute("preferences", preferences);
		return mapping.findForward("success");
	}
}
