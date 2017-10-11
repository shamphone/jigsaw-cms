package com.fulong.portlet.field.conditions;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.expression.FilterParser;
import com.fulong.longcon.repository.Node;
import com.fulong.portlet.PortletRender;

/**
 * 
 * <p>
 * Title: WebMaster Core Library
 * </p>
 * 
 * <p>
 * Description: Longcon WebMaster SV3
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Beijing Zhongke Fulong Computer Tech. LTD
 * </p>
 * 
 * @author jiangqi
 * @version 1.0
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
		PortletPreferences preferences = request.getPreferences();
		
		//片段的路径
		String truePath = getClipPath(request, response, "true");
		String falsePath = getClipPath(request, response, "false");
		
		String retValue = falsePath;
		Node node = lookupNode(request, response);
		String templateName =  this.getCurrentSiteTemplate(request, response).getName();
		if (node == null) {  //节点为空则判定为不满足条件
			//return mapping.findForward(this.NODONE);
			this.getServletContext().getContext("/"+templateName).getRequestDispatcher(retValue).include((ServletRequest)request, (ServletResponse)response);
			return null;

		}
		String[] conditions = preferences.getValues("conditions", new String[0]);
		for (int i = 0; i < conditions.length; i++) {
			FilterParser parser = this.getFilterParser(request, response);
			parser.parser(conditions[i]);
			//Property property = node.getProperty(parser.getPropertyDefinition());
			//Value proValue = property.getValue();
			//Value toCompare = parser.getValue();
			if(parser.validate(node)){
				retValue = truePath;
			}else{
				retValue = falsePath;
				break;
			}
		}
		this.getServletContext().getContext("/"+templateName).getRequestDispatcher(retValue).include((ServletRequest)request, (ServletResponse)response);
		return null;

	}
}
