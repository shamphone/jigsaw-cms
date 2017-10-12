/**
 * 
 */
package com.fulong.lyvc.dao;

import java.sql.SQLException;

import com.fulong.lyvc.data.ContactGroupData;

/**
 * ContactGroupDAO
 *
 * 龙驭视频会议系统 v3.0
 *
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 *
 * @author 李雄锋
 *
 * 最后修改时间：2009-3-18
 */
public interface ContactGroupDAO extends DAO {
	public long insert(ContactGroupData data) throws SQLException ;

	/**
	 * renameGroup
	 *
	 * @param groupId long
	 * @param newName String
	 * @throws SQLException 
	 */
	public void rename(long groupId, String newName) throws SQLException;

	public ContactGroupData[] findGroupsByUser(long userId) throws SQLException;

	public ContactGroupData findGroupsById(long id) throws SQLException;
}
