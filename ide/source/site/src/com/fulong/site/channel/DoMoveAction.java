package com.fulong.site.channel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.site.Channel;
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
public class DoMoveAction extends ChannelBaseAction {
    /**
     *
     * @throws Exception ：在这个方法的实现中，原则上不进行任何的异常处理，仅抛出异常
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     */
    protected ActionForward templateExecute(ActionMapping mapping,
                                            ActionForm form,
                                            HttpServletRequest request,
                                            HttpServletResponse response) throws
            Exception {
    	String channelTemplateName = request.getParameter("channelTemplateName");
    	String channelPath = request.getParameter("channelPath");
    	SiteTemplate channelTemplate = this.getSiteFactory(request).getTemplate(channelTemplateName);
    	Channel channel = channelTemplate.getChannel(channelPath);
    	
    	String folderTemplateName = request.getParameter("folderTemplateName");
        String parentFolder = request.getParameter("folderPath");
        SiteTemplate folderTemplate = this.getSiteFactory(request).getTemplate(folderTemplateName);
        SiteFolder folder = folderTemplate.getFolder(parentFolder);
        
    	this.moveTo(channel, folder);
        return null;
    }
}
