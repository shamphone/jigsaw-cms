/**
 * 
 */
package com.fulong.lyvc.handler.desktop;

import com.fulong.lyvc.TCPMessage;
import com.fulong.lyvc.TCPChannel;
import com.fulong.lyvc.message.StopReceiveDesktop;
import com.fulong.lyvc.message.StopSendDesktop;

/**
 * StopSendDesktopHandler
 * 
 * ��Ԧ��Ƶ����ϵͳ v3.0
 * 
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 * 
 * @author ���۷�
 * 
 *         ����޸�ʱ�䣺2009-3-13
 */
public class StopSendDesktopHandler extends DesktopBaseHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fulong.lyvc.net.Handler#execute(com.fulong.lyvc.net.TCPChannel,
	 * com.fulong.lyvc.message.BaseMessage)
	 */
	@Override
	public void execute(TCPChannel channel, TCPMessage message) throws Exception {
		this.setConferenceManager(channel.getDomain());
		
		StopSendDesktop srdMessage = (StopSendDesktop) message;

		// ֪ͨý�������
		notifyMediaServerRemoveRelation(srdMessage.receiverId, srdMessage.senderId);

		// ֪ͨ�Է�
		StopReceiveDesktop msg = new StopReceiveDesktop();
		msg.setConferenceId(message.getConferenceId());
		msg.senderId = srdMessage.senderId;
		msg.receiverId = srdMessage.receiverId;
		msg.conferenceId = srdMessage.conferenceId;
		sendMessage(srdMessage.receiverId, msg);

	}

}
