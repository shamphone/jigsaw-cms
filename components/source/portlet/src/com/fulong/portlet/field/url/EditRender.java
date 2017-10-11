package com.fulong.portlet.field.url;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.portlet.cms.ListContentPortletRender;
import com.fulong.portlet.field.PortletConfig;

/**
 *
 * <p>Title: 龙驭门户引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author Lixf
 * @version 1.0
 */
public class EditRender extends ListContentPortletRender {
    public ActionForward render(
            ActionMapping mapping,
            ActionForm form,
            RenderRequest request,
            RenderResponse response) throws Exception {
        PortletConfig config = new PortletConfig(request.getPreferences());
        NodeDefinition category = lookUpDefinition(request);
        request.getPreferences().setValue("category", category.getID());
        request.setAttribute("category", category);
        if (config.getField() != null) {
            PropertyDefinition property = category.getPropertyDefinition(config.getField());
            request.setAttribute("property", property);
        }
        return mapping.findForward("success");
    }
}
