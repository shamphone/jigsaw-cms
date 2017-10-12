package com.fulong.lyvc.manage.user;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.fulong.lyvc.ConferenceManager;
import com.fulong.lyvc.Group;
import com.fulong.lyvc.TCPChannel;
import com.fulong.lyvc.User;
import com.fulong.lyvc.handler.contact.AddContactHandler;
import com.fulong.lyvc.manage.base.BaseAction;
import com.fulong.lyvc.manage.form.AddMemberForm;
import com.fulong.lyvc.message.AddContact;
import com.fulong.lyvc.server.LyvcEventDispatcher;

/**
 * SaveMemberAction
 *
 * 龙驭视频会议系统 v3.0
 *
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 *
 * @author lifx
 *
 * 最后修改时间：2010-8-16
 */

public class SaveMemberAction extends BaseAction {

	/**
	 * 
	 * 向某个公共联系人组中添加一个成员
	 */
	protected ActionForward doPerform(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.setDomain(request);

		AddMemberForm form = (AddMemberForm) actionForm;
		
		//去掉前后的空格
		String accountName = form.getAccountName().trim();
		String password = form.getPassword().trim();
		String firstName = form.getFirstName().trim();
		String lastName = form.getLastName().trim();
		String email = form.getEmail().trim();
		String groupId = form.getGroupId().trim();
		
		ConferenceManager manager = this.getConferenceManager();
		Group group = manager.getGroup(groupId);
		
		//判断用户名是否存在
		User user = manager.getUserByAccountName(accountName);
		if(user != null) {
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("user.accountNameExists"));
			this.addErrors(request, messages);
			
			return mapping.getInputForward();
		}
		
		//判断邮箱是否存在
		user = manager.getUserByEmail(email);
		if(user != null) {
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("user.emailExists"));
			this.addErrors(request, messages);
			
			return mapping.getInputForward();
		}
		
		//用户名和邮箱都不存在
		user = manager.createUser(accountName, password, firstName, lastName, email);

		group.addMember(user);
		
		//获取所有的组
		String commonContactGroupId = manager.getCommonContactGroup().getId();
		Group commonGroup = manager.getGroup(commonContactGroupId);
		Collection<Group> groupList = new ArrayList<Group>();
//		groupList.add(commonGroup);
		groupList.addAll(getAllChildren(commonGroup));
		
		//得到创建者id
		String leaserId = manager.getLeaserId();
		User creator = manager.getUser(leaserId);
		
		//获取所有的成员
		Collection<User> userList= new ArrayList<User>();
		userList.add(creator);
		for(Group temp : groupList) 
			userList.addAll(temp.users());
		
		//获取bean，并进行初始化
		LyvcEventDispatcher dispatcher = (LyvcEventDispatcher) this.getWebApplicationContext().getBean("eventDispatcher");
		AddContactHandler handler = (AddContactHandler) dispatcher.eventHandlerMap.get(121);
		handler.setConferenceManager(this.domain);
		
		//发送创建公共联系人的消息
		AddContact msg = new AddContact();
		msg.setId("121");
		msg.isCommon = true;
		msg.setSenderId(creator.getId());
		msg.groupId = groupId;
//		if(!groupId.equals(commonContactGroupId))
//			msg.groupId = groupId;
//		else
//			msg.groupId = "0";
		msg.contactId = user.getId();
		msg.name = accountName;
		msg.firstName = firstName;
		msg.lastName = lastName;
		msg.email = email;
		
		for(User temp : userList) {
			//找到在线用户
			TCPChannel channel = handler.getChannel(temp.getId());
			if (channel != null) {
				handler.sendMessage(channel, msg);
			}
		}
		
		//从session中删除保存的参数
		request.getSession().removeAttribute("groups");
		
		return mapping.findForward("success");
	}
}
