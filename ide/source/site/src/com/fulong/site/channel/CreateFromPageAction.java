package com.fulong.site.channel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.site.SiteFolder;
import com.fulong.longcon.site.SiteTemplate;
import com.fulong.site.form.ChannelForm;

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
 * @date 2010-6-17	
 * @version 1.0.1
 */
public class CreateFromPageAction extends ChannelBaseAction {
    
    protected ActionForward templateExecute(ActionMapping mapping,
                                            ActionForm tform,
                                            HttpServletRequest request,
                                            HttpServletResponse response) throws
            Exception {
    	ChannelForm form = (ChannelForm) tform;
        // 获取当前栏目的模板
        String templateName = request.getParameter("templateName");
        form.setTemplateName(templateName);
        String channelType = request.getParameter("channelType");
        SiteTemplate template = this.getSiteFactory(request).getTemplate(templateName);
        
        // 设置默认的父栏目
        String folderPath = request.getParameter("folderPath");
        SiteFolder siteFolder = template.getFolder(folderPath);
        if(siteFolder == null){
        	siteFolder = template.getRootFolder();
        	folderPath = siteFolder.getContextPath();
        }
        form.setFolderPath(folderPath);
        form.setParentDisplayName(siteFolder.getDisplayName());
        
        // 生成默认网站名称和网站显示名称
        String no = generateID(siteFolder,channelType,request);
        String prefix = this.getSiteFactory(request).getChannelType(channelType).getPrefix();
        form.setName(prefix + no);
        form.setType(channelType);
        return mapping.findForward("success");
    }
}