/**
 * 
 */
package com.fulong.lyvc.dao;

import java.sql.SQLException;

import com.fulong.lyvc.data.GroupData;

/**
 * GroupDAO
 * 
 * 龙驭视频会议系统 v3.0
 * 
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 * 
 * @author 李雄锋
 * 
 *         最后修改时间：2009-3-18
 */
public interface GroupDAO extends DAO {
	/**
	 * 
	 * @param data
	 * @return
	 * @throws SQLException
	 */
	public long insert(GroupData data) throws SQLException;

	/**
	 * modify a group
	 */
	public void update(GroupData data) throws SQLException;

	/**
	 * get group by id
	 */
	public GroupData findById(long groupId) throws SQLException;

	/**
	 * get all subgroups under the group
	 */
	public GroupData[] findByParent(long groupId) throws SQLException;

	/**
	 * delete the group
	 */
	public void delete(long groupId) throws SQLException;

	/**
	 * set a new password of user
	 */
	public void updateManager(long groupId, long userId)
			throws SQLException;

	public boolean isGroupManager(long userId) throws SQLException;

}
