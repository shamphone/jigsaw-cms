/**
 * 
 */
package com.fulong.longcon.chart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

import org.jfree.data.jdbc.JDBCPieDataset;;

/**
 * 
 * 
 * Coolink协同工作框架模型
 * 
 * Copyright: 2009-2011 北京中科辅龙计算机技术股份有限公司
 * 
 * Company: 北京中科辅龙计算机技术股份有限公司
 * 
 * @author lixf
 * 
 * @version 2.0
 * 
 */
public class PreparedJDBCPieDataset extends JDBCPieDataset{

	public PreparedJDBCPieDataset(Connection con, String query) throws SQLException {
		super(con, query);
	}

	public PreparedJDBCPieDataset(String url, String driverName, String user, String password) throws SQLException, ClassNotFoundException {
		super(url, driverName, user, password);
	}

	public PreparedJDBCPieDataset(Connection con) {
		super(con);
	}

	public void executeQuery(Connection con, String query, Object[] params) throws SQLException {

		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = con.prepareStatement(query);
			for (int i = 0; i < params.length; i++) {
				statement.setObject(i + 1, params[i]);
			}
			resultSet = statement.executeQuery();

			ResultSetMetaData metaData = resultSet.getMetaData();

			int columnCount = metaData.getColumnCount();
			if (columnCount != 2) {
				throw new SQLException("Invalid sql generated.  PieDataSet requires 2 columns only");
			}

			int columnType = metaData.getColumnType(2);
			double value = Double.NaN;
			while (resultSet.next()) {
				Comparable<String> key = resultSet.getString(1);
				if(key==null)
					key= "";
				switch (columnType) {
				case Types.NUMERIC:
				case Types.REAL:
				case Types.INTEGER:
				case Types.DOUBLE:
				case Types.FLOAT:
				case Types.DECIMAL:
				case Types.BIGINT:
					value = resultSet.getDouble(2);
					setValue(key, value);
					break;

				case Types.DATE:
				case Types.TIME:
				case Types.TIMESTAMP:
					Timestamp date = resultSet.getTimestamp(2);
					value = date.getTime();
					setValue(key, value);
					break;

				default:
					System.err.println("JDBCPieDataset - unknown data type");
					break;
				}
			}

			fireDatasetChanged();

		} finally {
			if (resultSet != null) 
					resultSet.close();
			if (statement != null) 
					statement.close();
		}
	}
	
	public void executeQueryDirect(Connection con, String query, Object[] params) throws SQLException {

		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = con.prepareStatement(query);
			for (int i = 0; i < params.length; i++) {
				statement.setObject(i + 1, params[i]);
			}
			resultSet = statement.executeQuery();

			ResultSetMetaData metaData = resultSet.getMetaData();

			int columnCount = metaData.getColumnCount();
			if (columnCount != 2) {
				throw new SQLException("Invalid sql generated.  PieDataSet requires 1 columns only");
			}
			int columnType = metaData.getColumnType(2);
			while (resultSet.next()) {
				Comparable<String> key = resultSet.getString(1);
				if(key==null)
					key= "";
				switch (columnType) {
				case Types.NUMERIC:
				case Types.REAL:
				case Types.INTEGER:
				case Types.DOUBLE:
				case Types.FLOAT:
				case Types.DECIMAL:
				case Types.BIGINT:
					double value = resultSet.getDouble(2);
					setValue(key, value);
					break;
				case Types.CHAR:
				case Types.VARCHAR:
                case Types.LONGVARCHAR:
                	String string = (String) resultSet.getObject(2);
                	try {
                		if(string==null||string.equals("")){
                			string = "0";
                		}
                		Number number = Double.valueOf(string);
                		setValue(key, number);
                	}
                	catch (NumberFormatException e) {
                		// suppress (value defaults to null)
                	}
                	break;
				default:
					System.err.println("JDBCPieDataset - unknown data type");
					break;
				}
			}

			fireDatasetChanged();

		} finally {
			if (resultSet != null) 
					resultSet.close();
			if (statement != null)
					statement.close();
		}
	}

	private static final long serialVersionUID = 937723565133819672L;

}
