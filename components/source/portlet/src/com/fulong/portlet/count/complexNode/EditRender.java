package com.fulong.portlet.count.complexNode;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.portlet.cms.RepeaterEditRender;


/**
 * <p>Title: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公司</p>
 *
 * @author jiangqi
 * @version 2.0
 */
public class EditRender extends RepeaterEditRender {
    /**
     *
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request RenderRequest
     * @param response RenderResponse
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward editRender(NodeDefinition def,
                                    ActionMapping mapping,
                                    ActionForm form,
                                    RenderRequest request,
                                    RenderResponse response) throws Exception {
        PortletPreferences preferences = request.getPreferences();
        String complexField = preferences.getValue("complexField", null);
        if (complexField != null) {
            PropertyDefinition pd = def.getPropertyDefinition(complexField);
            if(pd==null){
            	request.setAttribute("propertyDeleted", complexField+"已被删除");
            }else{
            	request.setAttribute("property", pd);
                request.setAttribute("definition", pd.getReferenceDefinition());
            }
        }
        return mapping.findForward("success");

    }
}
