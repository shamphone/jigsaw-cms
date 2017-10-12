/**
 * 
 */
package com.fulong.lyvc;

/**
 * EventDispatcher
 * 
 * ��Ԧ��Ƶ����ϵͳ v3.0
 * 
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 * 
 * @author ���۷�
 * 
 *         ����޸�ʱ�䣺2009-3-14
 */
public abstract class EventDispatcher {
	private static EventDispatcher instance;

	public static EventDispatcher getInstance() {
		return instance;
	}

	public EventDispatcher() {
		if (instance != null)
			throw new IllegalStateException("Event Dispatcher is singleton, should not be instantiated more.");
		
		instance = this;
	}

	/**
	 * ����¼�
	 * 
	 * @param eventWrapper
	 */
	public abstract void addEvent(TCPEvent event);

	public abstract TCPMessage parseMessage(String xmlString) throws Exception;

}
