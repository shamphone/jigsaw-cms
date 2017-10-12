package com.fulong.site.rss;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <p>Title: Coolink平台协同管理系统</p>
 *
 * <p>Description: Coolink平台协同管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2010</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author luobin
 * @date 2010-10-25	
 * @version 1.0.1
 */

public class ToPropertyAction extends RSSBaseAction {

	@Override
	protected ActionForward templateExecute(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		request.setAttribute("languages", this.getLanguages(request));
		
		return mapping.findForward("success");
	}

}