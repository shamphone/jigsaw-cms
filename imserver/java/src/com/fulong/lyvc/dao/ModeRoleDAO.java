/**
 * 
 */
package com.fulong.lyvc.dao;

import java.sql.SQLException;

import com.fulong.lyvc.data.ModeRoleData;

/**
 * ConferenceModelRoleDAO
 * 
 * 龙驭视频会议系统 v3.0
 * 
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 * 
 * @author 李雄锋
 * 
 *         最后修改时间：2009-3-17
 */
public interface ModeRoleDAO extends DAO {

	/**
	 * 
	 * @param modelId
	 * @throws SQLException
	 */
	public void deleteByModelId(long modelId) throws SQLException;

	/**
	 * 
	 * @param data
	 * @return
	 * @throws SQLException
	 */
	public long insert(ModeRoleData data) throws SQLException;

	/**
	 * 
	 * @param role
	 * @throws SQLException
	 */
	public void update(ModeRoleData role) throws SQLException;

	/**
	 * getRoles
	 * 
	 * @return ConferenceModelRole[]
	 * @todo Implement this com.fulong.lyvc.conferencelibrary.ConferenceModel
	 *       method
	 */
	public ModeRoleData[] findByModeId(long modeId) throws SQLException;
	/**
	 * 
	 * @param conferenceId
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public ModeRoleData[] findByConferenceAndUser(long conferenceId, long userId) throws SQLException;
	/**
	 * 
	 * @param roleID
	 * @throws SQLException
	 */
	public void delete(long roleID) throws SQLException;

	public ModeRoleData findByID(long conferenceModelRoleID) throws SQLException;

}
