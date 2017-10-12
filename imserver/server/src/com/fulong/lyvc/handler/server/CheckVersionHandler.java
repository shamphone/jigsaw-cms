package com.fulong.lyvc.handler.server;

import java.util.Properties;

import com.fulong.lyvc.TCPChannel;
import com.fulong.lyvc.TCPMessage;
import com.fulong.lyvc.message.CheckVersion;
import com.fulong.lyvc.message.ServerInfo;

/**
 * 
 * CheckVersionHandler
 * 
 * ��Ԧ��Ƶ����ϵͳ v3.0
 * 
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 * 
 * @author ���۷�
 * 
 *         ����޸�ʱ�䣺2009-3-13
 */
public class CheckVersionHandler extends ServerBaseHandler {
	private Properties urls;

	public void setUrls(Properties urls) {
		this.urls = urls;
	}

	@Override
	public void execute(TCPChannel channel, TCPMessage message) throws Exception {
		sendServerInfo(channel);

		CheckVersion msg = (CheckVersion) message;
		CheckVersion msgToSend = new CheckVersion();
		msgToSend.bHasNewVersion = false;
		msgToSend.bNeedUpdate = false;
		
		if (msg.version < this.server.getMainClientVersion()) {
			msgToSend.bHasNewVersion = true;
		}
		
		if (msg.version < this.server.getMinClientVersion()) {
			msgToSend.bNeedUpdate = true;
		}
		
		sendMessage(channel, msgToSend);
	}

	private void sendServerInfo(TCPChannel channel) throws Exception {
		ServerInfo msg = new ServerInfo();
		msg.CreateFormalConferenceURL = this.urls.getProperty("CreateConferenceURL");
		msg.EditConferenceURL = this.urls.getProperty("EditConferenceURL");
		msg.DeleteConferenceURL = this.urls.getProperty("DeleteConferenceURL");
		msg.CreateConferenceNoticeURL = this.urls.getProperty("CreateNoticeURL");
		msg.CreateBulletinURL = this.urls.getProperty("CreateBulletinURL");
		msg.CommonContactMgrURL = this.urls.getProperty("CommonContactMgrURL");
		msg.ConferenceModeMgrURL = this.urls.getProperty("ConferenceModeMgrURL");
		msg.SystemRoleMgrURL = this.urls.getProperty("SystemRoleMgrURL");
		msg.SelfInfoMgrURL = this.urls.getProperty("SelfInfoMgrURL");
		msg.UserRegisterURL = this.urls.getProperty("UserRegisterURL");
		msg.ClientDownloadURL = this.urls.getProperty("ClientDownloadURL");
		sendMessage(channel, msg);
	}
}
