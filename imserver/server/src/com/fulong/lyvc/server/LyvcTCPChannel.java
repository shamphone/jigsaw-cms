/**
 * 
 */
package com.fulong.lyvc.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fulong.lyvc.EventDispatcher;
import com.fulong.lyvc.TCPChannel;
import com.fulong.lyvc.TCPEvent;
import com.fulong.lyvc.TCPServer;
import com.fulong.lyvc.User;

/**
 * 
* LyvcTCPChannel
* @author    <a href="lixf@fulong.com.cn">李雄峰</a>
* @date      2010-7-2
 */
public class LyvcTCPChannel implements Runnable, TCPChannel {

	// logger
	static Log logger = LogFactory.getLog(TCPChannel.class);

	private Socket mySock; 
	private InputStream myClientInputStream;
	private String key;
	protected boolean isRunning;
	private Thread mythread;
	private int myPort;
	private InetAddress peerAddress;
	private int peerPort;
	private String peerProtocol;
	private LyvcServerImpl server;
	// Read Buffer and buffer size
	private static final int bufferSize = 1024 * 64;
	private byte[] buffer = new byte[bufferSize];

	// The client userid which this channel connected to
	private User user;
	private int AVCompressionCardChannelNumber;

	private String domain;	//域名
	
	/**
	 * Constructor - gets called from the SIPStack class with a socket on
	 * accepting a new client. All the processing of the message is done here
	 * with the stack being freed up to handle new connections. The sock input
	 * is the socket that is returned from the accept. Global data that is
	 * shared by all threads is accessible in the Server structure.
	 * 
	 * @param sock
	 *            Socket from which to read and write messages. The socket is
	 *            already connected (was created as a result of an accept).
	 * 
	 * @param sipStack
	 *            Ptr to SIP Stack
	 */

	protected LyvcTCPChannel(Socket sock, LyvcServerImpl msgProcessor) throws IOException {
		mySock = sock;
		this.peerProtocol = "TCP";
		this.peerPort = mySock.getPort();
		this.peerAddress = mySock.getInetAddress();

		this.server = msgProcessor;
		this.myPort = this.server.getPort();

		myClientInputStream = mySock.getInputStream();
		mythread = new Thread(this);
		mythread.setName("TCPChannelThread-" + mySock.getRemoteSocketAddress());
		mythread.start();
	}

	public void bindUser(User user) {
		this.user = user;
	}

	public String getUserId() {
		if(this.user != null)
			return user.getId();
		
		return "0";
	}

	/**
	 * Returns "true" as this is a reliable transport.
	 */
	public boolean isReliable() {
		return true;
	}

	/**
	 * Close the message channel.
	 */
	public void close() {
		this.server.remove(this);		
		try {
			if (mySock != null) {
				mySock.close();
			}
		} catch (IOException ex) {
			logger.error(ex);
		}
	}

	/**
	 * get the transport string.
	 * 
	 * @return "tcp" in this case.
	 */
	public String getTransport() {
		return "TCP";
	}

	/**
	 * get the address of the client that sent the data to us.
	 * 
	 * @return Address of the client that sent us data that resulted in this
	 *         channel being created.
	 */
	public String getPeerAddress() {
		if (peerAddress != null) {
			return peerAddress.getHostAddress();
		} else {
			return getHost();
		}
	}

	protected InetAddress getPeerInetAddress() {
		return peerAddress;
	}

	public String getPeerProtocol() {
		return this.peerProtocol;
	}

	public void sendMessage(String message) throws IOException {
		byte[] bytes = message.getBytes();
		this.sendMessage(bytes);
	}

	/**
	 * Send a message
	 */
	public void sendMessage(byte message[]) throws IOException {

		if (message == null) {
			throw new IllegalArgumentException("Null argument");
		}

		try {
			OutputStream outputStream = mySock.getOutputStream();
			outputStream.write(message);
		} catch (IOException ex) {
			logger.warn("Fail to send message to user " + this.user.getName() + " (" + this.getUserId() + "). Remote address is " + this.mySock.getRemoteSocketAddress(), ex);
			if (ex.getMessage().equals("Socket is closed")) {
				
				EventDispatcher.getInstance().addEvent(TCPEvent.brokenChannelEvent(this));

				if (this.server.getMaxConnections() != -1) {
					synchronized (server) {
						server.releaseConnection();
					}
				}

				try {
					mySock.close();
				} catch (Exception ex2) {
					ex2.printStackTrace();
				}

				this.isRunning = false;
				this.server.remove(this);
			}
		}
	}

