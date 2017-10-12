package com.fulong.site.css;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.site.SiteFolder;
import com.fulong.longcon.site.SiteTemplate;

/**
 * <p>Title: Longcon WebMaster SV3</p>
 *
 * <p>Description: Longcon WebMaster SV3</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Beijing Zhongke Fulong Computer Tech. LTD.</p>
 *
 * @author luobin
 * @version 3.1
 */

public class CreateAction extends CSSBaseAction {
    public ActionForward cssPerform(ActionMapping mapping,
                                 ActionForm sform,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        String templateName = request.getParameter("templateName");
        request.setAttribute("templateName",templateName);
        SiteTemplate template = this.getSiteFactory(request).getTemplate(templateName);

        // 设置默认的父栏目
        String folderPath = request.getParameter("folderPath");
        SiteFolder siteFolder = template.getFolder(folderPath);
        
        // 生成默认网站栏目名称和网站栏目显示名称
        String no = generateID(siteFolder,"style",request);
        request.setAttribute("name", "style" + no);
        request.setAttribute("displayName", this.getResources(request).getMessage("style") + no);
        
        request.setAttribute("ParentDisplayName", siteFolder.getDisplayName());
        return mapping.findForward("success");
    }
}
