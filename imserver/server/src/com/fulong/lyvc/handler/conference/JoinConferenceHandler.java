/**
 * 
 */
package com.fulong.lyvc.handler.conference;

import java.util.ArrayList;

import com.fulong.lyvc.Conference;
import com.fulong.lyvc.Mode;
import com.fulong.lyvc.ModeRole;
import com.fulong.lyvc.TCPChannel;
import com.fulong.lyvc.TCPMessage;
import com.fulong.lyvc.User;
import com.fulong.lyvc.message.JoinConference;
import com.fulong.lyvc.message.JoinConferenceResponse;
import com.fulong.lyvc.message.UserJoinConference;

/**
 * JoinConferenceHandler
 * 
 * 龙驭视频会议系统 v3.0
 * 
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 * 
 * @author 李雄锋
 * 
 *         最后修改时间：2009-3-13
 */
public class JoinConferenceHandler extends ConferenceBaseHandler {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fulong.lyvc.net.Handler#execute(com.fulong.lyvc.net.TCPChannel,
	 * com.fulong.lyvc.message.BaseMessage)
	 */
	@Override
	public void execute(TCPChannel channel, TCPMessage message) throws Exception {
		this.setConferenceManager(channel.getDomain());
		
		String uid = message.getSenderId();
		String cid = ((JoinConference) message).conId;

		Conference theCon = this.getConferenceManager().getConference(cid);
		if (theCon == null)
			return;
		
		// 获取用户在会议中的身份
		ModeRole role = theCon.getMemberRole(uid);
		String roleId = "-1";	
		if(role == null) {
			Mode mode = theCon.getMode();
			for (ModeRole temp : mode.getRoles()) {
				if (temp.isDefault()) {
					roleId = temp.getId();
					break;
				}
			}
		}
		else {
			roleId = role.getId();
		}
		
		// 将用户加入会议
		User cu = this.getConferenceManager().getUser(uid);
		theCon.join(cu);
		
		// notify other user in conference
		UserJoinConference msgToSend = new UserJoinConference();
		msgToSend.setConferenceId(cid);
		msgToSend.conId = cid;
		msgToSend.roleId = roleId;
		msgToSend.userId = uid;
		msgToSend.userName = cu.getAccountName();
		msgToSend.firstName = cu.getFirstName();
		msgToSend.lastName = cu.getLastName();
		msgToSend.email = cu.getEmail();
		msgToSend.avCompressionCardChannelNumber = ((JoinConference) message).avCompressionCardChannelNumber;
		for (User user: theCon.getParticipants()) {
			if(!user.equals(cu))
				sendMessage(user, msgToSend);
		}

		// send all member in conference to this user
		ArrayList<UserJoinConference> userStack = new ArrayList<UserJoinConference>();
		for (User member: theCon.getParticipants()) {
			if(!member.equals(cu)){
				msgToSend = new UserJoinConference();
				msgToSend.setConferenceId(cid);
				msgToSend.conId = cid;
				msgToSend.userId = member.getId();
				msgToSend.userName = member.getAccountName();
				msgToSend.firstName = member.getFirstName();
				msgToSend.lastName = member.getLastName();
				msgToSend.email = member.getEmail();
				msgToSend.roleId = theCon.getMemberRole(member.getId()).getId();
				msgToSend.avCompressionCardChannelNumber = this.getChannel(cu.getId()).getAVCompressionCardChannelNumber();

				// MessageProcessor.sendMessage(channel, msgToSend);
				userStack.add(msgToSend);
			}
		}
		
		 for(int i = userStack.size() - 1;i > -1 ;i--)
			sendMessage(channel, userStack.get(i));
		
		// 返回用户身份
		JoinConferenceResponse msgToSend2 = new JoinConferenceResponse();
		msgToSend2.setConferenceId(cid);
		msgToSend2.conId = cid;
		msgToSend2.roleId = roleId;
		sendMessage(channel, msgToSend2);

		// 将信息加入servermonitor数据库
//		this.getServerMonitorSession().enterConf(cid, uid);

		// 通知媒体服务器
		/*
		 * 屏蔽白板功能 TCPChannel mediaServerChannel =
		 * this.server.mediaServerProcess.getMediaServerChannel();
		 * NotifyMediaServerUserJoinConference msgToMediaServer = new
		 * NotifyMediaServerUserJoinConference(); msgToMediaServer.userId = uid;
		 * msgToMediaServer.confId = cid;
		 * MessageProcessor.sendMessage(mediaServerChannel, msgToMediaServer);
		 */

	}

}
