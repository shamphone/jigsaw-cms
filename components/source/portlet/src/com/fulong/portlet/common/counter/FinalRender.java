package com.fulong.portlet.common.counter;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.counter.AccessCounterRepository;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.site.Channel;
import com.fulong.longcon.site.Site;
import com.fulong.portlet.PortletRender;

/**
 * 
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
 * @author Lixf
 * @author jiangqi
 * @version 3.0
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

		AccessCounterRepository repository = (AccessCounterRepository) this.getBeanFactory().getBean(
				"acccessCounterRepository");
		String name = request.getPreferences().getValue("type", "");
		if ((name == null) || (name.length() == 0)) {
			name = "system";
		}
		Channel channel = this.getCurrentChannel(request, response);
		Site site = this.getCurrentSite(request, response);
		Node node = this.getRequestNode(request);
		/*String noIncrease = request.getPreferences().getValue("noIncrease", "on");
		if(noIncrease.equals("false")){
			if (httpReq.getAttribute("com.fulong.portlet.counter") == null) {
				repository.increase("system");
				if (site != null) {
					repository.increase(site.getID());
					if (channel != null) {
						repository.increase(site.getID() + "." + channel.getID());
					}
				}
				if (node != null && name != null && name.equals("content")) {
					repository.increase(node.getID());
				}
				httpReq.setAttribute("com.fulong.portlet.counter", "true");
			}
		}*/		
		long count = 0;
		if (name.equalsIgnoreCase("system")) {
			count = repository.getCount("system");
		} else if (name.equalsIgnoreCase("site") && (site != null)) {
			count = repository.getCount(site.getDomain());
		} else if (name.equalsIgnoreCase("channel") && (site != null) && (channel != null)) {
			count = repository.getCount(channel.getSiteTemplate().getName()+channel.getContextPath());
		} else if (node != null) {
			count = repository.getCount(node.getID());
		}
		request.setAttribute("count", "" + count);
		request.setAttribute("preference", request.getPreferences());
		return mapping.findForward("success");

	}
}
