package com.fulong.portlet.component.select.anyProp;

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
import com.fulong.longcon.repository.Query;
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

	public ActionForward render(ActionMapping mapping, ActionForm form, RenderRequest request, RenderResponse response)
			throws Exception {
		Node node = this.lookupNode(request, response);
		PortletPreferences preferences = request.getPreferences();
		String definitionId = preferences.getValue("category", null);
		String referenceId = preferences.getValue("referenceId", null);
		String echo = preferences.getValue("echo", "nodeEcho");
		NodeDefinition definition = this.getRepository().getDefinitionManager().getDefinition(definitionId);

		Query query = this.getRepository().getQueryManager().createQuery(definition, Query.SQL);
		//query.filterByParent(this.getPassportProvider().getDefaultOrganization(), false);
		NodeIterator<?> nodes = query.nodes();
		request.setAttribute("nodes", nodes);
		request.setAttribute("preferences", preferences);
		String defaultValue = null;
		if(echo.equals("nodeEcho")){  //用于编辑页回显
		if (node != null) {
			Property property = node.getProperty(referenceId);
			if (property != null&&property.getString()!=null) 
				defaultValue = property.getString();		
			
		} 
		}else{   //用于搜索页回显
			defaultValue = request.getParameter(preferences.getValue("referenceId", null));
			if(defaultValue==null){
				defaultValue = "";
			}
		}
		if(defaultValue==null){
			defaultValue = request.getPreferences().getValue("defaultValue", "");
		}
		if(defaultValue == null)
			defaultValue="";
		request.setAttribute("defaultValue", defaultValue);
		return mapping.findForward("success");
	}
}
