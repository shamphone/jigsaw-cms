/**
 * 
 */
package com.fulong.lyvc.dao;

import java.sql.SQLException;

/**
 * ConferenceMemberDAO
 *
 * 龙驭视频会议系统 v3.0
 *
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 *
 * @author 李雄锋
 *
 * 最后修改时间：2009-3-17
 */
public interface ConferenceMemberDAO extends DAO{
	public  void insert(long conferenceID, long modelRoleID, long userID) throws SQLException;

	public long[] findByConference(long conferenceId) throws SQLException;


	public void delete(long confId, long userId) throws SQLException;

	public void deleteByConference(long confId) throws SQLException;

	public void deleteByUser(long userId) throws SQLException;

	public void deleteByMode(int type) throws SQLException;

	public boolean exists(long conferenceId, long userId) throws SQLException;
}
