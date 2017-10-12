package com.fulong.site.css;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <p>Title: Longcon WebMaster SV3</p>
 *
 * <p>Description: Longcon WebMaster SV3</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Beijing Zhongke Fulong Computer Tech. LTD.</p>
 *
 * @author qmj
 * @version 1.0
 */

public class UpdateCSSAction extends CSSBaseAction {
    public UpdateCSSAction() {
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
    public ActionForward cssPerform(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws
            Exception {
        /*
              CSSForm cssForm = (CSSForm) form;
              String cssname = cssForm.getCssID();
              this.getCSSRepository(request, response).updateCSS(cssname,
                   cssForm.getSource());
              return this.setForwardPath(mapping, "success", cssname);
         */return null;
    }
}
