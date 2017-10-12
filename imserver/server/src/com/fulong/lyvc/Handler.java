/**
 * 
 */
package com.fulong.lyvc;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fulong.lyvc.message.AddConference;

/**
 * Handler
 * 
 * ��Ԧ��Ƶ����ϵͳ v3.0
 * 
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 * 
 * @author ���۷�
 * 
 *         ����޸�ʱ�䣺2009-3-12
 */
public abstract class Handler {
	private final static String protocolHeader = "Protocol Version: 2.0\r\nContent-Length:";
	
	protected Log logger = LogFactory.getLog(this.getClass());
	
	protected TCPServer server;
	protected MediaServer mediaServer;
	protected ConferenceManager manager;
	protected ConferenceRepository conferenceRepository;

	public void setConferenceServer(TCPServer server) {
		this.server = server;
	}
	
	protected boolean sendMessage(String userId, TCPMessage message) throws Exception{
		TCPChannel channel = this.getChannel(userId);
		if(channel != null) {
			this.sendMessage(channel, message);
			return true;
		}
		
		return false;
	}
	
	protected boolean sendMessage(User user, TCPMessage message) throws Exception{
		TCPChannel channel = this.getConferenceServer().getChannel(user);
		if(channel != null) {
			this.sendMessage(channel, message);
			return true;
		}
		
		return false;
	}
	
	public TCPChannel getChannel(String userId) throws Exception {
		TCPChannel channel = null;
		
		User user = this.getConferenceManager().getUser(userId);
		if(user != null) 
			channel = this.getConferenceServer().getChannel(user);
		
		return channel;
	}
	
	public ConferenceManager getConferenceManager() {
		if(manager == null)
			manager = this.conferenceRepository.getConferenceManager("");
		
		return this.manager;
	}

	public TCPServer getConferenceServer() {
		return this.server;
	}
	
	public MediaServer getMediaServer() {
		return this.mediaServer;
	}
	
	public void setMediaServer(MediaServer server) {
		this.mediaServer = server;
	}

	public boolean sendMessage(TCPChannel chan, TCPMessage pBaseMessage) {
		try {
			String messageContent = pBaseMessage.toXML();
			StringBuffer sb = new StringBuffer();
			sb.append(protocolHeader);
			sb.append(messageContent.getBytes().length);
			sb.append("\r\n\r\n");
			sb.append(messageContent);
			if (chan != null)
				//chan.sendMessage(URLEncoder.encode(sb.toString(),"UTF-8"));
				chan.sendMessage(sb.toString());
			//logger.debug("message sent: "+ messageContent);
			return true;
		} catch (Exception e) {
			logger.warn("SendMessage error", e);
			return false;
		}
	}
	
	protected void sendConferenceToUser(TCPChannel channel, Conference con) throws IOException, Exception {
		AddConference msgToSend = new AddConference();	

		//获取资源访问的基本路径
		String baseURL = "http://" + channel.getDomain();
		if(baseURL == null)
			baseURL = "";
		
		StringBuffer docTitles = new StringBuffer();
		StringBuffer docURLs = new StringBuffer();
		for(Document doc: con.getDocuments()) {
			docTitles.append(doc.getFileName()+ ",");
			docURLs.append(baseURL + doc.getDocURL() + ",");
		}
		
		//获取会议模式的id
		String modeId = con.getMode().getId();
		
		//msgToSend.setSenderId(con.getCreator().getId());
		msgToSend.conFilesURL = docURLs.toString();
		msgToSend.conFilesDesc = docTitles.toString();
		msgToSend.isEnded = con.isEnded();
		msgToSend.isStarted = con.isStarted();
		msgToSend.creatorId = con.getCreator().getId();
		msgToSend.conId = con.getId();
		msgToSend.conDesc = con.getDesc();
		msgToSend.conName = con.getTitle();
		msgToSend.conModelId = modeId;
		msgToSend.startTime = con.getStartTime();
		msgToSend.endTime = con.getEndTime();		
		
		sendMessage(channel, msgToSend);
	}
	
	public void setConferenceRepository(ConferenceRepository conferenceRepository) {
		this.conferenceRepository = conferenceRepository;
	}
	
	public void setConferenceManager(String domain) {
		manager = conferenceRepository.getConferenceManager(domain);
	}
	
	public boolean isAuthorizedUser(User user, Group group) {
		boolean flag = true;
		
		if(group.users().contains(user)) {
			flag = false;
		}
		
		return flag;
	}
}
