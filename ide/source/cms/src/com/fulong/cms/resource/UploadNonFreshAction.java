package com.fulong.cms.resource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.cms.form.UploadFileForm;

/**
 *
 * <p>Title: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公司</p>
 *
 * @author Haojingwei
 * @version 2.0
 */
public class UploadNonFreshAction extends ResourceBaseAction {

    protected Log log = LogFactory.getLog(this.getClass());

    public ActionForward resourcePerform(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        UploadFileForm uploadForm = (UploadFileForm) form;
        String path = this.uploadFile(uploadForm.getFile(), request, response);
        path = path.replace("\\", "/");
        request.setAttribute("path", path);
        return mapping.findForward("outlet");
    }
}

