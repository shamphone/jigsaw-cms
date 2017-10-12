package com.fulong.site.editor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fulong.portal.model.PreferenceParser;
import com.fulong.site.template.TemplateBaseAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fulong.longcon.site.Channel;
import com.fulong.longcon.site.SiteTemplate;
import com.fulong.portal.servlet.PortalServletRequest;
import com.fulong.portal.servlet.PortalActionResponse;

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
public class EditPortletAction extends TemplateBaseAction {
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
        if (request.getParameter("portlet.action") == null) {
            //编辑状态
            String channelPath = request.getParameter("channel.page");
            request.setAttribute(Channel.class.getName(),
                                 this.parseChannel(channelPath,request));
            request.setAttribute(SiteTemplate.class.getName(),
                                 this.parseTemplate(channelPath,request));
            String preferencesXML = request.getParameter("portlet.pref");
            if (preferencesXML != null) {
                PreferenceParser parser = new PreferenceParser();
                request.setAttribute("preferences", parser.parse(preferencesXML));
            }
            return mapping.findForward("edit.success");
        } else {
            //保存状态
            request = new PortalServletRequest(request);
            String channelPath = request.getParameter("channel.page");
            request.setAttribute(Channel.class.getName(),
                                 this.parseChannel(channelPath,request));
            request.setAttribute(SiteTemplate.class.getName(),
                                 this.parseTemplate(channelPath,request));
            String preferencesXML = request.getParameter("portlet.pref");
            if (preferencesXML != null) {
                PreferenceParser parser = new PreferenceParser();
                request.setAttribute("preferences", parser.parse(preferencesXML));
            }
            //导航到这个页面去处理action
            request.getRequestDispatcher("/site/editor/editPortlet.jsp")
                    .forward(request, new PortalActionResponse(response));
            //action处理完成之后，将preference取出来，渲染view状态的占位符
            request.getSession().setAttribute("javax.portlet.preferences",
                                              request.getAttribute(
                    "javax.portlet.preferences"));
            request.getSession().setAttribute(Channel.class.getName(),
                    this.parseChannel(channelPath,request));
            request.getSession().setAttribute(SiteTemplate.class.getName(),
                    this.parseTemplate(channelPath,request));
            response.sendRedirect(request.getContextPath()+"/site/editor/saveSuccess.jsp?portlet.mode=view&portlet.window.owner="
                    + request.getParameter("portlet.window.owner")
                    + "&portlet.type=" +
                    request.getParameter("portlet.type"));
            return null;
        }
    }
}
