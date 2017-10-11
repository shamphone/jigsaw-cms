package com.fulong.portlet.site;

import java.io.File;

import com.fulong.common.FileWrapper;
import com.fulong.longcon.site.Channel;
import com.fulong.longcon.site.SiteTemplate;
import com.fulong.portlet.PortletRender;

/**
 * <p>
 * Title: 龙驭网站管理系统
 * </p>
 * 
 * <p>
 * Description: 龙驭网站管理系统
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * @author lixf
 * @version 1.0
 */
public abstract class SitePortletRender extends PortletRender {
	protected static final int DEPTH_SITE = 1;
	protected static final int DEPTH_TEMPLATE = 2;
	protected static final int DEPTH_CHANNEL = 3;

	/**
	 * 获取根文件夹
	 * 
	 * @param request
	 *            RenderRequest
	 * @param response
	 *            RenderResponse
	 * @return FileWrapper
	 * @throws Exception
	 */
	protected FileWrapper getRootFolder() throws Exception {
		String rootPath = this.portletContext.getRealPath("/sites");
		return new FileWrapper(rootPath, 1);
	}

	/**
	 * 从相对路径中解析出所包含的栏目
	 * 
	 * @param path
	 *            String 格式：/sites/[网站模板的名称]/[栏目名称].jsp
	 * @return Channel
	 */
	protected Channel parseChannel(String path) {
		String str[] = path.split("[/\\.]");
		String templateID = str[2];
		SiteTemplate template = this.getSiteFactory().getTemplate(templateID);
		return template.getChannel(str[3]);
	}

	/**
	 * 获取网站模板文件所在的文件夹的相对路径
	 * 
	 * @param template
	 *            SiteTemplate
	 * @return String
	 */
	protected String getTemplatePath(SiteTemplate template) {
		return "/sites/" + template.getName();
	}

	/**
	 * 获取网站模板所在的文件的文件夹
	 * 
	 * @param template
	 *            SiteTemplate
	 * @return File
	 */
	protected File getTemplateFolder(SiteTemplate template) {
		return new File(this.servlet.getServletContext().getRealPath(this.getTemplatePath(template)));
	}

	protected FileWrapper getTemplateFolderWrapper(SiteTemplate template) {
		return new FileWrapper(getTemplateFolder(template), DEPTH_TEMPLATE);
	}

	protected FileWrapper getJspfTemplateFolderWrapper(SiteTemplate template) {
		return new FileWrapper(new File(this.servlet.getServletContext().getRealPath(
				this.getTemplatePath(template) + "/jspf/")), DEPTH_TEMPLATE);
	}

	/**
	 * 获取栏目文件的相对路径
	 * 
	 * @param channel
	 *            Channel
	 * @return String
	 */
	protected String getChannelFilePath(Channel channel) {
		return "/sites/" + channel.getSiteTemplate().getName() + "/" + channel.getName() + ".jsp";
	}

	/**
	 * 获取栏目资源文件的相对路径
	 * 
	 * @param channel
	 *            Channel
	 * @return String
	 */
	protected String getChannelCssPath(Channel channel) {
		return "/sites/" + channel.getSiteTemplate().getName() + "/" + "style/style.css";
	}

	/**
	 * 获取栏目的页面文件
	 * 
	 * @param channel
	 *            Channel
	 * @return File
	 */
	protected File getChannelFile(Channel channel) {
		return new File(this.servlet.getServletContext().getRealPath(this.getChannelFilePath(channel)));
	}

	/**
	 * 获取栏目的资源文件
	 * 
	 * @param channel
	 *            Channel
	 * @return File
	 */
	protected File getChannelCss(Channel channel) {
		return new File(this.servlet.getServletContext().getRealPath(this.getChannelCssPath(channel)));
	}

	protected FileWrapper getChannelFileWrapper(Channel channel) {
		return new FileWrapper(getChannelFile(channel), DEPTH_CHANNEL);
	}
}
