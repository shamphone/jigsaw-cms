package com.fulong.lyvc.handler.server;

import java.security.Principal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fulong.longcon.repository.Node;
import com.fulong.lyvc.Conference;
import com.fulong.lyvc.ConferenceManager;
import com.fulong.lyvc.Group;
import com.fulong.lyvc.Mode;
import com.fulong.lyvc.ModeRole;
import com.fulong.lyvc.TCPChannel;
import com.fulong.lyvc.TCPMessage;
import com.fulong.lyvc.User;
import com.fulong.lyvc.message.AddContact;
import com.fulong.lyvc.message.AddGroup;
import com.fulong.lyvc.message.Advertizement;
import com.fulong.lyvc.message.ConferenceModelRoleTableItem;
import com.fulong.lyvc.message.ConferenceModelTableItem;
import com.fulong.lyvc.message.DefaultGroupId;
import com.fulong.lyvc.message.InstantConferenceModelId;
import com.fulong.lyvc.message.KickDuplicateLogin;
import com.fulong.lyvc.message.Login;
import com.fulong.lyvc.message.LoginSuccessfully;
import com.fulong.lyvc.message.MediaServerAddress;
import com.fulong.lyvc.message.NotifyMediaServerUserJoin;
import com.fulong.lyvc.message.RemoveContact;
import com.fulong.lyvc.message.ServerInternalError;
import com.fulong.lyvc.message.SetContactAdmin;
import com.fulong.lyvc.message.UpdateContactStatus;
import com.fulong.lyvc.message.UserChannelBroken;
import com.fulong.lyvc.message.UserDetail;
import com.fulong.lyvc.message.UserLoginResponse;
import com.fulong.lyvc.util.UserComparator;

/**
 * LoginHandler
 * 
 * 龙驭视频会议系统 v3.0
 * 
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 * 
 * @author 李雄锋
 * 
 * 最后修改时间：2010-11-10
 */

public class LoginHandler extends ServerBaseHandler {
	private static Log logger = LogFactory.getLog(LoginHandler.class);
	private Map<String, DuplicateLoginMessage> duplicateLoginMessages;
	private Properties adURLs;
	private final static int CONTACT_OFFLINE = 1;
	private final static int CONTACT_ONLINE = 2;

	public void setAdURLs(Properties adURLs) {
		this.adURLs = adURLs;
	}

	public LoginHandler() {
		duplicateLoginMessages = new HashMap<String, DuplicateLoginMessage>();
	}

