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
import com.fulong.lyvc.User;
import com.fulong.lyvc.manage.base.BaseAction;
import com.fulong.lyvc.manage.form.DeleteGroupForm;

/**
 * RemoveGroupAction
 *
 * 龙驭视频会议系统 v3.0
 *
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 *
 * @author lifx
 *
 * 最后修改时间：2010-8-16
 */

public class RemoveGroupAction extends BaseAction {

	/**
	 * 
	 * 删除一个公共联系人组
	 */
	protected ActionForward doPerform(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.setDomain(request);

		DeleteGroupForm form = (DeleteGroupForm) actionForm;
		
		//获取组id
		String groupId = form.getGroupId();
		
		ConferenceManager manager = this.getConferenceManager();
		Group group = manager.getGroup(groupId);
		
		//判断该组是否为空，只能删除空组
		Collection<Group> groups = new ArrayList<Group>();
		groups.add(group);
		groups.addAll(getAllChildren(group));
		
		Collection<User> users= new ArrayList<User>();
		for(Group temp : groups) 
			users.addAll(temp.users());
		
		if(users == null || users.size() > 0) {
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("group.notNull"));
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
//		RemoveGroupHandler handler = (RemoveGroupHandler) dispatcher.eventHandlerMap.get(119);
//		handler.setConferenceManager(this.domain);
//
//		//发送创建公共联系人组的消息
//		RemoveGroup msg = new RemoveGroup();
//		msg.setId("119");
//		msg.setSenderId(leaserId);
//		msg.groupId = groupId;
//		
//		for(User temp : userList) {
//			//找到在线用户
//			TCPChannel channel = handler.getChannel(temp.getId());
//			if (channel != null) {
//				handler.sendMessage(channel, msg);
//			}
//		}
			
		manager.delete(group);
		
		//从session中删除保存的参数
		request.getSession().removeAttribute("groups");
		
		return mapping.findForward("success");
	}
}
