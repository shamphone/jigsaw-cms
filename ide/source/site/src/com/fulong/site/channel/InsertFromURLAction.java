package com.fulong.site.channel;

import java.io.File;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.fulong.common.html.HtmlUtils;
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
public class InsertFromURLAction extends ChannelBaseAction {
	// 协议头正则字符串
	protected static final String REGEX_PROTOCOL = "[^\\s/\":]{3,}://";
	
	protected ActionForward templateExecute(ActionMapping mapping,
			ActionForm tform, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ChannelForm form = (ChannelForm) tform;
		String templateName = request.getParameter("templateName");
		// 所在模板
		SiteTemplate template = this.getSiteFactory(request).getTemplate(templateName);
		String folderPath =	form.getFolderPath();
		SiteFolder folder = template.getFolder(folderPath);
		
		String channelName = form.getName();
		String channelType = form.getType();
		
		// 从指定的URL地址复制
		String url = form.getUrl().trim();
		if (url.length() > 0) {
			if (!Pattern.matches(REGEX_PROTOCOL, url))
				url = "http://" + url;
			File templateFolder = template.getRootFolder().getFile();
			String folderRelativePath = "/"+template.getName()+ folderPath;
			String html = null;
			try {
				html = HtmlUtils.downloadHtmlWithResources(url, templateFolder, folderRelativePath, this.DEFAULT_ENCODING);
			} catch (Exception ex) {
				log.error(ex.getMessage());
				ActionMessages messages = new ActionMessages();
				messages.add("url", new ActionMessage("error.url.wrong"));
				this.saveErrors(request, messages);
				return mapping.findForward("failed");
			}
			if (html != null) {
				Channel channel = folder.addChannel(channelName, channelType,IOUtils.toInputStream(html, this.DEFAULT_ENCODING));
				request.setAttribute("channel", channel);
			}
		}
		return mapping.findForward("success");
	}
}