	/*
	 * 处理登录信息
	 */
	public void execute(TCPChannel channel, TCPMessage message) throws Exception {
		Login loginMessage = (Login) message;
		
		String domain = loginMessage.domain;
		this.setConferenceManager(domain);
		channel.setDomain(domain);
		channel.setAVCompressionCardChannelNumber(loginMessage.avCompressionCardChannelNumber);
		
		ConferenceManager manager = this.getConferenceManager();
		
		UserLoginResponse msgToSend = new UserLoginResponse();

		// 使用最高连接数即可；这个是多余的。 by lixf
		/*
		 * if(server.getUsers().size() > 100){ msgToSend.message = "已经达到最高在线人数";
		 * msgToSend.userId = 0; sendMessage(channel, msgToSend); return; }
		 */
		if (this.getMediaServer().getChannel() == null) {
			ServerInternalError msgToSend2 = new ServerInternalError();
			msgToSend2.description = "媒体服务器不可用";
			sendMessage(channel, msgToSend2);
			return;
		}

		// Get User information from User System
		User user = manager.getUserByAccountName(loginMessage.username);
		
		// If no user found, notify the client and return
		if(user == null || !checkUser(user) || !user.checkPassword(loginMessage.password)) {
			msgToSend.message = "错误的用户名或者密码";
			msgToSend.userId = "0";
			sendMessage(channel, msgToSend);

			logger.warn("Invalid username/password: " + loginMessage.username + "/" + loginMessage.password);
			return;
		}
		
		// 检查是否重复登录
		// 如果该用户已经登录，那么我们踢掉前一个登录的用户，然后在加上这个新用户
		// 由于事件队列的处理顺序的影响，我们首先在这里关掉上一个socket,以便在事件队列
		// 中产生一个userChannelBroken的事件，等各个业务处理模块处理完这个事件之后，
		// 我们再处理这个登录事件，为此，首先缓存这个登录事件，等到Server处理该 UserChannelBroken
		// 消息的时候，再重新把这个事件加入到消息队列中。

		// Socket的Keep-Alive也许能够帮上一些忙，但是也不能完全解决问题。
		// 另外，毕竟用户要对自己的密码安全负责，:-) 两个客户端使用同样的帐号登录，只能这样处理了
		// MSN也是这样。
		TCPChannel oldChannel = server.getChannel(user);
		if (oldChannel != null) {

			// Log this event
			logger.warn("Duplicate Login. old from: " + oldChannel.getRemoteAddress() + ". new from: " + channel.getRemoteAddress() + ". Kick off old.");

			// Cache this message
			DuplicateLoginMessage duplicateLoginMessage = new DuplicateLoginMessage();
			duplicateLoginMessage.channel = channel;
			duplicateLoginMessage.loginMessage = loginMessage;
			duplicateLoginMessages.put(user.getId(), duplicateLoginMessage);

			// Kick off user
			KickDuplicateLogin msgKickDuplicateLogin = new KickDuplicateLogin();
			sendMessage(oldChannel, msgKickDuplicateLogin);
//			return;
		}

		// Set the channel user id.
		channel.bindUser(user);
		logger.info("User " + user.getName() + "(" + user.getId() + ") login from address " + channel.getRemoteAddress());

		// Found the user, notify client
		msgToSend.userId = user.getId();
		msgToSend.message = "登录成功";
		sendMessage(channel, msgToSend);

		// 通知媒体服务器
		this.notifyMediaServer(user, loginMessage);

		// Send User detail information
		// Becuase our message queue has no priority for message handler, so
		// we split the message to two messages.
		this.sendUserInfo(channel, user);

		// Add this user to server user list

		// send mediaserver address
		sendMediaServerAddress(channel);

		// send advertisement address
		sendAdvertisement(channel);
		
		// Send this user his contact list and status
		sendContactList(user);
//		sendContactListAndStatus(user);

		// send this user all confernecemode and his conferneces
		sendConferenceListAndModelList(user, channel);

		// send login successfully message
		sendLoginSuccessfully(channel);
	}

	private void sendUserInfo(TCPChannel channel, User user) throws Exception {
		UserDetail detail = new UserDetail();
		detail.userId = user.getId();
		detail.email = user.getEmail();
		detail.firstName = user.getFirstName();
		detail.lastName = user.getLastName();
		detail.userName = user.getAccountName();
		detail.userRole = 0;
		// 普通用户如果是组管理员,则角色置为3
		if (this.isGroupManager(user)) 
			detail.userRole = 3;
		
		if (this.getConferenceManager().isAdministrator(user)) {
			detail.userRole = 1;
			
			//设置管理员角色
			SetContactAdmin msg = new SetContactAdmin();
			msg.setSenderId(user.getId());
			sendMessage(channel, msg);
		}
		
		// 会议管理员如果是组管理员,则角色置为4
		// if(this.getConferenceManager().isGroupManager(detail.userId) &&
		// detail.userRole == 2){
		// detail.userRole = 4;
		// }
		
		sendMessage(channel, detail);
	}

	private void notifyMediaServer(User user, Login loginMessage) throws Exception {
		TCPChannel mediaServerChannel = this.getMediaServer().getChannel();
		NotifyMediaServerUserJoin msgToMediaServer = new NotifyMediaServerUserJoin();
		msgToMediaServer.userId = user.getId();
		sendMessage(mediaServerChannel, msgToMediaServer);

		if (loginMessage.avCompressionCardChannelNumber > 0) {
			for (int i = 0; i < loginMessage.avCompressionCardChannelNumber; i++) {
				msgToMediaServer.userId = String.valueOf(100000000000000000L + Long.parseLong(user.getId()) * 10 + i + 1);
				sendMessage(mediaServerChannel, msgToMediaServer);
			}
		}
	}

	private void sendLoginSuccessfully(TCPChannel channel) throws Exception {
		// 发送登录成功消息
		LoginSuccessfully msg = new LoginSuccessfully();
		sendMessage(channel, msg);
	}

