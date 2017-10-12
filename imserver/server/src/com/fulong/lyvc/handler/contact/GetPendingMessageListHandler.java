/**
 * 
 */
package com.fulong.lyvc.handler.contact;

import java.util.Queue;

import com.fulong.lyvc.ConferenceManager;
import com.fulong.lyvc.EventDispatcher;
import com.fulong.lyvc.TCPChannel;
import com.fulong.lyvc.TCPMessage;
import com.fulong.lyvc.User;

/**
 * GetPendingMessageListHandler
 *
 * ��Ԧ��Ƶ����ϵͳ v3.0
 *
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 *
 * @author ���۷�
 *
 * ����޸�ʱ�䣺2009-3-13
 */
public class GetPendingMessageListHandler extends ContactBaseHandler {

	/* (non-Javadoc)
	 * @see com.fulong.lyvc.net.Handler#execute(com.fulong.lyvc.net.TCPChannel, com.fulong.lyvc.message.BaseMessage)
	 */
	
	/**
	 * 该方法用来获取留言（包括普通留言、系统公告等）
	 */
	public void execute(TCPChannel channel, TCPMessage message) throws Exception {
		this.setConferenceManager(channel.getDomain());
		ConferenceManager manager = this.getConferenceManager();
		
		//得到接收留言的用户（虽然message调用的方法看起来是得到消息的发送者，感觉怪怪的）
	    User user =  manager.getUser(message.getSenderId());
	    if(user != null) {
	    	TCPChannel sender = this.getConferenceServer().getChannel(user);
	        
		    Queue<String> messages = manager.getMessageStore(user);
	        while(!messages.isEmpty()) {
	        	TCPMessage msg = EventDispatcher.getInstance().parseMessage(messages.peek());
	        	sendMessage(sender, msg);
	        	messages.poll();
	        }
	    }
	}

}
