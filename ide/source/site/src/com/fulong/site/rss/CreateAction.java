package com.fulong.site.rss;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.site.SiteFolder;
import com.fulong.longcon.site.SiteTemplate;

public class CreateAction extends RSSBaseAction{
	@Override
	protected ActionForward templateExecute(ActionMapping mapping,
			ActionForm tform, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
	       // 获取当前栏目的模板
        String templateName = request.getParameter("templateName");
        request.setAttribute("templateName", templateName);
        
        String channelType = request.getParameter("channelType");
        
        SiteTemplate template = this.getSiteFactory(request).getTemplate(templateName);
        
        // 设置默认的父栏目
        String folderPath = request.getParameter("folderPath");
        SiteFolder siteFolder = template.getFolder(folderPath);
        if(siteFolder == null){
        	siteFolder = template.getRootFolder();
        	folderPath = siteFolder.getContextPath();
        }
        
        // 生成默认网站名称和网站显示名称
        String no = generateID(siteFolder,channelType,request);
        String prefix = this.getSiteFactory(request).getChannelType(channelType).getPrefix();
        request.setAttribute("name",prefix +  no);
        request.setAttribute("displayName", this.getResources(request).getMessage(prefix) + no);
        
        request.setAttribute("folderPath",folderPath);
        request.setAttribute("parentDisplayName",siteFolder.getDisplayName());
		request.setAttribute("languages", this.getLanguages(request));
		return mapping.findForward("success");
	}
}
