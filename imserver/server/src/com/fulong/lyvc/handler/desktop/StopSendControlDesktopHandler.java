/**
 * 
 */
package com.fulong.lyvc.handler.desktop;

import com.fulong.lyvc.TCPMessage;
import com.fulong.lyvc.TCPChannel;
import com.fulong.lyvc.message.StopReceiveControlDesktop;
import com.fulong.lyvc.message.StopSendControlDesktop;

/**
 * StopSendControlDesktopHandler
 * 
 * ��Ԧ��Ƶ����ϵͳ v3.0
 * 
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 * 
 * @author ���۷�
 * 
 *         ����޸�ʱ�䣺2009-3-13
 */
public class StopSendControlDesktopHandler extends DesktopBaseHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fulong.lyvc.net.Handler#execute(com.fulong.lyvc.net.TCPChannel,
	 * com.fulong.lyvc.message.BaseMessage)
	 */
	@Override
	public void execute(TCPChannel channel, TCPMessage message) throws Exception {
		this.setConferenceManager(channel.getDomain());

		StopSendControlDesktop sscdMessage = (StopSendControlDesktop) message;

		StopReceiveControlDesktop msg = new StopReceiveControlDesktop();
		msg.conferenceId = sscdMessage.conferenceId;
		msg.setConferenceId(message.getConferenceId());
		msg.receiverId = sscdMessage.receiverId;
		msg.senderId = sscdMessage.senderId;
		sendMessage(sscdMessage.receiverId, msg);

	}

}
