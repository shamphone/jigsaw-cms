/**
 * 
 */
package com.fulong.lyvc.handler.contact;

import com.fulong.lyvc.TCPChannel;
import com.fulong.lyvc.TCPMessage;
import com.fulong.lyvc.message.ChangeGroupName;

/**
 * ChangeGroupNameHandler
 * 
 * ��Ԧ��Ƶ����ϵͳ v3.0
 * 
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 * 
 * @author ���۷�
 * 
 *         ����޸�ʱ�䣺2009-3-13
 */
public class ChangeGroupNameHandler extends ContactBaseHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fulong.lyvc.net.Handler#execute(com.fulong.lyvc.net.TCPChannel,
	 * com.fulong.lyvc.message.BaseMessage)
	 */
	@Override
	public void execute(TCPChannel channel, TCPMessage message) throws Exception {
		this.setConferenceManager(channel.getDomain());
		this.getConferenceManager().getGroup(((ChangeGroupName) message).groupId).setName(((ChangeGroupName) message).newName);
	}

	public boolean sendMessage(TCPChannel chan, TCPMessage pBaseMessage) {
		return super.sendMessage(chan, pBaseMessage);
	}
}
