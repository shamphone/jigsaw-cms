package com.fulong.portlet.component.node.selector;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.Property;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.longcon.repository.property.ReferenceProperty;
import com.fulong.portlet.component.PortletConfig;
import javax.portlet.PortletPreferences;
import com.fulong.portlet.PortletRender;

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
public class FinalRender extends PortletRender {
	public ActionForward render(ActionMapping mapping, ActionForm form, RenderRequest request, RenderResponse response)
			throws Exception {
		Node node = this.lookupNode(request, response);
		PortletPreferences preferences = request.getPreferences();
		PortletConfig config = new PortletConfig(preferences);
		String echo = preferences.getValue("echo", "nodeEcho");
		String categoryId = request.getPreferences().getValue("category", NodeDefinition.NO_PROPERTIES_SCHEME);
		
		NodeDefinition refDefinition = this.getRepository().getDefinitionManager().getDefinition(categoryId);
		
		if(echo.equals("nodeEcho")){  //用于编辑回显
			if (node != null && node.getProperty(config.getPropertyId()) != null) {
				ReferenceProperty refValue = (ReferenceProperty) node.getProperty(config.getPropertyId());
				request.setAttribute("value", refValue.getReference());
			}
		}else{
				String pvalue = request.getParameter(config.getPropertyId());
				if(pvalue != null && pvalue.length()>0 && !pvalue.equalsIgnoreCase("")){
					node = this.getRepository().getNode(pvalue);
					Property refValue = node.getProperty(request.getPreferences().getValue("viewPropertyId", null));
					request.setAttribute("valueSousuo", refValue);
					request.setAttribute("valueid", pvalue);
				}
		}
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
			request.setAttribute("viewProperty", refDefinition.getPropertyDefinition(viewPropertyId));
		}
		request.setAttribute("selectedProps", listTemp);
		request.setAttribute("preferences", preferences);
		return mapping.findForward("success");
	}
}
