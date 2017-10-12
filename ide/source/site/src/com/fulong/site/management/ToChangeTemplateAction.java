package com.fulong.site.management;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
 * @version 2.0
 */
public class ToChangeTemplateAction extends ManagementBaseAction {

    protected ActionForward managementExecute(ActionMapping mapping,
                                              ActionForm aform,
                                              HttpServletRequest request,
                                              HttpServletResponse response) throws
            Exception {
		return mapping.findForward("success");
    }
}
