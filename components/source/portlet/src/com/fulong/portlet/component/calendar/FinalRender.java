package com.fulong.portlet.component.calendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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
import com.fulong.longcon.repository.Value;
import com.fulong.portlet.PortletRender;
import com.fulong.portlet.component.PortletConfig;

/**
 * 日历表单域占位符Final状态
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

	protected final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public ActionForward render(ActionMapping mapping, ActionForm form, RenderRequest request, RenderResponse response)
			throws Exception {
		Node node = this.lookupNode(request, response);
		PortletPreferences preferences = request.getPreferences();
		PortletConfig config = new PortletConfig(preferences);
		String definitionId = preferences.getValue("category-id", "");
		NodeDefinition definition = this.getRepository().getDefinitionManager().getDefinition(definitionId);
		String hasTime = preferences.getValue("hasTime", null);
		String date = null;
		String defaultMode = preferences.getValue("defaultMode", hasTime);
		String propertyWhich = preferences.getValue("propertyWhich", "1");
		int property;
		try {
		property = Integer.parseInt(propertyWhich);
		}
		catch(Exception e) {
			property = 1;
		}
		boolean isCompositeNode = false;
		if(defaultMode.equals("nodeEcho")){   //编辑节点时回显
			String[] splits = config.getPropertyId().split("\\.");
			if(definition!=null){
				PropertyDefinition pd = definition.getPropertyDefinition(splits[0]);
				if (pd.getType() == 0) {
					isCompositeNode = true;
				}
			}
			if(isCompositeNode){   //如果是复合属性
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
								date = this.getCalendar(childNode.getProperty(strSplit).getString(), hasTime);
							}else{
								date = "";
							}
						}
		        }
			}else{  //不是复合属性
				if(node!=null){
					Property prop = node.getProperty(config.getPropertyId());
					if (prop != null) {
						Value[] values = prop.getValues();
						date = this.getCalendar(getOrdinalValue(values,property), hasTime);
					}
				}
			}			
		}else if(defaultMode.equals("searchEcho")){    //搜索内容时回显
			String propertyId = preferences.getValue("propertyId", null);
			if (request.getParameterValues(propertyId) != null) {
				date = this.getCalendar(getOrdinalValue(request.getParameterValues(propertyId),property), hasTime);
		   }
		}else{     //默认常量值设定
			String propertyId = preferences.getValue("propertyId", null);
			if (request.getParameter(propertyId) != null && request.getParameter(propertyId).length() > 0) {
				date = this.getCalendar(request.getParameter(propertyId), hasTime);
			} else if ("now".equals(defaultMode)) {
				Calendar calendar = Calendar.getInstance();
				String offsetS = preferences.getValue("offset", "0");
				int offset = 0;
				try {
					offset = Integer.parseInt(offsetS);
				} catch (NumberFormatException ex) {
				}
				int unit = Integer.parseInt(preferences.getValue("unit", Calendar.DAY_OF_YEAR + ""));
				calendar.add(unit, offset);
				date = this.getCalendar(FORMATTER.format(calendar.getTime()), hasTime);
			} else if ("fix".equals(defaultMode)) {
				date = this.getCalendar(preferences.getValue("defaultValue", null), hasTime);
			}
		}
		request.setAttribute("calendar", date);		
		request.setAttribute("preferences", preferences);
		return mapping.findForward("success");
	}

	protected String getCalendar(String calendar, String hasTime) {
		if (calendar != null && calendar.length() > 0 && "false".equals(hasTime) && calendar.indexOf(" ") > 0) {
			calendar = calendar.substring(0, calendar.indexOf(" "));
		}
		return calendar;
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
