/**
 * 
 */
package com.fulong.lyvc.handler.server;

import java.util.Date;

import com.fulong.lyvc.Conference;
import com.fulong.lyvc.ConferenceManager;
import com.fulong.lyvc.Message;
import com.fulong.lyvc.MessageHandler;
import com.fulong.lyvc.TCPChannel;
import com.fulong.lyvc.User;
import com.fulong.lyvc.data.UserData;
import com.fulong.lyvc.message.BaseMessage;
import com.fulong.lyvc.message.ConferenceNotice;
import com.fulong.lyvc.message.DeleteConference;
import com.fulong.lyvc.message.ModifyConference;
import com.fulong.lyvc.message.SystemMessage;

/**
 * ConferenceLibraryMessageHandler
 *
 * ��Ԧ��Ƶ����ϵͳ v3.0
 *
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 *
 * @author ���۷�
 *
 * ����޸�ʱ�䣺2009-3-13
 */
public class ConferenceLibraryMessageHandler extends MessageHandler {

	public void execute(String messageBody) throws Exception {
		String messageType = BaseMessage.getStringBetweenTag(messageBody, "messagetype");
		if (messageType.equals("add"))
			this.webAddConference(BaseMessage.getStringBetweenTag(messageBody, "conferenceid"));
		else if (messageType.equals("modify"))
			this.webModifyConference(BaseMessage.getStringBetweenTag(messageBody, "conferenceid"));
		else if (messageType.equals("delete"))
			this.webDeleteConference(BaseMessage.getStringBetweenTag(messageBody, "conferenceid"));
		else if (messageType.equals("addMember"))
			this.webAddMemberToConference(BaseMessage.getStringBetweenTag(messageBody, "conferenceid"), BaseMessage.getStringBetweenTag(messageBody, "memberid"));
		else if (messageType.equals("removeMember"))
			this.webRemoveMemberFromConference(BaseMessage.getStringBetweenTag(messageBody, "conferenceid"), BaseMessage.getStringBetweenTag(messageBody, "memberid"));
		else if (messageType.equals("sendConferenceNotice"))
			this.webSendConferenceNotice(BaseMessage.getStringBetweenTag(messageBody, "conferenceid"), BaseMessage.getStringBetweenTag(messageBody, "noticeTitle"), BaseMessage.getStringBetweenTag(messageBody, "noticeContent"));
		else if (messageType.equals("sendBulletin"))
			this.webSendBulletin(BaseMessage.getStringBetweenTag(messageBody, "bulletinTitle"), BaseMessage.getStringBetweenTag(messageBody, "bulletinContent"));
		else if (messageType.equals("removeUser"))
			this.webRemoveUser(BaseMessage.getStringBetweenTag(messageBody, "userid"));
		else if (messageType.equals("addUser")) {
			UserData userData = new UserData();
			userData.setId(Long.parseLong(BaseMessage.getStringBetweenTag(messageBody, "userid")));
			userData.setAccountName(BaseMessage.getStringBetweenTag(messageBody, "accountname"));
			userData.setPassword(BaseMessage.getStringBetweenTag(messageBody,"password"));
			userData.setFirstName(BaseMessage.getStringBetweenTag(messageBody,"firstname"));
			userData.setLastName(BaseMessage.getStringBetweenTag(messageBody,"lastname"));
			userData.setEmail(BaseMessage.getStringBetweenTag(messageBody,"email"));
			webAddUser(userData);
		} else if (messageType.equals("setUserPassword"))
			webSetUserPassword(BaseMessage.getStringBetweenTag(messageBody, "userid"), BaseMessage.getStringBetweenTag(messageBody, "password"));
		else if (messageType.equals("addGroup"))
			webAddGroup(BaseMessage.getStringBetweenTag(messageBody, "parentgroupid"), BaseMessage.getStringBetweenTag(messageBody, "groupid"), BaseMessage.getStringBetweenTag(messageBody, "groupname"));
		else if (messageType.equals("modifyGroup"))
			webModifyGroup(BaseMessage.getStringBetweenTag(messageBody, "groupid"), BaseMessage.getStringBetweenTag(messageBody, "groupname"));
		else if (messageType.equals("removeGroup"))
			webRemoveGroup(BaseMessage.getStringBetweenTag(messageBody, "groupid"));
		else if (messageType.equals("addGroupMember"))
			webAddGroupMember(BaseMessage.getStringBetweenTag(messageBody, "groupid"), BaseMessage.getStringBetweenTag(messageBody, "userid"));
		else if (messageType.equals("delGroupMember"))
			webDelGroupMember(BaseMessage.getStringBetweenTag(messageBody, "groupid"), BaseMessage.getStringBetweenTag(messageBody, "userid"));
		else if (messageType.equals("moveGroupMember"))
			webMoveGroupMember(BaseMessage.getStringBetweenTag(messageBody, "fromgroupid"),BaseMessage.getStringBetweenTag(messageBody, "togroupid"), Long.parseLong(BaseMessage.getStringBetweenTag(messageBody, "memberid")));
		else
			throw new Exception("Unknown message from ConferenceLibrary");
	}

