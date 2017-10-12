package com.fulong.site.script;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.site.SiteFolder;
import com.fulong.longcon.site.SiteTemplate;

/**
 * <p>Title: Coolink平台协同管理系统</p>
 *
 * <p>Description: Coolink平台协同管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2010</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author luobin
 * @version 1.0.1
 */
public class CreateAction extends ScriptBaseAction {
    public ActionForward scriptPerform(ActionMapping mapping,
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
         String no = generateID(siteFolder,"script",request);
         request.setAttribute("name", "script" + no);
         request.setAttribute("displayName", this.getResources(request).getMessage("script") + no);
         
         request.setAttribute("ParentDisplayName", siteFolder.getDisplayName());
         return mapping.findForward("success");
    }
}
