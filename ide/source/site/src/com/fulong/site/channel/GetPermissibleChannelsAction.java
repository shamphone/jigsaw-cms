package com.fulong.site.channel;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

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
public class GetPermissibleChannelsAction extends ChannelBaseAction {
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
        String templateId = request.getParameter("templateId");
        String groupId = request.getParameter("groupId");
//        SiteTemplate template = this.getSiteFactory(request).getTemplate(templateId);
//        PassportIdentity group = this.getPassportProvider().getPrincipal(groupId);
//        List<Channel> channels = template.getPermissibleChannelsByGroup(group);
//        if (channels == null || channels.size() == 0)
//        	return null;
//        StringBuilder builder = new StringBuilder();
//        for (Channel channel : channels)
//        	builder.append(channel.getID()).append(",");
//		if (builder.length() > 0)
//			builder.delete(builder.length() - 1, builder.length());
        response.setContentType("text/html");
        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        Writer writer = response.getWriter();
//        writer.append(builder);
        writer.close();
        return null;
    }
}