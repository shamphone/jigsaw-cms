/**
 * 
 */
package com.fulong.site;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.site.Channel;
import com.fulong.longcon.site.Site;

/**
 * 
 * 
 * Coolink协同工作框架模型
 * 
 * Copyright: 2009-2011 北京中科辅龙计算机技术股份有限公司
 * 
 * Company: 北京中科辅龙计算机技术股份有限公司
 * 
 * @author lixf
 * 
 * @version 2.0
 * 
 */
public class PortalLoginAction extends SiteBaseAction {

	@Override
	protected ActionForward doPerform(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String domain = request.getServerName();
		Site site = this.getSiteFactory(request).getSite(domain);
		if (site != null) {
			Channel loginChannel = site.getTemplate().getChannel("/login.jsp");
			return this.forward(mapping, "success", new String[] { site.getTemplate().getName(), loginChannel.getName() });
		}
		return null;
	}

}
