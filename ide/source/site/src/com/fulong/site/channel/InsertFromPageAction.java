package com.fulong.site.channel;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.fulong.longcon.site.Channel;
import com.fulong.longcon.site.SiteFolder;
import com.fulong.longcon.site.SiteTemplate;
import com.fulong.site.form.ChannelForm;


/**
 * <p>Title: Coolink平台协同管理系统</p>
 *
 * <p>Description: Coolink平台协同管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2010</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author luobin
 * @date 2010-6-17	
 * @version 1.0.1
 */
public class InsertFromPageAction extends ChannelBaseAction {
	
	protected ActionForward templateExecute(ActionMapping mapping,
			ActionForm tform, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ChannelForm form = (ChannelForm) tform;
		FormFile file = form.getTemplateFile();
		if (!(file == null || file.getFileSize() == 0)) {
			InputStream input = file.getInputStream();
			String channelType = form.getType();
			String contentType = this.getContentType(channelType);
			input = new JSPUTF8InputStream(input,contentType).getInputStream();
			
			String templateName = request.getParameter("templateName");
			// 所在模板
			SiteTemplate template = this.getSiteFactory(request).getTemplate(templateName);
			String folderPath =	form.getFolderPath();
			SiteFolder folder = template.getFolder(folderPath);
			
			String channelName = form.getName();
			Channel channel = folder.addChannel(channelName,channelType,input);
			request.setAttribute("channel", channel);
			input.close();
			return mapping.findForward("success");
		}
		return null;
	}
}
