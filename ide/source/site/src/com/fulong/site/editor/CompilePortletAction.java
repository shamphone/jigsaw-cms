/**
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2010-2015
 */
package com.fulong.site.editor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.site.Channel;
import com.fulong.longcon.site.SiteTemplate;
import com.fulong.portal.model.PreferenceParser;
import com.fulong.site.template.TemplateBaseAction;

/**
 * CompilePortletAction
 * @author    <a href="lixf@fulong.com.cn">李雄峰</a>
 * @date      2010-5-29
 */
public class CompilePortletAction extends TemplateBaseAction {
    /**
    *
    * @param mapping ActionMapping
    * @param form ActionForm
    * @param request HttpServletRequest
    * @param response HttpServletResponse
    * @return ActionForward
    * @throws Exception
    */
   protected ActionForward templateExecute(ActionMapping mapping,
                                           ActionForm form,
                                           HttpServletRequest request,
                                           HttpServletResponse response) throws
           Exception {
           //request = new PortalServletRequest(request);
           String channelPath = request.getParameter("channel.page");
           request.setAttribute(Channel.class.getName(),
                                this.parseChannel(channelPath,request));
           request.setAttribute(SiteTemplate.class.getName(),
                                this.parseTemplate(channelPath,request));
           String preferencesXML = request.getParameter("portlet.pref");
           if (preferencesXML != null) {
               PreferenceParser parser = new PreferenceParser();               
               request.setAttribute("preferences", parser.parse(preferencesXML));
               request.setAttribute("portlet.id", parser.getId());
               request.setAttribute("portlet.type", parser.getType());
           }
           //导航到这个页面去处理action
           return mapping.findForward("success");
      
   }
}
