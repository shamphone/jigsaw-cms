/**
 * 
 */
package com.fulong.lyvc.handler.contact;

import com.fulong.longcon.repository.Node;
import com.fulong.lyvc.ConferenceManager;
import com.fulong.lyvc.Group;
import com.fulong.lyvc.TCPChannel;
import com.fulong.lyvc.TCPMessage;
import com.fulong.lyvc.User;
import com.fulong.lyvc.message.SearchContact;
import com.fulong.lyvc.message.SearchContactResult;
import com.fulong.lyvc.message.UserNotFoundByEmail;

/**
 * SearchContactHandler
 * 
 * ��Ԧ��Ƶ����ϵͳ v3.0
 * 
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 * 
 * @author ���۷�
 * 
 *         ����޸�ʱ�䣺2009-3-13
 */
public class SearchContactHandler extends ContactBaseHandler {

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
		User sender = manager.getUser(message.getSenderId());
		User user = manager.getUserByEmail(((SearchContact) message).email);
		Group group = manager.getCommonContactGroup();
		
		//不能添加系统管理员为“我的联系人”
		//不能添加公共联系人根组下的用户为”我的联系人”
		if (user == null || user.getId().equals(manager.getLeaserId())
				|| !isAuthorizedUser(user, group) || !isAuthorizedUser(sender, group) || !checkUser(user)) {
			UserNotFoundByEmail msg = new UserNotFoundByEmail();
			msg.email = ((SearchContact) message).email;
			sendMessage(message.getSenderId(), msg);
		}
		else {
			SearchContactResult msg = new SearchContactResult();
			msg.contactId = user.getId();
			msg.email = user.getEmail();
			msg.firstName = user.getFirstName();
			msg.lastName = user.getLastName();
			if (this.getChannel(user.getId()) == null) {
				msg.status = CONTACT_OFFLINE;
			} else {
				msg.status = CONTACT_ONLINE;
			}

			sendMessage(message.getSenderId(), msg);
		}
	}
	
	private boolean checkUser(User user) {
		boolean flag = true;
		
		if(user != null) {
			Node node = (Node) user;
			String parentId = node.getParent().getID();
			String leaserId = this.getConferenceManager().getLeaserId();
			if(!parentId.equals(leaserId))
				flag = false;
		}
		
		return flag;
	}
}
