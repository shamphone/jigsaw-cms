package com.fulong.cms.content;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.cms.form.SchemaForm;

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
public class SearchAction extends ContentBaseAction {

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
    public ActionForward doExecute(ActionMapping mapping,
                                       ActionForm form,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws
            Exception {
    	SchemaForm schemaForm = (SchemaForm) form;
        request.setAttribute("rootId", schemaForm.getID());
        request.setAttribute("isMutiple",request.getParameter("isMutiple"));
        request.setAttribute("left",request.getParameter("left"));
        request.setAttribute("searchText",request.getParameter("searchText"));
        request.setAttribute("orderfield",request.getParameter("orderfield"));
        request.setAttribute("selectedProps",request.getParameter("selectedProps"));
        request.setAttribute("sort",request.getParameter("sort"));
        request.setAttribute("readOnly", request.getParameter("readOnly"));
        return mapping.findForward("success");
    }
}
