package com.fulong.common.dao;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.sql.RowSet;
import javax.sql.rowset.CachedRowSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fulong.common.id.IDGenerator;
import com.sun.rowset.CachedRowSetImpl;

/**
 *
 * <p>Title: WebMaster Core Library</p>
 *
 * <p>Description: Longcon WebMaster SV3</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Beijing Zhongke Fulong Computer Tech. LTD</p>
 *
 * @author Lixf
 * @version 1.0
 */
public class JdbcDao implements Dao {
    public synchronized static long getNextID() {
        return IDGenerator.getInstance().getNextID();
    }

    protected  Connection connection;
    protected static final Log log = LogFactory.getLog(JdbcDao.class);
    public JdbcDao() {
        super();
    }

    /**
     * 复制表的内容,将所有来源表的内容复制到目标表中,这两个表的大纲是一样的.
     * @param srcTable String 来源表
     * @param destTable String 目标表
     * @throws SQLException
     */
    public void copy(String srcTable, String destTable) throws SQLException {
        StringBuffer sql_insert = new StringBuffer(" insert into ");
        sql_insert.append(destTable);
        sql_insert.append(" select * from " + srcTable);
        PreparedStatement srcStatement = connection.prepareStatement(
            sql_insert.toString());
        srcStatement.execute();
    }

    /**
     * 获取有pattern格式的所有表名
     * @param pattern String
     * @return String[]
     * @throws SQLException
     */
    public String[] findTables(String pattern) throws SQLException {
        DatabaseMetaData data = connection.getMetaData();
        ResultSet tables = data.getTables(null, null, pattern, null);
        ArrayList<String> result = new ArrayList<String>();
        while (tables.next()) {
            result.add(tables.getString("TABLE_NAME"));
        }
        tables.close();
        return (String[]) result.toArray(new String[result.size()]);
    }

    /**
     * 初始化连接，这个方法只能调用一次，并且在TransactionContext中调用；
     * @param conn Connection
     */
    protected final void setConnection(Connection conn) {
        if (conn == null) {
            throw new IllegalArgumentException("The connection is null for " +
                                               this.getClass().getName() + "!");
        }
        if (connection != null) {
            throw new UnsupportedOperationException(
                "Connection already set for " +
                this.getClass().getName() + "!");
        }
        connection = conn;
    }

    /**
     * 添加索引,索引的名字为IX_{tableName}_{column}.
     * @param tableName String
     * @param columns String
     * @throws SQLException
     */
    protected void addIndex(String tableName,
                            String column) throws SQLException {
        StringBuffer sql = new StringBuffer("CREATE INDEX ");
        sql.append("IX_" + tableName + "_" + column);
        sql.append(" ON " + tableName);
        sql.append(" USING btree (");
        sql.append(column);
        sql.append(")");
        PreparedStatement sqlParam = connection.prepareStatement(
            sql.toString());
        sqlParam.executeUpdate();
    }

    public boolean isTableExists(String tableName) {
    	/*DatabaseMetaData meta = this.connection.getMetaData();
        boolean exists = false;
        //ResultSet rs = meta.getTables(null, null, tableName.toLowerCase(), null);
        //发现当表明相同时任为false,故修改
        ResultSet rs = meta.getTables(null, null, null, new String[]{"TABLE"});
        while(rs.next()){
        	   //根据需求自己判断
        	if(rs.getString(3).equalsIgnoreCase(tableName)){
        		exists = true;
        		break;
        	}	
        }   
        rs.close();
        return exists;*/
    	//此处为暂时处理办法 by mali
    	boolean exists = false;
    	try {
			this.getMetaData(tableName);
			exists = true;
			return exists;
		} catch (SQLException e) {
			return exists;
		}
    }

    /**
     * 获取指定表的元数据
     * @param tableName
     * @return
     * @throws SQLException
     */
    public ResultSetMetaData getMetaData(String tableName) throws SQLException {
        String query = "SELECT * FROM "
            + tableName
            + " WHERE 1=0";
        PreparedStatement statement = connection.prepareStatement(
            query);
        ResultSet rs = statement.executeQuery();
        //RowSet rowSet = this.createCacheRowSet(statement);
        ResultSetMetaData metadata = rs.getMetaData();
        return metadata;
    }
    /**
     * 获取数据库的元数据
     * @return
     * @throws SQLException
     */
    public DatabaseMetaData getMetaData() throws SQLException {
        return connection.getMetaData();
    }
    /**
     * 获取数据集中的简单属性列
     * @param metaData
     * @return
     * @throws SQLException
     */
    public String[] getSimpleColumns(ResultSetMetaData metaData) throws
        SQLException {
        List<String> simpleColumns = new Vector<String>();

        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            if (metaData.getColumnType(i) != Types.CLOB) {
                simpleColumns.add(metaData.getColumnName(i));
            }
        }

