/**
 * 
 */
package com.fulong.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.common.BaseAction;
import com.fulong.longcon.repository.Repository;
import com.fulong.longcon.security.PassportProvider;
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
public abstract class SecurityBaseAction extends BaseAction {
	protected Log log = LogFactory.getLog(this.getClass());
	/**
	 * 覆盖实现Action基类中的Action
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return ActionForward
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		long start = 0;
		if (log.isTraceEnabled())
			start = System.currentTimeMillis();

		forward = doPerform(mapping, form, request, response);

		if (log.isTraceEnabled()) {
			log.trace("[" + (System.currentTimeMillis() - start) + "]");
		}
		return forward;
	}

	/**
	 * 所有的子类应该实现这个方法，进行页面初始化和用户事件处理
	 * 
	 * @throws java.lang.Exception
	 *             ：在这个方法的实现中，原则上不进行任何的异常处理，仅抛出异常
	 */

	protected abstract ActionForward doPerform(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception;
	/**
    *
    * @return Repository
    */

   protected Repository getRepository(HttpServletRequest request) {
       return (Repository)getBeanFactory().getBean("repository", Repository.class);
   }
   
   
   protected PassportProvider getPassportProvider(HttpServletRequest request) {
       return ((PassportProvider)this.getBeanFactory().getBean("passport"));
   }
   
   /**
   *
   * @return SiteFactory
   */
  protected SiteFactory getSiteFactory(HttpServletRequest request) {
      return (SiteFactory)getBeanFactory().getBean("siteFactory");
  }

  /**
   * 获取当前请求的网站
   * @param request PortletRequest
   * @param response PortletResponse
   * @return Site
   * @throws Exception
   */
   protected Site getCurrentSite(HttpServletRequest request,
       HttpServletResponse response) throws Exception {
	   Site site = (Site) request.getAttribute(Site.class.getName());
	   if (site == null) {
		   String siteId = request.getParameter("siteId");
		   if (siteId == null) {
			   siteId = request.getServerName();
		   }
		   site = this.getSiteFactory(request).getSite(siteId);
		   if (site != null) {
			   request.setAttribute(Site.class.getName(), site);
		   }
	   }
	   return site;
   }
}
