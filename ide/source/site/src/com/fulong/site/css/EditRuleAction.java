package com.fulong.site.css;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <p>Title: WebMaster Core Library</p>
 *
 * <p>Description: Longcon WebMaster SV3</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Beijing Zhongke Fulong Computer Tech. LTD</p>
 *
 * @author Lixf
 * @version 1.0
 */
public class EditRuleAction extends CSSBaseAction {

    /**
     * cssPerform
     *
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws Exception
     * @todo Implement this com.fulong.cms.site.css.CSSBaseAction method
     */
    public ActionForward cssPerform(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws
            Exception {
        /*
                 CSSRuleForm cssform = (CSSRuleForm) form;

                 String selector = request.getParameter("selector");
                 String css = request.getParameter("css");
                 StyleSheet sheet = this
                           .getCSSRepository(request, response)
                           .getCSS(css);
                 cssform.setCssID(css);
                 cssform.setSelector(selector);
                 CSSStyleRule rule = (CSSStyleRule) sheet.getCSSRule(selector);
                 CSSStyleDeclaration declaration = rule.getStyle();
                 for (int i = 0; i < declaration.getLength(); i++) {
            String item = declaration.item(i);
            cssform.setProperty(item,
                                declaration.getPropertyValue(item));
                 }
                 String text=rule.getStyle().getCssText();
         cssform.setRuleSource(text.substring(text.indexOf("{")+1,text.indexOf("}")));
                 request.setAttribute("fonts",this.getFonts(request));
                 request.setAttribute("stylesheet",sheet);
         */
        return mapping.findForward("success");
    }
}
