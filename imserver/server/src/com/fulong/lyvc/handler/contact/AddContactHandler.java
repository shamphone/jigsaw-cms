package com.fulong.lyvc.handler.contact;

import com.fulong.lyvc.ConferenceManager;
import com.fulong.lyvc.Group;
import com.fulong.lyvc.TCPChannel;
import com.fulong.lyvc.TCPMessage;
import com.fulong.lyvc.User;
import com.fulong.lyvc.message.AddContact;
import com.fulong.lyvc.message.AgreeContact;
import com.fulong.lyvc.message.UpdateContactStatus;

/**
 * 
 * AddContactHandler
 * 
 * 龙驭视频会议系统 v3.0
 * 
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 * 
 * @author 李雄锋
 * 
 * 最后修改时间：2010-9-8
 */

public class AddContactHandler extends ContactBaseHandler {

	public void execute(TCPChannel channel, TCPMessage message) throws Exception {
		this.setConferenceManager(channel.getDomain());
		ConferenceManager manager = this.getConferenceManager();
		
		User user = manager.getUser(message.getSenderId());
		
		String contactId = ((AddContact) message).contactId;
		User contact = manager.getUser(contactId);

		// ����Ѿ���jϵ�ˣ�ֱ�ӷ���
		if (channel.getUser().getInContactGroup(contact) != null) {
			return;
		}
		
		Group group = manager.getGroup(((AddContact) message).groupId);
		group.addMember(contact);
		contact.getContactGroup().addMember(channel.getUser());

		TCPChannel contactChannel = server.getChannel(contact);
		
		AddContact msgAddContact = new AddContact();
		msgAddContact.contactId = message.getSenderId();
		msgAddContact.email = user.getEmail();
		msgAddContact.firstName = user.getFirstName();
		msgAddContact.groupId = contact.getContactGroup().getId();
		msgAddContact.isCommon = false;
		msgAddContact.lastName = user.getLastName();
		msgAddContact.name = user.getAccountName();
		if (contactChannel == null) {
			msgAddContact.status = CONTACT_OFFLINE;
		} else {
			msgAddContact.status = CONTACT_ONLINE;
		}
		sendMessage(contactChannel, msgAddContact);
		
		if(contactChannel != null) {
			AgreeContact msgAgreeContact = new AgreeContact();
			msgAgreeContact.name = user.getLastName() + user.getFirstName();
			sendMessage(contactChannel, msgAgreeContact);
			
			UpdateContactStatus msgUpdateStatus = new UpdateContactStatus();
			msgUpdateStatus.bIsCommon = false;
			msgUpdateStatus.contactId = user.getId();
			msgUpdateStatus.status = CONTACT_ONLINE;
			
			sendMessage(channel, msgUpdateStatus);
			sendMessage(contactChannel, msgUpdateStatus);
		}
	}

	public boolean sendMessage(TCPChannel chan, TCPMessage pBaseMessage) {
		return super.sendMessage(chan, pBaseMessage);
	}
}
