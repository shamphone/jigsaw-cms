package com.fulong.lyvc;

import java.util.Collection;
import java.util.Date;
import java.util.Queue;

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
 * 最后修改时间：2010-8-24
 */

public interface ConferenceManager {
	public static long[] PREDEFINE_CONFERENCE_MODEL = new long[] { 1, 2, 3, 5 };
	
	/**
	 * 通过会议id获取该会议
	 */
	public Conference getConference(String conId);
	
	/**
	 * 该方法用来得到所有正在召开的普通会议
	 */
	public Collection<Conference> getHoldingConferences();
	
	/**
	 * 该方法用来得到某个用户参与的所有正在召开的普通会议
	 */
	public Collection<Conference> getHoldingConferences(User participant);
	
	/**
	 * 该方法用来得到某个用户参与的所有正在召开的即时会议
	 */
	public Collection<Conference> getHoldingInstantConferences(User participant);
	
	/**
	 * 该方法用来得到某个用户参与的普通会议
	 */
	public Collection<Conference> getNormalConferences(User participant);
	
	/**
	 * 该方法用来删除一个会议
	 */
	public void delete(Conference conference);

	/**
	 * 创建一个普通会议
	 */
	public Conference createNormalConference(String title, String creatorId, Date startTime, Date endTime, String desc);
	
	/**
	 * 创建一个即时会议
	 */
	public Conference createInstantConference(String title, User creator);
	
	/**
	 * 通过用户名获取用户
	 */
	public User getUserByAccountName(String accountName);
	
	/**
	 * 通过用户的邮箱获取用户
	 */
	public User getUserByEmail(String email);

	/**
	 * 通过用户的id获取用户
	 */
	public User getUser(String userId);

	/**
	 * 创建一个用户
	 */
	public User createUser(String accountName, String password, String firstName, String lastName, String email);
	
	/**
	 * 得到所有的用户
	 */
	public Collection<User> getUsers();
	
	/**
	 * 该方法用来判断某个用户是不是公共联系人组的根组
	 */
	public boolean isAdministrator(User user);

	/**
	 * 通过组id得到该组
	 */
	public Group getGroup(String groupId);
	
	/**
	 * 删除一个组
	 */
	public void delete(Group group);
	
	/**
	 * 删除一个用户
	 */
	public void delete(User user);

	/**
	 * 获取所有的会议模式
	 */
	public Collection<Mode> getModes();

	/**
	 * 获取所有预定义的会议模式
	 */
	public Collection<Mode> getPredefinedModes();

	/**
	 * 通过会议模式id，获取该模式
	 */
	public Mode getMode(String modelId);

	/**
	 * 获取某个用户收到的所有留言
	 */
	public Queue<String> getMessageStore(User receiver);
	
	/**
	 * 获取公共联系人组
	 */
	public Group getCommonContactGroup();
	
	/**
	 * 创建留言
	 */
	public Message createMessage(String senderId, String title, String content, Date saveDate); 
	
	/**
	 * 添加即时会议
	 */
	public void addInstantConference(Conference con);
	
	/**
	 * 结束即时会议
	 */
	public void finishInstantConference(String conId);
	
	/**
	 * 获取所有公司的正在进行的正式会议
	 */
	public Collection<Conference> getAllHoldingConferences();
	
	/**
	 * 获取租户节点id
	 */
	public String getLeaserId();
}
