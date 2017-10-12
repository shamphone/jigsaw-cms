package com.fulong.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * <p>Title: Longcon Passport 2.0</p>
 *
 * <p>Description: Longcon Passport core System</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Beijing Zhongke Fulong Computer Technology LTD.</p>
 *
 * @author JiangQi
 * @version 1.0
 */
public class LogoutAction
    extends BaseAction {

    /**
     * doPerform
     *
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws Exception
     * @todo Implement this com.fulong.cms.BaseAction method
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request,
                                         HttpServletResponse response) throws
        Exception {
        request.getSession().invalidate();
        return mapping.findForward("success");
    }

}