	private ByteArrayParseResult findMessage(byte[] buffer, int offset, int size) {
	
		int i;
		for (i = offset; i < size + offset - 1; i++) {
			if (buffer[i] == (byte) 13 && buffer[i + 1] == (byte) 10) {
				break;
			}
		}
		if (i == (size + offset - 1)) {
			return null;
		}

		int secondLineStartOffset = i + 2;
		int contentLengthStringStartOffset = secondLineStartOffset + "Content-Length:".length();
		if (contentLengthStringStartOffset >= (offset + size)) {
			return null;
		}

		for (i = contentLengthStringStartOffset; i < size + offset - 1; i++) {
			if (buffer[i] == (byte) 13 && buffer[i + 1] == (byte) 10) {
				break;
			}
		}
		
		if (i == (size + offset - 1)) {
			return null;
		}
		
		int messageStart = i + 4;
		String contentLengthString = new String(buffer, contentLengthStringStartOffset, i - contentLengthStringStartOffset);
		int contentLength = 0;
		try {
			contentLength = Integer.parseInt(contentLengthString);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		// 2, �ж������Ƿ�����
		if (size + offset < messageStart + contentLength) {
			return null;
		}

		// 3, ���ص�ǰ��Ϣ
		ByteArrayParseResult result = new ByteArrayParseResult();
		result.messageBody = new String(buffer, messageStart, contentLength);
		result.messageSize = messageStart - offset + contentLength;
		return result;
	}

	/**
	 * This gets invoked when thread.start is called from the constructor.
	 * Implements a message loop - reading the tcp connection and processing
	 * messages until we are done or the other end has closed.
	 */
	public void run() {

		this.isRunning = true;
		int dataSize = 0;

		try {
			while (true) {

				//
				// Read byte
				//
				int nbytes = myClientInputStream.read(buffer, dataSize, bufferSize - dataSize);
				if (nbytes == -1) {
					logger.info("Channel for user " + this.getUserName() + " (" + this.getUserId() + ") is closed. Remote address is: " + mySock.getRemoteSocketAddress());
					return;
				}
				dataSize = dataSize + nbytes;

				//
				// We may have read multiple message, parse them in a loop.
				//
				int dataPos = 0;
				while (dataSize > 0) {

					// ���ҵ�ǰ��Ϣ
					ByteArrayParseResult result = findMessage(buffer, dataPos, dataSize);

					// ��������ʣ�����ݲ���һ��������Ϣ��
					// ��ʣ����ݿ�����������ʼ��Ȼ���˳����
					if (result == null) {
						if (dataSize != 0) {
							for (int i = 0; i < dataSize; i++) {
								buffer[i] = buffer[i + dataPos];
							}
						}
						break;
					}

					// ����ʣ�����ݴ�С
					dataSize -= result.messageSize;
					dataPos += result.messageSize;
					
					// �����µ���Ϣ,���͸� EventDispatcher
					EventDispatcher.getInstance().addEvent(TCPEvent.socketEvent(this, result.messageBody));
				}
			}

		} catch (Exception ex) {
			logger.info("Channel for user " + this.getUserName() + " (" + this.getUserId() + ") is broken. Remote address is: " + mySock.getRemoteSocketAddress());
			return;

		} finally {
			EventDispatcher.getInstance().addEvent(TCPEvent.brokenChannelEvent(this));
			if (this.server.getMaxConnections() != -1) {
				synchronized (server) {
					server.releaseConnection();
				}
			}

			try {
				mySock.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			this.isRunning = false;
			this.server.remove(this);
		}
	}

	public boolean equals(Object other) {

		if (!this.getClass().equals(other.getClass())) {
			return false;
		} else {
			LyvcTCPChannel that = (LyvcTCPChannel) other;
			if (this.mySock != that.mySock) {
				return false;
			} else {
				return true;
			}
		}
	}

	/**
	 * Get an identifying key. This key is used to cache the connection and
	 * re-use it if necessary.
	 */
	public String getKey() {
		if (this.key != null) {
			return this.key;
		} else {
			this.key = getKey(this.peerAddress, this.peerPort, "TCP");
			return this.key;
		}
	}

	public int getPeerPort() {
		return peerPort;
	}

	public boolean isSecure() {
		return false;
	}

	public int getMyPort() {
		return myPort;
	}

	public void setMyPort(int myPort) {
		this.myPort = myPort;
	}

	public SocketAddress getRemoteAddress() {
		return this.mySock.getRemoteSocketAddress();
	}

	/**
	 * Get the host of this message channel.
	 * 
	 * @return host of this messsage channel.
	 */
	public String getHost() {
		return this.server.getHostAddress();
	}

	/**
	 * Get port of this message channel.
	 * 
	 * @return Port of this message channel.
	 */
	public int getPort() {
		if (this.server != null) {
			return server.getPort();
		} 
		else {
			return -1;
		}
	}

	/**
	 * Handle an exception. public abstract void
	 * handleException(SIPServerException ex);
	 */

	/**
	 * Convenience function to get the raw IP source address of a SIP message as
	 * a String.
	 */
	public String getRawIpSourceAddress() {
		String sourceAddress = getPeerAddress();
		String rawIpSourceAddress = null;
		try {
			InetAddress sourceInetAddress = InetAddress.getByName(sourceAddress);
			rawIpSourceAddress = sourceInetAddress.getHostAddress();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return rawIpSourceAddress;
	}

	/**
	 * generate a key given the inet address port and transport.
	 */
	public static String getKey(InetAddress inetAddr, int port, String transport) {
		return (transport + ":" + inetAddr.getHostAddress() + ":" + port).toLowerCase();
	}

	/**
	 * Generate a key given host and port.
	 */
	public static String getKey(HostPort hostPort, String transport) {
		return (transport + ":" + hostPort.getHost().getHostname() + ":" + hostPort.getPort()).toLowerCase();
	}

	/**
	 * Get the hostport structure of this message channel.
	 */
	public HostPort getHostPort() {
		HostPort retval = new HostPort();
		retval.setHost(new Host(this.getHost()));
		retval.setPort(this.getPort());
		
		return retval;
	}

	public HostPort getPeerHostPort() {
		HostPort retval = new HostPort();
		retval.setHost(new Host(this.getPeerAddress()));
		retval.setPort(this.getPeerPort());
		
		return retval;
	}

	public TCPServer getMessageProcessor() {
		return this.server;
	}

	public User getUser() {
		return this.user;
	}

	public int getAVCompressionCardChannelNumber() {
		return AVCompressionCardChannelNumber;
	}

	public void setAVCompressionCardChannelNumber(int compressionCardChannelNumber) {
		AVCompressionCardChannelNumber = compressionCardChannelNumber;
	}
	
	/**
	 * 设置域名
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	/**
	 * 获取域名
	 */
	public String getDomain() {
		return this.domain;
	}
	
	/**
	 * 获取用户名
	 */
	private String getUserName() {
		if(this.user != null)
			return user.getName();
		
		return "";
	}
}
