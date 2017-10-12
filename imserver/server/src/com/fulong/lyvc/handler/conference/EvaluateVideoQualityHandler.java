/**
 * 
 */
package com.fulong.lyvc.handler.conference;

import com.fulong.lyvc.TCPMessage;
import com.fulong.lyvc.TCPChannel;
import com.fulong.lyvc.message.EvaluateVideoQuality;

/**
 * EvaluateVideoQualityHandler
 *
 * ��Ԧ��Ƶ����ϵͳ v3.0
 *
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 *
 * @author ���۷�
 *
 * ����޸�ʱ�䣺2009-3-13
 */
public class EvaluateVideoQualityHandler extends ConferenceBaseHandler {

	/* (non-Javadoc)
	 * @see com.fulong.lyvc.net.Handler#execute(com.fulong.lyvc.net.TCPChannel, com.fulong.lyvc.message.BaseMessage)
	 */
	@Override
	public void execute(TCPChannel channel, TCPMessage message) throws Exception {
		this.setConferenceManager(channel.getDomain());

		this.sendMessage(((EvaluateVideoQuality)message).receiverId, message);
	}

}
