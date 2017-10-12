package com.fulong.site.template;

import java.io.File;
import java.util.Locale;
import java.util.zip.ZipException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.fulong.common.FileUtils;
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
public class InsertAction extends TemplateBaseAction {


    protected ActionForward templateExecute(ActionMapping mapping,
                                            ActionForm tform,
                                            HttpServletRequest request,
                                            HttpServletResponse response) throws
            Exception {
        TemplateForm form = (TemplateForm) tform;
        String name = form.getName().trim();
        String advanced = form.getAdvanced();
        SiteTemplate template = this.getSiteFactory(request).getTemplate(name);

        // 该名称对应的模板已存在，创建失败
        if (template != null)
            return this.getFailedForward(mapping, request, new ActionMessage("saveTemplateAction.existent"), "template.existent");
        
    	template = this.getSiteFactory(request).createTemplate(name);
        template.setDescription(form.getDescription());
        template.setDisplayName(form.getDisplayName());
        template.setLocale(new Locale(form.getLanguage()));
        
        template.setResolution(form.getResolution());
        //changeTheContextFile(template);
        
        // 从现有设计导入
        if (advanced.equals("template")) {
        	File root = template.getRootFolder().getFile();
        	try {
        		FileUtils.unzip(root, form.getStaticPage().getInputStream() , true);
			} catch (ZipException ex) {
	            log.error(ex.getMessage());
	            this.getSiteFactory(request).deleteTemplate(template);
	            return this.getFailedForward(mapping, request, new ActionMessage("zip.bad"), "zip.bad");
			}
    	} else if (advanced.equals("copy")) {
        	String source = form.getSourceName().trim();
        	if (source.length() > 0) {
        		try {
					SiteTemplate exist = this.getSiteFactory(request).getTemplate(source);
					exist.clone(template);
				} catch (Exception e) {
					log.error(e.getMessage());
					this.getSiteFactory(request).deleteTemplate(template);
		            return this.getFailedForward(mapping, request, new ActionMessage("copy.bad"), "copy.bad");
				}
        	}
    	}
        return this.getSuccessForward(mapping, request, template);
    }
    
    private ActionForward getSuccessForward(ActionMapping mapping, HttpServletRequest request, SiteTemplate template) {
        request.setAttribute("template", template);
        request.setAttribute("path", template.getRootFolder().getContextPath());
        return mapping.findForward("success");
    }
    
    private ActionForward getFailedForward(ActionMapping mapping, HttpServletRequest request, ActionMessage message, String errorKey) {
    	request.setAttribute("languages", this.getLanguages(request));
    	request.setAttribute("resolutions", this.getSiteFactory(request).getResolutions());
        ActionMessages messages = new ActionMessages();
        messages.add(errorKey, message);
        this.saveErrors(request, messages);
        return mapping.findForward("fail");
    }
}