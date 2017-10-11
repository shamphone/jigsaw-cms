package com.fulong.portlet.count.refNode;

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
        String refField = preferences.getValue("refField", null);
        if (refField != null) {
            PropertyDefinition pd = def.getPropertyDefinition(refField);
            if(pd!=null){
            	request.setAttribute("property", pd);
                request.setAttribute("definition", pd.getReferenceDefinition());
            }else{
            	request.setAttribute("propertyDeleted", refField+"已被删除");
            }
        }
        return mapping.findForward("success");

    }
}
