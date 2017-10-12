package com.fulong.site.editor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.site.Channel;
/**
 * <p>Title: 龙驭门户引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author 李雄锋
 * @author luobin
 * @version 1.0
 */
public class PageEditorAction extends EditorBaseAction {
	public ActionForward templateExecute(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws
			Exception {
		String path = request.getParameter("path");
		if(path==null){
			return null;
		}
		Channel channel = this.parseChannel(path,request);
		if (channel == null)
			return mapping.findForward("file.not.exists");   
		String ipAddress = this.getIpAddr(request);
		this.mildCheckout(channel, ipAddress);
		return this.forward(mapping, "success", this.getSiteFactory(request).getChannelType(channel.getType()).getPostfix());
	}
}
