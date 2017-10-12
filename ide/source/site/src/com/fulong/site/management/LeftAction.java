package com.fulong.site.management;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.site.SiteCategory;
import com.fulong.site.SiteBaseAction;

/**
 * 
 * <p>Title: Coolink平台协同管理系统</p>
 *
 * <p>Description: Coolink平台协同管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2009</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author liuzijun
 * @version 3.1
 */
public class LeftAction extends SiteBaseAction {

	/* (non-Javadoc)
	 * @see com.fulong.site.SiteBaseAction#doPerform(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */

	protected ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		/**
		 * modified by lzj in 2010-6-12
		 * 抛弃模板类别的建模方式
		 */
		/*Iterator<SiteCategory> siteCategories = this.getSiteFactory(request).categories();
		request.setAttribute("siteCategories", siteCategories);*/
		return mapping.findForward("success");
	}

}
