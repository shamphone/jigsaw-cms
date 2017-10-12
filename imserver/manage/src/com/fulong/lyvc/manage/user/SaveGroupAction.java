package com.fulong.lyvc.manage.user;

import java.util.ArrayList;
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
import com.fulong.lyvc.TCPChannel;
import com.fulong.lyvc.User;
import com.fulong.lyvc.handler.contact.AddGroupHandler;
import com.fulong.lyvc.jcr.SystemGroupNode;
import com.fulong.lyvc.manage.base.BaseAction;
import com.fulong.lyvc.manage.form.AddGroupForm;
import com.fulong.lyvc.message.AddGroup;
import com.fulong.lyvc.server.LyvcEventDispatcher;

/**
 * SaveGroupAction
 *
 * 龙驭视频会议系统 v3.0
 *
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 *
 * @author lifx
 *
 * 最后修改时间：2010-8-16
 */

public class SaveGroupAction extends BaseAction {

	/**
	 * 
	 * 添加一个公共联系人组
	 */
	protected ActionForward doPerform(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.setDomain(request);

		AddGroupForm form = (AddGroupForm) actionForm;
		
		//去掉前后的空格
		String groupId = form.getGroupId();
		String name = form.getGroupName();
		String desc = form.getGroupDesc();

		//得到公共联系人组
		ConferenceManager manager = this.getConferenceManager();
		SystemGroupNode group = (SystemGroupNode) manager.getGroup(groupId);

		//得到与该组同级别的所有组及其父组，并判断组名是否已经存在（同级别的组不能同名）
		Collection<Group> groups = new ArrayList<Group>();
		groups.add(group);
		groups.addAll(group.children());
		
		Iterator<? extends Group> iterator = groups.iterator();
		while(iterator.hasNext()) {
			Group temp = iterator.next();
			if(temp.getName().equals(name)) {
				ActionMessages messages = new ActionMessages();
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("group.nameExists"));
				this.addErrors(request, messages);
				
				return mapping.getInputForward();
			}
		}
		
		//获取所有的组
		Group commonGroup = manager.getCommonContactGroup();
		Collection<Group> groupList = new ArrayList<Group>();
//		groupList.add(commonGroup);
		groupList.addAll(getAllChildren(commonGroup));
		
		//得到创建者id
		String leaserId = manager.getLeaserId();
		User creator = manager.getUser(leaserId);
		
		//获取所有的成员
		Collection<User> users = new ArrayList<User>();
		users.add(creator);
		for(Group temp : groupList) 
			users.addAll(temp.users());
		
		//创建公共联系人组
		Group child = group.addChild(name, desc, leaserId);
		
		//获取bean，并进行初始化
		LyvcEventDispatcher dispatcher = (LyvcEventDispatcher) this.getWebApplicationContext().getBean("eventDispatcher");
		AddGroupHandler handler = (AddGroupHandler) dispatcher.eventHandlerMap.get(113);
		handler.setConferenceManager(this.domain);
		
		//发送创建公共联系人组的消息
		AddGroup msg = new AddGroup();
		msg.setId("113");
		msg.isCommon = true;
		msg.groupId = child.getId();
		msg.parentGroupId = groupId;
		msg.name = name;
//		if(!groupId.equals(commonContactGroupId))
//			msg.parentGroupId = groupId;
//		else
//			msg.parentGroupId = "0";
		
		for(User temp : users) {
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
