/**
 * 
 */
package com.fulong.lyvc.handler.contact;

import java.util.Date;

import com.fulong.lyvc.ConferenceManager;
import com.fulong.lyvc.Message;
import com.fulong.lyvc.TCPChannel;
import com.fulong.lyvc.TCPMessage;
import com.fulong.lyvc.User;
import com.fulong.lyvc.message.Leaveword;

/**
 * LeavewordHandler
 *
 * ��Ԧ��Ƶ����ϵͳ v3.0
 *
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 *
 * @author ���۷�
 *
 * ����޸�ʱ�䣺2009-3-13
 */
public class LeavewordHandler extends ContactBaseHandler {

	/* (non-Javadoc)
	 * @see com.fulong.lyvc.net.Handler#execute(com.fulong.lyvc.net.TCPChannel, com.fulong.lyvc.message.BaseMessage)
	 */
	/**
	 * 该方法用来发送留言
	 */
	public void execute(TCPChannel channel, TCPMessage message) throws Exception {
		this.setConferenceManager(channel.getDomain());

		String senderId = ((Leaveword)message).getSenderId();
		String title = "普通留言";
		String content = ((Leaveword)message).toXML();
		Date saveDate = ((Leaveword)message).sendDate;
		
		ConferenceManager manager = this.getConferenceManager();
		Message leaveword = manager.createMessage(senderId, title, content, saveDate);
		User receiver = manager.getUser(((Leaveword)message).receiverId);
		receiver.addMessage(leaveword.getId());
		
//		User receiver = this.getConferenceManager().getUser(((Leaveword)message).receiverId);
//		this.getConferenceManager().getMessageStore(receiver).offer(message.toXML());
	}

	public boolean sendMessage(TCPChannel chan, TCPMessage pBaseMessage) {
		return super.sendMessage(chan, pBaseMessage);
	}
	
	public boolean sendMessage(User user, TCPMessage pBaseMessage) throws Exception {
		return super.sendMessage(user, pBaseMessage);
	}
}
