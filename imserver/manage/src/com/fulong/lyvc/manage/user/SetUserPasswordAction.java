package com.fulong.lyvc.manage.user;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.lyvc.ConferenceManager;
import com.fulong.lyvc.Group;
import com.fulong.lyvc.User;
import com.fulong.lyvc.manage.base.BaseAction;
import com.fulong.lyvc.manage.form.SetUserPasswordForm;

/**
 * SetUserPasswordAction
 *
 * 龙驭视频会议系统 v3.0
 *
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 *
 * @author lifx
 *
 * 最后修改时间：2010-8-16
 */

public class SetUserPasswordAction extends BaseAction {
	
	/**
	 * 
	 * 初始化setUserPassword.jsp页面的部分内容
	 */
	protected ActionForward doPerform(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.setDomain(request);

		ConferenceManager manager = this.getConferenceManager();
		
		String groupId = request.getParameter("groupid");
		
		if(groupId == null || groupId.equals("")) {
			Group group = manager.getCommonContactGroup();
			
			Collection<Group> groups = new ArrayList<Group>();
			groups.add(group);
			groups.addAll(getAllChildren(group));
			request.getSession().setAttribute("groups", groups);
			
			Collection<? extends User> users = group.users();
			request.getSession().setAttribute("users", users);
		}
		else {
			SetUserPasswordForm form = (SetUserPasswordForm) actionForm;
			form.setGroupId(groupId);
			
			Group group = manager.getGroup(groupId);
			Collection<? extends User> users = group.users();
			
			request.getSession().setAttribute("users", users);
		}
		
		return mapping.findForward("success");
	}
}
