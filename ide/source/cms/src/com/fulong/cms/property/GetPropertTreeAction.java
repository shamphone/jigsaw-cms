package com.fulong.cms.property;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.PropertyDefinitionTreeBuilder;

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
public class GetPropertTreeAction extends PropertyBaseAction {

    /**
     * definitionPerform
     *
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward propertyPerform(ActionMapping mapping,
                                       ActionForm aform,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws
            Exception {
        String id = request.getParameter("ID");
        NodeDefinition def = this.getRepository(request).getDefinitionManager().
                             getDefinition(id);
        PropertyDefinitionTreeBuilder builder = new
                                                PropertyDefinitionTreeBuilder(
                def);
        request.setAttribute("propertylist", builder.build());
        request.setAttribute("definitionId",id);
        request.setAttribute("root",request.getParameter("root"));
        return mapping.findForward("success");
    }
}