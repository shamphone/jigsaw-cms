package com.fulong.site.management;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.site.Site;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <p>Title: 龙驭网站管理系统</p>
 *
 * <p>Description: 龙驭网站管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author liuzijun
 * @version 1.0
 */
public class DeleteAction extends ManagementBaseAction {
    /**
     * managementExecute
     *
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws Exception
     *   method
     */
    protected ActionForward managementExecute(ActionMapping mapping,
                                              ActionForm form,
                                              HttpServletRequest request,
                                              HttpServletResponse response) throws
            Exception {
        String[] ids = request.getParameterValues("id");
        for (int i = 0; i < ids.length; i++) {
            Node site = this.getRepository(request).getNode(ids[i]);
            if (site != null) {
                this.getRepository(request).delete(site);
            }
        }
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("text/html");
        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        response.getWriter().print("success");
        request.setAttribute("ids", ids);
        return null;
    }
}
