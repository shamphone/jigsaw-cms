package com.fulong.site.management;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fulong.longcon.site.Site;
import com.fulong.longcon.site.SiteObject;
import com.fulong.site.form.SiteForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fulong.longcon.security.Group;
import com.fulong.longcon.workflow.Activity;

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
public class StartAction extends ManagementBaseAction {
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
        String categoryID = request.getParameter("categoryID");
        request.setAttribute("categoryID", categoryID);
        SiteForm searchForm = (SiteForm) form;
/*         for (int i = 0; i < searchForm.getIDs().length; i++) {
            Site site = this.getSiteFactory(request).getSite(searchForm.getIDs()[i]);
           if (site != null) {
                Membership membership = site.getMembership();
                if(membership!=null){
                    Group group = membership.getGroup();
                    //Activity endState = group.getWorkflow().getActivity("end");
                    //membership.setState(endState);
                }
            }
        }*/
        return this.forward(mapping, "success", categoryID);

    }
}
