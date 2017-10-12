/**
 * 
 */
package com.fulong.lyvc.handler.desktop;

import com.fulong.lyvc.TCPMessage;
import com.fulong.lyvc.TCPChannel;
import com.fulong.lyvc.message.AgreeDesktop;
import com.fulong.lyvc.message.AgreeInviteDesktop;
import com.fulong.lyvc.message.MediaServerTCPForwardReady;

/**
 * MediaServerTCPForwardReadyHandler
 * 
 * ��Ԧ��Ƶ����ϵͳ v3.0
 * 
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 * 
 * @author ���۷�
 * 
 *         ����޸�ʱ�䣺2009-3-13
 */
public class MediaServerTCPForwardReadyHandler extends DesktopBaseHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fulong.lyvc.net.Handler#execute(com.fulong.lyvc.net.TCPChannel,
	 * com.fulong.lyvc.message.BaseMessage)
	 */
	@Override
	public void execute(TCPChannel channel, TCPMessage message) throws Exception {
		this.setConferenceManager(channel.getDomain());

		MediaServerTCPForwardReady ready = (MediaServerTCPForwardReady) message;

		if (ready.isAgreeInvite) {
			AgreeInviteDesktop msgToSend = new AgreeInviteDesktop();
			msgToSend.setConferenceId(message.getConferenceId());
			msgToSend.senderId = ready.toUserId;
			msgToSend.receiverId = ready.fromUserId;
			sendMessage(ready.fromUserId, msgToSend);
		} else {
			AgreeDesktop msgToSend = new AgreeDesktop();
			msgToSend.setConferenceId(message.getConferenceId());
			msgToSend.senderId = ready.fromUserId;
			msgToSend.receiverId = ready.toUserId;
			sendMessage(ready.toUserId, msgToSend);
		}
	}
}
