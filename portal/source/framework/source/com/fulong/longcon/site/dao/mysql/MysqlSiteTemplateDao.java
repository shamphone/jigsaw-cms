package com.fulong.longcon.site.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.sql.RowSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fulong.common.dao.DatabaseException;
import com.fulong.common.dao.JdbcDao;
import com.fulong.common.dao.JdbcException;
import com.fulong.common.dao.SQLParameter;
import com.fulong.longcon.site.dao.SiteTemplateDao;
import com.fulong.longcon.site.data.SiteTemplateData;

/**
 *
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公司</p>
 *
 * @author ljy
 * @version 2.0
 */
public class MysqlSiteTemplateDao
    extends JdbcDao implements SiteTemplateDao {
    private static final Log log = LogFactory.getLog(MysqlSiteTemplateDao.class);

    private static final String SQL_FIND_BY_ID =
        "select * from SITE_TEMPLATE where (PKID = ? or NAME =?) and del_flag ='0' ";

    public SiteTemplateData findByID(String ID) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = this.connection.prepareStatement(
                SQL_FIND_BY_ID);
            statement.setString(1, ID);
            statement.setString(2, ID);
            SiteTemplateData[] result = this.retrieve(statement,
                SQL_FIND_BY_ID);
            if (result.length > 0) {
                return result[0];
            }
            return null;
        }
        finally {
            this.close(statement);
        }
    }

    private static final String SQL_FIND_BY_NAME =
        "select * from SITE_TEMPLATE where NAME = ? and del_flag ='0' ";

    public SiteTemplateData findByName(String name) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = this.connection.prepareStatement(
                SQL_FIND_BY_NAME);
            statement.setString(1, name);
            SiteTemplateData[] result = this.retrieve(statement,
                SQL_FIND_BY_NAME);
            if (result.length > 0) {
                return result[0];
            }
            return null;
        }
        finally {
            this.close(statement);
        }
    }


    private SiteTemplateData[] retrieve(PreparedStatement
                                        statement, String sql) throws
        SQLException {
        long start = 0;
        if (log.isTraceEnabled()) {
            start = System.currentTimeMillis();
        }
        ResultSet rs = statement.executeQuery();
        if (log.isTraceEnabled()) {
            log.trace("[" +
                      (System.currentTimeMillis() - start) +
                      "]: query " +
                      sql);
        }
        ArrayList<SiteTemplateData> result = new ArrayList<SiteTemplateData>();
        while (rs.next()) {
            SiteTemplateData group = new SiteTemplateData(); ;
            this.populate(rs, group);
            result.add(group);
        }
        rs.close();
        statement.close();
        return (SiteTemplateData[]) result.toArray(new SiteTemplateData[
            result.size()]);
    }

    protected void populate(ResultSet rs, SiteTemplateData data) throws
        SQLException {
        data.setPkid(rs.getString("PKID"));
        data.setName(rs.getString("NAME"));
        data.setDisplayName(rs.getString("DISPLAY_NAME"));
        data.setDelFlag(rs.getBoolean("DEL_FLAG"));
        data.setStartDate(rs.getDate("START_DATE"));
        data.setExpiryDate(rs.getDate("EXPIRY_DATE"));
        data.setCreateDate(rs.getDate("CREATE_DATE"));
        data.setCategoryID(rs.getString("CATEGORY_ID"));
        data.setState(rs.getString("STATE"));
        data.setLocale(rs.getString("LOCALE"));
    }

    private static final String SQL_INSERT =
        "insert into SITE_TEMPLATE (PKID, NAME, DISPLAY_NAME, DEL_FLAG, START_DATE, EXPIRY_DATE, CREATE_DATE, CATEGORY_ID, STATE, LOCALE)" +
        "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public void insert(SiteTemplateData data) throws SQLException {
        PreparedStatement statement = null;
        try {
            if (data.getPkid() == null) {
                data.setPkid(JdbcDao.getNextID() + "");
            }
            statement = this.connection.prepareStatement(
                SQL_INSERT);
            statement.setString(1, data.getPkid());
            statement.setString(2, data.getName());
            statement.setString(3, data.getDisplayName());
            statement.setBoolean(4, data.isDelFlag());
            statement.setTimestamp(5, JdbcDao.toTimestame(data.getStartDate()));
            statement.setTimestamp(6, JdbcDao.toTimestame(data.getExpiryDate()));
            statement.setTimestamp(7, JdbcDao.toTimestame(data.getCreateDate()));
            statement.setString(8, data.getCategoryID());
            statement.setString(9, data.getState());
            statement.setString(10, data.getLocale());
            statement.executeUpdate();
        }
        finally {
            this.close(statement);
        }
    }

    private static final String SQL_UPDATE =
        "update SITE_TEMPLATE set NAME = ?, DISPLAY_NAME = ?, DEL_FLAG = ?, START_DATE = ?, EXPIRY_DATE = ?, CREATE_DATE = ?, CATEGORY_ID = ?, STATE = ?, LOCALE= ?" +
        "where PKID = ? and del_flag ='0' ";
    public void update(SiteTemplateData data) throws SQLException {
        PreparedStatement statement = null;
        try {
            if (data.getPkid() == null) {
                data.setPkid(JdbcDao.getNextID() + "");
            }
            statement = this.connection.prepareStatement(
                SQL_UPDATE);
            statement.setString(1, data.getName());
            statement.setString(2, data.getDisplayName());
            statement.setBoolean(3, data.isDelFlag());
            statement.setTimestamp(4, JdbcDao.toTimestame(data.getStartDate()));
            statement.setTimestamp(5, JdbcDao.toTimestame(data.getExpiryDate()));
            statement.setTimestamp(6, JdbcDao.toTimestame(data.getCreateDate()));
            statement.setString(7, data.getCategoryID());
            statement.setString(8, data.getState());
            statement.setString(9, data.getLocale());
            statement.setString(10, data.getPkid());
            statement.executeUpdate();
        }
        finally {
            this.close(statement);
        }

    }



    /**
        private static final String SQL_DELETE =
            "delete from SITE_TEMPLATE where pkid = ?";
     */
    private static final String SQL_DELETE =
        "update SITE_TEMPLATE set DEL_FLAG='1',pkid=concat(pkid,now()) where pkid = ?";

    public void delete(String ID) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = this.connection.prepareStatement(
                SQL_DELETE);
            statement.setString(1, ID);
            statement.executeUpdate();
        }
        catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
        finally {
            this.close(statement);
        }
    }

    public SiteTemplateData[] search(String query,
                                     SQLParameter[] parameters,
                                     int fromIndex,
                                     int number) throws
        SQLException {
        String sql = JdbcDao.toRangeQueryForMysql(query);
        PreparedStatement statement = null;
        try {
            List<SQLParameter> all = new ArrayList<SQLParameter>();
            if (parameters != null)
                Collections.addAll(all, parameters);
            all.add(new SQLParameter(Types.INTEGER,
                                     new Integer(fromIndex)));
            all.add(new SQLParameter(Types.INTEGER, new Integer(number)));
            statement = this.createStatement(sql,
                                             (SQLParameter[]) all.toArray(new
                SQLParameter[all.size()]));
            SiteTemplateData[] datas = this.retrieve(statement, sql);
            return datas;
        }
        catch (SQLException ex) {
            throw new JdbcException(ex);
        }
        finally {
            this.close(statement);
        }

    }

    public long countResultNum(String query, SQLParameter[] parameters) throws
        SQLException {
        long result = 0;
        PreparedStatement command = null;
        try {
            command = this.createStatement(query, parameters);

            RowSet rowset = this.createCacheRowSet(command);
            while (rowset.next()) {
                result = rowset.getLong(1);
            }
            return result;
        }
        catch (SQLException ex) {
            throw new JdbcException(ex);
        }
        finally {
            this.close(command);
        }

    }

    private static final String SQL_CountByCategory =
        "select count(*) from site_template t where t.category_id=? and del_flag ='0' ";
    /**
     * 按照分类计数
     * @param categoryID String
     * @return int
     * @throws SQLException
     */
    public int getCountbyCategory(String categoryID) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = this.connection.prepareStatement(
                SQL_CountByCategory);
            statement.setString(1, categoryID);
            int result = this.queryInt(statement);
            return result;
        }
        catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
        finally {
            this.close(statement);
        }

    }

    private PreparedStatement createStatement(String query,
                                              SQLParameter[] parameters) {

        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(query);
            if ( (parameters != null) && (parameters.length > 0)) {
                for (int i = 0; i < parameters.length; i++) {
                    command.setObject(i + 1, parameters[i].getValue());
                }
            }
            return command;
        }
        catch (SQLException ex) {
            throw new JdbcException(ex);
        }
    }

}
