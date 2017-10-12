package com.fulong.cms.resource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fulong.cms.form.ResourceForm;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
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
public class FolderItemsAction extends ResourceBaseAction {
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
    public ActionForward resourcePerform(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request,
                                         HttpServletResponse response) throws
            Exception {
        ResourceForm resourceForm = (ResourceForm) form;
        ResourceWrapper root = this.getRootFolder(request);
        String folder = resourceForm.getDecodedFolder();
        resourceForm.setFolder(folder);
        ResourceWrapper current;
        if (folder == null) {
            current = root;
        } else {
            current = new ResourceWrapper(this.getRepository(request), folder);
        }
        ResourceWrapper parent = current.getParent();
        request.setAttribute("folder", current);
        request.setAttribute("childFolders", current.getChildFolders());
        request.setAttribute("childFiles", current.getChildFiles());
        request.setAttribute("parentFolder", parent);
        request.setAttribute("folderList", root.childTree(false));
        request.setAttribute("owner", this.getCurrentOrg(request, response));
        return mapping.findForward("success");

    }
}
