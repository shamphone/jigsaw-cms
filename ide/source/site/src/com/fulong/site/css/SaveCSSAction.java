package com.fulong.site.css;

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

import com.fulong.longcon.site.Channel;

/**
 * <p>Title: 龙驭网站管理系统</p>
 *
 * <p>Description: 龙驭网站管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 * @author lichengzhao
 * @version 1.0
 */

public class SaveCSSAction extends CSSBaseAction {

	public ActionForward cssPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
        InputStream input = request.getInputStream();
		String cssPath = request.getParameter("cssPath");
		Channel channel = this.parseChannel(cssPath,request);
		File file = channel.getPage();
        OutputStream output = new FileOutputStream(file);
        try {
            IOUtils.copy(input, output);
        } finally {
        	IOUtils.closeQuietly(output);
        	IOUtils.closeQuietly(input);
        }
        response.setContentType("text/html");
        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        Writer writer = response.getWriter();
        writer.write("true");
        writer.close();
        return null;
	}
}
