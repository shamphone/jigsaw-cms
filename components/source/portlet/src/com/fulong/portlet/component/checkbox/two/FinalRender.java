package com.fulong.portlet.component.checkbox.two;

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
import com.fulong.longcon.repository.Value;
import com.fulong.portlet.PortletRender;

/**
 * 二级多选复选框占位符
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
 * @author liuzijun
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
		String categoryID = preferences.getValue("category", "");
		NodeDefinition category = this.getRepository().getDefinitionManager().getDefinition(categoryID);
		Query query = this.getRepository().getQueryManager().createQuery(category, Query.SQL);
		query.sortByOrdinal(true);
		NodeIterator<?> nodes = query.nodes();
		request.setAttribute("nodes", nodes);
		String[] properties = preferences.getValues("filter-patterns", new String[0]);
		if(node!=null){
			for(int i=0;i<properties.length;i++){
				Property prop = node.getProperty(properties[i]);
				if(prop!=null){
						Value[] values = prop.getValues();
						if(values!=null && values.length>0){
							String[] strs = new String[values.length]; 
							for(int j=0;j<values.length;j++){
								strs[j] = values[j].getString();
							}
							preferences.setValues("value"+i, strs);
						}
				}
			}
		}
		/*else{
			if (prop.getDefaultValue() != null && !prop.getDefaultValue().equals("")) {
				displayName = displayName+prop.getDefaultValue().getString();
				list.add(prop.getDefaultValue().getString());
			}else{
				list.add("");
			}
		}*/
		request.setAttribute("properties", properties);
		request.setAttribute("preferences", preferences);
		return mapping.findForward("success");
	}
}
