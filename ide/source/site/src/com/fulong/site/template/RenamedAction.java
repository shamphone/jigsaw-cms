package com.fulong.site.template;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fulong.site.form.TemplateForm;
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
 * @author lichengzhao
 * @version 3.1
 */
public class RenamedAction extends TemplateBaseAction {

    protected ActionForward templateExecute(ActionMapping mapping,
                                            ActionForm tform,
                                            HttpServletRequest request,
                                            HttpServletResponse response) throws
            Exception {
        TemplateForm form = (TemplateForm) tform;
        SiteTemplate template = this.getSiteFactory(request).getTemplate(form.getId());
        template.setDisplayName(form.getDisplayName());
        template.setResolution(form.getResolution());
        request.setAttribute("template", template);
        request.setAttribute("path", template.getRootFolder().getContextPath());
        return mapping.findForward("success");
    }
}
