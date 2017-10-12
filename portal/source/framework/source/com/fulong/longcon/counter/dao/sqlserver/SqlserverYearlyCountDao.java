package com.fulong.longcon.counter.dao.sqlserver;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.fulong.common.dao.JdbcDao;
import com.fulong.longcon.counter.dao.YearlyCountDao;

public class SqlserverYearlyCountDao extends JdbcDao implements YearlyCountDao {
	private static final String SQL_SELECT =
      "SELECT sumvalue FROM YEARLY_COUNT WHERE name=? and ACCESS_YEAR = ?";
	@Override
	public long loadCount(String name, int accessYear) throws SQLException {
		PreparedStatement statement = null;
		ResultSet rs = null;
		//初始为小于0的值就可以，用于判断该节点是否在计数器中是否有记录
		long result = -1;
		try {
			statement = this.connection.prepareStatement(SQL_SELECT);
			statement.setString(1, name);
			statement.setInt(2, accessYear);
			rs = statement.executeQuery();
			if (rs.next())
				result = rs.getLong(1);
			return result;
		} finally {
			this.close(statement, rs);
		}
	}
}
