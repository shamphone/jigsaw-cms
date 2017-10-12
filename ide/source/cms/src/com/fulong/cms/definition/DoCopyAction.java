package com.fulong.cms.definition;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fulong.cms.form.CategoryForm;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.NodeDefinitionTreeBuilder;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <p>Title: 龙驭网站管理系统</p>
 *
 * <p>Description: 龙驭网站管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author liuzijun
 * @version 1.0
 */
public class DoCopyAction extends DefinitionBaseAction {
    /**
     * categoryPerform
     *
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward definitionPerform(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request,
                                         HttpServletResponse response) throws
            Exception {
        CategoryForm categoryForm = (CategoryForm) form;
        NodeDefinition newDef = this.getRepository(request).getDefinitionManager().
                                copyDefinition(categoryForm.
                                               getName(),
                                               this.getRepository(request).
                                               getDefinitionManager().
                                               getDefinition(categoryForm.
                getCategoryID()), categoryForm.isChildrenCategory());
        NodeDefinitionTreeBuilder builder = new NodeDefinitionTreeBuilder( newDef );
        request.getSession().setAttribute("categoryTree", builder.build());
        request.getSession().setAttribute("category", newDef);
        request.getSession().setAttribute("isChildrenCategory", categoryForm.isChildrenCategory());
        return mapping.findForward("success");
    }
}
