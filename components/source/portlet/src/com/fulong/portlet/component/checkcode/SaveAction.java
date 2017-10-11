package com.fulong.portlet.component.checkcode;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import com.fulong.portlet.PortletAction;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;


/**
 * <p>Title: WebMaster Core Library</p>
 *
 * <p>Description: Longcon WebMaster SV3</p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: Beijing Zhongke Fulong Computer Tech. LTD</p>
 *
 * @author liulei
 * @version 1.0
 */
public class SaveAction extends PortletAction {
    /**
     *
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request ActionRequest
     * @param response ActionResponse
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward action(
            ActionMapping mapping,
            ActionForm form,
            ActionRequest request,
            ActionResponse response) throws Exception {

        request.getPreferences().store();
        return null;
    }
}
