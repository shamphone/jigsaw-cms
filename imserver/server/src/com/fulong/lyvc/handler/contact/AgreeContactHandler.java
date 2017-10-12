/**
 * 
 */
package com.fulong.lyvc.handler.contact;

import java.util.Date;

import com.fulong.lyvc.ConferenceManager;
import com.fulong.lyvc.Group;
import com.fulong.lyvc.Message;
import com.fulong.lyvc.TCPChannel;
import com.fulong.lyvc.TCPMessage;
import com.fulong.lyvc.User;
import com.fulong.lyvc.message.AddContact;
import com.fulong.lyvc.message.AgreeContact;

/**
 * AgreeContactHandler
 * 
 * ��Ԧ��Ƶ����ϵͳ v3.0
 * 
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 * 
 * @author ���۷�
 * 
 *         ����޸�ʱ�䣺2009-3-13
 */
public class AgreeContactHandler extends ContactBaseHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fulong.lyvc.net.Handler#execute(com.fulong.lyvc.net.TCPChannel,
	 * com.fulong.lyvc.message.BaseMessage)
	 */
	@Override
	public void execute(TCPChannel channel, TCPMessage message) throws Exception {
		this.setConferenceManager(channel.getDomain());
		ConferenceManager manager = this.getConferenceManager();
		User receiver = manager.getUser(((AgreeContact) message).receiverId);
		User sender = manager.getUser(message.getSenderId());

		// ����Ѿ���jϵ�ˣ�ֱ�ӷ���
		if (receiver.getInContactGroup(sender) != null) {
			return;
		}

		String groupId = ((AgreeContact) message).groupId;
		Group group = manager.getGroup(groupId);
		//判断要添加的组是不是公共联系人组
		if(groupId.equals("0") || group == null || groupId.equals(manager.getCommonContactGroup().getId())) {
			group = receiver.getContactGroup();
		}
		else {
			Group parentGroup = group.getParentGroup();
			if(parentGroup == null || parentGroup.getId().equals(manager.getCommonContactGroup().getId()))
				group = receiver.getContactGroup();
		}
		
		
		group.addMember(sender);
//		receiver.getContactGroup().addMember(sender);
		sender.getContactGroup().addMember(receiver);

		// �������jϵ�˵����������û�
		TCPChannel receiverChannel = server.getChannel(receiver);
		if (receiverChannel != null) {
			User contact = manager.getUser(message.getSenderId());
			AddContact msg = new AddContact();
			msg.isCommon = false;
			msg.contactId = message.getSenderId();
			msg.groupId = group.getId();
			msg.name = contact.getAccountName();
			msg.email = contact.getEmail();
			msg.firstName = contact.getFirstName();
			msg.lastName = contact.getLastName();
			if (getChannel(message.getSenderId()) == null) {
				msg.status = CONTACT_OFFLINE;
			} else {
				msg.status = CONTACT_ONLINE;
			}
			sendMessage(receiverChannel, msg);
			sendMessage(receiverChannel, message);
		} else {
			String senderId = ((AgreeContact) message).getSenderId();
			String title = "agree contact";
			String content = ((AgreeContact) message).toXML();
			Date saveDate = new Date(System.currentTimeMillis());

			Message agreeContact = manager.createMessage(senderId, title, content, saveDate);
			receiver = manager.getUser(((AgreeContact) message).receiverId);
			receiver.addMessage(agreeContact.getId());

			// manager.getMessageStore(receiver).offer(message.toXML());
		}

		// �������jϵ�˵�ͬ����ӵ��û�
		TCPChannel senderChannel = server.getChannel(sender);
		if (sender != null) {
			User contact = manager.getUser(((AgreeContact) message).receiverId);
			AddContact msg = new AddContact();
			msg.isCommon = false;
			msg.contactId = ((AgreeContact) message).receiverId;
			msg.groupId = sender.getContactGroup().getId();
			msg.name = contact.getAccountName();
			msg.email = contact.getEmail();
			msg.firstName = contact.getFirstName();
			msg.lastName = contact.getLastName();
			if (getChannel(((AgreeContact) message).receiverId) == null) {
				msg.status = CONTACT_OFFLINE;
			} else {
				msg.status = CONTACT_ONLINE;
			}
			sendMessage(senderChannel, msg);
		}
	}
}
