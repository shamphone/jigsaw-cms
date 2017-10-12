package com.fulong.site.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.taglib.TagUtils;

import com.fulong.common.FileUtils;
import com.fulong.longcon.site.SiteTemplate;

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
 * @author lichengzhao
 * @version 1.0
 */
public class ExportAction extends ResourceBaseAction {

    public ActionForward resourcePerform(ActionMapping mapping,
                                         ActionForm form,
                                         HttpServletRequest request,
                                         HttpServletResponse response) throws
            Exception {
    	String name = request.getParameter("template");
    	String[] pathes = request.getParameterValues("path");
    	SiteTemplate template = this.getSiteFactory(request).getTemplate(name);
    	File dest = null;
    	if (pathes == null || pathes.length == 0) {
    		return null;
    	} else if (pathes.length == 1) {
    		if (pathes[0].equals(""))
    			return null;
        	File file = new File(template.getRealPath(pathes[0]));
        	if (file.isDirectory())
        		dest = FileUtils.compress(file, null);
        	else
        		dest = file;
    	} else {
    		File[] files = new File[pathes.length];
    		for (int i = 0; i < pathes.length; i++)
    			files[i] = new File(template.getRealPath(pathes[i]));
    		File parent = files[0].getParentFile();
    		dest = FileUtils.compress(parent, files);
    	}
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + TagUtils.getInstance().encodeURL(dest.getName()));
        response.setContentLength((int) dest.length());
        InputStream input = null;
        OutputStream output = null;
        try {
			input = new FileInputStream(dest);
			output = response.getOutputStream();
			IOUtils.copy(input, output);
			output.flush();
		} finally {
			IOUtils.closeQuietly(input);
			IOUtils.closeQuietly(output);
		}
		return null;
    }
}
