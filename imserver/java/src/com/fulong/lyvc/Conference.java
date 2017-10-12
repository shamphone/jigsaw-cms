package com.fulong.lyvc;

import java.util.Collection;
import java.util.Date;

/**
 * 
 * Conference
 * 
 * 龙驭视频会议系统 v3.0
 * 
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 * 
 * @author 李雄锋
 * 
 * 最后修改时间：2010-9-8
 */

public interface Conference {
	/**
	 * 唯一标识符
	 * 
	 * @return
	 */
	public String getId();

	/**
	 * 开始时间
	 * 
	 * @return
	 */
	public Date getStartTime();

	/**
	 * 开始时间
	 * 
	 * @param startTime
	 */
	public void setStartTime(Date startTime);

	/**
	 * 标题
	 * 
	 * @return
	 */
	public String getTitle();

	/**
	 * 名称
	 * 
	 * @param title
	 */
	public void setTitle(String title);

	/**
	 * 描述
	 * 
	 * @return
	 */

	public String getDesc();

	/**
	 * 修改描述
	 * 
	 * @param desc
	 */
	public void setDesc(String desc) ;

	/**
	 * 结束时间
	 * 
	 * @return
	 */
	public Date getEndTime();

	/**
	 * 结束时间
	 * 
	 * @param endTime
	 * @
	 */
	public void setEndTime(Date endTime) ;

	/**
	 * 会议模式，只读
	 * 
	 * @return
	 * @ 
	 */
	public Mode getMode() ;

	/**
	 * 创建者，只读
	 * 
	 * @return
	 * @ 
	 */
	public User getCreator() ;

	/**
	 * 添加文档
	 * @param path
	 * @return
	 * @
	 */
	public Document addDocument(String path);
	
	/**
	 * 获取文档
	 * @param path
	 * @return
	 * @
	 */
	public Document getDocument(String path) ;
	
	/**
	 * 删除文档
	 * @param path
	 * @
	 */
	public void removeDocument(String path) ;
	
	/**
	 * 获取所有文档
	 * @return
	 * @
	 */
	public Collection<Document> getDocuments() ;
	
	/**
	 * 获得成员的角色
	 */
	public ModeRole getMemberRole(String userID) ;

	/**
	 * 获得所有会议成员列表
	 */
	public Collection<User> getMembers() ;
	
	/**
	 * 获得所有会议成员列表
	 */
	public Collection<User> getMembers(ModeRole role) ;

	/**
	 * 获得所有会议成员列表
	 */
	public Collection<User> getMembers(String roleId) ;
	
	/**
	 * 向会议中添加某种角色的成员
	 */
	public boolean addMember(String modelRoleID, String userID);
	
	/**
	 * 向会议中添加默认角色的成员
	 */
	public boolean addMember(String userID);

	/**
	 * 删除成员
	 */
	public void removeMember(String userID);
	
	/**
	 * 是否会议成员
	 * @param userID
	 * @return
	 * @
	 */
	public boolean isMember(String userID) ;
	
	/**
	 * 启动会议
	 */
	public void start();
	
	/**
	 * 终止会议
	 */
	public void terminate();
	
	/**
	 * 正在举行中
	 * @return
	 */
	public boolean isHolding();

	/**
	 * 是否已经开始
	 * @return
	 */
	public boolean isStarted();
	
	/**
	 * 是否已经结束
	 * @return
	 */
	public boolean isEnded();
	
	/**
	 * 加入会议
	 * @param user
	 * @return
	 */
	public boolean join(User user);
	
	/**
	 * 离开会议
	 * @param user
	 * @return
	 */
	public boolean leave(User user);
	
	/**
	 * 获取在线用户
	 * @return
	 */
	public Collection<User> getParticipants();

}
