package com.fulong.lyvc.dao;


import java.sql.SQLException;

import com.fulong.lyvc.data.UserData;

/**
 * 
 * UserSession
 *
 * 龙驭视频会议系统 v3.0
 *
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 *
 * @author 李雄锋
 *
 * 最后修改时间：2009-3-11
 */
public interface UserDAO extends DAO{
/**
 * 插入用户数据
 * @param userData
 * @return
 * @throws SQLException
 */
	public long insert(UserData userData) throws SQLException;

	/**
	 * Modify a user
	 */
	public void update(UserData userData) throws SQLException;

	/**
	 * Authenticate the user, if successful, return user, otherwise null
	 * @throws SQLException 
	 */
	public UserData authenticate(String username, String password) throws SQLException;

	/**
	 * Get user by id, if user not found, return null
	 * @throws SQLException 
	 */
	public UserData getUserById(long id) throws SQLException;

	/**
	 * Get user by email, if user not found, return null If multiple user found,
	 * only return the first one
	 * @throws SQLException 
	 */
	public UserData getByEmail(String email) throws SQLException;

	/**
	 * Get user by accountName, if user not found, return null If multiple user
	 * found, only return the first one
	 * @throws SQLException 
	 */
	public UserData getByAccountName(String accountName) throws SQLException;


/**
 * 获取组里面的所有成员
 * @param groupId
 * @return
 * @throws SQLException
 */
	public UserData[] getMembers(long groupId) throws SQLException;

	/**
	 * delete the user
	 */
	public void removeUser(long userId) throws SQLException;

	
	/**
	 * set a new password of user
	 */
	public void setUserPassword(long userId, String password) throws SQLException;

	/**
	 * get all users id
	 * @throws SQLException 
	 */
	public UserData[] getAllUsers() throws SQLException;

	/**
	 * get all users id
	 * @throws SQLException 
	 */
	public UserData[] findByConference(long conferenceId) throws SQLException;

	/**
	 * get all users id
	 * @throws SQLException 
	 */
	public UserData[] findByConference(long conferenceId, long roleId) throws SQLException;

	/**
	 * get all users id
	 * @throws SQLException 
	 */
	public UserData[] findByContactGroup(long groupId) throws SQLException;

}
