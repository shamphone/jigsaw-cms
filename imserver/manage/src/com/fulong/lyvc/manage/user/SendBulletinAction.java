package com.fulong.lyvc.manage.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.lyvc.ConferenceManager;
import com.fulong.lyvc.Group;
import com.fulong.lyvc.Message;
import com.fulong.lyvc.TCPChannel;
import com.fulong.lyvc.User;
import com.fulong.lyvc.handler.contact.LeavewordHandler;
import com.fulong.lyvc.manage.base.BaseAction;
import com.fulong.lyvc.manage.form.SendBulletinForm;
import com.fulong.lyvc.message.SystemMessage;
import com.fulong.lyvc.server.LyvcEventDispatcher;

/**
 * SendBulletinAction
 *
 * 龙驭视频会议系统 v3.0
 *
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 *
 * @author lifx
 *
 * 最后修改时间：2010-8-16
 */

public class SendBulletinAction extends BaseAction {

	/**
	 * 
	 * 发送系统公告
	 */
	protected ActionForward doPerform(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.setDomain(request);

		SendBulletinForm form = (SendBulletinForm) actionForm;
		
		String title = form.getTitle();
		String content = form.getContent();
		
		ConferenceManager manager = this.getConferenceManager();
		
		//获取所有的组
		Group group = manager.getCommonContactGroup();
		Collection<Group> groups = new ArrayList<Group>();
//		groups.add(group);
		groups.addAll(getAllChildren(group));
		
		//得到创建者id
		String leaserId = manager.getLeaserId();
		User creator = manager.getUser(leaserId);
		
		//获取所有的成员
		Collection<User> users = new ArrayList<User>();
		users.add(creator);
		for(Group temp : groups) 
			users.addAll(temp.users());
		
		//获取bean，并进行初始化
		LyvcEventDispatcher dispatcher = (LyvcEventDispatcher) this.getWebApplicationContext().getBean("eventDispatcher");
		LeavewordHandler handler = (LeavewordHandler) dispatcher.eventHandlerMap.get(144);
		handler.setConferenceManager(this.domain);

		//创建系统公告
		SystemMessage bulletin = new SystemMessage();
		bulletin.setId("192");
		bulletin.title = title;
		bulletin.content = content;
		bulletin.sendDate = new Date(System.currentTimeMillis());
		
		Iterator<User> iterator = users.iterator();
		while(iterator.hasNext()) {
			User user = iterator.next();
			bulletin.receiverId = user.getId();

			//找到在线用户和离线用户
			TCPChannel channel = handler.getChannel(user.getId());
			
			//发送公告给在线用户
			if (channel != null) {
				handler.sendMessage(channel, bulletin);
			}
			//将公告信息保存到离线用户的留言属性中
			else {
				content = bulletin.toXML();
				Date saveDate = new Date(System.currentTimeMillis());
				Message message = manager.createMessage(leaserId, title, content, saveDate);
				user.addMessage(message.getId());
			}
		}
		
		return mapping.findForward("success");
	}
}
