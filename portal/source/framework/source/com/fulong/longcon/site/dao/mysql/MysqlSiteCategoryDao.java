package com.fulong.longcon.site.dao.mysql;

import com.fulong.common.dao.JdbcDao;
import com.fulong.common.dao.DatabaseException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.fulong.common.dao.SQLParameter;
import com.fulong.common.dao.JdbcException;
import javax.sql.RowSet;
import java.sql.Types;
import java.util.Collections;
import java.util.List;
import com.fulong.longcon.site.dao.SiteCategoryDao;
import com.fulong.longcon.site.data.SiteCategoryData;
import java.util.Date;

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
public class MysqlSiteCategoryDao
    extends JdbcDao implements SiteCategoryDao {
    private static final Log log = LogFactory.getLog(MysqlSiteCategoryDao.class);

    private static final String SQL_FIND_BY_ID =
        "select * from SITE_CATEGORY where PKID = ? or NAME =?";

    public SiteCategoryData findByID(String ID) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = this.connection.prepareStatement(
                SQL_FIND_BY_ID);
            statement.setString(1, ID);
            statement.setString(2, ID);
            SiteCategoryData[] result = this.retrieve(statement,
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

    private static final String SQL_GET_ALL =
        "select * from site_category t order by t.create_time desc";
    public SiteCategoryData[] getAllCategories() throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = this.connection.prepareStatement(
                SQL_GET_ALL);
            SiteCategoryData[] result = this.retrieve(statement,
                SQL_GET_ALL);
            return result;
        }
        finally {
            this.close(statement);
        }
    }

    private SiteCategoryData[] retrieve(PreparedStatement
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
        ArrayList<SiteCategoryData> result = new ArrayList<SiteCategoryData>();
        while (rs.next()) {
            SiteCategoryData group = new SiteCategoryData(); ;
            this.populate(rs, group);
            result.add(group);
        }
        rs.close();
        statement.close();
        return (SiteCategoryData[]) result.toArray(new SiteCategoryData[
            result.size()]);
    }

    protected void populate(ResultSet rs, SiteCategoryData data) throws
        SQLException {
        data.setPkid(rs.getString("PKID"));
        data.setName(rs.getString("NAME"));
        data.setDisplayName(rs.getString("DISPLAY_NAME"));
        data.setDescription(rs.getString("DESCRIPTION"));
        data.setCreateDate(rs.getDate("CREATE_TIME"));
        data.setStartDate(rs.getDate("START_DATE"));
        data.setExpiryDate(rs.getDate("EXPIRY_DATE"));
        data.setState(rs.getString("STATE"));
        data.setGroupID(rs.getString("GROUPID"));
    }

    private static final String SQL_Insert =
        "insert into SITE_CATEGORY (PKID, NAME, DISPLAY_NAME, DESCRIPTION, CREATE_TIME, START_DATE, EXPIRY_DATE, STATE, GROUPID)" +
        "values (?, ?, ?, ?, ?, ?, ?, ?,?)";

    public void insert(SiteCategoryData data) throws SQLException {
        PreparedStatement statement = null;
        try {
            if (data.getPkid() == null) {
                data.setPkid(JdbcDao.getNextID() + "");
            }
            statement = this.connection.prepareStatement(
                SQL_Insert);
            statement.setString(1, data.getPkid());
            statement.setString(2, data.getName());
            statement.setString(3, data.getDisplayName());
            statement.setString(4, data.getDescription());
            if(data.getCreateDate()!=null)
                statement.setTimestamp(5, JdbcDao.toTimestame(data.getCreateDate()));
            else
                statement.setTimestamp(5, JdbcDao.toTimestame(new Date()));
            if(data.getStartDate()!=null)
                statement.setTimestamp(6, JdbcDao.toTimestame(data.getStartDate()));
            else
                statement.setTimestamp(6, null);
            if(data.getExpiryDate()!=null)
                statement.setTimestamp(7, JdbcDao.toTimestame(data.getExpiryDate()));
            else
                statement.setTimestamp(7, null);

            statement.setString(8, data.getState());
            statement.setString(9, data.getGroupID());
            statement.executeUpdate();
        }
        finally {
            this.close(statement);
        }

    }

    private static final String SQL_UPDATE =
        "update SITE_CATEGORY set NAME = ?, DISPLAY_NAME = ?, DESCRIPTION = ?, CREATE_TIME = ?, START_DATE = ?, EXPIRY_DATE = ?, STATE = ?, GROUPID = ?" +
        " where PKID = ?";
    public void update(SiteCategoryData data) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = this.connection.prepareStatement(
                SQL_UPDATE);

            statement.setString(1, data.getName());
            statement.setString(2, data.getDisplayName());
            statement.setString(3, data.getDescription());
            statement.setTimestamp(4, JdbcDao.toTimestame(data.getCreateDate()));
            statement.setTimestamp(5, JdbcDao.toTimestame(data.getStartDate()));
            statement.setTimestamp(6, JdbcDao.toTimestame(data.getExpiryDate()));
            statement.setString(7, data.getState());
            statement.setString(8, data.getGroupID());
            statement.setString(9, data.getPkid());
            statement.executeUpdate();
        }
        finally {
            this.close(statement);
        }

    }

    private static final String SQL_Delete_By_ID =
        "delete from SITE_CATEGORY where PKID=?";
    /**
     *
     * @param ID String
     * @throws SQLException
     */
    public void delete(String ID) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = this.connection.prepareStatement(
                SQL_Delete_By_ID);
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


    public SiteCategoryData[] search(String query,
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
            SiteCategoryData[] datas = this.retrieve(statement, sql);
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
