package com.fulong.site.editor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.site.Channel;

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
 * @date 2010-9-27	
 * @version 1.0.1
 */
public class CopyClipAction extends EditorBaseAction {
	public ActionForward templateExecute(ActionMapping mapping,	ActionForm form,HttpServletRequest request,	HttpServletResponse response) throws
			Exception {
		String orignalPortletId = request.getParameter("orignalPortletId");
		String orignalChannelPath = request.getParameter("orignalChannelPath");
		String newPortletId = request.getParameter("portletId");
		String channelPath = request.getParameter("channelPath");
		
		Channel orignalChannel = this.parseChannel(orignalChannelPath,request);
		Channel destChannel = this.parseChannel(channelPath,request);
		
		copyClip(orignalChannel, orignalPortletId, destChannel, newPortletId);
		
		return null;
	}
	
}
