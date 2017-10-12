package com.fulong.longcon.counter.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.fulong.common.dao.JdbcDao;
import com.fulong.longcon.counter.dao.ClickCountDao;

public class MysqlClickCountDao extends JdbcDao implements ClickCountDao{
	private static final String SQL_INSERT =
	      "insert into clickcount(SUMVALUE, NAME) values (?, ?)";
		public void insertCount(String name) throws SQLException{
		    PreparedStatement statement = null;
		    try {
		        statement = this.connection.prepareStatement(
		                SQL_INSERT);
		        statement.setLong(1, 0l);
		        statement.setString(2, name);
		        statement.executeUpdate();
		        statement.close();
		    } finally {
		        this.close(statement);
		    }
		}
		
		private static final String SQL_SELECT =
		  "SELECT sumvalue FROM clickcount WHERE name=?";
		public long loadCount(String name)throws SQLException {
		    PreparedStatement statement = null;
		    try {
		        statement = this.connection.prepareStatement(
		                SQL_SELECT);
		        statement.setString(1, name);
		        return this.queryLong(statement);
		    } finally {
		        this.close(statement);
		    }
		}
		
		private static final String SQL_UPDATE =
		  "UPDATE clickcount SET sumvalue=? where name=? ";
		public void saveCount(String name, long newValue)throws SQLException {
		    PreparedStatement statement = null;
		    try {
		        statement = this.connection.prepareStatement(
		                SQL_UPDATE);
		        statement.setLong(1, newValue);
		        statement.setString(2, name);
		        statement.executeUpdate();
		    } finally {
		        this.close(statement);
		    }
		}
}
