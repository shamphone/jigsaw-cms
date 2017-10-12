package com.fulong.longcon.workflow.dao;

import com.fulong.common.dao.JdbcDao;
import com.fulong.longcon.workflow.data.ProcessDefinitionData;
import java.sql.SQLException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * <p>
 * Title: 龙驭工作流系统
 * </p>
 * 
 * <p>
 * Description: 龙驭工作流系统
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c)北京中科辅龙计算机技术股份有限公司 2007
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * @author <a href='mailto:lixf@fulong.com.cn'>lixf</a>
 * @version 1.0
 */
public class ProcessDefinitionDao extends JdbcDao {
	private static final Log log = LogFactory
			.getLog(ProcessDefinitionDao.class);

	/**
	 * 插入一条论坛数据,同时自动生成论坛的唯一识别ID。
	 * 
	 * @param data
	 *            ForumData
	 * @throws SQLException
	 */
	private static final String SQL_insert_ProcessDefinitionData = "INSERT INTO WK_DEFINITION (PKID, NAME, BEGIN_ID, TYPE) values (?,?,?,?)";

	/**
	 * 插入一条过程定义到数据库中,同时为这个过程定义分配一个ID
	 * 
	 * @param data
	 *            ProcessDefinitionData
	 * @throws SQLException
	 */
	public void insert(ProcessDefinitionData data) throws SQLException {
		data.setPkid("" + getNextID());
		PreparedStatement statement = null;
		try {
			statement = connection
					.prepareStatement(SQL_insert_ProcessDefinitionData);
			statement.setString(1, data.getPkid());
			statement.setString(2, data.getName());
			statement.setString(3, data.getBeginID());
			statement.setString(4, data.getType());
			long startTime = 0;
			if (log.isTraceEnabled()) {
				startTime = System.currentTimeMillis();
				log.trace("Start execute: " + SQL_insert_ProcessDefinitionData);
			}
			statement.executeUpdate();
			if (log.isTraceEnabled()) {
				log.trace("End execute, the last time is : "
						+ (System.currentTimeMillis() - startTime + "")
						+ SQL_insert_ProcessDefinitionData);
			}
		} finally {
			if (statement != null)
				statement.close();
		}
	}

	private static final String SQL_delete_ProcessDefinitionData = "delete from WK_DEFINITION f where f.PKID = ?";

	public void delete(String pkid) throws SQLException {
		PreparedStatement statement = null;
		try {
			statement = connection
					.prepareStatement(SQL_delete_ProcessDefinitionData);
			statement.setString(1, pkid);
			long startTime = 0;
			if (log.isTraceEnabled()) {
				startTime = System.currentTimeMillis();
				log.trace("Start execute: " + SQL_delete_ProcessDefinitionData);
			}

			statement.executeUpdate();
			if (log.isTraceEnabled()) {
				log.trace("End execute, the last time is : "
						+ (System.currentTimeMillis() - startTime + "")
						+ SQL_delete_ProcessDefinitionData);
			}
		} finally {
			if (statement != null)
				statement.close();
		}
	}

	private static final String SQL_findByPKID = "SELECT * FROM WK_DEFINITION WHERE PKID=?";

	public ProcessDefinitionData findByPKID(String pkid) throws SQLException {
		PreparedStatement statement = connection
				.prepareStatement(SQL_findByPKID);
		statement.setString(1, pkid);

		ProcessDefinitionData[] data = retrieve(statement, SQL_findByPKID);
		if (data.length == 0) {
			return null;
		}
		return data[0];
	}

	private static final String SQL_findByAll = "SELECT * FROM WK_DEFINITION";

	public ProcessDefinitionData[] findAll() throws SQLException {
		PreparedStatement statement = connection
				.prepareStatement(SQL_findByAll);
		return retrieve(statement, SQL_findByAll);
	}

	private static final String SQL_update_ProcessDefinitionData = "update WK_DEFINITION w set w.NAME=?, w.BEGIN_ID=?,  w.TYPE=? where w.PKID=?";

	/**
	 * 插入一条过程定义到数据库中,同时为这个过程定义分配一个ID
	 * 
	 * @param data
	 *            ProcessDefinitionData
	 * @throws SQLException
	 */
	public void update(ProcessDefinitionData data) throws SQLException {

		PreparedStatement statement = null;
		try {
			statement = connection
					.prepareStatement(SQL_update_ProcessDefinitionData);
			statement.setString(4, data.getPkid());
			statement.setString(1, data.getName());
			statement.setString(2, data.getBeginID());
			statement.setString(3, data.getType());
			long startTime = 0;
			if (log.isTraceEnabled()) {
				startTime = System.currentTimeMillis();
				log.trace("Start execute: " + SQL_update_ProcessDefinitionData);
			}
			statement.executeUpdate();
			if (log.isTraceEnabled()) {
				log.trace("End execute, the last time is : "
						+ (System.currentTimeMillis() - startTime + "")
						+ SQL_update_ProcessDefinitionData);
			}
		} finally {
			if (statement != null)
				statement.close();
		}
	}

	public ProcessDefinitionData[] retrieve(PreparedStatement statement,
			String sql) throws SQLException {
		long start = 0;
		if (log.isTraceEnabled()) {
			start = System.currentTimeMillis();
		}
		ResultSet rs = null;
		try {
			rs = statement.executeQuery();
			if (log.isTraceEnabled()) {
				log.trace("[" + (System.currentTimeMillis() - start)
						+ "]: query " + sql);
			}
			ArrayList<ProcessDefinitionData> result = new ArrayList<ProcessDefinitionData>();
			while (rs.next()) {
				ProcessDefinitionData group = new ProcessDefinitionData();
				populate(rs, group);
				result.add(group);
			}
			return (ProcessDefinitionData[]) result.toArray(new ProcessDefinitionData[result.size()]);
		} finally {
			if (rs != null)
				rs.close();
			statement.close();
		}
	}

	public int retrieveInt(PreparedStatement statement, String sql)
			throws SQLException {
		long start = 0;
		if (log.isTraceEnabled()) {
			start = System.currentTimeMillis();
		}
		ResultSet rs = null;
		try {
			rs = statement.executeQuery();
			if (log.isTraceEnabled()) {
				log.trace("[" + (System.currentTimeMillis() - start) + "]: query " + sql);
			}
			int result = 0;
			if (rs.next())
				result = rs.getInt(1);

			return result;
		} finally {
			rs.close();
			statement.close();
		}
	}

	private static final String SQL_SELECT_SYSTEM_WKFLOW = " select * from wk_definition t Where t.Type =? ";

	/**
	 * type类别的工作流
	 * 
	 * @param type
	 *            String
	 * @return ProcessDefinitionData[]
	 * @throws SQLException
	 */
	public ProcessDefinitionData[] findByType(String type) throws SQLException {
		PreparedStatement statement = connection
				.prepareStatement(SQL_SELECT_SYSTEM_WKFLOW);
		statement.setString(1, type);
		return retrieve(statement, SQL_SELECT_SYSTEM_WKFLOW);

	}

	protected void populate(ResultSet rs, ProcessDefinitionData data)
			throws SQLException {
		data.setBeginID(rs.getString("BEGIN_ID"));
		data.setName(rs.getString("NAME"));
		data.setPkid(rs.getString("PKID"));
		data.setType(rs.getString("TYPE"));
	}

}
