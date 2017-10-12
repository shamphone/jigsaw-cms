package com.fulong.site.template;

import java.io.File;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.site.SiteFolder;
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
 * @version 1.0
 */
public class DeleteAction extends TemplateBaseAction {
	
    protected ActionForward templateExecute(ActionMapping mapping, ActionForm form, 
    		HttpServletRequest request, HttpServletResponse response) throws Exception {
    	response.setHeader("Cache-Control", "no-cache");
        String[] templateIds = request.getParameterValues("templateId");
        if (templateIds != null && templateIds.length > 0) {
            for (int i = 0; i < templateIds.length; i++) {
                SiteTemplate template = this.getSiteFactory(request).getTemplate(templateIds[i]);
                if (template != null) {
                	/*SiteFolder folder = template.getRootFolder();
	                File deletedFolder = new File(folder.getParent().getContextPath(), "." + folder.getName());
	                folder.renameTo(deletedFolder);*/
	                this.getSiteFactory(request).deleteTemplate(template);
                }
            }
        }
        return null;
    }
}
