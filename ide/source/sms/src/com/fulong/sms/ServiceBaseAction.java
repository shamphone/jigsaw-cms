package com.fulong.sms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.common.BaseAction;
import com.fulong.longcon.site.SiteFactory;
import com.fulong.service.ServiceManager;

/**
 * Service基类
 * 
 * @author liuzijun
 * 
 */
public abstract class ServiceBaseAction extends BaseAction {

	protected Log log = LogFactory.getLog(this.getClass());

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		ActionForward forward = this.doPerform(mapping, form, request, response);
		return forward;

	}

	protected abstract ActionForward doPerform(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	protected ServiceManager getServiceManager(HttpServletRequest request){
		return (ServiceManager)this.getBeanFactory().getBean("serviceManager");
	}
	
	 /**
    *
    * @return SiteFactory
    */
   protected SiteFactory getSiteFactory(HttpServletRequest request) {
       return (SiteFactory)getBeanFactory().getBean("siteFactory");
   }

}