	// �����û��ڻ������ϵͳ���´����Ļ���
	public void webAddConference(String conferenceId) throws Exception {
		logger.info("WebAddConference:" + conferenceId);
		String id = conferenceId;
		Conference con = getConferenceManager().getConference(id);
		if (con == null) {
			logger.warn("Can't find conference " + conferenceId + " in database.");
			return;
		}

		// Oops! The conference has ended!
		if (con.getEndTime().before(new java.util.Date())) {
			logger.warn("ignore ended conference:" + conferenceId);
			return;
		}

		// ������������
//		this.server.getConferenceTable().addDBConference(con);
	}

	public void webModifyConference(String conferenceId) throws Exception {

		logger.info("WebModifyConference:" + conferenceId);
		String id = conferenceId;
		Conference con = getConferenceManager().getConference(id);
		if (con == null) {
			logger.warn("Can't find conference " + conferenceId + " in database.");
			return;
		}

		// ���ܶ��Ѿ���ʼ�Ļ�������޸�
		if (con.isHolding()) {
			logger.warn("Can't modify started conference " + conferenceId);
			return;
		}

		// ����Ŀ�ʼʱ�䲻���ڵ�ǰʱ��֮ǰ
		if (con.getStartTime().before(new java.util.Date())) {
			logger.warn("Can't modify start time of conference " + conferenceId + " before now");
			return;
		}

		// ֪ͨ����ӵ��������Ŀͻ��ˣ������Ѿ����
		ModifyConference message = new ModifyConference();
		message.conDesc = con.getDesc();
		message.conId = id;
		message.conModelId = con.getMode().getId();
		message.conName = con.getTitle();
		message.endTime = con.getEndTime();
		message.startTime = con.getStartTime();
		for(User user:con.getMembers()) {
			TCPChannel channel=this.getConferenceServer().getChannel(user);
			if (channel != null) {
				sendMessage(channel, message);
			}
		}
	}

	public void webDeleteConference(String conferenceId) throws Exception {

		logger.info("WebDeteleConference:" + conferenceId);
		String id = conferenceId;

		// If the conference does not exist, it must have ended in the server
		// In fact, it is unlikely happen, because the conferencelibrary won't
		// delete started conference.
		// Leave this check here for safety
		Conference conference = this.getConferenceManager().getConference(id);
		if (conference == null) {
			return;
		}

		// �������ڽ��еĻ���,����ⲻ����ɾ��,��˲�Ӧ���յ������Ϣ
		// Leave this check here for safety
		if (conference.isHolding()) {
			logger.error("Receive delete conference when conference is running.");
			return;
		}

		this.getConferenceManager().delete(conference);
	}

	public void webAddMemberToConference(String conferenceId, String memberId) throws Exception {
		logger.info("WebAddMemberToConference:" + conferenceId + "," + memberId);

		Conference con = getConferenceManager().getConference(""+conferenceId);
		if (con == null) {
			logger.error("Receive WebAddMemberToConference, but can not find it in database.");
			
			return;
		}

		TCPChannel channel =this.getChannel(memberId);
		if (channel != null) {
			sendConferenceToUser(channel, con);
		}
	}

	public void webRemoveMemberFromConference(String conferenceId, String memberId) throws Exception {
		// After we add many limitation in conference library, this case will
		// only arise for
		// coming conference, not for started or ended conference.
		logger.info("WebRemoveMemberFromConference:" + conferenceId + ","+ memberId);

		Conference conference = this.getConferenceManager().getConference(conferenceId);
		if (conference == null) {
			return;
		}

		DeleteConference msgToSend = new DeleteConference();
		msgToSend.conId = conferenceId;
		sendMessage(memberId, msgToSend);
	}

