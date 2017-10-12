package com.fulong.site.channel;

import java.io.File;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.site.SiteTemplate;

/**
 * 检查栏目名是否可用
 * <p>Title: Coolink平台协同管理系统</p>
 *
 * <p>Description: Coolink平台协同管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2010</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author jiangqi
 * @version 1.0.1
 */
public class CheckChannelAction extends ChannelBaseAction {
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
        String templateName = request.getParameter("templateName");
        String channelName = request.getParameter("channelName");
        SiteTemplate template = this.getSiteFactory(request).getTemplate(templateName);
        File channelFile = null;
        if(template!=null){
        	channelFile = getChannelFile(template, channelName);
        }
        response.setContentType("text/html");
        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        Writer writer = response.getWriter();
        writer.append("" + (channelFile == null));
        writer.close();
        return null;
    }
}
