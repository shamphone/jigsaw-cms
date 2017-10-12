/**
 * 
 */
package com.fulong.longcon.chart;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;

import org.jfree.data.jdbc.JDBCCategoryDataset;
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
public class PreparedJDBCCategoryDataset extends JDBCCategoryDataset{

	public PreparedJDBCCategoryDataset(Connection connection) {
		super(connection);	
	}
	 /**
     * Populates the dataset by executing the supplied query against the 
     * existing database connection.  If no connection exists then no action 
     * is taken.
     * <p>
     * The results from the query are extracted and cached locally, thus 
     * applying an upper limit on how many rows can be retrieved successfully.
     *
     * @param con  the connection.
     * @param query  the query.
     * 
     * @throws SQLException if there is a problem executing the query.
     */
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

            if (columnCount < 2) {
                throw new SQLException(
                    "JDBCCategoryDataset.executeQuery() : insufficient columns "
                    + "returned from the database.");
            }

            // Remove any previous old data
            int i = getRowCount();
            while (--i >= 0) {
                removeRow(i);
            }

            while (resultSet.next()) {
                // first column contains the row key...
                Comparable<String> rowKey = resultSet.getString(1);
                if(rowKey==null){
                	rowKey = "";
                }
                for (int column = 2; column <= columnCount; column++) {

                    Comparable<String> columnKey = metaData.getColumnName(column);
                    int columnType = metaData.getColumnType(column);

                    switch (columnType) {
                        case Types.TINYINT:
                        case Types.SMALLINT:
                        case Types.INTEGER:
                        case Types.BIGINT:
                        case Types.FLOAT:
                        case Types.DOUBLE:
                        case Types.DECIMAL:
                        case Types.NUMERIC:
                        case Types.REAL: {
                            Number value = (Number) resultSet.getObject(column);
                            if (this.getTranspose()) {
                                setValue(value, columnKey, rowKey);
                            }
                            else {
                                setValue(value, rowKey, columnKey);
                            }
                            break;
                        }
                        case Types.DATE:
                        case Types.TIME:
                        case Types.TIMESTAMP: {
                            Date date = (Date) resultSet.getObject(column);
                            Number value = new Long(date.getTime());
                            if (this.getTranspose()) {
                                setValue(value, columnKey, rowKey);
                            }
                            else {
                                setValue(value, rowKey, columnKey);
                            }
                            break;
                        }
                        case Types.CHAR:
                        case Types.VARCHAR:
                        case Types.LONGVARCHAR: {
                            String string 
                                = (String) resultSet.getObject(column);
                            try {
                                Number value = Double.valueOf(string);
                                if (this.getTranspose()) {
                                    setValue(value, columnKey, rowKey);
                                }
                                else {
                                    setValue(value, rowKey, columnKey);
                                }
                            }
                            catch (NumberFormatException e) {
                                // suppress (value defaults to null)
                            }
                            break;
                        }
                        default:
                            // not a value, can't use it (defaults to null)
                            break;
                    }
                }
            }

            fireDatasetChanged();
        }
        finally {
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

            // Remove any previous old data
            int i = getRowCount();
            while (--i >= 0) {
                removeRow(i);
            }
            while (resultSet.next()) {
                // first column contains the row key...
                String rowKey = resultSet.getString(1);
                for (int column = 2; column <= columnCount; column++) {

                    Comparable<String> columnKey = metaData.getColumnName(column);
                    int columnType = metaData.getColumnType(column);

                    switch (columnType) {
	                    case Types.INTEGER:
	                    case Types.BIGINT:
	                    case Types.FLOAT:
	                    case Types.NUMERIC:
	                    	Number number = (Number) resultSet.getObject(column);
                            if (this.getTranspose()) {
                            	setValue(number, rowKey, columnKey);
                            } else {
                            	setValue(number, columnKey, rowKey);
                            }
                            break;
                        case Types.CHAR:
                        case Types.VARCHAR:
                        case Types.LONGVARCHAR:
	                    	String string = (String) resultSet.getObject(column);
	                        try {
	                        	if(string==null||string.equals("")){
	                        		string = "0";
	                        	}
	                            Number value = Double.valueOf(string);
	                            if (this.getTranspose()) {
	                            	setValue(value, rowKey, columnKey);
	                            } else {
	                            	setValue(value, columnKey, rowKey);
	                            }
	                        }
	                        catch (NumberFormatException e) {
	                            // suppress (value defaults to null)
	                        }
	                        break;
                        default:
                            // not a value, can't use it (defaults to null)
                            break;
                    }
                }
            }
            fireDatasetChanged();
        }
        finally {
            if (resultSet != null)          
                    resultSet.close();
            if (statement != null) 
                    statement.close();
              
        
        }
    }
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 279824336193564721L;

}
