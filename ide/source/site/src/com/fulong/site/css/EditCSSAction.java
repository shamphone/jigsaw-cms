package com.fulong.site.css;

import java.io.File;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fulong.site.form.CSSForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fulong.common.FileUtils;

public class EditCSSAction
    extends CSSBaseAction {

    /**
     * cssPerform
     *
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws Exception
     * @todo Implement this com.fulong.cms.site.css.CSSBaseAction method
     */
    public ActionForward cssPerform(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws
        Exception {
        CSSForm cssForm = (CSSForm) form;
        String path = cssForm.getPath();
        File file = new File(request.getSession().getServletContext().
                             getRealPath(path));
        if (!file.exists())
            file.createNewFile();
        String content = FileUtils.readFileToString(file, "utf-8");
        cssForm.setSource(content);
        request.setAttribute("channelListPath", request.getParameter("channelListPath"));
        return mapping.findForward("success");
    }
}
