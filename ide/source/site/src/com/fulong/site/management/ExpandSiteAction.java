package com.fulong.site.management;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import java.text.SimpleDateFormat;
import com.fulong.longcon.workflow.Activity;
import com.fulong.longcon.site.Site;
import com.fulong.site.form.SiteForm;
import com.fulong.longcon.security.Group;
import java.util.Date;
import java.util.Calendar;
import com.fulong.longcon.security.Organization;

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
public class ExpandSiteAction extends ManagementBaseAction {
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
    protected static SimpleDateFormat bartDateFormat = new SimpleDateFormat(
            "yyyy-MM-dd");
    protected ActionForward managementExecute(ActionMapping mapping,
                                              ActionForm form,
                                              HttpServletRequest request,
                                              HttpServletResponse response) throws
            Exception {
        String categoryID = request.getParameter("categoryID");
        request.setAttribute("categoryID", categoryID);
        /*
                 SiteForm searchForm = (SiteForm) form;
                 for (int i = 0; i < searchForm.getIDs().length; i++) {
            Site site = this.getSiteFactory(request).getSite(searchForm.getIDs()[i]);
            Organization owner=(Organization)site.getOwner();
            if (site != null) {
//                Membership membership = site.getMembership();
                    if (!searchForm.getExpandDate().equals("")) {
                        owner.setExpiringDate(bartDateFormat.parse(
                                searchForm.getExpandDate()));
                    } else {
                        Date date = owner.getExpiringDate();
                        if (date == null) {
                            date = new Date();
                        }
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
         calendar.add(Calendar.MONTH, searchForm.getExpandPeriod());
                        owner.setExpiringDate(calendar.getTime());
                    }
                }
                 }
         */
        return this.forward(mapping, "success", categoryID);

    }
}
