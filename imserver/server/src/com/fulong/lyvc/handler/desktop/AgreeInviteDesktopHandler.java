/**
 * 
 */
package com.fulong.lyvc.handler.desktop;

import com.fulong.lyvc.TCPMessage;
import com.fulong.lyvc.TCPChannel;
import com.fulong.lyvc.message.AgreeInviteDesktop;

/**
 * AgreeInviteDesktopHandler
 *
 * ��Ԧ��Ƶ����ϵͳ v3.0
 *
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 *
 * @author ���۷�
 *
 * ����޸�ʱ�䣺2009-3-13
 */
public class AgreeInviteDesktopHandler extends DesktopBaseHandler {

	/* (non-Javadoc)
	 * @see com.fulong.lyvc.net.Handler#execute(com.fulong.lyvc.net.TCPChannel, com.fulong.lyvc.message.BaseMessage)
	 */
	@Override
	public void execute(TCPChannel channel, TCPMessage message) throws Exception {
		this.setConferenceManager(channel.getDomain());
		
		//必须设置mediaServer中的TCPChannel的domain，否则不能弹出桌面共享的窗口
		this.getMediaServer().getChannel().setDomain(channel.getDomain());
		
		AgreeInviteDesktop aidMessage = (AgreeInviteDesktop)message;
		notifyMediaServerAddRelation(aidMessage.receiverId, aidMessage.senderId, true, aidMessage.getConferenceId());
	}
}
