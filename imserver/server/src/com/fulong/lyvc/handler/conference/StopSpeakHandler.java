/**
 * 
 */
package com.fulong.lyvc.handler.conference;

import com.fulong.lyvc.Conference;
import com.fulong.lyvc.TCPChannel;
import com.fulong.lyvc.TCPMessage;
import com.fulong.lyvc.User;

/**
 * StopSpeakHandler
 * 
 * ��Ԧ��Ƶ����ϵͳ v3.0
 * 
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 * 
 * @author ���۷�
 * 
 *         ����޸�ʱ�䣺2009-3-13
 */
public class StopSpeakHandler extends ConferenceBaseHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fulong.lyvc.net.Handler#execute(com.fulong.lyvc.net.TCPChannel,
	 * com.fulong.lyvc.message.BaseMessage)
	 */
	@Override
	public void execute(TCPChannel channel, TCPMessage message) throws Exception {
		String uid = message.getSenderId();
		String cid = message.getConferenceId();
		
		this.setConferenceManager(channel.getDomain());
		Conference con = this.getConferenceManager().getConference(cid);
		
		if (con != null) {
			for (User user : con.getParticipants()) {
				if (!user.getId().equals(uid)) {
					this.sendMessage(user, message);
				}
			}
		}
	}
}
