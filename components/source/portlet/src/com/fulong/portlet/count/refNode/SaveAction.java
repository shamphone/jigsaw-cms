package com.fulong.portlet.count.refNode;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;

import com.fulong.portlet.PortletAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * <p>Title: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公司</p>
 *
 * @author jiangqi
 * @version 2.0
 */
public class SaveAction extends PortletAction {
    /**
     *
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request ActionRequest
     * @param response ActionResponse
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward action(
            ActionMapping mapping,
            ActionForm form,
            ActionRequest request,
            ActionResponse response) throws Exception {

        PortletPreferences modified = request.getPreferences();
        modified.store();
        return null;
    }

}
