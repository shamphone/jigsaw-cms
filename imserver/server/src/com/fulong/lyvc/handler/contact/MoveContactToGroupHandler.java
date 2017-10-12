/**
 * 
 */
package com.fulong.lyvc.handler.contact;

import com.fulong.lyvc.TCPMessage;
import com.fulong.lyvc.TCPChannel;
import com.fulong.lyvc.User;
import com.fulong.lyvc.message.MoveContactToGroup;


/**
 * MoveContactToGroupHandler
 * 
 * ��Ԧ��Ƶ����ϵͳ v3.0
 * 
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 * 
 * @author ���۷�
 * 
 *         ����޸�ʱ�䣺2009-3-13
 */
public class MoveContactToGroupHandler extends ContactBaseHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fulong.lyvc.net.Handler#execute(com.fulong.lyvc.net.TCPChannel,
	 * com.fulong.lyvc.message.BaseMessage)
	 */
	@Override
	public void execute(TCPChannel channel, TCPMessage message) throws Exception {
		this.setConferenceManager(channel.getDomain());

		String contactId = ((MoveContactToGroup) message).contactId;
		String oldGroupId = ((MoveContactToGroup) message).oldGroupId;
		String newGroupId = ((MoveContactToGroup) message).newGroupId;
		User contact = this.getConferenceManager().getUser(contactId);
		this.getConferenceManager().getGroup(oldGroupId).removeMember(contact);
		this.getConferenceManager().getGroup(newGroupId).addMember(contact);

		sendMessage(message.getSenderId(), message);
	}
	
	public boolean sendMessage(TCPChannel chan, TCPMessage pBaseMessage) {
		return super.sendMessage(chan, pBaseMessage);
	}
}