	public void webRemoveUser(String userId) throws Exception {
		logger.info("WebRemoveUser:" + userId);
		//
		// server.contactProcess.removeUser(userId); //������ܻ������⣬ԭ4�ƺ�û��ʵ�����,lixf��
//		Map confTable = this.server.getConferenceTable().getConTable();
		getConferenceManager().delete(getConferenceManager().getUser(userId));
		// Just kick user
		TCPChannel peer = getChannel(userId);
		if (peer != null) {
			peer.close();
		}
	}

	public void webAddUser(UserData userData) throws Exception {
//		getConferenceManager().insert(userData);
		logger.info("WebAddUser:" + userData.getId());
	}

	public void webSetUserPassword(String userId, String password)
			throws Exception {
//		getConferenceManager().setUserPassword(userId, password);
		logger.info("WebSetUserPassword: userid:" + userId);
	}

	public void webAddGroup(String parentGroupId, String groupId, String groupName)
			throws Exception {
//		getConferenceManager().addGroup(parentGroupId, groupId, groupName);
		logger.info("WebAddGroup: name:" + groupName + " id:" + groupId + " parentId:" + parentGroupId);
	}

	public void webModifyGroup(String groupId, String groupName) throws Exception {
//		getConferenceManager().modifyGroup(groupId, groupName, "");
		logger.info("WebModifyGroup: group:" + groupId + " groupName:" + groupName);
	}

	public void webRemoveGroup(String groupId) throws Exception {
		getConferenceManager().delete(getConferenceManager().getGroup(groupId));
		logger.info("WebRemoveGroup: " + groupId);
	}

	public void webAddGroupMember(String groupId, String userId) throws Exception {
//		getConferenceManager().insert(groupId, userId);
		logger.info("WebAddGroupMember: group:" + groupId + " user:" + userId);
	}

	public void webDelGroupMember(String groupId, String memberId) throws Exception {
//		getConferenceManager().delete(groupId, memberId);
		logger.info("WebDelGroupMember: group:" + groupId + " memberId:" + memberId);
	}

	public void webMoveGroupMember(String fromGroupId, String toGroupId,
			long memberId) throws Exception {
		if (fromGroupId.equals(toGroupId)) {
			return;
		}
//		getConferenceManager().moveGroupMember(fromGroupId, toGroupId,memberId);
		logger.info("WebMoveGroupMember: fromgroup:" + fromGroupId + " togroup:" + toGroupId + " memberId:" + memberId);
	}

	public void webSendConferenceNotice(String conferenceId, String noticeTitle, String noticeContent) throws Exception {
		logger.info("WebSendConferenceNotice:" + conferenceId);

		Conference con = getConferenceManager().getConference(conferenceId);
		if (con == null) {
			logger.warn("Can't find conference " + conferenceId + " in database.");
			return;
		}

		// ֪ͨ���л����Ա
		ConferenceNotice message = new ConferenceNotice();
		message.conferenceId = conferenceId;
		message.title = noticeTitle;
		message.content = noticeContent;
		message.sendDate = new Date(System.currentTimeMillis());
		
		for(User user:con.getMembers()) {
			if (!sendMessage(user, message)) {
				String senderId = message.getSenderId();
				String title = "会议通知";
				String content = message.toXML();
				Date saveDate = message.sendDate;
				
				ConferenceManager manager = this.getConferenceManager();
				Message leaveword = manager.createMessage(senderId, title, content, saveDate);
				User receiver = manager.getUser(message.receiverId);
				receiver.addMessage(leaveword.getId());
				
//				String userId = user.getId();
//				this.getConferenceManager().getMessageStore(this.getConferenceManager().getUser(userId)).offer(message.toXML());				
			}
		}
	}

	public void webSendBulletin(String bulletinTitle, String bulletinContent) throws Exception {

		SystemMessage message = new SystemMessage();
		message.title = bulletinTitle;
		message.content = bulletinContent;
		message.sendDate = new Date(System.currentTimeMillis());

		for (User user:getConferenceManager().getUsers()) {
			//离线用户保存留言（系统公告）
			if (!sendMessage(user, message)) {
				String senderId = message.getSenderId();
				String title = "系统公告";
				String content = message.toXML();
				Date saveDate = message.sendDate;
				
				ConferenceManager manager = this.getConferenceManager();
				Message bulletin = manager.createMessage(senderId, title, content, saveDate);
				User receiver = manager.getUser(message.receiverId);
				receiver.addMessage(bulletin.getId());
				
//				this.getConferenceManager().getMessageStore(user).offer(message.toXML());	
			}
		}
	}
}
