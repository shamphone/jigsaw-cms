package com.fulong.portlet.component.checkbox.ref;

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
import com.fulong.portlet.PortletRender;

/**
 * 复选框表单域占位符
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

	public ActionForward render(ActionMapping mapping, ActionForm form, RenderRequest request, RenderResponse response)
			throws Exception {
		Node node = this.lookupNode(request, response);
		PortletPreferences preferences = request.getPreferences();
		String definitionId = preferences.getValue("definitionId", null);
		String referenceId = preferences.getValue("referenceId", null);
		String echo = preferences.getValue("echo", "nodeEcho");

		NodeDefinition definition = this.getRepository().getDefinitionManager().getDefinition(definitionId);
		PropertyDefinition propertyDef = definition.getPropertyDefinition(referenceId);
		NodeDefinition reference = propertyDef.getReferenceDefinition();

		Query query = this.getRepository().getQueryManager().createQuery(reference, Query.SQL);
		query.sortByOrdinal(true);
		NodeIterator<?> nodes = query.nodes();

		request.setAttribute("nodes", nodes);
		if(echo.equals("nodeEcho")){  //用于编辑页回显
		if (node != null) {
			Property property = node.getProperty(referenceId);
			if (property != null) {
				Value[] values = property.getValues();
				if(values!=null && values.length>0){
					String[] strs = new String[values.length]; 
					for(int i=0;i<values.length;i++){
						strs[i] = values[i].getString();
					}
					preferences.setValues("value", strs);
				}				
			}
		} else {
			if (propertyDef.getDefaultValues() != null && propertyDef.getDefaultValues().length > 0) {
				Value[] values = propertyDef.getDefaultValues();
				String[] sV = new String[values.length];
				for (int i = 0; i < values.length; i++) {
					sV[i] = values[i].getString();
				}
				preferences.setValues("value", sV);
			}
		}
		}else{   //用于搜索页回显
			String oValue = request.getParameter(preferences.getValue("referenceId", null));
			if(oValue==null){
				oValue = "";
			}
			preferences.setValue("value", oValue);
		}
		request.setAttribute("preferences", preferences);
		return mapping.findForward("success");
	}
}
