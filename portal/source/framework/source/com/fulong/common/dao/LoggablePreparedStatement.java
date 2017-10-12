/**
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2010-2015
 */
package com.fulong.common.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * LoggablePreparedStatement
 * @author    <a href="lixf@fulong.com.cn">李雄峰</a>
 * @date      2010-9-13
 */
public class LoggablePreparedStatement extends PreparedStatementWrapper{
	private LoggableConnection connection;
	public LoggablePreparedStatement(LoggableConnection connection, PreparedStatement statement){
		super(statement);
		this.connection = connection;
	}
	/**
	 * @param parameterIndex
	 * @param x
	 * @throws SQLException
	 * @see java.sql.PreparedStatement#setDate(int, java.sql.Date)
	 */
	public void setDate(int parameterIndex, Date x) throws SQLException {
		connection.log("["+parameterIndex+","+x+"]");
		super.setDate(parameterIndex, x);
	}
	/**
	 * @param parameterIndex
	 * @param x
	 * @throws SQLException
	 * @see java.sql.PreparedStatement#setDouble(int, double)
	 */
	public void setDouble(int parameterIndex, double x) throws SQLException {
		connection.log("["+parameterIndex+","+x+"]");
		super.setDouble(parameterIndex, x);
	}

	/**
	 * @param parameterIndex
	 * @param x
	 * @throws SQLException
	 * @see java.sql.Preparedsuper#setFloat(int, float)
	 */
	public void setFloat(int parameterIndex, float x) throws SQLException {
		connection.log("["+parameterIndex+","+x+"]");		
		super.setFloat(parameterIndex, x);
	}
	/**
	 * @param parameterIndex
	 * @param x
	 * @throws SQLException
	 * @see java.sql.Preparedsuper#setInt(int, int)
	 */
	public void setInt(int parameterIndex, int x) throws SQLException {
		connection.log("["+parameterIndex+","+x+"]");		
		super.setInt(parameterIndex, x);
	}
	/**
	 * @param parameterIndex
	 * @param x
	 * @throws SQLException
	 * @see java.sql.Preparedsuper#setLong(int, long)
	 */
	public void setLong(int parameterIndex, long x) throws SQLException {
		connection.log("["+parameterIndex+","+x+"]");		
		super.setLong(parameterIndex, x);
	}
	/**
	 * @param parameterIndex
	 * @param sqlType
	 * @param typeName
	 * @throws SQLException
	 * @see java.sql.Preparedsuper#setNull(int, int, java.lang.String)
	 */
	public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException {
		connection.log("["+parameterIndex+",null]");		
		super.setNull(parameterIndex, sqlType, typeName);
	}
	/**
	 * @param parameterIndex
	 * @param sqlType
	 * @throws SQLException
	 * @see java.sql.Preparedsuper#setNull(int, int)
	 */
	public void setNull(int parameterIndex, int sqlType) throws SQLException {
		connection.log("["+parameterIndex+",null]");	
		super.setNull(parameterIndex, sqlType);
	}


	/**
	 * @param parameterIndex
	 * @param x
	 * @throws SQLException
	 * @see java.sql.Preparedsuper#setObject(int, java.lang.Object)
	 */
	public void setObject(int parameterIndex, Object x) throws SQLException {
		connection.log("["+parameterIndex+","+x+"]");			
		super.setObject(parameterIndex, x);
	}
	/**
	 * @param parameterIndex
	 * @param x
	 * @throws SQLException
	 * @see java.sql.Preparedsuper#setShort(int, short)
	 */
	public void setShort(int parameterIndex, short x) throws SQLException {
		connection.log("["+parameterIndex+","+x+"]");			
		super.setShort(parameterIndex, x);
	}
	/**
	 * @param parameterIndex
	 * @param x
	 * @throws SQLException
	 * @see java.sql.Preparedsuper#setString(int, java.lang.String)
	 */
	public void setString(int parameterIndex, String x) throws SQLException {
		connection.log("["+parameterIndex+","+x+"]");	
		super.setString(parameterIndex, x);
	}
	/**
	 * @param parameterIndex
	 * @param x
	 * @throws SQLException
	 * @see java.sql.Preparedsuper#setTime(int, java.sql.Time)
	 */
	public void setTime(int parameterIndex, Time x) throws SQLException {
		connection.log("["+parameterIndex+","+x+"]");	
		super.setTime(parameterIndex, x);
	}
	/**
	 * @param parameterIndex
	 * @param x
	 * @param cal
	 * @throws SQLException
	 * @see java.sql.Preparedsuper#setTimestamp(int, java.sql.Timestamp, java.util.Calendar)
	 */
	public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
		connection.log("["+parameterIndex+","+x+"]");			
		super.setTimestamp(parameterIndex, x, cal);
	}
	/**
	 * @param parameterIndex
	 * @param x
	 * @throws SQLException
	 * @see java.sql.Preparedsuper#setTimestamp(int, java.sql.Timestamp)
	 */
	public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
		connection.log("["+parameterIndex+","+x+"]");			
		super.setTimestamp(parameterIndex, x);
	}
	
}
