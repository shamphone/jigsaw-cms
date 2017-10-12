package com.fulong.lyvc.handler.desktop;

import com.fulong.lyvc.TCPMessage;
import com.fulong.lyvc.TCPChannel;
import com.fulong.lyvc.message.DesktopClientKeyEvent;

/**
 * 
 * DesktopClientKeyEventHandler
 * 
 * ��Ԧ��Ƶ����ϵͳ v3.0
 * 
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 * 
 * @author ���۷�
 * 
 *         ����޸�ʱ�䣺2009-3-13
 */
public class DesktopClientKeyEventHandler extends DesktopBaseHandler {

	@Override
	public void execute(TCPChannel channel, TCPMessage message) throws Exception {
		this.setConferenceManager(channel.getDomain());

		sendMessage(((DesktopClientKeyEvent) message).receiverId, message);
	}

}
