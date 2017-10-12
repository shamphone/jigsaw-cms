/**
 * 
 */
package com.fulong.lyvc.dao;

import java.sql.SQLException;

/**
 * ContactMemberDAO
 * 
 * 龙驭视频会议系统 v3.0
 * 
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 * 
 * @author 李雄锋
 * 
 *         最后修改时间：2009-3-18
 */
public interface ContactMemberDAO extends DAO {

	/**
	 * addGroupMember
	 * 
	 * @param userId
	 *            long
	 * @param groupId
	 *            long
	 * @throws SQLException
	 */
	public void insert(long userId, long groupId) throws SQLException;

	/**
	 * removeGroupMember
	 * 
	 * @param userId
	 *            long
	 * @param groupId
	 *            long
	 * @throws SQLException
	 */
	public void delete(long userId, long groupId) throws SQLException;

	/**
	 * 
	 * @param userId
	 * @param groupId
	 * @return
	 * @throws SQLException
	 */
	public boolean isMember(long userId, long groupId) throws SQLException;

}
