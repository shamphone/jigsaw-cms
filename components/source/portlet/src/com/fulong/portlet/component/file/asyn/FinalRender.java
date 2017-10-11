package com.fulong.portlet.component.file.asyn;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.Node;
import com.fulong.portlet.PortletRender;

/**
 * 多文件上载表单域占位符
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
		String propID = preferences.getValue("propertyId", "");
		String echo = preferences.getValue("echo", "nodeEcho");
		//List<String> values= new ArrayList();
		List<String> displayValues= new ArrayList<String>();
		
		if(echo.equals("nodeEcho")){  //用于编辑页回显
			if (node != null) {
				if(propID!=null&&!propID.equals("")){
					if(node.getProperty(propID)!=null){
						if(node.getProperty(propID).getArray()!=null){
							String[] values = node.getProperty(propID).getArray();
							for(int i=0;i<values.length;i++){
								String[] temp = values[i].split("/");
								displayValues.add(temp[temp.length-1]);
							}
							request.setAttribute("values", values);
							
						}else {
							String[] values = new String[1];
							values[0] = node.getProperty(propID).getString();
							String[] temp = values[0].split("/");
							displayValues.add(temp[temp.length-1]);
							request.setAttribute("values", values);
							
						}
					}
				}
			}
		}
		request.setAttribute("displayValues", displayValues);
		request.setAttribute("preferences", preferences);
		return mapping.findForward("success");
	}
}
