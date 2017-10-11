package com.fulong.portlet.component.text.suggest;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import com.fulong.longcon.repository.Node;
import com.fulong.portlet.component.PortletConfig;
import javax.portlet.PortletPreferences;
import com.fulong.portlet.PortletRender;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.longcon.repository.Value;

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
		PropertyDefinition propertyDef = definition.getPropertyDefinition(config.getPropertyId());
		if(echo.equals("nodeEcho")){  //用于编辑页回显
		if (node != null && node.getProperty(config.getPropertyId()) != null) {
			Value[] values = node.getProperty(config.getPropertyId()).getValues();
			if(values!=null&&values.length>1){
				for(int i=0; i<values.length-1; i++){
					displayName = displayName+values[i].getString()+" ";
				}
				displayName = displayName+values[values.length-1].getString();
			}else if(values!=null&&values.length==1){
				displayName = values[0].getString();
			}			
		} else {
			if (propertyDef.getDefaultValue() != null && !propertyDef.getDefaultValue().equals("")) {
				displayName = propertyDef.getDefaultValue().getString();
			}
		}
		request.setAttribute("displayName", displayName);
		}else{   //用于搜索页回显
			String oValue = request.getParameter(config.getPropertyId());
			if(oValue==null){
				oValue = "";
			}
			request.setAttribute("displayName", oValue);
		}
		request.setAttribute("preferences", preferences);
		request.setAttribute("conditions", preferences.getValues("conditions", new String[0]));
		return mapping.findForward("success");
	}
}
