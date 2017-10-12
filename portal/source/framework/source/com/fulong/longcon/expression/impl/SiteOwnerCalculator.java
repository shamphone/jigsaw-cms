/**
 * 
 */
package com.fulong.longcon.expression.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.Value;
import com.fulong.longcon.site.Site;
import com.fulong.longcon.site.SiteFactory;

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
public class SiteOwnerCalculator extends BasicCalculator  {
	private SiteFactory siteFactory;

	public Value calc(HttpServletRequest request, HttpServletResponse response) throws Exception {
	     Site site = (Site) request.getAttribute(Site.class.getName());
         if (site == null) {
             String siteId = request.getParameter("siteId");
             if (siteId == null) {
                 siteId = request.getServerName();
             }
             site = this.siteFactory.getSite(siteId);
             if (site != null) {
                 request.setAttribute(Site.class.getName(), site);
             }
         }
         return this.repository.getValueFactory().createValue((Node)site.getOwner());
	}

	public void setSiteFactory(SiteFactory siteFactory) {
		this.siteFactory = siteFactory;
	}

}
