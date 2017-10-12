package com.fulong.site.clip;

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
public class CreateAction extends ClipBaseAction {
    
    protected ActionForward templateExecute(ActionMapping mapping,
                                            ActionForm tform,
                                            HttpServletRequest request,
                                            HttpServletResponse response) throws
            Exception {
        // 获取当前栏目的模板
        String templateName = request.getParameter("templateName");
        request.setAttribute("templateName",templateName);
        SiteTemplate template = this.getSiteFactory(request).getTemplate(templateName);

        // 设置默认的父栏目
        String folderPath = request.getParameter("folderPath");
        SiteFolder siteFolder = template.getFolder(folderPath);
        
        String no = generateID(siteFolder,"clip",request);
        request.setAttribute("name", "clip" + no);
        request.setAttribute("displayName", this.getResources(request).getMessage("clip") + no);
        
        request.setAttribute("folderPath", folderPath);
        request.setAttribute("ParentDisplayName", siteFolder.getDisplayName());
        return mapping.findForward("success");
    }
}