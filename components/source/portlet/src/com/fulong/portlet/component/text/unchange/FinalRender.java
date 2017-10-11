package com.fulong.portlet.component.text.unchange;

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
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.portlet.PortletRender;
import com.fulong.portlet.component.PortletConfig;

/**
 * 文本框表单域占位符
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
		PortletConfig config = new PortletConfig(preferences);
		String definitionId = config.getCategoryID();
		NodeDefinition definition = this.getRepository().getDefinitionManager().getDefinition(definitionId);
		String echo = preferences.getValue("echo", "nodeEcho");
		String displayName = "";
		String[] properties = preferences.getValues("filter-patterns", new String[0]);
		List<String> list = new ArrayList<String>();
		if(echo.equals("nodeEcho")){  //用于编辑页回显
		for(int i=0;i<properties.length;i++){
			PropertyDefinition prop = definition.getPropertyDefinition(properties[i]);
			if(prop!=null){
				if(node!=null){
					if(node.getProperty(properties[i])!=null&&node.getProperty(properties[i]).getString()!=null&&!node.getProperty(properties[i]).getString().equals("null")){
						displayName = displayName+node.getProperty(properties[i]).getString()+" ";
						list.add(node.getProperty(properties[i]).getString());
					}else{
						list.add("");
					}					
				}else{
					if (prop.getDefaultValue() != null && !prop.getDefaultValue().equals("")) {
						displayName = displayName+prop.getDefaultValue().getString();
						list.add(prop.getDefaultValue().getString());
					}else{
						list.add("");
					}
				}
			}
		}
		request.setAttribute("displayName", displayName);
		} else{   //用于搜索页回显
			for(int i=0;i<properties.length;i++){
				String oValue = request.getParameter(properties[i]);
				if(oValue!=null&&!oValue.equals("")){
					displayName = displayName+oValue+" ";
					list.add(oValue);
				}else{
					list.add("");
				}
				request.setAttribute("displayName", displayName);
			}
			
		}
		displayName = displayName.trim();
		request.setAttribute("values", list);
		request.setAttribute("properties", properties);
		request.setAttribute("preferences", preferences);
		return mapping.findForward("success");
	}
}
