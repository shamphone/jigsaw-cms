package com.fulong.cms.resource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.cms.form.ResourceForm;
import com.fulong.longcon.repository.Node;

/**
 *
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
public class DeleteAction extends ResourceBaseAction {
    /**
     * resourcePerform
     *
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward resourcePerform(ActionMapping mapping,
                                         ActionForm form,
                                         HttpServletRequest request,
                                         HttpServletResponse response) throws
            Exception {
        ResourceForm resourceForm = (ResourceForm) form;
        String[] items = resourceForm.getItem();
        if (items != null) {
            for (int i = 0; i < items.length; i++) {
                int pos = items[i].indexOf("resources");
                String name= items[i].substring(pos+ "resources".length()+1);
                ResourceWrapper folder = new ResourceWrapper(this.getRepository(request),name);
                folder.deleteTree();
            }
        }
        Node org = this.getCurrentOrg(request, response);
        return this.forward(mapping, "success",
                                    org.getID() + "/");
    }
}
