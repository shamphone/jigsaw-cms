package com.fulong.portlet.component.textarea;

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
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.longcon.repository.Value;

/**
 * 文本区表单域占位符
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
		PortletConfig config = new PortletConfig(preferences);
		String definitionId = config.getCategoryID();
		NodeDefinition definition = this.getRepository().getDefinitionManager().getDefinition(definitionId);
		PropertyDefinition propertyDef = definition.getPropertyDefinition(config.getPropertyId());
		String propertyWhich = request.getPreferences().getValue("propertyWhich", null);
		int property;
		try {
			property = Integer.parseInt(propertyWhich);
		}
		catch(Exception e) {
			property = 1;
		}
		boolean isCompositeNode = false;
		String[] splits = config.getPropertyId().split("\\.");
		if(definition!=null){
			PropertyDefinition pd = definition.getPropertyDefinition(splits[0]);
			if (pd.getType() == 0) {
				isCompositeNode = true;
			}
		}
		String echo = preferences.getValue("echo", "nodeEcho");
		if(echo.equals("nodeEcho")){
			if(isCompositeNode){  //如果是复合属性
				if (config.getPropertyId().indexOf('.') > 1) {
		            String strSplit = config.getPropertyId().substring(config.getPropertyId().indexOf('.')+1);
						if (node != null ) {
							NodeIterator<Node> childs = node.getNodes(splits[0]);
							Node childNode = null;
							for(int i=0;i<childs.getSize();i++){
								childNode = childs.nextNode();
								if(i==property-1){
									break;
								}
							}
							if(childNode!=null){
								preferences.setValue("value", childNode.getProperty(strSplit).getString());
							}else{
								preferences.setValue("value", "");
							}
						}else {
							if (propertyDef.getDefaultValue() != null && !propertyDef.getDefaultValue().equals("")) {
								Value[] text = propertyDef.getDefaultValues();
								preferences.setValue("value", getOrdinalValue(text,property));
							}
						}
		        }
			}else{  //不是复合属性
				if (node != null && node.getProperty(config.getPropertyId()) != null) {
					Value[] textarea = node.getProperty(config.getPropertyId()).getValues();
					if(textarea!=null&&textarea.length>0){
						preferences.setValue("value", getOrdinalValue(textarea,property));
					}else{
						preferences.setValue("value", "");
					}
				} else {
					if (propertyDef.getDefaultValue() != null && !propertyDef.getDefaultValue().equals("")) {
						Value[] textarea = propertyDef.getDefaultValues();
						preferences.setValue("value", getOrdinalValue(textarea,property));
					}
				}
			}
		}else if(echo.equals("searchEcho")){   //用于搜索页回显
			String[] oValues = request.getParameterValues(config.getPropertyId());
			preferences.setValue("value", getOrdinalValue(oValues,property));
		}else if(echo.equals("defaultEcho")){   //用占位符中配置的初始值做回显
			String defaultValue = preferences.getValue("value", "");
			preferences.setValue("value", defaultValue);
		}

		request.setAttribute("preferences", preferences);
		return mapping.findForward("success");
	}
	
	protected String getOrdinalValue(String[] values, int No) {
		String retValue = "";
		if(values!=null&&values.length>0){
			if (0 < No && No< values.length+1) {
				retValue = values[No-1];
			} else if (No <= 0) {
				retValue = values[0];
			}
		}
		return retValue;
	}
	protected String getOrdinalValue(Value[] values, int No) {
		String retValue = "";
		if(values!=null&&values.length>0){
			if (0 < No && No< values.length+1) {
				retValue = values[No-1].getString();
			} else if (No <= 0) {
				retValue = values[0].getString();
			}
		}
		if(retValue==null){
			retValue = "";
		}
		return retValue;
	}
}
