package com.fulong.lyvc.manage.user;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.fulong.lyvc.ConferenceManager;
import com.fulong.lyvc.Group;
import com.fulong.lyvc.manage.base.BaseAction;
import com.fulong.lyvc.manage.form.EditGroupForm;

/**
 * UpdateGroupAction
 *
 * 龙驭视频会议系统 v3.0
 *
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 *
 * @author lifx
 *
 * 最后修改时间：2010-8-16
 */

public class UpdateGroupAction extends BaseAction {

	/**
	 * 
	 * 修改某个公共联系人组中的名称和描述
	 */
	protected ActionForward doPerform(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.setDomain(request);

		EditGroupForm form = (EditGroupForm) actionForm;
		
		//获取基本数据
		String groupId = form.getGroupId();
		String groupName = form.getGroupName();
		String groupDesc = form.getGroupDesc();
		
		ConferenceManager manager = this.getConferenceManager();
		String rootGroupId = manager.getCommonContactGroup().getId();

		//得到该组及其父组
		Group group = manager.getGroup(groupId);
		
		Collection<? extends Group> groups;
		if(!groupId.equals(rootGroupId)) {
			Group parentGroup = group.getParentGroup();
			
			//得到与该组同级别的所有组，并判断组名是否已经存在（同级别的组不能同名）
			groups = parentGroup.children();
		}
		else {
			groups = group.children();
		}
		
		Iterator<? extends Group> iterator = groups.iterator();
		while(iterator.hasNext()) {
			Group temp = iterator.next();
			if(temp.getName().equals(groupName)) {
				ActionMessages messages = new ActionMessages();
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("group.nameExists"));
				this.addErrors(request, messages);
				
				return mapping.getInputForward();
			}
		}
		
		group.setName(groupName);
		group.setDesc(groupDesc);
		
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
//		ChangeGroupNameHandler handler = (ChangeGroupNameHandler) dispatcher.eventHandlerMap.get(123);
//		handler.setConferenceManager(this.domain);
//		
//		//发送创建公共联系人组的消息
//		ChangeGroupName msg = new ChangeGroupName();
//		msg.setId("123");
//		msg.setSenderId(leaserId);
//		msg.groupId = groupId;
//		msg.newName = groupName;
//		
//		for(User temp : userList) {
//			//找到在线用户
//			TCPChannel channel = handler.getChannel(temp.getId());
//			if (channel != null) {
//				handler.sendMessage(channel, msg);
//			}
//		}
		
		//从session中删除保存的参数
		request.getSession().removeAttribute("groups");
		
		return mapping.findForward("success");
	}
	
}
