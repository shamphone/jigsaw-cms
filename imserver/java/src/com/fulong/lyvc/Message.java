package com.fulong.lyvc;

import java.util.Date;

/**
 * Message
 *
 * 龙驭视频会议系统 v3.0
 *
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 *
 * @author 李雄锋
 *
 * 最后修改时间：2010-8-17
 */

public interface Message {

	/**
	 * @return Returns the id.
	 */
	public String getId();
	
	/**
	 * @return Returns the senderid.
	 */
	public String getSenderid();

	/**
	 * set the senderId.
	 */
	public void setSenderid(String receiverId);

	/**
	 * @return Returns the title.
	 */
	public String getTitle();

	/**
	 * set the title.
	 */
	public void setTitle(String title);
	
	/**
	 * @return Returns the content
	 */
	public String getContent();
	
	/**
	 * set the content
	 */
	public void setContent(String content);

	/**
	 * @return Returns the saveDate.
	 */
	public Date getSaveDate();

	/**
	 * set the saveDate.
	 */
	public void setSaveDate(Date saveDate);
}
