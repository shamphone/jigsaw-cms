/**
 * 
 */
package com.fulong.lyvc.handler.media;

import com.fulong.lyvc.TCPMessage;
import com.fulong.lyvc.TCPChannel;

/**
 * GetMediaServerAddressHandler
 *
 * ��Ԧ��Ƶ����ϵͳ v3.0
 *
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 *
 * @author ���۷�
 *
 * ����޸�ʱ�䣺2009-3-13
 */
public class GetMediaServerAddressHandler extends MediaBaseHandler {

	/* (non-Javadoc)
	 * @see com.fulong.lyvc.net.Handler#execute(com.fulong.lyvc.net.TCPChannel, com.fulong.lyvc.message.BaseMessage)
	 */
	@Override
	public void execute(TCPChannel channel, TCPMessage message) throws Exception {
		/*
        TCPChannel msChan = server.mediaServerProcess.getMediaServerChannel();
        MediaServerAddress msgToSend = new MediaServerAddress();
        if (msChan != null) {
            msgToSend.host = this.mediaServerIP;
            msgToSend.udpport = this.mediaServerUDPPort;
            msgToSend.tcpport = this.mediaServerTCPPort;
            MessageProcessor.sendMessage(channel, msgToSend);
        }
*/

	}

}
