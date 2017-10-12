/**
 * 
 */
package com.fulong.lyvc.handler.conference;

import java.util.Calendar;
import java.util.Collection;

import com.fulong.lyvc.Conference;
import com.fulong.lyvc.TCPChannel;
import com.fulong.lyvc.TCPMessage;
import com.fulong.lyvc.User;
import com.fulong.lyvc.handler.server.InstantConferenceModel;
import com.fulong.lyvc.message.QuitConference;
import com.fulong.lyvc.message.UserQuitConference;

/**
 * QuitConferenceHandler
 * 
 * 龙驭视频会议系统 v3.0
 * 
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 * 
 * @author 李雄锋
 * 
 * 最后修改时间：2010-9-10
 */
public class QuitConferenceHandler extends ConferenceBaseHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fulong.lyvc.net.Handler#execute(com.fulong.lyvc.net.TCPChannel,
	 * com.fulong.lyvc.message.BaseMessage)
	 */
	@Override
	public void execute(TCPChannel channel, TCPMessage message) throws Exception {
		this.setConferenceManager(channel.getDomain());
		
		String userId = message.getSenderId();
		User user = this.getConferenceManager().getUser(userId);

		String cid = ((QuitConference) message).conId;
		Conference con = this.getConferenceManager().getConference(cid);

		// 用户退出会议
		con.leave(user);

		// 给即时会议中的其他人员发送退出会议的通知
		UserQuitConference msgToSend = new UserQuitConference();
		msgToSend.userId = userId;
		msgToSend.setConferenceId(cid);
		msgToSend.conferenceId = cid;
		Collection<User> participants = con.getParticipants();
		for (User member : participants) {
			sendMessage(member, msgToSend);
		}
		
		//设置即时会议的结束时间（最后一个参与者退出即时会议时）
		if(con.getMode().getId().equals(InstantConferenceModel.instantConferenceModelId) && participants != null && participants.size() == 0) {
			con.setEndTime(Calendar.getInstance().getTime());
			//TODO 将该会议从保存即时会议的变量中删除
			this.getConferenceManager().finishInstantConference(cid);
		}
		
		// ��servermonitor��ɾ�����
//		this.server.getServerMonitorSession().quitConf(cid, userId);
		// ֪ͨý�������
		/*
		 * �qΰװ幦�� TCPChannel mediaServerChannel =
		 * this.server.mediaServerProcess.getMediaServerChannel();
		 * NotifyMediaServerUserQuitConference msgToMediaServer = new
		 * NotifyMediaServerUserQuitConference(); msgToMediaServer.userId =
		 * userId; msgToMediaServer.confId = cid;
		 * MessageProcessor.sendMessage(mediaServerChannel, msgToMediaServer);
		 */

	}

}
