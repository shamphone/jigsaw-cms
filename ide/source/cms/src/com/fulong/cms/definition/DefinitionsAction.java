package com.fulong.cms.definition;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.cms.form.SchemaForm;

import com.fulong.longcon.repository.NodeDefinitionTreeBuilder;
import com.fulong.common.util.Tree;

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
public class DefinitionsAction extends DefinitionBaseAction {

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
    public ActionForward definitionPerform(ActionMapping mapping,
                                       ActionForm form,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws
            Exception {
        SchemaForm schemaForm = (SchemaForm) form;
        schemaForm.setParentID(schemaForm.getID() == null ?
                               NodeDefinition.NO_PROPERTIES_SCHEME : schemaForm.getID());
        String categories[] = request.getParameterValues("categories");
        NodeDefinitionTreeBuilder builder = new NodeDefinitionTreeBuilder(this.
                getRepository(request).getDefinitionManager().getDefinition(schemaForm.
                getParentID()));
        Tree tree = builder.buildPartTree();
        if (categories != null) {
            tree.diableAll();
            for (int i = 0; i < categories.length; i++) {
            	
                tree.enable(categories[i]);
            }
        }
        request.setAttribute("sel",request.getParameter("sel"));
        request.setAttribute("categorylist", tree);
        request.setAttribute("readOnly", request.getParameter("readOnly"));
        return mapping.findForward("success");
    }
}
