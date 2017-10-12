package com.fulong.lyvc;

import java.sql.Connection;
import java.sql.SQLException;

import com.fulong.lyvc.dao.DAO;

/**
 * DAOFactory
 *
 * 龙驭视频会议系统 v3.0
 *
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 *
 * @author 李雄锋
 *
 * 最后修改时间：2009-3-17
 */

public interface DAOFactory {
	/**
	 * 获取一个连接
	 * @return
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException;
	
	/**
	 * 获取DAO
	 * @param dao
	 * @param connection
	 * @return
	 */
	public <T extends DAO> T getDAO(String dao,Connection connection);

}