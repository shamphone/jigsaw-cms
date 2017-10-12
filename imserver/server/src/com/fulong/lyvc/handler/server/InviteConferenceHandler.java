/**
 * 
 */
package com.fulong.lyvc.handler.server;

import java.util.Collection;
import java.util.Date;

import com.fulong.lyvc.Conference;
import com.fulong.lyvc.ConferenceManager;
import com.fulong.lyvc.TCPChannel;
import com.fulong.lyvc.TCPMessage;
import com.fulong.lyvc.User;
import com.fulong.lyvc.message.InviteConference;

/**
 * 
 * Conference
 * 
 * 龙驭视频会议系统 v3.0
 * 
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 * 
 * @author 李雄锋
 * 
 * 最后修改时间：2010-8-26
 */

public class InviteConferenceHandler extends ServerBaseHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fulong.lyvc.net.Handler#execute(com.fulong.lyvc.net.TCPChannel,
	 * com.fulong.lyvc.message.BaseMessage)
	 */
	@Override
	public void execute(TCPChannel channel, TCPMessage message) throws Exception {
		this.setConferenceManager(channel.getDomain());
		ConferenceManager manager = this.getConferenceManager();
		
		InviteConference inviteConference = (InviteConference) message;
		if (this.getChannel(inviteConference.contactId) == null)
			return;

		// 向会议中添加用户，其角色为该会议的内置角色（每种会议只有一种）
		Conference dbConference = manager.getConference(inviteConference.conferenceId);
		
		//将该会议重新添加到保存即时会议的变量中
		if(dbConference.getMode().getId().equals(InstantConferenceModel.instantConferenceModelId)) {
			Collection<User> participants = dbConference.getParticipants();
			if(participants != null && participants.size() == 0) {
				manager.addInstantConference(dbConference);
			}
		}
		
		//向会议中添加用户
		dbConference.addMember(inviteConference.contactId);
		
		//重新设置会议结束时间，修改其为当前时间后八个小时
		//最后一个用户退出会议时，设置了会议结束时间为那时候的时间，若不重新设置，将无法进行会议
		long time = System.currentTimeMillis() + (long)(8*60*60*1000);
		dbConference.setEndTime(new Date(time));
		
		//发送添加会议的信息给用户
		//如果用户原来没有在该会议中，需要发送；如果用户原来在会议中，但是注销后重新登录了，也需要发送
		sendConferenceToUser(this.getChannel(inviteConference.contactId), dbConference);

		// 发送邀请参加会议的信息
		sendMessage(inviteConference.contactId, message);
	}

}
