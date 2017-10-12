package com.fulong.lyvc;

import java.io.IOException;
import java.util.Collection;

/**
 * 
 * MessageProcessor
 * 
 * ��Ԧ��Ƶ����ϵͳ v3.0
 * 
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 * 
 * @author ���ڣ����۷�
 * 
 *         ����޸�ʱ�䣺2009-3-13
 */
public interface TCPServer {	


	/**
	 * �������
	 */
	public void start() throws Exception;

	/**
	 * Stop the event scanner. Decrement the reference count and exit the
	 * scanner thread if the ref count goes to 0.
	 */
	public void stop();

	/**
	 * ��汾��
	 * 
	 * @return
	 */
	public int getMainClientVersion();

	/**
	 * С�汾��
	 * 
	 * @return
	 */
	public int getMinClientVersion();

	/**
	 * ��ȡ
	 * 
	 * @return
	 */
//	public ConferenceTable getConferenceTable();

	/**
	 * ���l����
	 * 
	 * @return
	 */
	public int getMaxConnections();

	/**
	 * ����ַ
	 * 
	 * @return
	 */
	public String getHostAddress();

	/**
	 * Return the transport string.
	 * 
	 * @return the transport string
	 */
	public String getTransport();

	/**
	 * Returns the port that we are listening on.
	 * 
	 * @return Port address for the tcp accept.
	 */
	public int getPort();

	/**
	 * ��ȡ�û����ڵ�ͨ��
	 */
	public TCPChannel getChannel(User user);
	
	
	/**
	 * ��ȡ���л��ͨ��
	 * @return
	 */
	public Collection<TCPChannel> channels();
	
	/**
	 * �ж����еĿͻ���l�ӣ��������е���ʱ��������߻��顣
	 * @throws IOException
	 */
	public void disconnectAll() throws IOException;

	/**
	 * �ͷ�l��
	 */
	public void releaseConnection();
	
	public void setConferenceRepository(ConferenceRepository conferenceRepository);
	
	public ConferenceRepository getConferenceRepository();
}
