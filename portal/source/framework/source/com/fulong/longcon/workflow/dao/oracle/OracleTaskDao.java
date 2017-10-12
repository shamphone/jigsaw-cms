package com.fulong.longcon.workflow.dao.oracle;

import com.fulong.common.dao.JdbcDao;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.fulong.common.dao.SQLParameter;
import java.sql.Types;
import java.util.Collections;
import java.util.List;
import com.fulong.common.dao.JdbcException;
import com.fulong.longcon.workflow.dao.TaskDao;
import com.fulong.longcon.workflow.data.TaskData;

/**
 * 
 * <p>
 * Title: 事务处理核心引擎
 * </p>
 * 
 * <p>
 * Description: 龙驭核心引擎
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术股份有限公司 2007
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * @author lishaobo
 * @version 1.0
 */
public class OracleTaskDao extends JdbcDao implements TaskDao {
	private static final Log log = LogFactory.getLog(OracleTaskDao.class);

	private static final String SQL_FIND_BY_ID = "select * from CONTENT_TASK where PKID=? ";

	/**
	 * 
	 * @param id
	 *            String
	 * @return ContentTaskData
	 * @throws SQLException
	 */
	public TaskData findByID(String id) throws SQLException {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(SQL_FIND_BY_ID);
			statement.setString(1, id);
			TaskData[] result = retrieve(statement, SQL_FIND_BY_ID);
			if ((result != null) && (result.length > 0))
				return result[0];
			return null;
		} finally {
			close(statement);
		}

	}

	private static final String SQL_GET_CURRENT = "select * from CONTENT_TASK where CONTENT_ID=? and CATEGORY_ID=? and NEXT_ID='0' ";

	public TaskData getCurrentTask(String contentId, String categoryId)
			throws SQLException {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(SQL_GET_CURRENT);
			statement.setString(1, contentId);
			statement.setString(2, categoryId);
			TaskData[] result = retrieve(statement, SQL_GET_CURRENT);
			if ((result != null) && (result.length > 0))
				return result[0];
			return null;
		} finally {
			close(statement);
		}
	}

	private static final String SQL_INSERT = " insert into CONTENT_TASK (PKID, CONTENT_ID, PREV_ID, NEXT_ID, ACTIVITY, BEGIN_DATE, EXECUTE_DATE, "
			+ " DEADLINE, ASSIGNEE_ID, EXECUTOR_ID, EXECUTED, MESSAGE,CATEGORY_ID) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";

	/**
	 * 
	 * @param data
	 *            ContentData
	 * @throws SQLException
	 */
	public void insert(TaskData data) throws SQLException {
		PreparedStatement statement = null;
		try {
			if (data.getPkid() == null)
				data.setPkid(getNextID() + "");
			statement = connection.prepareStatement(SQL_INSERT);
			statement.setString(1, data.getPkid());
			statement.setString(2, data.getContentID());
			statement.setString(3, data.getPrevID());
			statement.setString(4, data.getNextID());
			statement.setString(5, data.getActivity());
			if (data.getBeginDate() != null)
				statement.setDate(6, toSQLDate(data.getBeginDate()));
			else
				statement.setDate(6, null);
			if (data.getExecuteDate() != null)
				statement.setDate(7, toSQLDate(data.getExecuteDate()));
			else
				statement.setDate(7, null);
			if (data.getDeadline() != null)
				statement.setDate(8, toSQLDate(data.getDeadline()));
			else
				statement.setDate(8, null);
			statement.setString(9, data.getAssigneeID());
			statement.setString(10, data.getExecutorID());
			statement.setBoolean(11, data.getExecuted());
			statement.setString(12, data.getMessage());
			statement.setString(13, data.getCategoryID());
			statement.executeUpdate();
		} finally {
			close(statement);
		}
	}

	private static final String SQL_UPDATE = " update CONTENT_TASK t set CONTENT_ID=?, PREV_ID=?, NEXT_ID=?, ACTIVITY=?, BEGIN_DATE=?, EXECUTE_DATE=?, "
			+ " DEADLINE=?, ASSIGNEE_ID=?, EXECUTOR_ID=?, EXECUTED=?, MESSAGE=?, CATEGORY_ID=? where PKID =?";

	/**
	 * 
	 * @param data
	 *            ContentData
	 * @throws SQLException
	 */
	public void update(TaskData data) throws SQLException {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(SQL_UPDATE);
			statement.setString(1, data.getContentID());
			statement.setString(2, data.getPrevID());
			statement.setString(3, data.getNextID());
			statement.setString(4, data.getActivity());
			if (data.getBeginDate() != null)
				statement.setDate(5, toSQLDate(data.getBeginDate()));
			else
				statement.setDate(5, null);
			if (data.getExecuteDate() != null)
				statement.setDate(6, toSQLDate(data.getExecuteDate()));
			else
				statement.setDate(6, null);
			if (data.getDeadline() != null)
				statement.setDate(7, toSQLDate(data.getDeadline()));
			else
				statement.setDate(7, null);

			statement.setString(8, data.getAssigneeID());
			statement.setString(9, data.getExecutorID());
			statement.setBoolean(10, data.getExecuted());
			statement.setString(11, data.getMessage());
			statement.setString(12, data.getCategoryID());
			statement.setString(13, data.getPkid());
			statement.executeUpdate();
		} finally {
			close(statement);
		}
	}

	private static final String SQL_DELETE = " delete from CONTENT_TASK t  where CONTENT_ID=? and CATEGORY_ID=?";

	/**
	 * 
	 * @param contentID
	 *            String
	 * @param categoryID
	 *            String
	 * @throws SQLException
	 */
	public void delete(String contentID, String categoryID) throws SQLException {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(SQL_DELETE);
			statement.setString(1, contentID);
			statement.setString(2, categoryID);
			statement.executeUpdate();
		} finally {
			close(statement);
		}
	}

	private TaskData[] retrieve(PreparedStatement statement, String sql)
			throws SQLException {
		long start = 0;
		if (log.isTraceEnabled()) {
			start = System.currentTimeMillis();
		}
		ResultSet rs = statement.executeQuery();
		if (log.isTraceEnabled()) {
			log.trace("[" + (System.currentTimeMillis() - start) + "]: query "
					+ sql);
		}
		ArrayList<TaskData> result = new ArrayList<TaskData>();
		while (rs.next()) {
			TaskData group = new TaskData();
			;
			populate(rs, group);
			result.add(group);
		}
		rs.close();
		statement.close();
		return (TaskData[]) result.toArray(new TaskData[result.size()]);
	}

	/**
	 * 
	 * @param query
	 *            String
	 * @param parameters
	 *            SQLParameter[]
	 * @return ContentTaskData[]
	 * @throws SQLException
	 */
	public TaskData[] search(String query, SQLParameter[] parameters)
			throws SQLException {
		PreparedStatement statement = null;
		try {
			statement = createStatement(query, parameters);
			TaskData[] datas = retrieve(statement, query);
			return datas;
		} catch (SQLException ex) {
			throw new JdbcException(ex);
		} finally {
			close(statement);
		}

	}

	public TaskData[] search(String query, SQLParameter[] parameters,
			int fromIndex, int number) throws SQLException {
		String sql = toRangeQuery(query);
		PreparedStatement statement = null;
		try {

			List<SQLParameter> all = new ArrayList<SQLParameter>();
			if (parameters != null)
				Collections.addAll(all, parameters);
			all.add(new SQLParameter(Types.INTEGER, new Integer(fromIndex
					+ number)));
			all.add(new SQLParameter(Types.INTEGER, new Integer(fromIndex)));

			statement = createStatement(sql, (SQLParameter[]) all
					.toArray(new SQLParameter[all.size()]));

			TaskData[] datas = retrieve(statement, sql);
			return datas;

		} catch (SQLException ex) {
			throw new JdbcException(ex);
		} finally {
			close(statement);
		}

	}

	private PreparedStatement createStatement(String sql,
			SQLParameter[] parameters) {
		PreparedStatement command = null;
		try {
			command = connection.prepareStatement(sql);
			if ((parameters != null) && (parameters.length > 0)) {
				for (int i = 0; i < parameters.length; i++) {
					command.setObject(i + 1, parameters[i].getValue());
				}
			}
			return command;
		} catch (SQLException ex) {
			throw new JdbcException(ex);
		}

	}

	public int countResultNum(String query, SQLParameter[] parameters)
			throws SQLException {
		int result = 0;
		PreparedStatement command = null;
		try {
			command = createStatement(query, parameters);
			result = queryInt(command);
			return result;
		} catch (SQLException ex) {
			throw new JdbcException(ex);
		} finally {
			close(command);
		}
	}

	protected void populate(ResultSet rs, TaskData data) throws SQLException {
		data.setPkid(rs.getString("PKID"));
		data.setContentID(rs.getString("CONTENT_ID"));
		data.setPrevID(rs.getString("PREV_ID"));
		data.setNextID(rs.getString("NEXT_ID"));
		data.setActivity(rs.getString("ACTIVITY"));

		data.setBeginDate(rs.getDate("BEGIN_DATE"));
		data.setExecuteDate(rs.getDate("EXECUTE_DATE"));
		data.setDeadline(rs.getDate("DEADLINE"));

		data.setAssigneeID(rs.getString("ASSIGNEE_ID"));
		data.setExecutorID(rs.getString("EXECUTOR_ID"));

		data.setExecuted(rs.getBoolean("EXECUTED"));
		data.setMessage(rs.getString("MESSAGE"));
		data.setCategoryID(rs.getString("CATEGORY_ID"));
	}
}
