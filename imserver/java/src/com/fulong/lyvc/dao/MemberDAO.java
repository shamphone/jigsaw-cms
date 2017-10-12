/**
 * 
 */
package com.fulong.lyvc.dao;

import java.sql.SQLException;

/**
 * MemberDAO
 *
 * 龙驭视频会议系统 v3.0
 *
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 *
 * @author 李雄锋
 *
 * 最后修改时间：2009-3-18
 */
public interface MemberDAO extends DAO{
	
	public long getParentId(long Id) throws SQLException ;
	/**
	 * make the user as the child of the group identified by parentGroupId
	 */
	public void insert(long parentGroupId, long userId)
			throws SQLException;

	/**
	 * make the user as the child of the group identified by parentGroupId
	 */
	public boolean isMember(long parentGroupId, long userId)
			throws SQLException;

	/**
	 * remove the member from the group
	 */
	public void delete(long groupId, long memberId) throws SQLException;

	/**
	 * remove the member from the group
	 */
	public void deleteByGroup(long groupId) throws SQLException;

}
