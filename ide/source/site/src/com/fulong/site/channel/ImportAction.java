package com.fulong.site.channel;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.site.form.ChannelForm;
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
 * @author jiangqi
 * @version 1.0
 */
public class ImportAction extends ChannelBaseAction {
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
                                            ActionForm aform,
                                            HttpServletRequest request,
                                            HttpServletResponse response) throws
            Exception {
    	ChannelForm form = (ChannelForm) aform;
    	String templateName = request.getParameter("templateName");
    	String contextPath = request.getParameter("contextPath");
    	String channelType = request.getParameter("channelType");
    	form.setTemplateName(templateName);
    	form.setPath(contextPath);
    	form.setType(channelType);
        return mapping.findForward("success");
    }
}
