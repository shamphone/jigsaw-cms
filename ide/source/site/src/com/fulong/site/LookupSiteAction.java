package com.fulong.site;

import com.fulong.longcon.site.Site;
import org.apache.struts.action.ActionForward;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;

/**
 * 获取当前请求的网站的最终页面。
 *
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
public class LookupSiteAction extends SiteBaseAction {

    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response) throws
            Exception {
        String domain =request.getServerName();
        Site site = this.getSiteFactory(request).getSite(domain);
        if (site != null) {
            String path = "sites/" + site.getTemplate().getName() +
                          "/index.jsp";
            response.sendRedirect(path);
        }
        return null;
    }

}
