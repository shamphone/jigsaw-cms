package com.fulong.site.channel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.site.SiteTemplate;

/**
 * <p>Title: 龙驭网站管理系统</p>
 *
 * <p>Description: 龙驭网站管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author lixf
 * @version 1.0
 */
public class OpenChannelAction extends ChannelBaseAction {
    /**
     *
     * @throws Exception ：在这个方法的实现中，原则上不进行任何的异常处理，仅抛出异常
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     */
    protected ActionForward templateExecute(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String templateName = request.getParameter("templateName");
		
		String[] filterTypes = request.getParameterValues("filterType");
		SiteTemplate template = this.getSiteFactory(request).getTemplate(templateName);
		request.setAttribute("template", template);
		
		if (filterTypes != null) {
			request.setAttribute("filterTypes", filterTypes);
		}
		if (request.getParameter("multi") != null){
			String[] path = request.getParameterValues("path");
			if (path != null&&path.length>0) {
				request.setAttribute("defaultChannel", path);
			}
			return mapping.findForward("multi");
		}else{
			String path = request.getParameter("path");
			if (path != null) {
				request.setAttribute("defaultChannel", path);
			}
			return mapping.findForward("single");
		}
	}
}