	private void sendConferenceListAndModelList(User user, TCPChannel channel) throws Exception {

		// 通知该用户临时会议的模式ID
		InstantConferenceModelId icmiMessage = new InstantConferenceModelId();
		icmiMessage.instantConferenceModelId = InstantConferenceModel.instantConferenceModelId;
		sendMessage(channel, icmiMessage);
		
		// 通知该用户所有的会议模式
		ConferenceModelTableItem msgModel = new ConferenceModelTableItem();
		ConferenceModelRoleTableItem msgRole = new ConferenceModelRoleTableItem();
		
		ConferenceManager manager = this.getConferenceManager();
		Collection<Mode> modes = manager.getModes();
		if(modes != null && modes.size() > 0) {
			for(Mode itemModel : modes) {
				msgModel.modelId = itemModel.getId();
				msgModel.modelName = itemModel.getName();
				sendMessage(channel, msgModel);

				msgRole.modelId = msgModel.modelId;
				// List listRole =
				// this.getConferenceManager().getMode(msgRole.modelId).getRoles();
				Collection<ModeRole> roles = itemModel.getRoles();
				if(roles == null)
					return;
				for (ModeRole itemRole : itemModel.getRoles()) {
					msgRole.roleId = itemRole.getId();
					msgRole.roleName = itemRole.getName();
					StringBuffer sb = new StringBuffer();
					int[] rights = itemRole.getRights();
					if(rights != null) {
						for (int k = 0; k < rights.length; k++) {
							sb.append(rights[k]);
							if (k < rights.length - 1)
								sb.append(",");
						}
					}
					
					msgRole.rights = sb.toString();
					sendMessage(channel, msgRole);
				}
			}
		}
		
		// 通知该用户所有他能够参加的会议
		Collection<Conference> conferences = manager.getNormalConferences(user);
		for (Conference dbConference : conferences) {
			sendConferenceToUser(channel, dbConference);
		}
	}
	
	public void sendContactList(User user) throws Exception {
		ConferenceManager manager = this.getConferenceManager();
		
		// 发送公共联系人和组
		Group uc = manager.getCommonContactGroup();
		
		//获取所有公共联系人组
		Collection<Group> commonGroups = new ArrayList<Group>();
		commonGroups.add(uc);
		commonGroups.addAll(getAllChildren(uc));
		
		//发送公共联系人组
		for(Group group : commonGroups) {
			AddGroup msgAddGroup = new AddGroup();
			msgAddGroup.isCommon = true;
			msgAddGroup.groupId = group.getId();
			msgAddGroup.parentGroupId = group.getParentGroup().getId();
			msgAddGroup.name = group.getName();
			sendMessage(this.getChannel(user.getId()), msgAddGroup);
		}
		
		//获取所有的公共联系人
		Map<String, String> map = new HashMap<String, String>();
		List<User> members = new ArrayList<User>();
		for(Group temp : commonGroups) {
			Collection<? extends User> users = temp.users();
			members.addAll(users);
			
			for(User tmp : users)
				map.put(tmp.getId(), temp.getId());
		}
		
		//对所有公共联系人按姓名排序
		UserComparator comparator = new UserComparator();
		Collections.sort(members, comparator);
		
		//发送所有公共联系人
		for(User member : members) {
			String contactId = member.getId();
			String groupId = map.get(contactId);
			
			//在公共联系人中不显示自己
			if (contactId.equals(user.getId()))
				continue;
			
			AddContact msgAddContact = new AddContact();
			msgAddContact.isCommon = true;
			msgAddContact.contactId = contactId;
			msgAddContact.groupId = groupId;
			msgAddContact.name = member.getAccountName();
			msgAddContact.email = member.getEmail();
			msgAddContact.firstName = member.getFirstName();
			msgAddContact.lastName = member.getLastName();
			String userId = member.getId();
			TCPChannel channel = this.getChannel(userId);
			if (channel != null)
				msgAddContact.status = CONTACT_ONLINE;
			else
				msgAddContact.status = CONTACT_OFFLINE;

			sendMessage(this.getChannel(user.getId()), msgAddContact);
			
			// 通知对方自己上线
			if (this.getChannel(member.getId()) != null) {
				UpdateContactStatus msg = new UpdateContactStatus();
				msg.bIsCommon = true;
				msg.contactId = user.getId();
				msg.status = CONTACT_ONLINE;
				sendMessage(this.getChannel(member.getId()), msg);
			}
		}
		
		//发送消息通知管理员（公共联系人）
		String managerId = manager.getLeaserId();
		if(!user.getId().equals(managerId) && this.getChannel(managerId) != null) {
			UpdateContactStatus msg = new UpdateContactStatus();
			msg.bIsCommon = true;
			msg.contactId = user.getId();
			msg.status = CONTACT_ONLINE;
			sendMessage(this.getChannel(managerId), msg);
		}

		// 发送自己的联系人和组
		TCPChannel channel = this.getConferenceServer().getChannel(user);
		
		// 发送自己的默认组id
		DefaultGroupId msg = new DefaultGroupId();
		msg.defaultGroupId = user.getContactGroup().getId();
		sendMessage(channel, msg);

		// 发送自己的联系人和组
		Collection<Group> groups  = new ArrayList<Group>();
		Group contactGroup = user.getContactGroup();
		groups.add(contactGroup);
		groups.addAll(getAllChildren(contactGroup));
		for (Group group : groups) {
			if (!group.getId().equals(msg.defaultGroupId)) {
				AddGroup msgAddGroup = new AddGroup();
				msgAddGroup.isCommon = false;
				msgAddGroup.groupId = group.getId();
				msgAddGroup.parentGroupId = group.getParentGroup().getId();//msg.defaultGroupId;
				msgAddGroup.name = group.getName();
				sendMessage(channel, msgAddGroup);
			}
			// Long[] memberIds = (Long [])groups[i].memberIds.toArray(new
			// Long[0]);
			for (User contact : group.users()) {
				AddContact msgAddContact = new AddContact();
				msgAddContact.isCommon = false;
				msgAddContact.contactId = contact.getId();
				msgAddContact.groupId = group.getId();
				msgAddContact.name = contact.getAccountName();
				msgAddContact.email = contact.getEmail();
				msgAddContact.firstName = contact.getFirstName();
				msgAddContact.lastName = contact.getLastName();
				if (this.getConferenceServer().getChannel(contact) == null) {
					msgAddContact.status = CONTACT_OFFLINE;
				} else {
					msgAddContact.status = CONTACT_ONLINE;
				}
				sendMessage(channel, msgAddContact);
				
				//通知对方自己上线
				if (contact != null) {
					UpdateContactStatus msgUpdateStatus = new UpdateContactStatus();
					msgUpdateStatus.bIsCommon = false;
					msgUpdateStatus.contactId = user.getId();
					msgUpdateStatus.status = CONTACT_ONLINE;
					sendMessage(this.getConferenceServer().getChannel(contact), msgUpdateStatus);
				}
			}
		}
	}

