package com.fulong.portlet.component.checkbox.manual;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.Property;
import com.fulong.longcon.repository.Value;
import com.fulong.portlet.PortletRender;
import com.fulong.portlet.component.PortletConfig;

/**
 * 选项按钮表单域占位符
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

	public ActionForward render(ActionMapping mapping, ActionForm form, RenderRequest request, RenderResponse response)
			throws Exception {
		Node node = this.lookupNode(request, response);
		PortletPreferences preferences = request.getPreferences();
		PortletConfig config = new PortletConfig(preferences);
		NodeDefinition definition = this.getRepository().getDefinitionManager().getDefinition(config.getCategoryID());
		String value = preferences.getValue("value", "");
		String echo = preferences.getValue("echo", "nodeEcho");
		if(echo.equals("nodeEcho")){  //用于编辑页回显
		if (node != null && node.getDefinition().equals(definition)) {
			Property property = node.getProperty(config.getPropertyId());
			Value[] cValues = property.getValues();
			if(cValues!=null&&cValues.length>0){
				for(int i=0;i<cValues.length;i++){
					if(value.equals(cValues[i].getString())){
						preferences.setValue("checked", "true");
						//request.setAttribute("checked", "true");
						break;
					}else{
						preferences.setValue("checked", "false");
						//request.setAttribute("checked", "false");
					}
				}
			}
		}
		} else{   //用于搜索页回显
			String oValue = request.getParameter(config.getPropertyId());
			if(oValue!=null){
			if(value.equals(oValue)){
				preferences.setValue("checked", "true");
			} else {
				preferences.setValue("checked", "false");
			}
		}
		}
		request.setAttribute("preferences", preferences);
		return mapping.findForward("success");
	}
}
