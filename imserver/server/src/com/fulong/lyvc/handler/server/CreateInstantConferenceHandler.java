package com.fulong.lyvc.handler.server;

import java.util.Calendar;
import java.util.Date;

import com.fulong.lyvc.Conference;
import com.fulong.lyvc.ConferenceManager;
import com.fulong.lyvc.TCPChannel;
import com.fulong.lyvc.TCPMessage;
import com.fulong.lyvc.User;
import com.fulong.lyvc.message.CreateInstantConference;
import com.fulong.lyvc.message.InviteConference;

/**
 * CreateInstantConference
 * 
 * 龙驭视频会议系统 v3.0
 * 
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 * 
 * @author 李雄锋
 * 
 * 最后修改时间：2010-8-25
 */
public class CreateInstantConferenceHandler extends ServerBaseHandler {

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
		
		CreateInstantConference cifMessage = (CreateInstantConference) message;
		User user = manager.getUser(cifMessage.getSenderId());
		String conferenceName = user.getName() + "与";

		String[] userIds = cifMessage.participants.split(",");
		for (int i = 0; i < userIds.length; i++) {
			String participantId = userIds[i];
			User confUser = manager.getUser(participantId);
			if (confUser != null) {
				conferenceName = conferenceName + confUser.getName();
			}
			if (conferenceName.length() > 70) {
				if (userIds.length - i > 1)
					conferenceName = conferenceName + "...";
				break;
			}
			if (userIds.length - i > 1)
				conferenceName = conferenceName + ",";
		}
		conferenceName = conferenceName + "的临时会议";

		// Add conference to database
		// We use 2005-1-1 as the start date
		// one hour after now as the end date

		Conference con = manager.createInstantConference(conferenceName, user);
		con.setStartTime(Calendar.getInstance().getTime());
		//修改会议结束时间为开始时间后八个小时（会议结束时，会在内容库中修改为会议的实际结束时间）
		long time = System.currentTimeMillis() + (long)(8*60*60*1000);
		con.setEndTime(new Date(time));
		String conferenceId = con.getId();
		// Add the conference to conference list

		// this.server.getConferenceTable().addDBConference(con);

		// Find all the participants
		// Add this conference to each user and invite them
		
		//添加发起人
		con.addMember(InstantConferenceModel.callerRoleId, message.getSenderId());
		sendConferenceToUser(channel, con);
		
		InviteConference inviteMessage = new InviteConference();
		inviteMessage.conferenceId = conferenceId;
		inviteMessage.contactId = user.getId();
		inviteMessage.holderId = inviteMessage.contactId;
		sendMessage(channel, inviteMessage);

		//添加参会人
		String[] participants = cifMessage.participants.split(",");
		for (int i = 0; i < participants.length; i++) {
			String participantId = participants[i];
			con.addMember(InstantConferenceModel.calleeRoleId, participantId);

			TCPChannel peer = this.getChannel(participantId);
			if (peer != null) {
				sendConferenceToUser(peer, con);
				inviteMessage.contactId = participantId;
				inviteMessage.holderId = "0";
				sendMessage(peer, inviteMessage);
			}
		}
	}

}
