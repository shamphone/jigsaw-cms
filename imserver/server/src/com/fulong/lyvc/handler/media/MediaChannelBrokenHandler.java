/**
 * 
 */
package com.fulong.lyvc.handler.media;

import java.util.ArrayList;
import java.util.Collection;

import com.fulong.lyvc.ChannelBrokenHandler;
import com.fulong.lyvc.ConferenceManager;
import com.fulong.lyvc.Group;
import com.fulong.lyvc.MediaServer;
import com.fulong.lyvc.TCPChannel;
import com.fulong.lyvc.User;
import com.fulong.lyvc.message.NotifyMediaServerUserExit;
import com.fulong.lyvc.message.ServerInternalError;
import com.fulong.lyvc.message.UpdateContactStatus;

/**
 * MediaChannelBrokenHandler
 * 
 * 龙驭视频会议系统 v3.0
 * 
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 * 
 * @author 李雄锋
 * 
 *         最后修改时间：2009-3-13
 */
public class MediaChannelBrokenHandler extends ChannelBrokenHandler {
	private final static int CONTACT_OFFLINE = 1;
	
	public void execute(TCPChannel channel) throws Exception {
		this.setConferenceManager(channel.getDomain());
		ConferenceManager manager = this.getConferenceManager();
		
		if(channel.getUser() != null) {
			TCPChannel chan = this.server.getChannel(channel.getUser());
			if(chan != null)
				return;
		}
		
		MediaServer mediaServer = this.getMediaServer();
		if (channel == mediaServer.getChannel()) {
			// Set cached channel to null
			mediaServer.setChannel(null);

			// 通知所有客户端，
			for (TCPChannel item : this.server.channels()) {
				ServerInternalError msgToSend = new ServerInternalError();
				msgToSend.description = "媒体服务器不可用";
				sendMessage(item, msgToSend);
			}
			this.server.disconnectAll();
			/*
			 * for (Iterator it = this.server.getConferenceTable().getConTable()
			 * .values().iterator(); it.hasNext();) { ((ConferenceInServer)
			 * it.next()).removeAllUser(); }
			 */

			logger.error("Media server channel broken, conference server will stop.");
			synchronized (mediaServer) {
				mediaServer.notify();
			}
		}
		else {
			// If the message sernderid is 0, this user is not authenticated.
			String userId = channel.getUserId();
			
			if (!userId.equals("0")) {
				// 同一个用户由Tcpchannel.sendMessage产生的channelBorken事件可能有多个，
				// 可导致媒体服务器崩溃而使会议服务器停止，因此我们只处理一个这样的消息
				TCPChannel userChannel = this.getChannel(userId);
				if (userChannel != null) {
					NotifyMediaServerUserExit msgToSend = new NotifyMediaServerUserExit();
					msgToSend.userId = userId;
					sendMessage(userChannel, msgToSend);
					int num = userChannel.getAVCompressionCardChannelNumber();
					if (num > 0) {
						for (int i = 0; i < num; i++) {
							msgToSend.userId = String.valueOf(100000000000000000L + Long.parseLong(userId) * 10 + i + 1);
							sendMessage(mediaServer.getChannel(), msgToSend);
						}
					}
				}
				
				//发送消息给公共联系人中的在线用户，通知对方自己离线
				
				//获取所有的公共联系人组
				Group commonGroup = manager.getCommonContactGroup();
				Collection<Group> groupList = new ArrayList<Group>();
//				groupList.add(commonGroup);
				groupList.addAll(getAllChildren(commonGroup));
				
				//获取所有的公共联系人成员
				Collection<User> userList= new ArrayList<User>();
				userList.add(manager.getUser(manager.getLeaserId()));
				for(Group temp : groupList) 
					userList.addAll(temp.users());
				
				//设置自己离线的消息
				UpdateContactStatus msg = new UpdateContactStatus();
				msg.bIsCommon = true;	//公共联系人
				msg.contactId = userId;
				msg.status = CONTACT_OFFLINE;
				
				//发送消息通知公共联系人中的在线用户
				for(User user : userList) {
					userChannel = this.getChannel(user.getId());
					if(userChannel != null) {
						sendMessage(userChannel, msg);
					}
				}
				
				//发送消息给我的联系人中的在线用户，通知对方自己离线
				
				//获取我的联系人中的所有组
				Collection<Group> groups  = new ArrayList<Group>();
				User user = manager.getUser(userId);
				Group group = user.getContactGroup();
				groups.add(group);
				groups.addAll(getAllChildren(group));
				
				//获取我的联系人中的所有组
				Collection<User> users = new ArrayList<User>();
				for(Group temp : groups) 
					users.addAll(temp.users());
				
				//发送消息通知我的联系人中的在线用户
				msg.bIsCommon = false;	//我的联系人
				for (User contact : users) {
					userChannel = this.getChannel(contact.getId());
					if(userChannel != null) {
						sendMessage(userChannel, msg);
					}
				}
			}
		}
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
}
