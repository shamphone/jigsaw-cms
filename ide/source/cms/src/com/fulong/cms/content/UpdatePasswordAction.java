package com.fulong.cms.content;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.security.User;


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
public class UpdatePasswordAction extends ContentBaseAction {

    public ActionForward doExecute(ActionMapping mapping,
                                   ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response) throws
        Exception {
        ActionErrors errors = new ActionErrors();
        User user = null;
        String username = request.getParameter("username");
        user = this.getPassportProvider(request).getUser(username);
        if (!user.checkPassword(request.getParameter("oldPassWord"))) {
            request.setAttribute("content", user);
            errors.add(ActionErrors.GLOBAL_ERROR,
                       new ActionError("errors.password.second"));
            this.saveErrors(request, errors);
            return mapping.findForward("failed");
        }
        user.changePassword(request.getParameter("newPassWord"));
        return mapping.findForward("success");

    }
}
