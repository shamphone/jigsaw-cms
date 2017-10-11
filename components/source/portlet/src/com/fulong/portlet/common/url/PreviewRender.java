package com.fulong.portlet.common.url;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.fulong.longcon.site.SiteTemplate;
import com.fulong.portlet.PortletRender;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequestDispatcher;

/**
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
 * @author Lixf
 * @version 1.0
 */
public class PreviewRender extends PortletRender {

	/**
	 * execute
	 * 
	 * @param config
	 *            PortletConfig
	 * @param request
	 *            RenderRequest
	 * @param response
	 *            RenderResponse
	 * @throws Exception
	 */
	public ActionForward render(ActionMapping mapping, ActionForm form, RenderRequest request, RenderResponse response)
			throws Exception {

		URLCache cache = (URLCache) request.getPortletSession().getPortletContext().getAttribute(
				URLCache.ATTRIBUTE_NAME);
		PortletPreferences preferences = request.getPreferences();
		SiteTemplate template = this.getCurrentSiteTemplate(request, response);
		String path = cache.add(preferences,template);
		String relative = path.substring(request.getPortletSession().getPortletContext().getRealPath("").length());
		PortletRequestDispatcher dispatcher = portletConfig.getPortletContext().getRequestDispatcher(relative);
		dispatcher.include(request, response);
		return null;

	}

}
