package com.fulong.cms;

import javax.servlet.http.*;

import org.apache.struts.action.*;
import com.fulong.longcon.security.User;
import com.fulong.longcon.repository.Node;

/**
 * <p>Title: 龙驭网站管理系统</p>
 *
 * <p>Description: 龙驭网站管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @liuzijun
 * @version 1.0
 */
public class ViewAction extends CMSBaseAction {
    /**
     * doPerform
     *
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws Exception
     */
    protected ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws
            Exception {
        String nodeID = request.getParameter("nodeID");
        Node node = this.getRepository(request).getNode(nodeID);
        request.setAttribute("node", node);
        return mapping.findForward("success");
    }
}
