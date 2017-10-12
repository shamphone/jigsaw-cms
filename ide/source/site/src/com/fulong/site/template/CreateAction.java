package com.fulong.site.template;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.site.SiteCategory;
import com.fulong.longcon.site.SiteTemplate;
import com.fulong.site.form.TemplateForm;
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
public class CreateAction extends TemplateBaseAction {

    private static final String FORMAT = "%1$03d";

    protected ActionForward templateExecute (ActionMapping mapping,
                                             ActionForm tform,
                                             HttpServletRequest request,
                                             HttpServletResponse response) throws
            Exception {
    	TemplateForm form = (TemplateForm) tform;
        // 生成默认模板名称和显示名称
        int tempNo = 1;
        String no = String.format(FORMAT, tempNo);
        SiteTemplate template = this.getSiteFactory(request).getTemplate("template" + no);
        while (template != null) {
            no = String.format(FORMAT, ++tempNo);
            template = this.getSiteFactory(request).getTemplate("template" + no);
        }
        form.setName("template" + no);
        form.setDisplayName(this.getResources(request).getMessage("template") + no);

        form.setAdvanced("blank");
        // 设置默认类别
        String templateID = request.getParameter("templateID");
        request.setAttribute("resolutions", this.getSiteFactory(request).getResolutions());
        // 模板语言列表
    	request.setAttribute("languages", this.getLanguages(request));
        return mapping.findForward("success");
    }
}