        return (String[]) simpleColumns.toArray(new String[simpleColumns.size()]);
    }

    public String[] getLobColumns(ResultSetMetaData metaData) throws
        SQLException {
        List<String> lobColumns = new Vector<String>();

        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            if (metaData.getColumnType(i) == Types.CLOB) {
                lobColumns.add(metaData.getColumnName(i));
            }
        }

        return (String[]) lobColumns.toArray(new String[lobColumns.size()]);
    }

    public void setColumnValue(RowSet rowupdate, String rowName, String value) throws
        SQLException {
        ResultSetMetaData meta = rowupdate.getMetaData();
        for (int i = 1; i < meta.getColumnCount(); i++) {
            if (meta.getColumnName(i).equalsIgnoreCase(rowName)) {
                int type = meta.getColumnType(i);
                switch (type) {
                    case Types.BIGINT:
                        rowupdate.updateLong(rowName, Long.parseLong(value));
                        break;
                    case Types.BOOLEAN:
                        rowupdate.updateBoolean(rowName,
                                                Boolean.parseBoolean(value));
                        break;
                    case Types.CHAR:
                        rowupdate.updateByte(rowName, Byte.parseByte(value));
                        break;
                    case Types.DATE:
                        rowupdate.updateDate(rowName,
                                             java.sql.Date.valueOf(value));
                        break;
                    case Types.DECIMAL:
                        rowupdate.updateLong(rowName, Long.parseLong(value));
                        break;
                    case Types.DOUBLE:
                        rowupdate.updateDouble(rowName,
                                               Double.parseDouble(value));
                        break;
                    case Types.FLOAT:
                        rowupdate.updateFloat(rowName, Float.parseFloat(value));
                        break;
                    case Types.INTEGER:
                        rowupdate.updateInt(rowName, Integer.parseInt(value));
                        break;
                    case Types.NUMERIC:
                        rowupdate.updateLong(rowName, Long.parseLong(value));
                        break;
                    case Types.REAL:
                        rowupdate.updateDouble(rowName,
                                               Double.parseDouble(value));
                        break;
                    case Types.SMALLINT:
                        rowupdate.updateInt(rowName, Integer.parseInt(value));
                        break;
                    case Types.TIME:
                        rowupdate.updateTime(rowName,
                                             java.sql.Time.valueOf(value));
                        break;
                    case Types.TIMESTAMP:
                        rowupdate.updateTimestamp(rowName,
                                                  java.sql.Timestamp.valueOf(
                            value));
                        break;
                    case Types.TINYINT:
                        rowupdate.updateInt(rowName, Integer.parseInt(value));
                        break;
                    case Types.CLOB:
                        writeClob(rowupdate.getClob(rowName), value);
                        break;
                    default:
                        rowupdate.updateString(rowName, value);
                        break;
                }
            }
        }
    }

    public void setParameter(RowSet rowset, int rowIndex, String value) throws
        SQLException {
        int type = rowset.getMetaData().getColumnType(rowIndex);
        switch (type) {
            case Types.BIGINT:
                rowset.updateLong(rowIndex, Long.parseLong(value));
                break;
            case Types.BOOLEAN:
                rowset.updateBoolean(rowIndex, Boolean.parseBoolean(value));
                break;
            case Types.CHAR:
                rowset.updateByte(rowIndex, Byte.parseByte(value));
                break;
            case Types.DATE:
                rowset.updateDate(rowIndex, java.sql.Date.valueOf(value));
                break;
            case Types.DECIMAL:
                rowset.updateLong(rowIndex, Long.parseLong(value));
                break;
            case Types.DOUBLE:
                rowset.updateDouble(rowIndex, Double.parseDouble(value));
                break;
            case Types.FLOAT:
                rowset.updateFloat(rowIndex, Float.parseFloat(value));
                break;
            case Types.INTEGER:
                rowset.updateInt(rowIndex, Integer.parseInt(value));
                break;
            case Types.NUMERIC:
                rowset.updateLong(rowIndex, Long.parseLong(value));
                break;
            case Types.REAL:
                rowset.updateDouble(rowIndex, Double.parseDouble(value));
                break;
            case Types.SMALLINT:
                rowset.updateInt(rowIndex, Integer.parseInt(value));
                break;
            case Types.TIME:
                rowset.updateTime(rowIndex, java.sql.Time.valueOf(value));
                break;
            case Types.TIMESTAMP:
                rowset.updateTimestamp(rowIndex,
                                       java.sql.Timestamp.valueOf(value));
                break;
            case Types.TINYINT:
                rowset.updateInt(rowIndex, Integer.parseInt(value));
                break;

            default:
                rowset.updateString(rowIndex, value);
                break;
        }
    }

    protected static String readClob(Clob clob) throws SQLException {
        if (clob != null) {
            Reader clobStream = clob.getCharacterStream();
            StringBuffer sb = new StringBuffer();
            int nchars = 0;
            char[] buffer = new char[1024];
            try {
                while ( (nchars = clobStream.read(buffer)) != -1) {
                    sb.append(buffer, 0, nchars);
                }
                clobStream.close();
            } catch (IOException ex) {
                throw new SQLException("Error to read from clob data!");
            }

            return sb.toString();
        }
        return null;

    }

    protected static void writeClob(Clob clob, String value) throws
        SQLException {
        Writer writer = clob.setCharacterStream(0);
        if (value != null && value.length() > 0) {
            Reader reader = new StringReader(value);
            try {
                char[] cbuffer = new char[10 * 1024];
                int nread = 0;
                while ( (nread = reader.read(cbuffer)) != -1) {
                    writer.write(cbuffer, 0, nread);
                }
                reader.close();
                writer.close();

            } catch (Exception ex) {
                throw new SQLException(ex.getMessage());
            }
        }
    }

    public static String toOracleDate(Date dateValue) {
        java.sql.Time time = new java.sql.Time(dateValue.getTime());
        java.sql.Date date = new java.sql.Date(dateValue.getTime());
        String value = "TO_DATE('" + date.toString() + " " + time.toString() +
            "'," +
            "'YYYY-MM-DD HH24:MI:SS')";
        return value;
    }

    public static Date toDate(Timestamp value) {
        if (value != null) {
            return new Date(value.getTime());
        }
        return null;
    }

    public static java.sql.Date toSQLDate(Date dateValue) {
        if (dateValue != null) {
            java.sql.Date date = new java.sql.Date(dateValue.getTime());
            return date;
        }
        return null;
    }

    public static java.sql.Timestamp getSysSqlDate() {
        return toTimestame(toSQLDate(new Date()));
    }

    public static Timestamp toTimestame(Date dateValue) {
        if (dateValue == null) {
            return null;
        }
        return new Timestamp(dateValue.getTime());
    }

 //   public final Timestamp MIN_DATE = new Timestamp(0);
 //   public final Timestamp MAX_DATE = new Timestamp(200, 1, 1, 1, 1, 1, 1);
    protected int queryInt(PreparedStatement statement) throws SQLException {
        int result = 0;

        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            result = rs.getInt(1);
        }
        rs.close();
        statement.close();
        return result;

    }

    protected long queryLong(PreparedStatement statement) throws SQLException {
        long result = 0;
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            result = rs.getLong(1);
        }
        rs.close();
        statement.close();
        return result;

    }

    protected long[] queryLongArray(PreparedStatement statement) throws
        SQLException {
        long[] result = new long[1024];
        int size = 0;
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            result[size++] = rs.getLong(1);
        }
        rs.close();
        statement.close();
        long[] trim = new long[size];
        System.arraycopy(result, 0, trim, 0, size);
        return trim;

    }

    protected String[] queryStringArray(PreparedStatement statement) throws
        SQLException {
        ResultSet rs = statement.executeQuery();
        ArrayList<String> result = new ArrayList<String>();
        while (rs.next()) {
            result.add(rs.getString(1));
        }
        rs.close();
        statement.close();
        return (String[]) result.toArray(new String[result.size()]);
    }

    protected String queryString(PreparedStatement statement) throws
        SQLException {
        ResultSet rs = statement.executeQuery();
        String result = null;
        if (rs.next()) {
            result = rs.getString(1);
        }
        rs.close();
        statement.close();
        return result;
    }

    protected Object queryObject(PreparedStatement statement) throws
        SQLException {
        ResultSet rs = statement.executeQuery();
        Object result = null;
        int type = rs.getMetaData().getColumnType(1);
        if (rs.next()) {
            if (type == Types.CLOB) {
                Clob clob = rs.getClob(1);
                result = readClob(clob);
            } else {
                result = rs.getObject(1);
            }
        }
        rs.close();
        statement.close();
        return result;
    }

    protected String queryClob(PreparedStatement statement) throws SQLException {
        ResultSet rs = null;
        String result;
        try {
            rs =  statement.executeQuery();
            if (rs.next()) {
                Clob clob = rs.getClob(1);
                result = readClob(clob);
            } else {
                return null;
            }
        } finally {
            this.close(statement, rs);
        }
        return result;
    }

    protected boolean hasRow(PreparedStatement statement) throws SQLException {
        ResultSet rs = statement.executeQuery();
        boolean result = rs.next();
        rs.close();
        statement.close();
        return result;

    }

    public static String toString(long[] source) {
        StringBuffer bf = new StringBuffer();
        for (int i = 0; i < source.length; i++) {
            bf.append(source[i]);
            bf.append(" ");
        }
        return bf.toString();
    }

    public static long[] getLongArray(String source) {
        if (source == null) {
            return new long[0];
        }
        String[] split = source.split("\\s");
        long[] result = new long[split.length];
        for (int i = 0; i < split.length; i++) {
            result[i] = Long.parseLong(split[i]);
        }
        return result;
    }

    public static String toRangeQuery(String source) {
        StringBuffer query = new StringBuffer();
        query.append(
            "SELECT * FROM ( SELECT row_.*, rownum rownum_ FROM ( ");
        query.append(source);
        query.append(" ) row_ WHERE rownum <= ? ) WHERE rownum_ > ?");
        return query.toString();
    }
    

    public static String toRangeQueryForMysql(String source) {
        StringBuffer query = new StringBuffer();
        query.append(source);
        query.append(" limit ?,? ");
        return query.toString();
    }	
    
    /**
     * SqlServer20005 实现排序
     * @param source String
     * @return String
     * @author songbo
     * @addtime 2010-7-26
     */
    public static String toRangeQueryForSqlserver(String source){
    	String order="";
    	StringBuffer query = new StringBuffer();
    	query.append("SELECT row_.*, identity(int,1,1) as rownum_ into #toRangeQueryTemp FROM ( ");
        query.append(source);
        if(source.indexOf("ORDER BY")!=-1){
            order=source.substring(source.indexOf("ORDER BY"), source.length());
            order=order.replace(".", "");
            order=order.replace("nORDERNO", "row_.ORDERNO");
        }
    	query.append(" ) row_ ");
    	query.append(order);
    	query.append(";SELECT * FROM #toRangeQueryTemp r WHERE r.rownum_<=? and r.rownum_>?;drop table #toRangeQueryTemp;");
		return query.toString();
    }
    
    public static String toRangeQueryForPostgresql(String source) {
        StringBuffer query = new StringBuffer();
        query.append(source);
        query.append(" offset ? limit ? ");
        return query.toString();
    }
    
    public RowSet createCacheRowSet(PreparedStatement statement) throws
        SQLException {
        ResultSet rs = statement.executeQuery();
        CachedRowSet rowSet = new CachedRowSetImpl();
        rowSet.populate(rs);
        rs.close();
        statement.close();
        return rowSet;
    }

    /**
     * 关闭preparedstatement;
     * @param command PreparedStatement
     */

    protected void close(PreparedStatement command) throws SQLException {
        if (command != null)
            command.close();
    }

    protected void close(PreparedStatement command, ResultSet rs) throws
        SQLException {
        try {
            if (rs != null)
                rs.close();
        } finally {
            if (command != null)
                command.close();

        }
    }

    protected Date[] queryDateArray(PreparedStatement statement) throws
            SQLException {
        Date[] result = new Date[1024];
        int size = 0;
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            result[size++] = rs.getTimestamp(1);
        }
        rs.close();
        statement.close();
        Date[] trim = new Date[size];
        System.arraycopy(result, 0, trim, 0, size);
        return trim;
    }


    protected String[] queryClobArray(PreparedStatement statement) throws
             SQLException {
         ResultSet rs = statement.executeQuery();
         ArrayList<String> result = new ArrayList<String>();
         String resultString;
         while (rs.next()) {
             Clob clob = rs.getClob(1);
             resultString = readClob(clob);
             result.add(resultString);
         }
         rs.close();
         statement.close();
         return (String[]) result.toArray(new String[result.size()]);
    }

    protected boolean[] queryBooleanArray(PreparedStatement statement) throws
              SQLException {
          boolean[] result = new boolean[1024];
          int size = 0;
          ResultSet rs=null;
          try{
              rs = statement.executeQuery();
              while (rs.next()) {
                  result[size++] = rs.getBoolean(1);
              }
          }finally{
              this.close(statement, rs);
          }
          boolean[] trim = new boolean[size];
          System.arraycopy(result, 0, trim, 0, size);
          return trim;
    }


    protected double[] queryDoubleArray(PreparedStatement statement) throws
            SQLException {
        double[] result = new double[1024];
        int size = 0;
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            result[size++] = rs.getDouble(1);
        }
        rs.close();
        statement.close();
        double[] trim = new double[size];
        System.arraycopy(result, 0, trim, 0, size);
        return trim;
    }

    protected static final String EMPTY_CLOB = "EMPTY_CLOB()";
    protected static final String EMPTY_BLOB = "EMPTY_BLOB()";
    
    /**
	 * 此方法返回的结果是类似于这个sql形式：以供子查询使用 select pkid from node where pkid='0' union
	 * all select pkid from node where parent_id in(select pkid from node where
	 * pkid='0')
	 * 
	 * @param tablename
	 *            递归的表
	 * @param childColName
	 *            表的子列名
	 * @param parentColName
	 *            父列名 如果与子列调换 改变递归方向
	 * @param childValue
	 *            子列值
	 * @param parentValue
	 *            父列值
	 * @param returnColName
	 *            返回值的列名
	 * @return
	 * @throws SQLException
	 */
	public  String getClauseForRecursive(String tablename, String childColName,
			String parentColName, String childValue, String parentValue,
			String returnColName)  {
		CallableStatement proc = null;
		try {
			try {
				proc = connection
						.prepareCall("{ call recursive_select(?,?,?,?,?,?,?)  }");
			} catch (SQLException e1) {				
				log.trace(e1);
			}
			try {
				proc.setString(1, tablename);
			} catch (SQLException e) {				
				log.trace(e);
			}
			try {
				proc.setString(2, childColName);
			} catch (SQLException e) {				
				log.trace(e);
			}
			try {
				proc.setString(3, parentColName);
			} catch (SQLException e) {				
				log.trace(e);
			}
			try {
				proc.setString(4, childValue);
			} catch (SQLException e) {				
				log.trace(e);
			}
			try {
				proc.setString(5, parentValue);
			} catch (SQLException e) {				
				log.trace(e);
			}
			try {
				proc.setString(6, returnColName);
			} catch (SQLException e) {				
				log.trace(e);
			}
			try {
				proc.registerOutParameter(7, java.sql.Types.VARCHAR);
			} catch (SQLException e) {				
				log.trace(e);
			}
			try {
				proc.execute();
			} catch (SQLException e) {				
				log.trace(e);
			}
			try {
				if (proc.wasNull())
					return null;
			} catch (SQLException e) {				
				log.trace(e);
			}
			try {
				return proc.getString(7);
			} catch (SQLException e) {
				
				log.trace(e);
			}

		} finally {
			try {
				proc.close();
			} catch (SQLException e) {
				log.trace(e);
			}
		}
		return null;

	}
	
	/**
	 * 方法同上 只是支持传入的值为一个SQL或者多值参数 比如select pkid from node  或者 ‘123’，‘456’ 
	 * 此时要注意单引号转义,所以传递多参数时的格式: "''value1'',''value2'',''value3'',''value4''" 
	 * @param tablename
	 * @param childColName
	 * @param parentColName
	 * @param childSQL
	 * @param parentSQL
	 * @param returnColName
	 * @return
	 * @throws SQLException
	 */
	
	public  String getClauseForRecursiveSQL(String tablename, String childColName,
			String parentColName, String childSQL, String parentSQL,
			String returnColName) throws SQLException {
		CallableStatement proc = null;		
		try {
			proc = connection
					.prepareCall("{ call recursive_select_sql(?,?,?,?,?,?,?)  }");
			proc.setString(1, tablename);
			proc.setString(2, childColName);
			proc.setString(3, parentColName);
			proc.setString(4, childSQL);
			proc.setString(5, parentSQL);
			proc.setString(6, returnColName);
			proc.registerOutParameter(7, java.sql.Types.VARCHAR);
			proc.execute();
			if (proc.wasNull())
				return null;
			return proc.getString(7);

		} finally {
			proc.close();
		}

	}
	
	
    /**
	 * 此方法返回的结果是类似于这个sql形式：以供子查询使用 select pkid from node where pkid='0' union
	 * all select pkid from node where parent_id in(select pkid from node where
	 * pkid='0')这个是MSSQL的实现 查询子类
	 * 
	 * @param tablename
	 *            递归的表
	 * @param childColName
	 *            表的子列名
	 * @param parentColName
	 *            父列名 如果与子列调换 改变递归方向
	 * @param childValue
	 *            子列值
	 * @param parentValue
	 *            父列值
	 * @param returnColName
	 *            返回值的列名
	 * @return String
	 * @author songbo
	 * @addtime 2010-7-26
	 * @throws SQLException
	 */
	public  String getClauseForRecursivePostgresql(String tablename, String childColName,
			String parentColName, String childValue, String parentValue,
			String returnColName)  {
		CallableStatement proc = null;
		try {
			try {
				proc = connection
						.prepareCall("{ ?=call recursive_select(?,?,?,?,?,?)}");
			} catch (SQLException e) {
				log.trace(e);
			}
			try {
				proc.registerOutParameter(1, java.sql.Types.VARCHAR);
			} catch (SQLException e) {
				log.trace(e);
			}
			try {
				proc.setString(2, tablename);
			} catch (SQLException e) {
				log.trace(e);
			}
			try {
				proc.setString(3, childColName);
			} catch (SQLException e) {
				log.trace(e);
			}
			try {
				proc.setString(4, parentColName);
			} catch (SQLException e) {
				log.trace(e);
			}
			try {
				proc.setString(5, childValue);
			} catch (SQLException e) {
				log.trace(e);
			}
			try {
				proc.setString(6, parentValue);
			} catch (SQLException e) {
				log.trace(e);
			}
			try {
				proc.setString(7, returnColName);
			} catch (SQLException e) {
				log.trace(e);
			}
			try {
				proc.execute();
			} catch (SQLException e) {
				log.trace(e);
			}
			try {
				return proc.getString(1);
			} catch (SQLException e) {
				log.trace(e);
			}
		} finally {
			try {
				proc.close();
			} catch (SQLException e) {
				log.trace(e);
			}
		}
		return null;

	}
	
	/**
	 * 方法同上 只是支持传入的值为一个SQL或者多值参数 比如select pkid from node  或者 ‘123’，‘456’ 
	 * 此时要注意单引号转义,所以传递多参数时的格式: "''value1'',''value2'',''value3'',''value4''" 
	 * @param tablename
	 * @param childColName
	 * @param parentColName
	 * @param childSQL
	 * @param parentSQL
	 * @param returnColName
	 * @return
	 * @throws SQLException
	 */
	public  String getClauseForRecursivePostgresqlSQL(String tablename, String childColName,
			String parentColName, String childSQL, String parentSQL,
			String returnColName) throws SQLException {
		CallableStatement proc = null;		
		try {
			proc = connection
					.prepareCall("{ ?=call recursive_select_sql(?,?,?,?,?,?)  }");
			proc.registerOutParameter(1, java.sql.Types.VARCHAR);
			proc.setString(2, tablename);
			proc.setString(3, childColName);
			proc.setString(4, parentColName);
			proc.setString(5, childSQL);
			proc.setString(6, parentSQL);
			proc.setString(7, returnColName);
			proc.execute();
/*			if (proc.wasNull())
				return null;*/
			return proc.getString(1);

		} finally {
			proc.close();
		}

	}
}
