/**
 * 
 */
package com.fulong.lyvc.data;

import java.util.Date;

/**
 * MessageData
 * 
 * ��Ԧ��Ƶ����ϵͳ v3.0
 * 
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 * 
 * @author ���۷�
 * 
 *         ����޸�ʱ�䣺2009-3-19
 */
public class MessageData {
	private long id;
	private long receiverid;
	private String message;
	private Date saveDate;
	
	public MessageData() {
		this.saveDate= new Date();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getReceiverid() {
		return receiverid;
	}

	public void setReceiverid(long receiverid) {
		this.receiverid = receiverid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getSaveDate() {
		return saveDate;
	}

	public void setSaveDate(Date saveDate) {
		this.saveDate = saveDate;
	}
}
