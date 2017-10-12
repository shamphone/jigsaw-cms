/**
 * 
 */
package com.fulong.lyvc.handler.contact;

import com.fulong.lyvc.TCPChannel;
import com.fulong.lyvc.TCPMessage;

/**
 * CopyContactHandler
 * 
 * ��Ԧ��Ƶ����ϵͳ v3.0
 * 
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 * 
 * @author ���۷�
 * 
 *         ����޸�ʱ�䣺2009-3-13
 */
public class CopyContactHandler extends ContactBaseHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fulong.lyvc.net.Handler#execute(com.fulong.lyvc.net.TCPChannel,
	 * com.fulong.lyvc.message.BaseMessage)
	 */
	@Override
	public void execute(TCPChannel channel, TCPMessage message) throws Exception {
/*		if (message.getSenderId() != this.server.getContactAdminId())
			return;
		Group source = this.getConferenceManager().getUser(this.server.getContactAdminId()).getContactGroup();		
		if (((CopyContact) message).bIsGroupId) {
			for(Group child:source.children()){
				if (child.getId() == ((CopyContact) message).receiverId) {
					for(User user:child.users()) {
						copyContactsOfContactAdmin(source,user.getContactGroup());
					}
					break;
				}
			}
		} else {
			long receiverId = ((CopyContact) message).receiverId;
			if (receiverId == 0) {
				for (int i = 0; i < groups.length; i++) {
					Long[] memberIds = (Long[]) groups[i].memberIds
							.toArray(new Long[0]);
					for (int ii = 0; ii < memberIds.length; ii++) {
						copyContactsOfContactAdmin(groups,
								contactAdminDefaultGroupId, memberIds[ii]
										.longValue(), groups[i].id,
								groups[i].name);
					}
				}
			} else {
				for (int i = 0; i < groups.length; i++) {
					Long[] memberIds = (Long[]) groups[i].memberIds
							.toArray(new Long[0]);
					for (int ii = 0; ii < memberIds.length; ii++) {
						if (memberIds[ii].longValue() == receiverId) {
							copyContactsOfContactAdmin(groups,
									contactAdminDefaultGroupId, receiverId,
									groups[i].id, groups[i].name);
							break;
						}
					}
				}
			}
		}

	}

	private void copyContactsOfContactAdmin(Group source, Group dest) throws SQLException// ���û����ڵ�����
	{
		System.out.println("����jϵ�˵�" + dest.getCreator().getId());

		long receiverDefaultGroupId = getConferenceManager().getUser(receiverId).getContactGroup();
				.getDefaultGroupId(receiverId);
		ContactGroup[] receiverGroups = getConferenceManager().getGroups(
				receiverId, true);
		ClientUser peer = server.getUser(receiverId);
		for (int i = 0; i < contactAdminGroups.length; i++) {
			long groupId = 0;
			// ����Ա��Ĭ���鲻����
			if (contactAdminGroups[i].id != contactAdminDefaultGroupId) {
				// ����к͹���Ա����ͬ����飬������Ա���µ�jϵ�˼ӵ��û�������
				// �����Ƚ�b���ټ�jϵ��
				for (int ii = 0; ii < receiverGroups.length; ii++) {
					if (receiverGroups[ii].name
							.equalsIgnoreCase(contactAdminGroups[i].name)) {
						groupId = receiverGroups[ii].id;
						break;
					}
				}
				if (groupId == 0) {
					groupId = getConferenceManager().addGroup(receiverId,
							contactAdminGroups[i].name);
					if (peer != null) {
						AddGroup msgAddGroup = new AddGroup();
						msgAddGroup.isCommon = false;
						msgAddGroup.groupId = groupId;
						msgAddGroup.parentGroupId = receiverDefaultGroupId;
						msgAddGroup.name = contactAdminGroups[i].name;
						sendMessage(peer.getChannel(),
								msgAddGroup);
					}
				}
			} else {
				groupId = receiverDefaultGroupId;
			}
			Long[] memberIds = (Long[]) contactAdminGroups[i].memberIds
					.toArray(new Long[0]);
			for (int j = 0; j < memberIds.length; j++) {
				if (receiverId == memberIds[j].longValue())
					continue;
				// ����Ѿ���jϵ��,���ٸ���
				boolean bIsContact = false;
				for (int k = 0; k < receiverGroups.length; k++) {
					for (int kk = 0; kk < receiverGroups[k].memberIds.size(); kk++) {
						if (((Long) receiverGroups[k].memberIds.get(kk))
								.longValue() == memberIds[j].longValue()) {
							bIsContact = true;
							break;
						}
					}
					if (bIsContact) {
						break;
					}
				}
				if (bIsContact)
					continue;
				// if( processor.getContactSession().isContact(receiverId, memberIds[j])
				// )
				// continue;
				// �ӵ��Լ���jϵ������
				getConferenceManager().addGroupMember(memberIds[j].longValue(),
						groupId);

				// �Է����Լ�Ϊjϵ�˵��ʵ�������
				ClientUser onlineContact = server.getUser(memberIds[j]
						.longValue());
				long groupId2 = getConferenceManager()
						.getDefaultGroupId(memberIds[j].longValue());
				// �ҳ��Լ��ڹ���Ա���飬�Է��ӵ��������Ա��ͬ������У����û�о��ȴ���
				if (receiverInContactAdminGroupId != contactAdminDefaultGroupId) {
					ContactGroup[] groups2 = getConferenceManager().getGroups(
							memberIds[j].longValue(), false);
					boolean bFound = false;
					for (int jj = 0; jj < groups2.length; jj++) {
						if (receiverInContactAdminGroupName
								.equalsIgnoreCase(groups2[jj].name)) {
							groupId2 = groups2[jj].id;
							bFound = true;
							break;
						}
					}
					if (!bFound) {
						long receiverDefaultGroupId2 = groupId2;
						groupId2 = getConferenceManager().addGroup(memberIds[j]
								.longValue(), receiverInContactAdminGroupName);
						if (onlineContact != null) {
							AddGroup msgAddGroup = new AddGroup();
							msgAddGroup.isCommon = false;
							msgAddGroup.groupId = groupId2;
							msgAddGroup.parentGroupId = receiverDefaultGroupId2;
							msgAddGroup.name = receiverInContactAdminGroupName;
							sendMessage(onlineContact
									.getChannel(), msgAddGroup);
						}
					}
				}
				getConferenceManager().addGroupMember(receiverId, groupId2);
				// �������jϵ����Ϣ��˫����������ߵĻ�
				if (peer != null) {
					User contact = getConferenceManager().getUser(memberIds[j]
							.longValue());
					AddContact msgAddContact = new AddContact();
					msgAddContact.isCommon = false;
					msgAddContact.contactId = contact.getId();
					msgAddContact.groupId = groupId;
					msgAddContact.name = contact.getAccountName();
					msgAddContact.email = contact.getEmail();
					msgAddContact.firstName = contact.getFirstName();
					msgAddContact.lastName = contact.getLastName();
					if (onlineContact != null)
						msgAddContact.status = this.CONTACT_ONLINE;
					else
						msgAddContact.status = this.CONTACT_OFFLINE;
					sendMessage(peer.getChannel(),
							msgAddContact);
				}
				if (onlineContact != null) {
					User contact = getConferenceManager().getUser(receiverId);
					AddContact msgAddContact = new AddContact();
					msgAddContact.isCommon = false;
					msgAddContact.contactId = receiverId;
					msgAddContact.groupId = groupId2;
					msgAddContact.name = contact.getAccountName();
					msgAddContact.email = contact.getEmail();
					msgAddContact.firstName = contact.getFirstName();
					msgAddContact.lastName = contact.getLastName();
					if (peer != null)
						msgAddContact.status = this.CONTACT_ONLINE;
					else
						msgAddContact.status = this.CONTACT_OFFLINE;
					sendMessage(onlineContact.getChannel(),
							msgAddContact);
				}
			}
		}*/
	}
}
