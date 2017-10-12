package com.fulong.site.template;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fulong.longcon.site.SiteTemplate;
import com.fulong.site.form.TemplateForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
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
 * @author lichengzhao
 * @version 1.0
 */
public class RenameAction extends TemplateBaseAction {

    protected ActionForward templateExecute(ActionMapping mapping,
                                            ActionForm tform,
                                            HttpServletRequest request,
                                            HttpServletResponse response) throws
            Exception {
    	TemplateForm form=(TemplateForm)tform;
        String templateId = request.getParameter("templateId");
        SiteTemplate siteTemplate = this.getSiteFactory(request).getTemplate(templateId);
        form.setId(siteTemplate.getID());
        form.setName(siteTemplate.getName());
        form.setResolution(siteTemplate.getResolution());
        request.setAttribute("resolutions", this.getSiteFactory(request).getResolutions());
        form.setDisplayName(siteTemplate.getDisplayName());
        return mapping.findForward("success");
    }
}
