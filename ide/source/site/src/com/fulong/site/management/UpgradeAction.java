package com.fulong.site.management;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fulong.longcon.security.Group;
import com.fulong.longcon.site.Site;
import com.fulong.longcon.site.SiteTemplate;
import com.fulong.site.form.SiteForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fulong.longcon.site.SiteCategory;

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
public class UpgradeAction extends ManagementBaseAction {
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
        SiteForm searchForm = (SiteForm) form;
        String siteID = request.getParameter("siteID");
        String categoryID = request.getParameter("categoryID");
        /*
        Site site = this.getSiteFactory(request).getSite(siteID);
        if (searchForm.getSiteModel() != null &&
            !searchForm.getSiteModel().equals("")) {
            SiteTemplate template = this.getSiteFactory(request).getTemplate(
                    searchForm.getSiteModel());
            if (template != null) {
                site.setTemplate(template);
            }
        }

        if (searchForm.getGroups() != null && !searchForm.getGroups().equals("")) {
            SiteCategory category = this.getSiteFactory(request).getCategory(searchForm.getGroups());
            Group group = category.getGroup();
            site.setCategory(category);
//            Membership membership = site.getMembership();
//            if(membership!=null){
//                membership.setGroup(group);
//            }else{
                group.addMember(site.getOwner());
//            }
        }
        request.setAttribute("searchForm", searchForm);
        request.setAttribute("siteID", siteID);
        */
        request.setAttribute("categoryID", categoryID);
        return this.forward(mapping, "success", categoryID);

    }
}
