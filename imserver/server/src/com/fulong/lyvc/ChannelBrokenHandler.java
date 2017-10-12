/**
 * 
 */
package com.fulong.lyvc;



/**
 * ChannelBrokenHandler
 *
 * ��Ԧ��Ƶ����ϵͳ v3.0
 *
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 *
 * @author ���۷�
 *
 * ����޸�ʱ�䣺2009-3-13
 */
public abstract class ChannelBrokenHandler extends Handler {
	
	public abstract void execute(TCPChannel channel) throws Exception ;
	
}
