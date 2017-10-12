package com.fulong.lyvc.dao;

import java.sql.SQLException;

import com.fulong.lyvc.data.ConferenceData;

/**
 * 
 * ConferenceSession
 * 
 * 龙驭视频会议系统 v3.0
 * 
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 * 
 * @author 李雄锋
 * 
 *         最后修改时间：2009-3-11
 */
public interface ConferenceDAO extends DAO {

	//
	// Conference interface
	//

	// 会议查看，会议不存在时返回null
	public ConferenceData findById(long conferenceID) throws SQLException;

	// 会议创建
	public long insert(ConferenceData conference) throws SQLException;

	// 会议修改
	public void update(ConferenceData conference) throws SQLException;

	// 会议删除
	public void delete(long conferenceID) throws SQLException;

	// 获得会议列表
	public ConferenceData[] findAll() throws SQLException;

	/**
	 * 获取用户创建的会议列表
	 * 
	 * @param creatorId
	 * @return
	 * @throws SQLException
	 */
	public ConferenceData[] findByCreator(long creatorId)
			throws SQLException;

	//
	// Right interface
	//

	// 删除一个权限
//	public void deleteConferenceRight(long rightID)	throws SQLException;

	// 取得一个权限
//	public ConferenceRightData getConferenceRight(long rightID)	throws SQLException;

	// 获得所有可用权限
	// public abstract ArrayList getAllConferenceRight() throws SQLException;

	// 从会议中删除一个用户
	// public abstract void deleteConferenceUser(long confId, long userId)
	// throws SQLException;

	// 获取用户在系统中的角色id
	// public abstract long getUserRole(long userId) throws SQLException;

//	public abstract void createBulletin(String title, String content)	throws SQLException, IOException;

//	public abstract void createConferenceNotice(long conferenceId,	String title, String content) throws SQLException, IOException;

	/*
	 * // 用户管理，通过web修改用户及公共联系人后通知会议服务器，应该由usersession来实现。 //
	 * 目前放在这儿是为了利用conferencesession现成的listener public abstract void
	 * notifyListenerRemoveUser(long userId) throws SQLException;
	 * 
	 * public abstract void notifyListenerAddUser(long id, String name, String
	 * passwd, String fname, String lname, String email) throws SQLException,
	 * IOException;
	 * 
	 * public abstract void notifyListenerSetUserPassword(long userId, String
	 * password) throws SQLException, IOException;
	 * 
	 * public abstract void notifyListenerAddGroup(long parentGroupId, long
	 * groupId, String groupName) throws SQLException, IOException;
	 * 
	 * public abstract void notifyListenerModifyGroup(long groupId, String
	 * groupName, String groupDesc) throws SQLException, IOException;
	 * 
	 * public abstract void notifyListenerRemoveGroup(long groupId) throws
	 * SQLException, IOException;
	 * 
	 * public abstract void notifyListenerAddGroupMember(long groupId, long
	 * userId) throws SQLException, IOException;
	 * 
	 * public abstract void notifyListenerDelGroupMember(long groupId, long
	 * memberId) throws SQLException, IOException;
	 * 
	 * public abstract void notifyListenerMoveGroupMember(long fromGroupId, long
	 * toGroupId, long memberId) throws SQLException, IOException;
	 */
	public void deleteByModel(int model) throws SQLException;

	public void deleteByCreatorId(long creatorId) throws SQLException;
}
