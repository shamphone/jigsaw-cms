package com.fulong.site.resource;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.site.SiteTemplate;

/**
 *
 * <p>Title: 龙驭网站管理系统</p>
 *
 * <p>Description: 龙驭网站管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author jiangqi
 * @version 1.0
 */
public class SaveAction extends ResourceBaseAction {
    /**
     * resourcePerform
     *
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward resourcePerform(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request,
                                         HttpServletResponse response) throws
            Exception {
        String name = request.getParameter("template");
        SiteTemplate template = this.getSiteFactory(request).getTemplate(name);
        String path = request.getParameter("path");
		File folder = template.getRootFolder().getFile();		 
		if ((path != null) && (path.length() > 0))
			folder = new File(folder, path);
		if (folder.mkdirs()) {
	        response.setContentType("text/html");
	        response.setHeader("Content-Type", "text/html; charset=UTF-8");			
			response.getWriter().write(path);
			response.getWriter().close();
		}else
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		return null;
    }
}
