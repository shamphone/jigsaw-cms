/**
 * 
 */
package com.fulong.lyvc.handler.contact;

import com.fulong.lyvc.Group;
import com.fulong.lyvc.TCPChannel;
import com.fulong.lyvc.TCPMessage;
import com.fulong.lyvc.User;
import com.fulong.lyvc.message.RemoveContact;

public class RemoveContactHandler extends ContactBaseHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fulong.lyvc.net.Handler#execute(com.fulong.lyvc.net.TCPChannel,
	 * com.fulong.lyvc.message.BaseMessage)
	 */
	@Override
	public void execute(TCPChannel channel, TCPMessage message) throws Exception {
		this.setConferenceManager(channel.getDomain());

		User contact = this.getConferenceManager().getUser(((RemoveContact) message).contactId);
		User sender = this.getConferenceManager().getUser(message.getSenderId());
		Group contactGroup = contact.getInContactGroup(sender);
		Group senderGroup = sender.getInContactGroup(contact);
		
		if(senderGroup != null) {
			RemoveContact msg = new RemoveContact();
			msg.contactId = message.getSenderId();
			msg.groupId = senderGroup.getId();
			sendMessage(((RemoveContact) message).contactId, msg);
		}
		
		if ((sender != null) && (contactGroup != null))
			contactGroup.removeMember(sender);
		
		if ((contact != null) && (senderGroup != null))
			senderGroup.removeMember(contact);
		
//		this.removeMember(((RemoveContact) message).contactId, ((RemoveContact) message).groupId);
//		this.removeMember(message.getSenderId(), contactGroup.getId());
	}

	public boolean sendMessage(TCPChannel chan, TCPMessage pBaseMessage) {
		return super.sendMessage(chan, pBaseMessage);
	}
	
}
