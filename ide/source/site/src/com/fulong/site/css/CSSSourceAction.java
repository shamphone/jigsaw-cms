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
public class CSSSourceAction extends CSSBaseAction {
    public CSSSourceAction() {
        super();
    }

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
    public ActionForward cssPerform(ActionMapping mapping,
                                    ActionForm form,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws
            Exception {
        /*
                 CSSForm cssForm=(CSSForm)form;
                 String id=request.getParameter("id");
                 StyleSheet styleSheet=this
                              .getCSSRepository(request,response)
                              .getCSS(id);
                 cssForm.setCssID(styleSheet.getContextPath());
                 cssForm.setSource(styleSheet.getText());
                 request.setAttribute("cssStyleSheet",styleSheet);
         */
        return mapping.findForward("success");

    }
}
