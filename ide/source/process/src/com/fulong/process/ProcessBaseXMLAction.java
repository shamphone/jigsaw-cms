/**
 * 
 */
package com.fulong.process;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.fulong.common.AjaxAction;
import com.fulong.longcon.repository.Repository;
import com.fulong.longcon.site.Site;
import com.fulong.longcon.site.SiteFactory;
import com.fulong.longcon.site.SiteTemplate;
import com.fulong.longcon.workflow.WorkflowService;
import com.fulong.service.ServiceManager;

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
public abstract class ProcessBaseXMLAction extends  AjaxAction{
	public WorkflowService getWorkflowService(HttpServletRequest request) {
		return (WorkflowService)this.getBeanFactory()
		.getBean("workflow");
	}
	public ServiceManager getServiceManager(HttpServletRequest request){
		return (ServiceManager)this.getBeanFactory().getBean(
		"serviceManager");
	}

	public Repository getRepository(HttpServletRequest request){
		return (Repository)this.getBeanFactory().getBean(
		"repository");
	}

	protected SiteFactory getSiteFactory(HttpServletRequest request) {
		return (SiteFactory)getBeanFactory().getBean("siteFactory");
	}

	protected Collection<SiteTemplate> getTemplates(Site site,HttpServletRequest request){
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
