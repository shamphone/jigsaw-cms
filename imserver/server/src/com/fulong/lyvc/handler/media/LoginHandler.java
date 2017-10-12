/**
 * 
 */
package com.fulong.lyvc.handler.media;

import com.fulong.lyvc.TCPMessage;
import com.fulong.lyvc.TCPChannel;
import com.fulong.lyvc.message.MediaServerLogin;

/**
 * LoginHandler
 * 
 * ��Ԧ��Ƶ����ϵͳ v3.0
 * 
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 * 
 * @author ���۷�
 * 
 *         ����޸�ʱ�䣺2009-3-13
 */
public class LoginHandler extends MediaBaseHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fulong.lyvc.net.Handler#execute(com.fulong.lyvc.net.TCPChannel,
	 * com.fulong.lyvc.message.BaseMessage)
	 */
	@Override
	public void execute(TCPChannel channel, TCPMessage message) throws Exception {
		MediaServerLogin msg = (MediaServerLogin) message;
		this.getMediaServer().setChannel(channel);
		this.getMediaServer().setIP(msg.ip);
		this.getMediaServer().setUDPPort(msg.udpPort);
		this.getMediaServer().setTCPPort(msg.tcpPort);

		logger.info("Media server login from address " + channel.getRemoteAddress());
		logger.info("Meida server ip:" + msg.ip);
		logger.info("Meida server tcp port:" + msg);
		logger.info("Meida server udp port:" + msg.udpPort);
	}
}
