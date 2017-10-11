package com.fulong.portlet.field.no;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.fulong.portlet.PortletRender;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
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
 * @version 2.0
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
		/*
		 * <bean:define id="num" name="preferences" property="value(num)"
		 * type="java.lang.String"/> <% String indexId = +""; int n =
		 * Integer.parseInt(indexId)+Integer.parseInt(num); out.print(n); %>
		 */
		int start = Integer.parseInt(request.getPreferences().getValue("num", "1"));
		if(request.getAttribute("indexId")!=null){
		int offset = Integer.parseInt("" + request.getAttribute("indexId"));
		request.setAttribute("num", (start + offset));
		}
		return mapping.findForward("success");
	}

}
