package com.fulong.longcon.counter.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.fulong.common.dao.JdbcDao;
import com.fulong.longcon.counter.dao.DiaryCountDao;

public class MysqlDiaryCountDao extends JdbcDao implements DiaryCountDao{
	 /**
     * 获取计数值
     * @return long
     */
    private static final String SQL_SELECT =
      "SELECT sumvalue FROM diary_count WHERE name=? and access_date =?";
    public long loadCount(String name, Date accessTime) throws SQLException {
        PreparedStatement statement = null;
        ResultSet rs = null;
        //初始为小于0的值就可以，用于判断该节点是否在计数器中是否有记录
        long result = -1;
        try {
            statement = this.connection.prepareStatement(
                    SQL_SELECT);
            statement.setString(1, name);
            statement.setDate(2,JdbcDao.toSQLDate(accessTime));
             rs = statement.executeQuery();
            if (rs.next())
                result = rs.getLong(1);
            return result;
        } finally {
            this.close(statement, rs);
        }
    }

    /**
     * 将计数值保存到数据库中
     * @param newValue long
     */
    private static final String SQL_UPDATE =
      "UPDATE diary_count SET sumvalue=? where name=?  and access_date =?";
    public void saveCount(String name, long newValue, Date accessTime) throws
        SQLException {
        PreparedStatement statement = null;
        try {
            statement = this.connection.prepareStatement(
                SQL_UPDATE);
            statement.setLong(1, newValue);
            statement.setString(2, name);
            statement.setDate(3, JdbcDao.toSQLDate(accessTime));

            statement.executeUpdate();

        } finally {
            this.close(statement);
        }
    }

    /**
     * 插入一条记录
     * @param name String
     */
    private static final String SQL_INSERT =
      "insert into diary_count(SUMVALUE, NAME,access_date) values (?, ?,?)";
    public void insertCount(String name, Date accessTime) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = this.connection.prepareStatement(
                SQL_INSERT);
            statement.setLong(1, 0l);
            statement.setString(2, name);
            statement.setDate(3, JdbcDao.toSQLDate(accessTime));
            statement.executeUpdate();
            statement.close();
        } finally {
            this.close(statement);
        }
    }
}
