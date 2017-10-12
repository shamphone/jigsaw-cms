package com.fulong.site.css;

import java.io.File;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fulong.site.form.CSSForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.commons.io.FileUtils;

public class UploadCSSAction extends CSSBaseAction {

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
        File rDirectory = file.getParentFile();
        if (!rDirectory.isDirectory())
            rDirectory.mkdirs();

        FileUtils.writeStringToFile(file, cssForm.getSource(), "utf-8");
        ActionMessages messages = new ActionMessages();
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("update.success"));
        this.saveMessages(request.getSession(), messages);
        return this.forward(mapping, "success", cssForm.getPath());
    }
}
