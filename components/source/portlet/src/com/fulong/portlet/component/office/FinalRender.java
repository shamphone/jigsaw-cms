package com.fulong.portlet.component.office;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.RequestUtils;

import com.fulong.longcon.repository.Node;
import com.fulong.portlet.PortletRender;
import com.fulong.portlet.component.PortletConfig;


/**
 * <p>Title: Coolink平台协同管理系统</p>
 *
 * <p>Description: Coolink平台协同管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2010</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author luobin
 * @date 2010-8-18	
 * @version 1.0.1
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
		
		if (node != null && node.getProperty(config.getPropertyId()) != null) {
			String path = node.getProperty(config.getPropertyId()).getString();
			request.setAttribute("path", path);
			request.setAttribute("name", FilenameUtils.getName(path));
			request.setAttribute("absolutePath", RequestUtils.serverURL((HttpServletRequest)request)+path);
		}
		
		request.setAttribute("preferences", preferences);
		return mapping.findForward("success");
	}
}
