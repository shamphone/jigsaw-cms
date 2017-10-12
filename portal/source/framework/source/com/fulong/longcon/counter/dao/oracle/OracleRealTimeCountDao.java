package com.fulong.longcon.counter.dao.oracle;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import com.fulong.common.dao.JdbcDao;
import com.fulong.longcon.counter.dao.RealTimeCountDao;

/**
 *
 * <p>Title: Longcon Passport System</p>
 *
 * <p>Description: Longcon Passport System</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author lishaobo
 * @version 1.0
 */
public class OracleRealTimeCountDao extends JdbcDao implements RealTimeCountDao {
    private static final String SQL_INSERT =
            "insert into REALTIME_COUNT(NAME, ACCESS_TIME) values (?, ?)";
    public void insertCount(String name, Date accessDate) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = this.connection.prepareStatement(
                    SQL_INSERT);
            statement.setString(1, name);
            statement.setTimestamp(2, toTimestame(accessDate));

            statement.executeUpdate();
            statement.close();
        } finally {
            this.close(statement);
        }
    }

    private static final String SQL_SELECT =
            "SELECT sumvalue FROM REALTIME_COUNT WHERE name=? and ACCESS_TIME =?";
    public long loadCount(String name, Date accessTime) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = this.connection.prepareStatement(
                    SQL_SELECT);
            statement.setString(1, name);
            statement.setTimestamp(2, toTimestame(accessTime));

            return this.queryLong(statement);
        } finally {
            this.close(statement);
        }

    }

    private static final String SQL_UPDATE =
            "UPDATE REALTIME_COUNT SET sumvalue=? where name=? and ACCESS_TIME =?";
    public void saveCount(String name, long newValue, Date accessTime) throws
        SQLException {
        PreparedStatement statement = null;
        try {
            statement = this.connection.prepareStatement(
                    SQL_UPDATE);
            statement.setLong(1, newValue);
            statement.setString(2, name);
            statement.setTimestamp(3, toTimestame(accessTime));
            statement.executeUpdate();
        } finally {
            this.close(statement);
        }

    }

}
