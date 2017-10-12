package com.fulong.cms.content;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;

/**
 *
 * <p>Title: 龙驭内容管理系统-插件</p>
 *
 * <p>Description: 主要包括工作流、编辑器、校验、格式化</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author 姜崎
 * @version 2.0
 */
public class SendContentActivity extends BatchAction {
    /**
     * contentPerform
     *
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward doExecute(Node[] contents,
    		NodeDefinition category,
                                   ActionMapping mapping,
                                   ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response) throws

            Exception {
        if ((contents == null) || (contents.length < 1))
            return mapping.findForward("noSelection");
        /**
        ExchangeManager manager = this.getRemoteManager();
        String categoryID = request.getParameter("categoryID");
        Iterator categories = manager.getAllCategories(manager.SENDER,
                request.getUserPrincipal(),
                this.getRepository(request).
                getDefinitionManager().getDefinition(categoryID));
        // 用于属性匹配，提供源分类的属性列表
        List list = new ArrayList();
        List properties = IteratorUtils.toList(category.
                                               propertyDefinitions(false));
        for (int i = 0; i < properties.size(); i++) {
            PropertyDefinition pd = (PropertyDefinition) properties.get(i);
            list.add(pd);
        }
        request.setAttribute("list", list);
        request.setAttribute("categories", categories);
        request.setAttribute("categoryID", categoryID);
        request.setAttribute("activity", this);
        */
        return mapping.findForward("success");
    }
}
