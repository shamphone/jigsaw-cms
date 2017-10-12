package com.fulong.longcon.counter.dao.sqlserver;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.fulong.common.dao.JdbcDao;
import com.fulong.longcon.counter.dao.TotallyCountDao;

public class SqlserverTotallyCountDao extends JdbcDao implements TotallyCountDao {
    private static final String SQL_INSERT =
      "insert into TOTALLY_COUNT(SUMVALUE, NAME) values (?, ?)";
    public void insertCount(String name) throws SQLException {
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
      "SELECT sumvalue FROM TOTALLY_COUNT WHERE name=?";
    public long loadCount(String name) throws SQLException {
        PreparedStatement statement = null;
        ResultSet rs = null;
        //初始为小于0的值就可以，用于判断该节点是否在计数器中是否有记录
        long result = -1;
        try {
            statement = this.connection.prepareStatement(
                SQL_SELECT);
            statement.setString(1, name);
            rs = statement.executeQuery();
            if (rs.next())
                result = rs.getLong(1);
            return result;
        } finally {
            this.close(statement, rs);
        }
    }

    private static final String SQL_UPDATE =
      "UPDATE TOTALLY_COUNT SET sumvalue=? where name=? ";
    public void saveCount(String name, long newValue) throws SQLException {
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