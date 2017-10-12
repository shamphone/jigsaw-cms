/**
 * 
 */
package com.fulong.lyvc.handler.media;

import com.fulong.lyvc.TCPMessage;
import com.fulong.lyvc.TCPChannel;
import com.fulong.lyvc.message.AgreeVideo;
import com.fulong.lyvc.message.NotifyMediaServerAddVideo;

/**
 * AgreeVideoHandler
 *
 * ��Ԧ��Ƶ����ϵͳ v3.0
 *
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 *
 * @author ���۷�
 *
 * ����޸�ʱ�䣺2009-3-13
 */
public class AgreeVideoHandler extends MediaBaseHandler {

	/* (non-Javadoc)
	 * @see com.fulong.lyvc.net.Handler#execute(com.fulong.lyvc.net.TCPChannel, com.fulong.lyvc.message.BaseMessage)
	 */
	@Override
	public void execute(TCPChannel channel, TCPMessage message) throws Exception {
		this.setConferenceManager(channel.getDomain());

		AgreeVideo mess = (AgreeVideo)message;
        //֪ͨý�������
        NotifyMediaServerAddVideo msgToSend = new NotifyMediaServerAddVideo();
        msgToSend.fromUserId = mess.videoSenderId;
        msgToSend.toUserId = mess.receiverId;
        sendMessage(this.getMediaServer().getChannel(), msgToSend);

        //֪ͨ�Է�
        sendMessage(mess.receiverId, message);
	}

}
