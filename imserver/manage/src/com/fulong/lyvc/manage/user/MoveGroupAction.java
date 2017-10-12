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
import com.fulong.lyvc.manage.base.BaseAction;
import com.fulong.lyvc.manage.form.MoveGroupForm;

/**
 * MoveGroupAction
 *
 * 龙驭视频会议系统 v3.0
 *
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2010
 *
 * @author lifx
 *
 * 最后修改时间：2010-8-16
 */

public class MoveGroupAction extends BaseAction {

	/**
	 * 初始化moveGroup.jsp页面的部分内容（移动公共联系人组）
	 */
	
	
	protected ActionForward doPerform(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.setDomain(request);

		ConferenceManager manager = this.getConferenceManager();
		
		Group group = manager.getCommonContactGroup();
		
		String groupId = request.getParameter("groupid");
		
		if(groupId == null || groupId.equals("")) {
			Collection<Group> groups = new ArrayList<Group>();
			Collection<Group> children = getAllChildren(group);
			groups.addAll(children);
			request.getSession().setAttribute("groups", groups);
			
			Collection<Group> newGroups = new ArrayList<Group>();
			newGroups.addAll(children);
			newGroups.remove(children.iterator().next());
			request.getSession().setAttribute("newGroups", newGroups);
		}
		else {
			MoveGroupForm form = (MoveGroupForm) actionForm;
			form.setGroupId(groupId);
			
			Collection<Group> newGroups = new ArrayList<Group>();
			newGroups.add(group);
			newGroups.addAll(getAllChildren(group));
			
			//去掉该组本身
			group = manager.getGroup(groupId);
			newGroups.remove(group);
			
			//去掉该组的所有子组
			newGroups.removeAll(getAllChildren(group));
			
			//去掉该组的父组
			group = group.getParentGroup();
			newGroups.remove(group);
			
			request.getSession().setAttribute("newGroups", newGroups);
		}
		
		return mapping.findForward("success");
	}

}
