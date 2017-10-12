package com.fulong.site.management;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.site.Site;
import com.fulong.longcon.site.SiteTemplate;
import com.fulong.site.form.SiteForm;

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
 * @author liuzijun
 * @version 1.0
 */
public class UpdateAction extends ManagementBaseAction {

    protected ActionForward managementExecute(ActionMapping mapping,
                                              ActionForm sform,
                                              HttpServletRequest request,
                                              HttpServletResponse response) throws
            Exception {
        SiteForm form = (SiteForm) sform;
        Node site = this.getRepository(request).getNode(form.getID());
        if(site!=null){
        	site.setProperty("displayName", form.getDisplayName());
        	site.setProperty("templates", form.getTemplateIDs());
        	site.setProperty("navigateTemplates", form.getNavigateTemplateIDs());
        }
        request.setAttribute("site",site);
        return mapping.findForward("success");
    }
}
