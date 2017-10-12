/**
 * 
 */
package com.fulong.lyvc;

import java.util.Date;
import java.util.EventObject;

/**
 * TCPEvent
 *
 * ��Ԧ��Ƶ����ϵͳ v3.0
 *
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 *
 * @author ���۷�
 *
 * ����޸�ʱ�䣺2009-3-15
 */
public class TCPEvent extends EventObject {
	
	private static final long serialVersionUID = TCPEvent.class.getName().hashCode();
	public static final int SOCKET_EVENT = 1;
	public static final int BROKEN_CHANNEL_EVENT =2 ;
	public static final int CONFERENCE_EVENT = 3;
	public static final int TIMER_EVENT = 4;
	private TCPChannel channel;
	private String message;
	private int type;
	
	public static TCPEvent socketEvent(TCPChannel channel, String message) {
		TCPEvent event = new TCPEvent(channel);
		event.message = message;
		event.type = SOCKET_EVENT;
		return event;
	}
	
	public static TCPEvent brokenChannelEvent(TCPChannel channel) {
		TCPEvent event = new TCPEvent(channel);
		event.type = BROKEN_CHANNEL_EVENT;
		return event;
	}
	public static TCPEvent conferenceEvent(String message) {
		TCPEvent event = new TCPEvent(message);
		event.type = CONFERENCE_EVENT;
		return event;
	}
	public static TCPEvent timerEvent() {
		TCPEvent event = new TCPEvent();
		event.type = TIMER_EVENT;
		return event;
	}
	
	
	public TCPChannel getChannel() {
		return channel;
	}


	public String getMessage() {
		return message;
	}


	public int getType() {
		return type;
	}
	
	public Date getDate() {
		return (Date)this.getSource();
	}
	
	

	private TCPEvent(TCPChannel channel) {
		super(new Date());
		this.channel = channel;
	}
	
	private TCPEvent() {
		super(new Date());
	}
	
	private TCPEvent(String message) {
		super(new Date());
		this.message = message;
	}

}
