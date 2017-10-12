package com.fulong.lyvc.handler.server;

import com.fulong.lyvc.TCPMessage;
import com.fulong.lyvc.TCPChannel;
/**
 * 
 * KeepAliveHandler
 *
 * ��Ԧ��Ƶ����ϵͳ v3.0
 *
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 *
 * @author ���۷�
 *
 * ����޸�ʱ�䣺2009-3-13
 */
public class KeepAliveHandler extends ServerBaseHandler {

	@Override
	public void execute(TCPChannel channel, TCPMessage message) throws Exception {
		this.setConferenceManager(channel.getDomain());

		sendMessage(channel, message);
	}

}