	public void sendContactListAndStatus(User user) throws Exception {
		ConferenceManager manager = this.getConferenceManager();
		
		// 发送公共联系人和组
		Group uc = manager.getCommonContactGroup();
		if (uc != null && isAuthorizedUser(user, uc)) {
			sendCommonContact(user, uc, "0");
		}
		
		//发送消息通知管理员（公共联系人）
		String managerId = manager.getLeaserId();
		if(!user.getId().equals(managerId) && this.getChannel(managerId) != null) {
			UpdateContactStatus msg = new UpdateContactStatus();
			msg.bIsCommon = true;
			msg.contactId = user.getId();
			msg.status = CONTACT_ONLINE;
			sendMessage(this.getChannel(managerId), msg);
		}

		// 发送自己的联系人和组
		TCPChannel channel = this.getConferenceServer().getChannel(user);
		
		// 发送自己的默认组id
		DefaultGroupId msg = new DefaultGroupId();
		msg.defaultGroupId = user.getContactGroup().getId();
		sendMessage(channel, msg);

		// 发送自己的联系人和组
		Collection<Group> groups  = new ArrayList<Group>();
		Group contactGroup = user.getContactGroup();
		groups.add(contactGroup);
		groups.addAll(getAllChildren(contactGroup));
		for (Group group : groups) {
			if (!group.getId().equals(msg.defaultGroupId)) {
				AddGroup msgAddGroup = new AddGroup();
				msgAddGroup.isCommon = false;
				msgAddGroup.groupId = group.getId();
				msgAddGroup.parentGroupId = group.getParentGroup().getId();//msg.defaultGroupId;
				msgAddGroup.name = group.getName();
				sendMessage(channel, msgAddGroup);
			}
			// Long[] memberIds = (Long [])groups[i].memberIds.toArray(new
			// Long[0]);
			for (User contact : group.users()) {
				AddContact msgAddContact = new AddContact();
				msgAddContact.isCommon = false;
				msgAddContact.contactId = contact.getId();
				msgAddContact.groupId = group.getId();
				msgAddContact.name = contact.getAccountName();
				msgAddContact.email = contact.getEmail();
				msgAddContact.firstName = contact.getFirstName();
				msgAddContact.lastName = contact.getLastName();
				if (this.getConferenceServer().getChannel(contact) == null) {
					msgAddContact.status = CONTACT_OFFLINE;
				} else {
					msgAddContact.status = CONTACT_ONLINE;
				}
				sendMessage(channel, msgAddContact);
				
				//通知对方自己上线
				if (contact != null) {
					UpdateContactStatus msgUpdateStatus = new UpdateContactStatus();
					msgUpdateStatus.bIsCommon = false;
					msgUpdateStatus.contactId = user.getId();
					msgUpdateStatus.status = CONTACT_ONLINE;
					sendMessage(this.getConferenceServer().getChannel(contact), msgUpdateStatus);
				}
			}
		}
	}

