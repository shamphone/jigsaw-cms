package com.fulong.cms.definition;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.NodeDefinitionIterator;

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
public class CopyAction extends DefinitionBaseAction {
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
        String categoryID = request.getParameter("categoryID");
        NodeDefinition n=this.getRepository(request).getDefinitionManager().getDefinition(categoryID);
        NodeDefinitionIterator it=n.getInheritDefinitions();
        if(it.hasNext()){
        	request.setAttribute("noInherit", "noInherit");
        }
        request.setAttribute("categoryID", categoryID);
        return mapping.findForward("success");
    }
}