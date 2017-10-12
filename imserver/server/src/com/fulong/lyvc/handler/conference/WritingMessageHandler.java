/**
 * 
 */
package com.fulong.lyvc.handler.conference;

import com.fulong.lyvc.Conference;
import com.fulong.lyvc.TCPChannel;
import com.fulong.lyvc.TCPMessage;
import com.fulong.lyvc.User;
import com.fulong.lyvc.message.WritingMessage;

/**
 * WritingMessageHandler
 *
 * 龙驭视频会议系统 v3.0
 *
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 *
 * @author lifx
 *
 * 最后修改时间：2010-8-6
 */

public class WritingMessageHandler extends ConferenceBaseHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fulong.lyvc.net.Handler#execute(com.fulong.lyvc.net.TCPChannel,
	 * com.fulong.lyvc.message.BaseMessage)
	 */
	public void execute(TCPChannel channel, TCPMessage message) throws Exception {
		if (((WritingMessage) message).receiverId.equals("0")) {
			this.setConferenceManager(channel.getDomain());

			String uid = message.getSenderId();
			String cid = message.getConferenceId();
			Conference con = this.getConferenceManager().getConference(cid);
			if (con != null) {
				for (User user : con.getParticipants()) {
					if (!user.getId().equals(uid)) {
						this.sendMessage(user, message);
					}
				}
			}
		} else {
			sendMessage(((WritingMessage) message).receiverId, message);
		}
	}
}
