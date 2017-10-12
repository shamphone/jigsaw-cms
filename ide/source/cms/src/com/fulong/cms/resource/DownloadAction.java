package com.fulong.cms.resource;

import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.taglib.TagUtils;

import com.fulong.longcon.repository.Node;

/**
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
public class DownloadAction extends ResourceBaseAction {
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
    	String id = request.getParameter("id");
    	Node node = this.getRepository(request).getNode(id);
    	// resource-content
        if (node != null) {
            Long length = node.getProperty("length").getLong();
            InputStream input = node.getProperty("resource-content").getStream();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + TagUtils.getInstance().encodeURL(node.getName()));
            response.setContentLength(length.intValue());
            //read data from file and write to ServletOutputStream
            if (length != 0) {
                OutputStream output = response.getOutputStream();
                IOUtils.copy(input, output);
                output.flush();
                input.close();
                output.close();
            }
        }
        return null;
    }
}
