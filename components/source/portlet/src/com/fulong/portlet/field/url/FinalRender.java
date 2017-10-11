package com.fulong.portlet.field.url;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.Property;
import com.fulong.portlet.PortletRender;
import com.fulong.portlet.field.PortletConfig;

/**
 *
 * <p>Title: WebMaster Core Library</p>
 *
 * <p>Description: Longcon WebMaster SV3</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Beijing Zhongke Fulong Computer Tech. LTD</p>
 *
 * @author jiangqi
 * @version 1.0
 */
public class FinalRender extends PortletRender {

    /**
     * execute
     *
     * @param request RenderRequest
     * @param response RenderResponse
     * @throws Exception
     */
    public ActionForward render(
            ActionMapping mapping,
            ActionForm form,
            RenderRequest request, RenderResponse response) throws Exception {
        PortletPreferences preferences = request.getPreferences();
        PortletConfig config = new PortletConfig(preferences);
        Node node = lookupNode(request, response);
        if (node == null) {
            return mapping.findForward(NODONE);
        }
        Property property = node.getProperty(config.getField());
        if(property!=null){
        	 request.setAttribute("url", property.getString());
        }
        return mapping.findForward("success");

    }

}
