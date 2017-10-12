package com.fulong.site.channel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.site.Channel;
import com.fulong.longcon.site.SiteTemplate;

/**
 * <p>Title: 龙驭网站管理系统</p>
 *
 * <p>Description: 龙驭网站管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author lixf
 * @version 1.0
 */
public class DoPublishAction extends ChannelBaseAction {
    public ActionForward templateExecute(ActionMapping mapping,
                                         ActionForm form,
                                         HttpServletRequest request,
                                         HttpServletResponse response) throws
            Exception {
        SiteTemplate site = this.getSiteFactory(request).getTemplate(request.getParameter("templateID"));
        String[] channels = request.getParameterValues("channels");
        if(channels!=null)
        for (int i = 0; (channels != null) & (i < channels.length); i++) {
            Channel channel=site.getChannel(channels[i]);
            channel.publish();
            request.setAttribute("working", channel.getWorkingPath());
            //channel.setState(Channel.State.PUBLISHED);
        }
        request.setAttribute("channels", channels);
        return mapping.findForward("success");

    }
}
