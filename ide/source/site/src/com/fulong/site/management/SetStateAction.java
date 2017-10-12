package com.fulong.site.management;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fulong.longcon.workflow.Activity;
import com.fulong.longcon.site.Site;
import com.fulong.site.form.SiteForm;
import com.fulong.longcon.security.Group;
import com.fulong.longcon.workflow.Transition;

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
public class SetStateAction extends ManagementBaseAction {
    /**
     * managementExecute
     *
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws Exception
     * @todo Implement this com.fulong.site.management.ManagementBaseAction
     *   method
     */
    protected ActionForward managementExecute(ActionMapping mapping,
                                              ActionForm form,
                                              HttpServletRequest request,
                                              HttpServletResponse response) throws
            Exception {
        String categoryID = request.getParameter("categoryID");
        String activity = request.getParameter("activity");
        request.setAttribute("categoryID", categoryID);
        SiteForm searchForm = (SiteForm) form;
        /*
        for (int i = 0; i < searchForm.getIDs().length; i++) {
            Site site = this.getSiteFactory(request).getSite(searchForm.getIDs()[i]);
            if (site != null) {
                Membership membership = site.getMembership();
                if(membership!=null){
                    Group group = membership.getGroup();
                    //Transition transition = group.getWorkflow().getTransition(activity);
                    //Activity state = transition.getTo();
                    //membership.setState(state);
                }

            }
        }
        */
        return this.forward(mapping, "success", categoryID);

    }
}
