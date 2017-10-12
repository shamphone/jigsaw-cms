package com.fulong.lyvc.dao;

import java.sql.SQLException;

import com.fulong.lyvc.data.ModeData;

/**
 * 
 * ConferenceModelSession
 * 
 * 龙驭视频会议系统 v3.0
 * 
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 * 
 * @author 李雄锋
 * 
 *         最后修改时间：2009-3-11
 */
public interface ModeDAO extends DAO {

	// 会议模式查看，不存在返回null
	public ModeData findByID(long conferenceModelID) throws SQLException;

	// 查看所有会议模式，不存在返回null
	public ModeData[] findAll() throws SQLException;

	/**
	 * 
	 * @return
	 * @throws SQLException
	 */
	public ModeData[] findUserDefinedModels() throws SQLException;

	// 会议模式创建
	public long insert(ModeData conferenceModel) throws SQLException;

	// 会议模式修改
	public void update(ModeData conferenceModel) throws SQLException;

	// 会议模式删除
	public void delete(long conferenceModelID) throws SQLException;

}
