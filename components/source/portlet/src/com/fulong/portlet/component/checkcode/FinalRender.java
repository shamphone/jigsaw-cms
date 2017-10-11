package com.fulong.portlet.component.checkcode;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import com.fulong.portlet.PortletRender;
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
public class FinalRender extends PortletRender
{
    /**
     *
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request RenderRequest
     * @param response RenderResponse
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward render(ActionMapping mapping, ActionForm form,
                                RenderRequest request, RenderResponse response) throws
            Exception 
    {
    	PortletPreferences preferences = request.getPreferences();
    	request.setAttribute("preferences", preferences);
        return mapping.findForward("success");
    }
}
