package com.fulong.lyvc.jcr;

import java.util.Calendar;
import java.util.Date;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeWrapper;
import com.fulong.longcon.repository.Property;
import com.fulong.lyvc.Message;

/**
 * MessageNode
 *
 * 龙驭视频会议系统 v3.0
 *
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 *
 * @author lifx
 *
 * 最后修改时间：2010-8-17
 */

public class MessageNode extends NodeWrapper implements Message {
	
	private static final String TITLE = "title";				//标题
	private static final String CONTENT = "content";			//内容
	private static final String SAVEDATE = "saveDate";			//留言保存日期
	private static final String SENDERID = "senderId";			//留言的发送者id
	
	public MessageNode(Node node){
		super(node);
	}

	/**
	 * 获取唯一标识
	 * 
	 * @return
	 */
	public String getId() {
		return this.getNode().getID();
	}
	
	/**
	 * 获取留言标题
	 */
	public String getTitle() {
		String title = null;
		
		Property property = this.getNode().getProperty(TITLE);
		if(property != null)
			title = property.getString();
		
		return title;
	}

	/**
	 * 获取留言内容
	 */
	public String getContent() {
		String message = null;
		
		Property property = this.getNode().getProperty(CONTENT);
		if(property != null)
			message = property.getString();
		
		return message;
	}
	
	/**
	 * 获取发送者id
	 */
	public String getSenderid() {
		String receiverId = null;
		
		Property property = this.getNode().getProperty(SENDERID);
		if(property != null)
			receiverId = property.getString();
		
		return receiverId;
	}
	
	/**
	 * 获取保存日期
	 * 
	 * @return
	 */
	public Date getSaveDate() {
		Date saveDate = null;
		
		Property property = this.getNode().getProperty(SAVEDATE);
		if(property != null)
			saveDate = property.getDate().getTime();
		
		return saveDate;
	}

	/**
	 * 设置留言标题
	 */
	public void setTitle(String title) {
		Property property = this.getNode().getProperty(TITLE);
		if(property != null)
			property.setValue(title);
	}
	
	/**
	 * 设置留言内容
	 */
	public void setContent(String content) {
		Property property = this.getNode().getProperty(CONTENT);
		if(property != null)
			property.setValue(content);
	}

	/**
	 * 设置发送者id
	 */
	public void setSenderid(String senderId) {
		Property property = this.getNode().getProperty(SENDERID);
		if(property != null)
			property.setValue(senderId);
	}

	/**
	 * 设置保存日期
	 * 
	 * @return
	 */
	public void setSaveDate(Date saveDate) {
		Property property = this.getNode().getProperty(SAVEDATE);
		if(property != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(saveDate);
			
			property.setValue(calendar);
		}
	}

	@Override
	public void setMaxOrderNo(int orderNo) {
		// TODO Auto-generated method stub
		
	}
}
