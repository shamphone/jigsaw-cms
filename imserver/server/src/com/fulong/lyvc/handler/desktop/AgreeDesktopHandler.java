/**
 * 
 */
package com.fulong.lyvc.handler.desktop;

import com.fulong.lyvc.TCPMessage;
import com.fulong.lyvc.TCPChannel;
import com.fulong.lyvc.message.AgreeDesktop;

/**
 * AgreeDesktopHandler
 * 
 * ��Ԧ��Ƶ����ϵͳ v3.0
 * 
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 * 
 * @author ���۷�
 * 
 *         ����޸�ʱ�䣺2009-3-13
 */
public class AgreeDesktopHandler extends DesktopBaseHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fulong.lyvc.net.Handler#execute(com.fulong.lyvc.net.TCPChannel,
	 * com.fulong.lyvc.message.BaseMessage)
	 */
	@Override
	public void execute(TCPChannel channel, TCPMessage message) throws Exception {
		this.setConferenceManager(channel.getDomain());
		
		//必须设置mediaServer中的TCPChannel的domain，否则不能弹出桌面共享的窗口
		this.getMediaServer().getChannel().setDomain(channel.getDomain());

		AgreeDesktop adMessage = (AgreeDesktop) message;
		notifyMediaServerAddRelation(adMessage.senderId, adMessage.receiverId, false, adMessage.getConferenceId());
	}
}
