package com.fulong.portlet.site.visitor;

import java.util.ArrayList;
import java.util.HashMap;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.site.Site;
import com.fulong.portlet.PortletRender;

/**
 * <p>
 * Title: 龙驭网站内容管理系统
 * </p>
 * 
 * <p>
 * Description: 龙驭网站内容管理系统
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术股份有限公司 2008
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * @author jiangqi
 * @version 2.0
 */

public class FinalRender extends PortletRender {

	/**
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            RenderRequest
	 * @param response
	 *            RenderResponse
	 * @return ActionForward
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward render(ActionMapping mapping, ActionForm form, RenderRequest request, RenderResponse response)
			throws Exception {
		HashMap map = (HashMap) this.getServlet().getServletContext().getAttribute("zxfk");
		Site site = this.getCurrentSite(request, response);
		ArrayList<Site> arr = new ArrayList<Site>();
		Site orgsite = null;
		if (map != null && site != null) {
			HashMap list = (HashMap) map.get(site.getID());
			if (list != null) {
				Object[] orgs = list.values().toArray();
				for (int i = 0; i < orgs.length; i++) {
					orgsite = this.getSiteFactory().getSite((String) orgs[i]);
					if (orgsite != null) {
						arr.add(orgsite);
					}
				}
				if (!arr.isEmpty()) {
					request.setAttribute("sites", arr);
				}
			}
		}
		request.setAttribute("preferences", request.getPreferences());
		return mapping.findForward("success");
	}
}