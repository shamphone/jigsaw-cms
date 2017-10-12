package com.fulong.lyvc.manage.conference;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.lyvc.manage.base.BaseAction;

/**
 * CreateNoticeAction
 *
 * 龙驭视频会议系统 v3.0
 *
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 *
 * @author lifx
 *
 * 最后修改时间：2010-9-2
 */

public class CreateNoticeAction extends BaseAction {

	/**
	 * 
	 * 初始化sendNotice.jsp页面的部分内容（发送会议通知）
	 */
	protected ActionForward doPerform(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String conferenceId = request.getParameter("conferenceId");
		request.getSession().setAttribute("conferenceId", conferenceId);
		
		return mapping.findForward("success");
	}
}
