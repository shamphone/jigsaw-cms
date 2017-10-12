package com.fulong.process;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.common.BaseAction;
import com.fulong.longcon.repository.Repository;
import com.fulong.longcon.site.Site;
import com.fulong.longcon.site.SiteFactory;
import com.fulong.longcon.site.SiteTemplate;
import com.fulong.longcon.workflow.WorkflowService;
import com.fulong.service.ServiceManager;
/**
 * 
 * <p>Title: Coolink流程模型管理系统</p>
 *
 * <p>Description: Coolink流程模型管理系统</p>
 *
 *
 * <p>Copyright: Copyright (c)北京中科辅龙计算机技术股份有限公司 2007</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author sunyuchao</a>
 * @version 1.0
 */

public abstract class ProcessBaseAction extends BaseAction{
	protected Log log = LogFactory.getLog(this.getClass());

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Date begin = null;
		if (log.isTraceEnabled()) {
			begin = new Date();
		}

			ActionForward forward = this.doPerform(mapping, form, request, response);
			if (log.isTraceEnabled()) {
				log.trace("execution " + (new Date().getTime() - begin.getTime()));
			}
			return forward;

	}
	protected abstract ActionForward doPerform(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception;

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
