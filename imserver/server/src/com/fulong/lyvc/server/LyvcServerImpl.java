/**
 * 
 */
package com.fulong.lyvc.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fulong.lyvc.ConferenceRepository;
import com.fulong.lyvc.TCPChannel;
import com.fulong.lyvc.TCPServer;
import com.fulong.lyvc.User;

/**
 * 
 * LyvcServerImpl
 * 
 * @author <a href="lixf@fulong.com.cn">李雄峰</a>
 * @date 2010-7-2
 */
public class LyvcServerImpl implements Runnable, TCPServer {

	// Log
	private static Log logger = LogFactory.getLog(LyvcServerImpl.class);
	private int mainClientVersion;
	private int minClientVersion;
	private boolean isRunning;
	private String name;
	
	private ConferenceRepository conferenceRepository;		
	
	/**
	 * max number of simultaneous connections.
	 */
	private int maxConnections;

	private Thread thread;
	private int port;
	private int nConnections;
	private Map<String, TCPChannel> channels;
	private ServerSocket sock;

	/**
	 * IP address of stack -- this can be re-written by stun.
	 */
	private String stackAddress; // My host address.

	// private ListeningPoint listeningPoint;

	public LyvcServerImpl() {
		this.channels = new Hashtable<String, TCPChannel>();

		this.maxConnections = -1;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMainClientVersion() {
		return mainClientVersion;
	}

	public void setMainClientVersion(int mainClientVersion) {
		this.mainClientVersion = mainClientVersion;
	}

	public int getMinClientVersion() {
		return minClientVersion;
	}

	public void setMinClientVersion(int minClientVersion) {
		this.minClientVersion = minClientVersion;
	}

	public void setMaxConnections(int maxConnections) {
		this.maxConnections = maxConnections;
	}

	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * Start the processor.
	 */
	public void start() throws Exception {

		String address = InetAddress.getLocalHost().getHostAddress();
		setHostAddress(address);

		thread = new Thread(this);
		thread.setName(this.name);
		this.sock = new ServerSocket(this.port);
		thread.start();
		this.isRunning = true;
		logger.info("Lyvc Server start up. ");
		// serverMonitorSession.serverStart();
	}

	/**
	 * Run method for the thread that gets created for each accept socket.
	 */
	public void run() {
		// Accept new connectins on our socket.
		while (this.isRunning) {
			try {
				synchronized (this) {
					// sipStack.maxConnections == -1 means we are
					// willing to handle an "infinite" number of
					// simultaneous connections (no resource limitation).
					// This is the default behavior.
					while (this.isRunning && this.maxConnections != -1 && this.nConnections >= this.maxConnections) {
						try {
							this.wait();
							if (!this.isRunning)
								return;
						} catch (InterruptedException ex) {
							break;
						}
					}
					this.nConnections++;
				}

				Socket newsock = sock.accept();
				logger.info("Connection from: " + newsock.getRemoteSocketAddress());
				TCPChannel tcpMessageChannel = new LyvcTCPChannel(newsock, this);
				this.cacheMessageChannel(tcpMessageChannel);

			} catch (SocketException ex) {
				logger.error("Socket error, server shut down.", ex);
				this.isRunning = false;
			} catch (IOException ex) {
				logger.info("Socket io error, retry again.", ex);
				continue;
			} catch (Exception ex) {
				logger.error("Error in process socket.", ex);
			}
		}
	}

	/**
	 * Run method for the thread that gets created for each accept socket.
	 */
	public synchronized void stop() {

		isRunning = false;
		// this.listeningPoint = null;
		try {
			sock.close();
		} catch (IOException e) {
			logger.error("Error in close socket. ", e);
		}

		for (TCPChannel channel : this.channels.values()) {
			channel.close();
		}
		
		this.notify();
	}

	public synchronized void clearChannels() {
		for (TCPChannel channel : this.channels.values()) {
			channel.close();
		}
		this.channels.clear();
	}

	protected synchronized void cacheMessageChannel(TCPChannel messageChannel) {
		String key = messageChannel.getKey();
		TCPChannel currentChannel = (TCPChannel) channels.get(key);
		if (currentChannel != null) {
			currentChannel.close();
		}
		this.channels.put(key, messageChannel);
	}

	public synchronized TCPChannel getChannel(HostPort targetHostPort) throws IOException {
		String key = LyvcTCPChannel.getKey(targetHostPort, "TCP");
		if (channels.get(key) != null) {
			return (TCPChannel) this.channels.get(key);
		} 
		else {
			return null;
		}
	}

	public int getMaximumMessageSize() {
		return Integer.MAX_VALUE;
	}

	public void releaseConnection() {
		nConnections--;
		notify();
	}

	public String getHostAddress() {
		return this.stackAddress;
	}

	public int getMaxConnections() {
		return this.maxConnections;
	}

	public int getPort() {
		return this.port;
	}

	public String getTransport() {
		return "TCP";
	}

	/**
	 * Set my address.
	 * 
	 * @param stackAddress
	 *            -- A string containing the stack address.
	 */
	private void setHostAddress(String stackAddress) throws UnknownHostException {
		if (stackAddress.indexOf(':') != stackAddress.lastIndexOf(':') && stackAddress.trim().charAt(0) != '[') {
			this.stackAddress = '[' + stackAddress + ']';
		} 
		else {
			this.stackAddress = stackAddress;
		}
		// this.stackInetAddress = InetAddress.getByName(stackAddress);
	}

	public TCPChannel getChannel(User user) {
		for (TCPChannel channel : channels()) {
			User temp = channel.getUser();
			if (temp != null && user.equals(temp))
				return channel;
		}
		
		return null;
	}

	protected synchronized void remove(TCPChannel channel) {
		this.channels.remove(channel.getKey());
	}

	public Collection<TCPChannel> channels() {
		return this.channels.values();
	}

	public void disconnectAll() throws IOException {
		for (TCPChannel channel : this.channels.values()) {
			channel.close();
		}
		this.channels.clear();
	}
	
	public void setConferenceRepository(ConferenceRepository conferenceRepository) {
		this.conferenceRepository = conferenceRepository;
	}
	
	public ConferenceRepository getConferenceRepository() {
		return this.conferenceRepository;
	}
}
