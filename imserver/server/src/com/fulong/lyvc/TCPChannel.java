package com.fulong.lyvc;

import java.io.IOException;
import java.net.SocketAddress;

/**
 * 
 * TCPChannel
 * 
 * ��Ԧ��Ƶ����ϵͳ v3.0
 * 
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 * 
 * @author ���۷�
 * 
 *         ����޸�ʱ�䣺2009-3-14
 */
public interface TCPChannel {
	/**
	 * 
	 * @return
	 */
	public String getUserId();

	/**
	 * ��ǰͨ�9�j�Ŀͻ����û�
	 * 
	 * @return
	 */
	public User getUser();
	
	/**
	 * �󶨵�ָ���û�ID��
	 * @param user
	 */
	public void bindUser(User user);

	/**
	 * Returns "true" as this is a reliable transport.
	 */
	public boolean isReliable();

	/**
	 * Close the message channel.
	 */
	public void close();

	/**
	 * get the transport string.
	 * 
	 * @return "tcp" in this case.
	 */
	public String getTransport();

	/**
	 * get the address of the client that sent the data to us.
	 * 
	 * @return Address of the client that sent us data that resulted in this
	 *         channel being created.
	 */
	public String getPeerAddress();

	// protected InetAddress getPeerInetAddress() ;

	public String getPeerProtocol();
/**
 * ������Ϣ
 * @param message
 * @throws IOException
 */
	public void sendMessage(String message) throws IOException;

	/**
	 * Send a message
	 */
	public void sendMessage(byte message[]) throws IOException;
/**
 * �ͻ��˶˿�
 * @return
 */
	public int getPeerPort();
/**
 * 
 * @return
 */
	public boolean isSecure();
	/**
	 * ����Ψһʶ����
	 * @return
	 */
	public String getKey();
	/**
	 * 
	 * @return
	 */
	public SocketAddress getRemoteAddress();
	/**
	 * ѹ��ͨ����
	 * @param num
	 */
	public void setAVCompressionCardChannelNumber(int num);
	/**
	 * ѹ��ͨ����
	 * @return
	 */
	public int getAVCompressionCardChannelNumber();
	
	/**
	 * 设置域名
	 * @param domain
	 */
	public void setDomain(String domain);
	
	/**
	 * 获取域名
	 * @return
	 */
	public String getDomain();
}
