package com.fulong.site.template;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.InputStream;
import java.util.Locale;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fulong.site.form.TemplateForm;
import com.fulong.longcon.site.SiteCategory;
import com.fulong.longcon.site.SiteTemplate;
import com.fulong.common.FileUtils;

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
public class UpdateAction extends TemplateBaseAction {

    protected ActionForward templateExecute(ActionMapping mapping,
                                            ActionForm tform,
                                            HttpServletRequest request,
                                            HttpServletResponse response) throws
            Exception {
        TemplateForm form = (TemplateForm) tform;
        SiteTemplate template = this.getSiteFactory(request).getTemplate(form.getId());
        template.setDescription(form.getDescription());
        template.setDisplayName(form.getDisplayName());
        template.setLocale(new Locale(form.getLanguage()));
        //上传缩略图
        if (form.getPhoto().getFileSize() != 0)
        {
        	InputStream is=form.getPhoto().getInputStream();
        	try{
        	}finally{
        		is.close();
        	}
        }
        request.setAttribute("template", template);
        return mapping.findForward("success");
    }
}
