package com.fulong.lyvc.manage.user;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.fulong.lyvc.Conference;
import com.fulong.lyvc.ConferenceManager;
import com.fulong.lyvc.Group;
import com.fulong.lyvc.User;
import com.fulong.lyvc.manage.base.BaseAction;
import com.fulong.lyvc.manage.form.DeleteMemberForm;

/**
 * RemoveMemberAction
 *
 * 龙驭视频会议系统 v3.0
 *
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2010
 *
 * @author lifx
 *
 * 最后修改时间：2010-8-16
 */

public class RemoveMemberAction extends BaseAction {

	/**
	 * 
	 * 删除公共联系人中的一个成员
	 */
	protected ActionForward doPerform(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.setDomain(request);

		DeleteMemberForm form = (DeleteMemberForm) actionForm;
		
		String parentGroupId = form.getGroupId();
		String userId = form.getUserId();
		
		if(userId == null || userId.equals("")) {
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("user.notSelected"));
			this.addErrors(request, messages);
			
			return mapping.getInputForward();
		}
		
		ConferenceManager manager = this.getConferenceManager();
		User user = manager.getUser(userId);
		
		//判断该用户是否正在参加某些会议（包括正式会议和即时会议）
		
		//得到该用户正在参与的所有正式会议
		Collection<Conference> conferences = manager.getHoldingConferences(user);
		if(conferences.size() > 0) {
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("user.inconference"));
			this.addErrors(request, messages);
			
			return mapping.getInputForward();
		}
		
		//得到该用户正在参与的所有即时会议
		conferences = manager.getHoldingInstantConferences(user);
		if(conferences.size() > 0) {
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("user.inconference"));
			this.addErrors(request, messages);
			
			return mapping.getInputForward();
		}
		
//		//获取所有的组
//		Group commonGroup = manager.getCommonContactGroup();
//		Collection<Group> groupList = new ArrayList<Group>();
//		groupList.add(commonGroup);
//		groupList.addAll(getAllChildren(commonGroup));
//		
//		//得到创建者id
//		String leaserId = manager.getLeaserId();
//		User creator = manager.getUser(leaserId);
//		
//		//获取所有的成员
//		Collection<User> userList= new ArrayList<User>();
//		userList.add(creator);
//		for(Group temp : groupList) 
//			userList.addAll(temp.users());
//		
//		//获取bean，并进行初始化
//		LyvcEventDispatcher dispatcher = (LyvcEventDispatcher) this.getWebApplicationContext().getBean("eventDispatcher");
//		RemoveContactHandler handler = (RemoveContactHandler) dispatcher.eventHandlerMap.get(118);
//		handler.setConferenceManager(this.domain);
//		
//		//发送创建公共联系人组的消息
//		RemoveContact msg = new RemoveContact();
//		msg.setId("118");
//		msg.contactId = userId;
//		msg.groupId = parentGroupId;
//		
//		for(User temp : userList) {
//			//找到在线用户
//			TCPChannel channel = handler.getChannel(temp.getId());
//			if (channel != null && !userId.equals(temp.getId())) {
//				handler.sendMessage(channel, msg);
//			}
//		}
		
		Group group = manager.getGroup(parentGroupId);
		group.removeMember(user);
		manager.delete(user);
		
		//从session中删除保存的参数
		request.getSession().removeAttribute("groups");
		request.getSession().removeAttribute("users");
		
		return mapping.findForward("success");
	}

}
