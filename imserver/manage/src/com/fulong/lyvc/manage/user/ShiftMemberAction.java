package com.fulong.lyvc.manage.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.fulong.lyvc.ConferenceManager;
import com.fulong.lyvc.Group;
import com.fulong.lyvc.User;
import com.fulong.lyvc.manage.base.BaseAction;
import com.fulong.lyvc.manage.form.MoveMemberForm;

/**
 * ShiftMember
 *
 * 龙驭视频会议系统 v3.0
 *
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2010
 *
 * @author lifx
 *
 * 最后修改时间：2010-8-16
 */

public class ShiftMemberAction extends BaseAction {

	/**
	 * 
	 * 移动一个公共联系人
	 */
	protected ActionForward doPerform(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.setDomain(request);

		MoveMemberForm form = (MoveMemberForm) actionForm;
		
		String groupId = form.getGroupId();
		String userId = form.getUserId();
		String newGroupId = form.getNewGroupId();
		
		//某个组下可能没有成员，因此需要判断用户是否存在
		if(userId != null && newGroupId != null) {
			ConferenceManager manager = this.getConferenceManager();
			
			Group oldGroup = manager.getGroup(groupId);
			User user = manager.getUser(userId);
			Group newGroup = manager.getGroup(newGroupId);
			
			newGroup.addMember(user);
			oldGroup.removeMember(user);
			
//			//获取所有的组
//			Group commonGroup = manager.getCommonContactGroup();
//			Collection<Group> groupList = new ArrayList<Group>();
//			groupList.add(commonGroup);
//			groupList.addAll(getAllChildren(commonGroup));
//			
//			//得到创建者id
//			String leaserId = manager.getLeaserId();
//			User creator = manager.getUser(leaserId);
//			
//			//获取所有的成员
//			Collection<User> userList= new ArrayList<User>();
//			userList.add(creator);
//			for(Group temp : groupList) 
//				userList.addAll(temp.users());
//			
//			//获取bean，并进行初始化
//			LyvcEventDispatcher dispatcher = (LyvcEventDispatcher) this.getWebApplicationContext().getBean("eventDispatcher");
//			MoveContactToGroupHandler handler = (MoveContactToGroupHandler) dispatcher.eventHandlerMap.get(116);
//			handler.setConferenceManager(this.domain);
//
//			//发送创建公共联系人组的消息
//			MoveContactToGroup msg = new MoveContactToGroup();
//			msg.setId("116");
//			msg.setSenderId(creator.getId());
//			msg.contactId = user.getId();
//			msg.oldGroupId = groupId;
//			msg.newGroupId = newGroupId;
//			
//			for(User temp : userList) {
//				//找到在线用户
//				TCPChannel channel = handler.getChannel(temp.getId());
//				if (channel != null) {
//					handler.sendMessage(channel, msg);
//				}
//			}
		}
		else {
			ActionMessages messages = new ActionMessages();
			
			if(userId == null)
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("user.notSelected"));
			else
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("group.notSelected"));
			
			this.addErrors(request, messages);
			
			return mapping.getInputForward();
		}
		
		//从session中删除保存的参数
		request.getSession().removeAttribute("groups");
		request.getSession().removeAttribute("newGroups");
		request.getSession().removeAttribute("users");
		
		return mapping.findForward("success");
	}

}
