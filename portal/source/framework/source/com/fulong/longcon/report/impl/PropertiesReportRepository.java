package com.fulong.longcon.report.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import javax.sql.DataSource;
import javax.sql.rowset.CachedRowSet;

import javax.swing.table.TableModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.fulong.common.ResourceUtils;
import com.fulong.common.dao.DatabaseException;
import com.fulong.longcon.report.ReportRepository;
import com.sun.rowset.CachedRowSetImpl;
import java.util.Vector;
import java.util.Calendar;

/**
 * <p>Title: 龙驭核心引擎</p>
 *
 * <p>Description: 龙驭核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author lixf
 * @version 2.0
 */
public class PropertiesReportRepository implements ReportRepository {
    private DataSource datasource;
    private Properties properties;
    private Class<?> rowSetClass;
    private String file;
    private static final Log log = LogFactory.getLog(PropertiesReportRepository.class);
    public void init() {
        this.rowSetClass = CachedRowSetImpl.class;
        try {
            this.setSQLConfigFile();
        } catch (IOException ex) {

        }
    }

    /**
     * 设置数据源
     * @param ds DataSource
     */
    public void setDataSource(DataSource ds) {
        this.datasource = ds;
    }

    public void setSQLConfigFile() throws IOException {
        this.properties = new Properties();
        properties.load(ResourceUtils.getResourceAsStream(this.file));
        log.info("Load config file from " + file + ".");
    }

    public void setRowSetClas(String className) throws ClassNotFoundException {
        this.rowSetClass = Class.forName(className);
    }

    /**
     *
     * @param sql String
     * @return TableModel
     * @todo Implement this com.fulong.longcon.report.ReportRepository method
     */
    public TableModel getReport(String sqlName) {
        return getReport(sqlName, new Object[0]);

    }

    protected CachedRowSet newCachedRowSet() throws SQLException {
        try {
            return (CachedRowSet)this.rowSetClass.newInstance();
        } catch (IllegalAccessException ex) {
            return null;
        } catch (InstantiationException ex) {
            return null;
        }
    }

