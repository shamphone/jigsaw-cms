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
import com.fulong.lyvc.message.ApplyContact;

/**
 * ApplyContactHandler
 * 
 * ��Ԧ��Ƶ����ϵͳ v3.0
 * 
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 * 
 * @author ���۷�
 * 
 *         ����޸�ʱ�䣺2009-3-13
 */
public class ApplyContactHandler extends ContactBaseHandler {

	public void execute(TCPChannel channel, TCPMessage message) throws Exception {
		this.setConferenceManager(channel.getDomain());
		ConferenceManager manager = this.getConferenceManager();
		
		String senderId = message.getSenderId();
		String receiverId = ((ApplyContact) message).receiverId;
		User receiver = manager.getUser(receiverId);
		Group group = manager.getGroup(manager.getLeaserId());
		
		//判断发送者是不是公共联系人根组下的用户，以及是不是管理员
		//公共联系人根组下的用户不是公共联系人列表中的成员，可以理解为是未经授权的用户
		if(!senderId.equals(group.getId()) && !receiverId.equals(group.getId())) {
			if (!sendMessage(receiverId, message)) {
				String title = "apply contact";
				String content = ((ApplyContact)message).toXML();
				Date saveDate = new Date(System.currentTimeMillis());
				
				Message applyContact = manager.createMessage(senderId, title, content, saveDate);
				receiver.addMessage(applyContact.getId());
			}
		}
	}

}
