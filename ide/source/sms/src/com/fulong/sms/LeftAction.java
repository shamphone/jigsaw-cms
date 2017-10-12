package com.fulong.sms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.site.Site;
import com.fulong.longcon.site.SiteTemplate;


/**
 * @author Administrator
 *
 */
public class LeftAction extends ServiceBaseAction {

	/* (non-Javadoc)
	 * @see com.fulong.service.ServiceBaseAction#doPerform(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setAttribute("templates", getTemplates(getCurrentSite(request),request));		
		request.setAttribute("serviceManager", this.getServiceManager(request));
		return mapping.findForward("success");
	}

	private Collection<SiteTemplate> getTemplates(Site site,HttpServletRequest request){
		List<SiteTemplate> list = new ArrayList<SiteTemplate>();
		for(String name:site.getTemplates()){
			SiteTemplate template = this.getSiteFactory(request).getTemplate(name);
			if(template!=null){
				list.add(template);
			}
		}
		return list;
	}
	
	protected Site getCurrentSite(HttpServletRequest request) throws Exception {
		String domain = request.getServerName();
		if(request.getServerPort()!=80){
			domain += ":"+request.getServerPort();
		}
		Site site = this.getSiteFactory(request).getSite(domain);
		return site;
	}
}
