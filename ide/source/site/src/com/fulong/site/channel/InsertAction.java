package com.fulong.site.channel;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.site.Channel;
import com.fulong.longcon.site.SiteFolder;
import com.fulong.longcon.site.SiteTemplate;

/**
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
public class InsertAction extends ChannelBaseAction {

	protected ActionForward templateExecute(ActionMapping mapping,
			ActionForm tform, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ServletInputStream input = request.getInputStream();
		String templateName = request.getParameter("templateName");
		// 所在模板
		SiteTemplate template = this.getSiteFactory(request).getTemplate(templateName);
		String folderPath =	request.getParameter("folderPath");
		SiteFolder folder = template.getFolder(folderPath);
		
		String channelName = request.getParameter("channelName");
		String channelType = request.getParameter("channelType");
		Channel channel = folder.addChannel(channelName,channelType,input);
		request.setAttribute("channel", channel);
		input.close();
		return mapping.findForward("success");
	}
}
