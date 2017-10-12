/**
 * 
 */
package com.fulong.lyvc.server;

import java.io.IOException;

import org.apache.commons.logging.Log;import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;

import com.fulong.lyvc.MediaServer;
import com.fulong.lyvc.TCPChannel;

/**
 * 
* MediaServerImpl
* @author    <a href="lixf@fulong.com.cn">李雄峰</a>
* @date      2010-7-2
 */
public class MediaServerImpl implements MediaServer {
	private static Log logger = LogFactory.getLog(MediaServerImpl.class);
	private String IP;
	private int UDPPort;
	private int TCPPort;
	private TCPChannel channel;
	private Resource command;
	private boolean auto;
	private String args;

	public void setArgs(String args) {
		this.args = args;
	}

	public void setAuto(boolean auto) {
		this.auto = auto;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String ip) {
		IP = ip;
	}

	public int getUDPPort() {
		return UDPPort;
	}

	public void setUDPPort(int port) {
		UDPPort = port;
	}

	public int getTCPPort() {
		return TCPPort;
	}

	public void setTCPPort(int port) {
		TCPPort = port;
	}

	public TCPChannel getChannel() {
		return channel;
	}

	public void setChannel(TCPChannel channel) {
		this.channel = channel;
	}

	public void setCommand(Resource command) {
		this.command = command;
	}

	public void start() throws IOException {
		if (this.auto) {
//			String path =ResourceUtils.getResource(command);
			logger.info("Auto launch the media server :"+ command.getFile().getPath()+" "+args);
			Runtime.getRuntime().exec(command.getFile().getPath()+" "+args);
			logger.info("Media server startup.");
		}

	}

}
