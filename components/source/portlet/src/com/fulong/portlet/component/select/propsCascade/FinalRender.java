package com.fulong.portlet.component.select.propsCascade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.collections.KeyValue;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

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
 * <p>Title: Coolink平台协同管理系统</p>
 *
 * <p>Description: Coolink平台协同管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2010</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author luobin
 * @date 2010-9-8	
 * @version 1.0.1
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
		preferences.getValue("IDValue", null);
		String[] propertyIds = preferences.getValues("propertyIds", new String[0]);
        request.setAttribute("propertyIds", propertyIds);
		
		String echo = preferences.getValue("echo", "nodeEcho");
		
		 //字典大纲
        String category = request.getPreferences().getValue("category", null);
		NodeDefinition definition = this.getRepository().getDefinitionManager().getDefinition(category);
		if(definition!=null){
			
			Query query = this.getRepository().getQueryManager().createQuery(definition, Query.SQL);
			query.filterByParent(this.getPassportProvider().getDefaultOrganization(), false);
			NodeIterator nodes = query.nodes();
			request.setAttribute("nodes", nodes);
			
			Node defaultNode = null;
			List<LabelValueBean> list = new ArrayList<LabelValueBean>();
			if(echo.equals("nodeEcho")){  //用于编辑页回显
				if (node != null) {
					defaultNode = node;
				} else {
					String sValue = request.getPreferences().getValue("defaultValue", "");
					if (sValue.equals("user")) {
						defaultNode = (Node) request.getUserPrincipal();
					} else {
						Node tmp = this.getRepository().getNode(sValue);
						if (tmp != null)
							defaultNode = tmp;
					}
				}
				for(String property : propertyIds){
					String value = null;
					if(defaultNode!=null){
						 value = defaultNode.getProperty(property).getString();
					}
					LabelValueBean bean = new LabelValueBean(property,value);
					list.add(bean);
				}
			}else{
				for(String property : propertyIds){
					String value = request.getParameter(property);
					LabelValueBean bean = new LabelValueBean(property,value);
					list.add(bean);
				}
			}
			request.setAttribute("properties", list);
		}
		request.setAttribute("preferences", preferences);
		return mapping.findForward("success");
	}
}
