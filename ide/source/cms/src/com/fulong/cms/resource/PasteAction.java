package com.fulong.cms.resource;

import java.io.File;
import java.net.URLDecoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fulong.common.FileUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fulong.common.FileWrapper;
import com.fulong.cms.form.ResourceForm;
import com.fulong.longcon.resource.Resource;


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
public class PasteAction extends ResourceBaseAction {
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
        String contextPath = resourceForm.getDecodedFolder();
        Resource folder = this.getResourceManager().getResource(contextPath);
        Cookie operation = this.getCookie(request, response, "operation");
        if (operation != null) {
            Cookie fileString = this.getCookie(request, response, "clipboard");
            if (fileString != null) {
                String fileList = URLDecoder.decode(fileString.getValue(),
                        "UTF-8");
                String[] items = fileList.split("\\;");
                for (int i = 0; i < items.length; i++) {
                    ResourceWrapper item = new ResourceWrapper(this.getRepository(request), items[i]);
                    item.copy(folder);
                    if (operation.getValue().equals("cut")) {
                        item.deleteTree();
                    }
                }
            }
            if (operation.getValue().equals("cut")) {
                this.deleteCookie(request, response, "operation");
                this.deleteCookie(request, response, "clipboard");
            }
        }

        return this.forward(mapping, "success",
                                   request.getParameter("folder"));
    }
}
