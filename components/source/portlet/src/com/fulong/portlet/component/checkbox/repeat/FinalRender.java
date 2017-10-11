package com.fulong.portlet.component.checkbox.repeat;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.Property;
import com.fulong.longcon.repository.ValueUtils;
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

	public ActionForward render(ActionMapping mapping, ActionForm form, RenderRequest request, RenderResponse response) throws Exception {
		Node valueNode = this.lookupNode(request, response);
		PortletPreferences preferences = request.getPreferences();
		request.setAttribute("preferences", preferences);
		String echo = preferences.getValue("echo", "nodeEcho");
		String value = null;
		if(echo.equals("nodeEcho")){  //用于编辑页回显
		if(preferences.getValue("userID", "").length()==5 && preferences.getValue("value", "").length()==0)
			value = valueNode.getID();
		else if (preferences.getValue("userID", "").length()==4)
			value = valueNode.getID();
		else{			
		Property property = valueNode.getProperty(preferences.getValue("value", "title"));
		if (property != null) 
			value = property.getString();
		}
		} else{   //用于搜索页回显
			value = request.getParameter(preferences.getValue("name", null));
		}
		if(value!=null){
			request.setAttribute("value", value);
			Node editNode = null;
			if (request.getParameter("contentId") != null)
				editNode = this.getRepository().getNode(request.getParameter("contentId"));
			String[] currentValues = new String[0];
			// 确定当前控件是否被选中；
			if (editNode != null) {
				Property current = editNode.getProperty(preferences.getValue("name", "title"));
				if(current!=null){
					currentValues = ValueUtils.toStringArray(current.getValues());	
				}
			}else{
				currentValues= new String[]{preferences.getValue("initValue", "")};				
			}
			for(int i=0;i<currentValues.length;i++)
			if ((value != null) && (currentValues[i] != null) && value.equals(currentValues[i]))
				request.setAttribute("checked", "checked");			
		}

		return mapping.findForward("success");
	}
}
