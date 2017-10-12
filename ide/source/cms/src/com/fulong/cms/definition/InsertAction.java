package com.fulong.cms.definition;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.cms.form.SchemaForm;
import com.fulong.longcon.repository.NodeDefinition;

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
public class InsertAction extends DefinitionBaseAction {
    /**
     * dictPerform
     *
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws Exception
     * @todo Implement this com.fulong.cms.dict.DictBaseAction method
     */
    public ActionForward definitionPerform(ActionMapping mapping,
                                           ActionForm form,
                                           HttpServletRequest request,
                                           HttpServletResponse response) throws Exception 
    {
        SchemaForm dictForm = (SchemaForm) form;        
        if (dictForm.getName() != null && !dictForm.getName().equals("")) 
        {
            NodeDefinition entry = this.getRepository(request).getDefinitionManager().createDefinition(dictForm.getName(),this.getRepository(request).getDefinitionManager().getDefinition(dictForm.getParentID()));
            request.setAttribute("dict", entry);
        }
        return mapping.findForward("success");
    }
}
