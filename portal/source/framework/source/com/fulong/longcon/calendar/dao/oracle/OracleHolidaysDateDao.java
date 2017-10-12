package com.fulong.longcon.calendar.dao.oracle;

import com.fulong.common.dao.JdbcDao;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.fulong.longcon.calendar.dao.HolidaysDateDao;
import java.util.Date;
import com.fulong.longcon.calendar.data.HolidaysDateData;

public class OracleHolidaysDateDao extends JdbcDao implements HolidaysDateDao {
	
	private static final Log log = LogFactory.getLog(OracleHolidaysDateDao.class);

	/**
	 * 
	 * @param holidaysId
	 *            String
	 * @param date
	 *            Date
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean isHoliday(String holidaysId, Date date) throws SQLException {
		HolidaysDateData data = this.getHolidayDate(holidaysId, date);
		if (data == null)
			return false;
		return data.isIsHoliday();
	}

	private static final String SQL_FIND_BY_IDAndDate = "select * from HOLIDAY_DATE  where HOLIDAYS_ID=? and THE_DATE=?";

	/**
	 * 
	 * @param holidaysId
	 *            String
	 * @param date
	 *            Date
	 * @return HolidaysDateData
	 * @throws SQLException
	 */
	public HolidaysDateData getHolidayDate(String holidaysId, Date date)
			throws SQLException {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(SQL_FIND_BY_IDAndDate);
			statement.setString(1, holidaysId);
			statement.setDate(2, toSQLDate(date));
			HolidaysDateData[] result = this.retrieve(statement,
					SQL_FIND_BY_IDAndDate);
			if (result.length > 0) {
				return result[0];
			}
			return null;
		} finally {
			this.close(statement);
		}

	}

	private static final String SQL_INSERT = "insert into HOLIDAY_DATE(HOLIDAYS_ID, THE_DATE, IS_HOLIDAY) values (?, ?, ?)";

	/**
	 * 
	 * @param data
	 *            HolidaysDateData
	 * @throws SQLException
	 */
	public void insert(HolidaysDateData data) throws SQLException {
		PreparedStatement statement = null;
		try {
			statement = this.connection.prepareStatement(SQL_INSERT);
			statement.setString(1, data.getHolidaysId());
			statement.setDate(2, toSQLDate(data.getDate()));
			statement.setBoolean(3, data.isIsHoliday());
			statement.executeUpdate();
		} finally {
			this.close(statement);
		}
	}

	private static final String SQL_UPDATE = "update HOLIDAY_DATE set IS_HOLIDAY=? where HOLIDAYS_ID=? and THE_DATE=?";

	/**
	 * 
	 * @param data
	 *            HolidaysDateData
	 * @throws SQLException
	 */
	public void update(HolidaysDateData data) throws SQLException {
		PreparedStatement statement = null;
		try {
			statement = this.connection.prepareStatement(SQL_UPDATE);
			statement.setBoolean(1, data.isIsHoliday());
			statement.setString(2, data.getHolidaysId());
			statement.setDate(3, toSQLDate(data.getDate()));
			statement.executeUpdate();
		} finally {
			this.close(statement);
		}

	}

	private static final String SQL_DELETE = "delete from HOLIDAY_DATE  where HOLIDAYS_ID=? and THE_DATE=?";

	/**
	 * 
	 * @param holidaysId
	 *            String
	 * @param date
	 *            Date
	 * @throws SQLException
	 */
	public void delete(String holidaysId, Date date) throws SQLException {
		PreparedStatement statement = null;
		try {
			statement = this.connection.prepareStatement(SQL_DELETE);
			statement.setString(1, holidaysId);
			statement.setDate(2, toSQLDate(date));
			statement.executeUpdate();
		} finally {
			this.close(statement);
		}

	}

	private HolidaysDateData[] retrieve(PreparedStatement statement, String sql)
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
		ArrayList<HolidaysDateData> result = new ArrayList<HolidaysDateData>();
		while (rs.next()) {
			HolidaysDateData group = new HolidaysDateData();
			this.populate(rs, group);
			result.add(group);
		}
		rs.close();
		statement.close();
		return (HolidaysDateData[]) result.toArray(new HolidaysDateData[result.size()]);
	}

	protected void populate(ResultSet rs, HolidaysDateData data)
			throws SQLException {
		data.setHolidaysId(rs.getString("HOLIDAYS_ID"));
		data.setDate(rs.getDate("THE_DATE"));
		data.setIsHoliday(rs.getBoolean("IS_HOLIDAY"));
	}

}
