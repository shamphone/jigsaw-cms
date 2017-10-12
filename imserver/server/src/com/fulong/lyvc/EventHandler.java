/**
 * 
 */
package com.fulong.lyvc;


/**
 * EventHandler
 *
 * ��Ԧ��Ƶ����ϵͳ v3.0
 *
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 *
 * @author ���۷�
 *
 * ����޸�ʱ�䣺2009-3-13
 */
public abstract class EventHandler extends Handler {

	public abstract void execute(TCPChannel channel, TCPMessage message) throws Exception ;
}