	public void removeUser(User user) throws SQLException {
		// send message to this user's contacts
		RemoveContact msg = new RemoveContact();

		for (Group group : user.getContactGroup().children()) {
			for (User member : group.users()) {
				TCPChannel channel = this.getConferenceServer().getChannel(member);
				if (channel != null) {
					msg.contactId = user.getId();
					msg.groupId = user.getInContactGroup(member).getId();
					sendMessage(channel, msg);
				}
			}
		}

		getConferenceManager().delete(user);
		// todo: send message to common contacts if userId is a common contact
		// id
	}

	/*
	 * private boolean isCommonContact( long userId, UserContact commonContacts
	 * ) { if( commonContacts.userIds != null ) { for( int i = 0; i <
	 * commonContacts.userIds.size(); i++ ) { if(
	 * ((Long)commonContacts.userIds.get(i)).longValue() == userId ) return
	 * true; } } for( int j = 0; j < commonContacts.subGroups.size(); j++ ) {
	 * boolean b = isCommonContact( userId,
	 * (UserContact)(commonContacts.subGroups.get(j)) ); if( b ) return true; }
	 * return false; }
	 */
	private void sendCommonContact(User peer, Group uc, String parentGroupId) throws Exception {
		String groupId = uc.getId();
//		String commonGroupId = this.getConferenceManager().getCommonContactGroup().getId();
//		if (groupId.equals(commonGroupId))
//			groupId = "0";
		
		if (Long.parseLong(groupId) > 0) {
			AddGroup msgAddGroup = new AddGroup();
			msgAddGroup.isCommon = true;
			msgAddGroup.groupId = groupId;
			msgAddGroup.parentGroupId = parentGroupId;
			msgAddGroup.name = uc.getName();
			sendMessage(this.getChannel(peer.getId()), msgAddGroup);
			if(!groupId.equals(this.getConferenceManager().getCommonContactGroup().getId())) {
				Enumeration<? extends Principal> members = uc.members();
				while(members.hasMoreElements()) {
					User member = (User) members.nextElement();
					String contactId = member.getId();
					
					//在公共联系人中不显示自己
					if (contactId.equals(peer.getId()))
						continue;
					
					AddContact msgAddContact = new AddContact();
					msgAddContact.isCommon = true;
					msgAddContact.contactId = contactId;
					msgAddContact.groupId = groupId;
					msgAddContact.name = member.getAccountName();
					msgAddContact.email = member.getEmail();
					msgAddContact.firstName = member.getFirstName();
					msgAddContact.lastName = member.getLastName();
					String userId = member.getId();
					TCPChannel channel = this.getChannel(userId);
					if (channel != null)
						msgAddContact.status = CONTACT_ONLINE;
					else
						msgAddContact.status = CONTACT_OFFLINE;
		
					sendMessage(this.getChannel(peer.getId()), msgAddContact);
					
					// 通知对方自己上线
					if (this.getChannel(member.getId()) != null) {
						UpdateContactStatus msg = new UpdateContactStatus();
						msg.bIsCommon = true;
						msg.contactId = peer.getId();
						msg.status = CONTACT_ONLINE;
						sendMessage(this.getChannel(member.getId()), msg);
					}
					// System.out.println("addContact: id = " + msgAddContact.groupId +
					// "; name = " + msgAddContact.name);
				}
			}
		}
		for (Group child : uc.children()) {
			sendCommonContact(peer, child, groupId);
		}
	}

