package com.fulong.site.editor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
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
 * @author lichengzhao
 * @version 1.0
 */
public class SaveFileAction extends EditorBaseAction {

    protected ActionForward templateExecute(ActionMapping mapping,
                                            ActionForm form,
                                            HttpServletRequest request,
                                            HttpServletResponse response) throws
            Exception {
        InputStream input = request.getInputStream();
        String relativePath = request.getParameter("path");
        File file = new File(this.getServletContext().getRealPath(relativePath));
        OutputStream output = new FileOutputStream(file);
        IOUtils.copy(input, output);
        output.close();
        input.close();
        response.setContentType("text/html");
        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        Writer writer = response.getWriter();
        writer.write("true");
        writer.close();
        return null;
    }
}