    public TableModel getReport(String sqlName, Object[] params) {
        String sql = this.properties.getProperty(sqlName);
        if (sql == null){
            sql=sqlName;
        }

        log.debug(sql);

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = this.datasource.getConnection();
            statement = connection.prepareStatement(sql);

            for (int i = 0; i < params.length; i++)
                statement.setObject(i + 1, params[i]);
            rs = statement.executeQuery();
            CachedRowSet crs = newCachedRowSet();
            crs.populate(rs);
            return new ResultSetTableModel(crs);
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
            /**
                     } catch (IllegalAccessException ex) {
                         throw new RuntimeException(ex);
                     } catch (InstantiationException ex) {
                         throw new RuntimeException(ex);
             */
        } finally {
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException ex) {
                throw new DatabaseException(ex);
            } finally {
                try {
                    if (statement != null)
                        statement.close();
                } catch (SQLException ex) {
                    throw new DatabaseException(ex);
                } finally {
                    try {
                        if (connection != null) connection.close();
                    } catch (SQLException ex) {
                        throw new DatabaseException(ex);
                    }

                }
            }
        }

    }

    /**
     * 通过拼接获得sql语句,执行SQL语句获得查询结果
     * @param sqlName String,配置文件中sql语句名
     * @param sqlParts String[],待拼接的部分
     * @param params Object[],参数
     * @return TableModel
     */
    public TableModel getReport(String sqlName, String[] sqlParts,
                                Object[] params) {
        String sqlToFill = this.properties.getProperty(sqlName);
        if (sqlToFill == null)
            throw new IllegalArgumentException("There is no sql with name " +
                                               sqlName + ".");

//        String sql = StringUtils.format(sqlToFill, (Object[])sqlParts);
        String sql = this.format(sqlToFill, (Object[]) sqlParts);
        log.debug(sql);
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = this.datasource.getConnection();
            statement = connection.prepareStatement(sql);
            for (int i = 0; i < params.length; i++)
                statement.setObject(i + 1, params[i]);
            rs = statement.executeQuery();
            CachedRowSet crs = (CachedRowSet)this.rowSetClass.newInstance();
            crs.populate(rs);
            return new ResultSetTableModel(crs);
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        } catch (InstantiationException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException ex) {
                throw new DatabaseException(ex);
            } finally {
                try {
                    if (statement != null)
                        statement.close();
                } catch (SQLException ex) {
                    throw new DatabaseException(ex);
                } finally {
                    try {
                        if (connection != null) connection.close();
                    } catch (SQLException ex) {
                        throw new DatabaseException(ex);
                    }

                }
            }
        }

    }

    public long getRecordsCount(String sqlName, String[] sqlParts,
                                Object[] params) {
        String sqlToFill = this.properties.getProperty(sqlName);
        if (sqlToFill == null)
            throw new IllegalArgumentException("There is no sql with name " +
                                               sqlName + ".");

        String sql = this.format(sqlToFill, (Object[]) sqlParts);

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = this.datasource.getConnection();
            statement = connection.prepareStatement(sql);
            for (int i = 0; i < params.length; i++)
                statement.setObject(i + 1, params[i]);
            rs = statement.executeQuery();
            if (rs.next())
                return rs.getLong(1);
            else
                return 0;

        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        } finally {
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException ex) {
                throw new DatabaseException(ex);
            } finally {
                try {
                    if (statement != null)
                        statement.close();
                } catch (SQLException ex) {
                    throw new DatabaseException(ex);
                } finally {
                    try {
                        if (connection != null) connection.close();
                    } catch (SQLException ex) {
                        throw new DatabaseException(ex);
                    }

                }
            }
        }

    }

    /**
     * 通过拼接获得获得数量的sql语句,执行SQL语句获得查询结果，为该sql查询的记录数
     * @param sqlName String
     * @param sqlParts String[]
     * @param params Object[]
     * @return long
     */
    public long getRecordsCount(String sqlName, Object[] params) {
        String sqlToFill = this.properties.getProperty(sqlName);
        if (sqlToFill == null)
            throw new IllegalArgumentException("There is no sql with name " +
                                               sqlName + ".");

        // String sql = this.format(sqlToFill, (Object[]) sqlParts);

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = this.datasource.getConnection();
            statement = connection.prepareStatement(sqlToFill);
            for (int i = 0; i < params.length; i++)
                statement.setObject(i + 1, params[i]);
            rs = statement.executeQuery();
            if (rs.next())
                return rs.getLong(1);
            else
                return 0;

        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        } finally {
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException ex) {
                throw new DatabaseException(ex);
            } finally {
                try {
                    if (statement != null)
                        statement.close();
                } catch (SQLException ex) {
                    throw new DatabaseException(ex);
                } finally {
                    try {
                        if (connection != null) connection.close();
                    } catch (SQLException ex) {
                        throw new DatabaseException(ex);
                    }

                }
            }
        }

    }

    private String format(String source, Object[] args) {
        String result = source;
        for (int i = 0; i < args.length; i++) {
            String regex = "\\x7B" + i + "\\x7D";
            if (args[i] == null) {
                result = result.replaceFirst(regex, "");
            } else {
                String encoded;
                encoded = args[i].toString();
                result = result.replaceFirst(regex, encoded);
            }
        }
        return result;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

////////////////////////////////////以下方法软协系统使用
    public TableModel getPaidTable() {
        String[] params = getPaidSqlparams();
        String sql = toSqlStatPaidPerYear(params);
        log.debug(sql);
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = this.datasource.getConnection();
            statement = connection.prepareStatement(sql);
            for (int i = 0; i < params.length; i++)
                statement.setObject(i + 1, params[i]);
            rs = statement.executeQuery();
            CachedRowSet crs = (CachedRowSet)this.rowSetClass.newInstance();
            crs.populate(rs);
            return new ResultSetTableModel(crs);
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        } catch (InstantiationException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException ex) {
                throw new DatabaseException(ex);
            } finally {
                try {
                    if (statement != null)
                        statement.close();
                } catch (SQLException ex) {
                    throw new DatabaseException(ex);
                } finally {
                    try {
                        if (connection != null) connection.close();
                    } catch (SQLException ex) {
                        throw new DatabaseException(ex);
                    }

                }
            }
        }

    }

    /**
     * 软协历年缴费清单
     * @return String
     */
    public String[] getPaidSqlparams() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2003);
        Calendar now = Calendar.getInstance();
        Vector<String> v = new Vector<String>();
        while (cal.get(Calendar.YEAR) < now.get(Calendar.YEAR)) {
            String s = cal.get(Calendar.YEAR) + "";
            v.add(s);
            cal.add(Calendar.YEAR, 1);
        }
        String[] ss = (String[]) v.toArray(new String[v.size()]);
        return ss;
        //return toSqlStatPaidPerYear(ss);
    }

    /**
     *
     * @param args String[]
     * @return String
     */
    private String toSqlStatPaidPerYear(String[] args) {
        if (args == null)
            return null;
        StringBuffer sqlBuf = new StringBuffer();
        StringBuffer select = new StringBuffer(" select ");
        StringBuffer from = new StringBuffer(" from ");
        int many = args.length;
        if (many == 1) {
            sqlBuf.append("");
            return sqlBuf.toString();
        } else {
            select.append(this.appendSelectForPaid(many));
            from.append(this.appendFromForPaid(many));
            return select.append(from).toString();
        }
    }

    /**
     *
     * @param num int
     * @return StringBuffer
     */
    private StringBuffer appendSelectForPaid(int num) {
        StringBuffer select1 = new StringBuffer(" t0.Orgname ,");
        StringBuffer select2 = new StringBuffer(" t0.postname ,");
        StringBuffer selectOther = new StringBuffer(" t0.yy ");
        for (int i = 0; i < num-1; i++) {
            String ti = "t" + (i + 1);
            select1= new StringBuffer(" nvl(" + select1.toString() + ti + ".Orgname), ");
            select2= new StringBuffer(" nvl(" + select2.toString() + ti + ".postname), ");
            selectOther.append("," + ti + ".yy  ");
        }
        //selectOther.append(" , ''");//加上空列
        return select1.append(select2).append(selectOther);
    }

    /**
     *
     * @param num int
     * @return StringBuffer
     */

    private StringBuffer appendFromForPaid(int num) {
        String ch =
            " ( Select o.enterprisename orgname , dc.Name postname ,dv.Value yy ";
        ch += " From content c1 Left Join Reference_Value r On r.property_id ='post-content-ref' And r.Node_Id = c1.Node_Id ";
        ch += " Left Join Organization o On o.Id = r.Value Left Join String_Value s1 On s1.property_id ='asspost' And s1.Node_Id = c1.Node_Id ";
        ch += " Left Join dict_entry dc On dc.pkid = s1.Value  Left Join Double_Value dv On dv.property_id ='amoutpaid' And dv.Node_Id = c1.Node_Id ";
        ch += "Where c1.category_id = '2435141659092'    And C1.Del_Flag = '0' And Exists  (Select * From String_Value s2 Where  s2.property_id ='prop390' ";
        ch += " And s2.Node_Id =c1.Node_Id And s2.Value =?)  )";
        StringBuffer from = new StringBuffer(ch + " t0 ");
        for (int i = 0; i < num-1; i++) {
            String t = "t" + i;
            String ti = "t" + (i + 1);
            String on = " on " + ti + ".Orgname=" + t + ".Orgname";
            from.append(" Full Join  " + ch + ti + on);
        }
        return from;
    }

    /**
     * 组合最终sql
     * @param args String[]
     * @return String
     */
    public String toSql(String[] args) {
        String sql = "";
        for (int i = 0; args != null && i < args.length; i++) {
            String joinSql = "";
            sql += joinSql;
        }
        return sql;
    }
}