	protected void channelBroken(TCPChannel channel) throws Exception {

		String userId = channel.getUserId();

		// 如果用户还没有登录验证，在逻辑层面对其不做处理
		if (Long.parseLong(userId) <= 0) {
			return;
		}

		if (this.getChannel(userId) == null) {
			return;
		}

		// 遍历该用户所在的所有会议,删除这个用户
		for (Conference attented : this.getConferenceManager().getHoldingConferences(channel.getUser())) {
			attented.leave(channel.getUser());
		}

		// Notify all user that one use channel is broken
		UserChannelBroken message = new UserChannelBroken();
		message.userId = userId;
		for (TCPChannel item : server.channels())
			sendMessage(item, message);

		// 检查是否是因为重复登录而踢掉这个用户,如果是这样,我们再调用登录函数，处理这个事件
		if (duplicateLoginMessages.containsKey(userId)) {
			DuplicateLoginMessage duplicateLoginMessage = (DuplicateLoginMessage) duplicateLoginMessages.get(userId);
			duplicateLoginMessages.remove(userId);
			this.execute(duplicateLoginMessage.channel, duplicateLoginMessage.loginMessage);
		}

		// 从servermonitor删除用户
		// server.getServerMonitorSession().leaveServer(userId);
	}

	private void sendAdvertisement(TCPChannel channel) throws Exception {
		// 发送广告地址
		Advertizement msgAd = new Advertizement();
		msgAd.homepage = this.adURLs.getProperty("adHomepage");
		msgAd.mainframe = this.adURLs.getProperty("adMainframe");
		msgAd.roomframe = this.adURLs.getProperty("adRoomframe");
		sendMessage(channel, msgAd);
	}

	private boolean isGroupManager(User user) throws SQLException {
		/**
		 * 公共联系人组，id=1；
		 */
		Group group = this.getConferenceManager().getCommonContactGroup();
		if(group != null)
			return this.isGroupManager(group, user);
		else
			return false;
	}

	private boolean isGroupManager(Group group, User user) throws SQLException {
		if (user.equals(group.getManager()))
			return true;
		
		for (Group child : group.children()) {
			if (this.isGroupManager(child, user))
				return true;
		}
		
		return false;
	}

	protected void sendMediaServerAddress(TCPChannel channel) throws Exception {

		MediaServerAddress msgToSend = new MediaServerAddress();
		msgToSend.host = this.getMediaServer().getIP();
		msgToSend.udpport = this.getMediaServer().getUDPPort();
		msgToSend.tcpport = this.getMediaServer().getTCPPort();
		sendMessage(channel, msgToSend);
	}

	public boolean sendMessage(TCPChannel chan, TCPMessage pBaseMessage) {
		return super.sendMessage(chan, pBaseMessage);
	}
	
	public boolean sendMessage(User user, TCPMessage message) throws Exception{
		return super.sendMessage(user, message);
	}
	
	/**
	 * 
	 * 获取一个组下的所有子组（包括每一层的子组）
	 */
	private static Collection<Group> getAllChildren(Group group) {
		ArrayList<Group> groups = new ArrayList<Group>();
		groups.addAll(group.children());
		
		for(int i=0; i<groups.size(); i++) {
			groups.addAll(groups.get(i).children());
		}
		
		return groups;
	}
	
	/**
	 * 判断用户是否系统授权用户
	 */
	private boolean checkUser(User user) {
		ConferenceManager manager = this.getConferenceManager();
		
		String leaserId = manager.getLeaserId();

		Node node = (Node) user;
		if(leaserId.equals(user.getId()) || leaserId.equals(node.getParent().getID())) {
			//用户是管理员
			if(leaserId.equals(user.getId()))
				return true;
			
			//普通用户
			Collection<Group> groupList = new ArrayList<Group>();
			Group group = manager.getCommonContactGroup();
			groupList.add(group);
			groupList.addAll(getAllChildren(group));
			
			Collection<User> userList = new ArrayList<User>();
			for(Group temp : groupList)
				userList.addAll(temp.users());
			
			if(userList.contains(user))
				return true;
		}
		
		return false;
	}
	
}
