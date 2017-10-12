/**
 * 
 */
package com.fulong.lyvc.handler.desktop;

import com.fulong.lyvc.EventHandler;
import com.fulong.lyvc.TCPChannel;
import com.fulong.lyvc.message.NotifyMediaServerAddDesktop;
import com.fulong.lyvc.message.NotifyMediaServerRemoveDesktop;

/**
 * DesktopBaseHandler
 *
 * ��Ԧ��Ƶ����ϵͳ v3.0
 *
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 *
 * @author ���۷�
 *
 * ����޸�ʱ�䣺2009-3-13
 */
public abstract class DesktopBaseHandler extends EventHandler {
	
	   protected void notifyMediaServerAddRelation(String serverId, String clientId, boolean isAgreeInvite, String conferenceId){
	        NotifyMediaServerAddDesktop msgToSend = new NotifyMediaServerAddDesktop();
	        msgToSend.setConferenceId(conferenceId);
	        msgToSend.fromUserId = serverId;
	        msgToSend.toUserId   = clientId;
	        msgToSend.isAgreeInvite = isAgreeInvite;
	        TCPChannel mediaChannel = this.getMediaServer().getChannel();
	        sendMessage(mediaChannel, msgToSend);
	    }

	   protected void notifyMediaServerRemoveRelation(String serverId, String clientId){
	        NotifyMediaServerRemoveDesktop msgToSend = new NotifyMediaServerRemoveDesktop();
	        msgToSend.fromUserId = serverId;
	        msgToSend.toUserId   = clientId;
	        TCPChannel mediaChannel = this.getMediaServer().getChannel();
	        sendMessage(mediaChannel, msgToSend);
	    }
}
