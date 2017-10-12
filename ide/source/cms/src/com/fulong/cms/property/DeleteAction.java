package com.fulong.cms.property;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.PropertyDefinition;

/**
 * <p>Title: 龙驭网站管理系统</p>
 *
 * <p>Description: 龙驭网站管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author jiangqi
 * @version 1.0
 */
public class DeleteAction extends PropertyBaseAction {
    /**
     * dictPerform
     *
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward propertyPerform(ActionMapping mapping,
                                       ActionForm form,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws
            Exception {
        String definitionId = request.getParameter("definitionId");
        NodeDefinition definition = this.getRepository(request).getDefinitionManager().getDefinition(definitionId);  
        String[] ids = request.getParameterValues("id");
        for (int i = 0; ids != null && i < ids.length; i++) {
            PropertyDefinition property = definition.getPropertyDefinition(ids[i]);
            if(property!=null)
            	definition.delete(property);
        }
        return mapping.findForward("success");
    }
}
