/**
 * 
 */
package com.fulong.lyvc.handler.desktop;

import com.fulong.lyvc.TCPMessage;
import com.fulong.lyvc.TCPChannel;
import com.fulong.lyvc.message.StopReceiveDesktop;
import com.fulong.lyvc.message.StopSendDesktop;

/**
 * StopReceiveDesktopHandler
 * 
 * ��Ԧ��Ƶ����ϵͳ v3.0
 * 
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 * 
 * @author ���۷�
 * 
 *         ����޸�ʱ�䣺2009-3-13
 */
public class StopReceiveDesktopHandler extends DesktopBaseHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fulong.lyvc.net.Handler#execute(com.fulong.lyvc.net.TCPChannel,
	 * com.fulong.lyvc.message.BaseMessage)
	 */
	@Override
	public void execute(TCPChannel channel, TCPMessage message) throws Exception {
		this.setConferenceManager(channel.getDomain());

		StopReceiveDesktop ssdMessage = (StopReceiveDesktop) message;

		// ֪ͨý�������
		notifyMediaServerRemoveRelation(ssdMessage.senderId, ssdMessage.receiverId);

		// ֪ͨ�Է�
		StopSendDesktop msg = new StopSendDesktop();
		msg.senderId = ssdMessage.senderId;
		msg.setConferenceId(message.getConferenceId());
		msg.receiverId = ssdMessage.receiverId;
		msg.conferenceId = ssdMessage.conferenceId;
		sendMessage(ssdMessage.receiverId, msg);
	}
}
