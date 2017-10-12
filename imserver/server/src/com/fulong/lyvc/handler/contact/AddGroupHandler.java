/**
 * 
 */
package com.fulong.lyvc.handler.contact;

import com.fulong.lyvc.ConferenceManager;
import com.fulong.lyvc.Group;
import com.fulong.lyvc.TCPChannel;
import com.fulong.lyvc.TCPMessage;
import com.fulong.lyvc.User;
import com.fulong.lyvc.message.AddGroup;

/**
 * AddGroupHandler
 * 
 * ��Ԧ��Ƶ����ϵͳ v3.0
 * 
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 * 
 * @author ���۷�
 * 
 *         ����޸�ʱ�䣺2009-3-13
 */
public class AddGroupHandler extends ContactBaseHandler {
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
		
		String groupId = ((AddGroup) message).groupId;
		Group root = manager.getGroup(groupId);
		
		//判断要添加的组是不是公共联系人组
		if(groupId.equals("0") || root == null || groupId.equals(manager.getCommonContactGroup().getId())) {
			root = sender.getContactGroup();
		}
		else {
			Group parentGroup = root.getParentGroup();
			if(parentGroup == null || parentGroup.getId().equals(manager.getCommonContactGroup().getId()))
				root = sender.getContactGroup();
		}
		
		Group group = root.addChild(((AddGroup) message).name, ((AddGroup) message).name, sender.getId());
		
		AddGroup msg = new AddGroup();
		msg.isCommon = false;
		msg.groupId = group.getId();
		msg.parentGroupId = group.getParentGroup().getId();
		msg.name = ((AddGroup) message).name;
		this.sendMessage(message.getSenderId(), msg);
	}
	
	public boolean sendMessage(TCPChannel chan, TCPMessage pBaseMessage) {
		return super.sendMessage(chan, pBaseMessage);
	}
}
