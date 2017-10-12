package com.fulong.longcon.calendar.dao.oracle;

import com.fulong.common.dao.JdbcDao;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.fulong.longcon.calendar.dao.HolidaysDao;
import com.fulong.longcon.calendar.data.HolidaysData;

public class OracleHolidaysDao extends JdbcDao implements HolidaysDao {
	private static final Log log = LogFactory.getLog(OracleHolidaysDao.class);

	private static final String SQL_FIND_BY_ID = "select * from HOLIDAYS  where ID=? ";

	/**
	 * 
	 * @param ID
	 *            String
	 * @return HolidaysData
	 * @throws SQLException
	 */
	public HolidaysData findByID(String ID) throws SQLException {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(SQL_FIND_BY_ID);
			statement.setString(1, ID);
			HolidaysData[] result = this.retrieve(statement, SQL_FIND_BY_ID);
			if (result.length > 0) {
				return result[0];
			}
			return null;

		} finally {
			this.close(statement);
		}
	}

	private static final String SQL_Find_ALL = "select * from HOLIDAYS";

	/**
	 * 
	 * @return HolidaysData[]
	 * @throws SQLException
	 */
	public HolidaysData[] findAll() throws SQLException {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(SQL_Find_ALL);
			HolidaysData[] result = this.retrieve(statement, SQL_Find_ALL);
			return result;
		} finally {
			this.close(statement);
		}

	}

	private static final String SQL_INSERT = "insert into HOLIDAYS(ID, DISPLAY_NAME, PARENT_ID) values (?, ?, ?)";

	/**
	 * 
	 * @param data
	 *            HolidaysData
	 * @throws SQLException
	 */
	public void insert(HolidaysData data) throws SQLException {
		PreparedStatement statement = null;
		try {
			statement = this.connection.prepareStatement(SQL_INSERT);
			statement.setString(1, data.getId());
			statement.setString(2, data.getDisplayName());
			statement.setString(3, data.getParentId());
			statement.executeUpdate();
		} finally {
			this.close(statement);
		}
	}

	private static final String SQL_UPDATE = "update HOLIDAYS set DISPLAY_NAME=?, PARENT_ID=? where ID =?";

	/**
	 * 
	 * @param data
	 *            HolidaysData
	 * @throws SQLException
	 */
	public void update(HolidaysData data) throws SQLException {
		PreparedStatement statement = null;
		try {
			statement = this.connection.prepareStatement(SQL_UPDATE);
			statement.setString(3, data.getId());
			statement.setString(1, data.getDisplayName());
			statement.setString(2, data.getParentId());
			statement.executeUpdate();
		} finally {
			this.close(statement);
		}

	}

	private HolidaysData[] retrieve(PreparedStatement statement, String sql)
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
		ArrayList<HolidaysData> result = new ArrayList<HolidaysData>();
		while (rs.next()) {
			HolidaysData group = new HolidaysData();
			this.populate(rs, group);
			result.add(group);
		}
		rs.close();
		statement.close();
		return (HolidaysData[]) result.toArray(new HolidaysData[result.size()]);
	}

	protected void populate(ResultSet rs, HolidaysData data)
			throws SQLException {
		data.setId(rs.getString("ID"));
		data.setDisplayName(rs.getString("DISPLAY_NAME"));
		data.setParentId(rs.getString("PARENT_ID"));
	}

	private static final String SQL_DELETE = "delete from HOLIDAYS  where ID=? ";

	public void delete(String ID) throws SQLException {
		PreparedStatement statement = null;
		try {
			statement = this.connection.prepareStatement(SQL_DELETE);
			statement.setString(1, ID);
			statement.executeUpdate();
		} finally {
			this.close(statement);
		}

	}

}
