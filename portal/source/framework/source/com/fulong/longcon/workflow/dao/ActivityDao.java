package com.fulong.longcon.workflow.dao;

import com.fulong.common.dao.JdbcDao;
import com.fulong.longcon.workflow.data.ActivityData;
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
public class ActivityDao extends JdbcDao {
	private static final Log log = LogFactory.getLog(ActivityDao.class);

	private static final String SQL_insert_ActivityData = "INSERT INTO wk_activity t (PKID, NAME, DEFINITION_ID, TYPE,IS_END,DURATION) values (?,?,?,?,?,?)";

	public void insert(ActivityData data) throws SQLException {
		data.setPkid("" + getNextID());
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(SQL_insert_ActivityData);
			statement.setString(1, data.getPkid());
			statement.setString(2, data.getName());
			statement.setString(3, data.getDefinitionID());
			statement.setString(4, data.getType());
			statement.setBoolean(5, data.isEnd());
			statement.setInt(6, data.getDuration());
			long startTime = 0;
			if (log.isTraceEnabled()) {
				startTime = System.currentTimeMillis();
				log.trace("Start execute: " + SQL_insert_ActivityData);
			}
			statement.executeUpdate();
			if (log.isTraceEnabled()) {
				log.trace("End execute, the last time is : "
						+ (System.currentTimeMillis() - startTime + "")
						+ SQL_insert_ActivityData);
			}
		} finally {
			if (statement != null)
				statement.close();
		}

	}

	private static final String SQL_delete_ActivityData = "delete from wk_activity t where t.PKID = ? and definition_id=?";

	public void delete(String definitionID, String ID) throws SQLException {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(SQL_delete_ActivityData);
			statement.setString(1, ID);
			statement.setString(2, definitionID);
			long startTime = 0;
			if (log.isTraceEnabled()) {
				startTime = System.currentTimeMillis();
				log.trace("Start execute: " + SQL_delete_ActivityData);
			}

			statement.executeUpdate();
			if (log.isTraceEnabled()) {
				log.trace("End execute, the last time is : "
						+ (System.currentTimeMillis() - startTime + "")
						+ SQL_delete_ActivityData);
			}
		}

		finally {
			if (statement != null)
				statement.close();
		}

	}

	private static final String SQL_findByPKID = "SELECT * FROM wk_activity t WHERE t.PKID=? and definition_ID=?";

	public ActivityData findByPKID(String pkid, String definitionID)
			throws SQLException {
		PreparedStatement statement = connection
				.prepareStatement(SQL_findByPKID);
		statement.setString(1, pkid);
		statement.setString(2, definitionID);
		ActivityData[] data = this.retrieve(statement, SQL_findByPKID);
		if (data.length == 0) {
			return null;
		}
		return data[0];
	}

	private static final String SQL_findByDefinitionID = "SELECT * FROM wk_activity t WHERE t.DEFINITION_ID=?";

	public ActivityData[] findByDefinitionID(String definitionID)
			throws SQLException {
		PreparedStatement statement = connection
				.prepareStatement(SQL_findByDefinitionID);
		statement.setString(1, definitionID);
		return this.retrieve(statement, SQL_findByDefinitionID);

	}

	private static final String SQL_update_ActivityData = "update wk_activity w set w.NAME=?, w.DEFINITION_ID=?, w.TYPE=?, w.is_end=?, w.duration=?  where w.PKID=?and w.definition_id=?";

	public void update(ActivityData data) throws SQLException {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(SQL_update_ActivityData);
			statement.setString(6, data.getPkid());
			statement.setString(7, data.getDefinitionID());
			statement.setString(1, data.getName());
			statement.setString(2, data.getDefinitionID());
			statement.setString(3, data.getType());
			statement.setBoolean(4, data.isEnd());
			statement.setInt(5, data.getDuration());
			long startTime = 0;
			if (log.isTraceEnabled()) {
				startTime = System.currentTimeMillis();
				log.trace("Start execute: " + SQL_update_ActivityData);
			}
			statement.execute();
			if (log.isTraceEnabled()) {
				log.trace("End execute, the last time is : "
						+ (System.currentTimeMillis() - startTime + "")
						+ SQL_update_ActivityData);
			}
		} finally {
			if (statement != null)
				statement.close();
		}
	}

	public ActivityData[] retrieve(PreparedStatement statement, String sql)
			throws SQLException {
		ResultSet rs = null;
		try {
			long start = 0;
			if (log.isTraceEnabled()) {
				start = System.currentTimeMillis();
			}
			rs = statement.executeQuery();
			if (log.isTraceEnabled()) {
				log.trace("[" + (System.currentTimeMillis() - start)
						+ "]: query " + sql);
			}
			ArrayList<ActivityData> result = new ArrayList<ActivityData>();
			while (rs.next()) {
				ActivityData group = new ActivityData();
				this.populate(rs, group);
				result.add(group);
			}

			return (ActivityData[]) result.toArray(new ActivityData[result.size()]);
		} finally {
			if (rs != null)
				rs.close();
			if (statement != null)
				statement.close();
		}
	}

	protected void populate(ResultSet rs, ActivityData data)
			throws SQLException {
		data.setName(rs.getString("NAME"));
		data.setPkid(rs.getString("PKID"));
		data.setType(rs.getString("TYPE"));
		data.setDefinitionID(rs.getString("DEFINITION_ID"));
		data.setEnd(rs.getBoolean("IS_END"));
		data.setDuration(rs.getInt("DURATION"));
	}
}
