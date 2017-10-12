package com.fulong.longcon.counter.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.fulong.common.dao.JdbcDao;
import com.fulong.longcon.counter.dao.WeeklyCountDao;

public class MysqlWeeklyCountDao extends JdbcDao implements WeeklyCountDao {
	private static final String SQL_SELECT = 
		  "SELECT sumvalue FROM WEEKLY_COUNT WHERE name=? and ACCESS_WEEK=? and ACCESS_YEAR = ?";
		@Override
		public long loadCount(String name, long accessWeek, long accessYear) throws SQLException {
			PreparedStatement statement = null;
	        ResultSet rs = null;
	        //初始为小于0的值就可以，用于判断该节点是否在计数器中是否有记录
	        long result = -1;
	        try {
	            statement = this.connection.prepareStatement(
	                SQL_SELECT);
	            statement.setString(1, name);
	            statement.setLong(2, accessWeek);
	            statement.setLong(3, accessYear);
	            rs = statement.executeQuery();
	            if (rs.next())
	                result = rs.getLong(1);
	            return result;
	        } finally {
	            this.close(statement, rs);
	        }
		}
	}
