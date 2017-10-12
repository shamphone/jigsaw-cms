package com.fulong.lyvc.manage.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.lyvc.manage.base.BaseAction;

/**
 * CreateBulletinAction
 *
 * 龙驭视频会议系统 v3.0
 *
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 *
 * @author lifx
 *
 * 最后修改时间：2010-8-16
 */

public class CreateBulletinAction extends BaseAction {

	/**
	 * 
	 * 初始化sendBulletin.jsp页面的部分内容（发送系统公告）
	 */
	protected ActionForward doPerform(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		return mapping.findForward("success");
	}
}
