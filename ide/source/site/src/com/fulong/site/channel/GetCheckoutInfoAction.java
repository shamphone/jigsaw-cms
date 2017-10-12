package com.fulong.site.channel;

import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.common.util.ParameterSet;
import com.fulong.longcon.site.Channel;
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
public class GetCheckoutInfoAction extends ChannelBaseAction {
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
        String templateID = request.getParameter("template");
        SiteTemplate template = this.getSiteFactory(request).getTemplate(templateID);
        String contextPath = request.getParameter("channel");
        Channel channel = null;
        response.setContentType("text/xml");
        response.setHeader("Content-Type", "text/xml; charset=UTF-8");
        Writer writer = response.getWriter();
        if(template==null){
        	response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown template name :"+templateID+".");
        	return null;
        }
        channel = template.getChannel(contextPath);
        if(channel == null){
        	response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown context path :"+ contextPath+".");
        	return null;        	
        }
        ParameterSet set = new ParameterSet();
        if(channel.isWriting()){
        	if(channel.getWriter()!=null){
        		set.put("ip",channel.getWriter());
        		set.put("self", ""+channel.getWriter().equals(this.getIpAddr(request)));
        	}
        	if(channel.getLastWritingTime()!=null){
        		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        		String time = format.format(channel.getLastWritingTime());
        		set.put("time",time);
        	}
        }
        writer.append(set.toXML());
        writer.close();
        return null;
    }
}
