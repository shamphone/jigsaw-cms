package com.fulong.site.management;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fulong.longcon.site.Site;
import com.fulong.site.form.SiteForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fulong.longcon.site.SiteCategory;
import com.fulong.longcon.site.SiteTemplateCollection;

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
public class SiteUpgradeAction extends ManagementBaseAction {
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
        SiteCategory category = this.getSiteFactory(request).getCategory(categoryID);
        if (category != null) {
            request.setAttribute("category",category);
        }

        Site site = this.getSiteFactory(request).getSite(siteID);
        if (site.getTemplate() != null) {
            searchForm.setSiteModel(site.getTemplate().getDisplayName().
                                    toString());
        }
        request.setAttribute("groups",
                             this.getSiteFactory(request).categories());
        SiteTemplateCollection stc = this.getSiteFactory(request).getTemplates();
        stc.filterByState("published");
        request.setAttribute("siteModels",stc.templates());
        if (site.getTemplate() != null) {
            searchForm.setSiteModel(site.getTemplate().getID());
        }
        if(site.getCategory()!=null){
            searchForm.setGroups(site.getCategory().getID());
        }
        request.setAttribute("searchForm", searchForm);
        request.setAttribute("site", site);
        request.setAttribute("siteID", siteID);
        request.setAttribute("categoryID", categoryID);
        */
        return mapping.findForward("success");
    }
}
