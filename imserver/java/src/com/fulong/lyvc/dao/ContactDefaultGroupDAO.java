/**
 * 
 */
package com.fulong.lyvc.dao;

import java.sql.SQLException;

import com.fulong.lyvc.data.ContactGroupData;

/**
 * ContactDefaultGroupDAO
 *
 * 龙驭视频会议系统 v3.0
 *
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 *
 * @author 李雄锋
 *
 * 最后修改时间：2009-3-18
 */
public interface ContactDefaultGroupDAO extends DAO{

	/**
	 * getDefaultGroupId
	 * 
	 * @param userId
	 *            long
	 * @return long
	 * @throws SQLException
	 */
	public void insert(ContactGroupData data) throws SQLException;

	/**
	 * getDefaultGroupId
	 * 
	 * @param userId
	 *            long
	 * @return long
	 */
	public ContactGroupData findByUser(long userId) throws SQLException;
	/**
	 * getDefaultGroupId
	 * 
	 * @param userId
	 *            long
	 * @return long
	 */
	public ContactGroupData findById(long id) throws SQLException;

}
