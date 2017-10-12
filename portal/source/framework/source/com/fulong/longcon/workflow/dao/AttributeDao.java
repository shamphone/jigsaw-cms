package com.fulong.longcon.workflow.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fulong.common.dao.JdbcDao;
import com.fulong.longcon.workflow.data.AttributeData;

/**
 * <p>Title: 龙驭工作流系统</p>
 *
 * <p>Description: 龙驭工作流系统</p>
 *
 * <p>Copyright: Copyright (c)北京中科辅龙计算机技术股份有限公司 2007</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author <a href='mailto:lixf@fulong.com.cn'>lixf</a>
 * @version 1.0
 */
public class AttributeDao extends JdbcDao {
    private static final Log log = LogFactory.getLog(AttributeDao.class);


    private static final String SQL_insert_AttributeData =
            "INSERT INTO INF_DESC t (OBJECTID, TYPE, NAME, VALUE) values (?,?,?,?)";

    public void insert(AttributeData data) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(
                    SQL_insert_AttributeData);
            statement.setString(1, data.getObjectId());
            statement.setInt(2, data.getType());
            statement.setString(3, data.getName());
            statement.setString(4, data.getValue());

            long startTime = 0;
            if (log.isTraceEnabled()) {
                startTime = System.currentTimeMillis();
                log.trace("Start execute: " + SQL_insert_AttributeData);
            }
            statement.execute();
            if (log.isTraceEnabled()) {
                log.trace("End execute, the last time is : " +
                          (System.currentTimeMillis() - startTime + "") +
                          SQL_insert_AttributeData);
            }
        } finally {
            if (statement != null)
                statement.close();
        }

    }


    private static final String SQL_delete_AttributeData =
            "delete from INF_DESC t where t.OBJECTID = ?";

    public void delete(String id) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(
                    SQL_delete_AttributeData);
            statement.setString(1, id);
            long startTime = 0;
            if (log.isTraceEnabled()) {
                startTime = System.currentTimeMillis();
                log.trace("Start execute: " + SQL_delete_AttributeData);
            }

            statement.execute();
            if (log.isTraceEnabled()) {
                log.trace("End execute, the last time is : " +
                          (System.currentTimeMillis() - startTime + "") +
                          SQL_delete_AttributeData);
            }
        }

        finally {
            if (statement != null)
                statement.close();
        }

    }


    private static final String SQL_delete_ByIdAndName =
            "delete from INF_DESC t where t.OBJECTID = ? and t.NAME=?";

    public void delete(String id, String name) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(
                    SQL_delete_ByIdAndName);
            statement.setString(1, id);
            statement.setString(2, name);
            long startTime = 0;
            if (log.isTraceEnabled()) {
                startTime = System.currentTimeMillis();
                log.trace("Start execute: " + SQL_delete_ByIdAndName);
            }

            statement.execute();
            if (log.isTraceEnabled()) {
                log.trace("End execute, the last time is : " +
                          (System.currentTimeMillis() - startTime + "") +
                          SQL_delete_ByIdAndName);
            }
        }

        finally {
            if (statement != null)
                statement.close();
        }

    }

    private static final String SQL_findByObjectID =
            "SELECT * FROM INF_DESC t WHERE t.OBJECTID=?";

    public AttributeData[] findByPKID(String pkid) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                SQL_findByObjectID);
        statement.setString(1, pkid);

        AttributeData[] data = this.retrieve(statement, SQL_findByObjectID);
        if (data.length == 0) {
            return null;
        }
        return data;
    }


    private static final String SQL_findByObjectIDAndName =
            "SELECT * FROM INF_DESC t WHERE t.OBJECTID=? and t.NAME=?";

    public AttributeData findByObjectIDAndName(String id, String name) throws
            SQLException {
        PreparedStatement statement = connection.prepareStatement(
                SQL_findByObjectIDAndName);
        statement.setString(1, id);
        statement.setString(2, name);

        AttributeData[] data = this.retrieve(statement,
                                             SQL_findByObjectIDAndName);
        if (data.length == 0) {
            return null;
        }
        return data[0];
    }


    private static final String SQL_update_AttributeData =
            "update INF_DESC w set w.VALUE=? where w.OBJECTID=? and w.NAME=? and w.TYPE=?";

    public void update(AttributeData data) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(
                    SQL_update_AttributeData);
            statement.setString(1, data.getValue());
            statement.setString(2, data.getObjectId());
            statement.setString(3, data.getName());
            statement.setInt(4, data.getType());
            long startTime = 0;
            if (log.isTraceEnabled()) {
                startTime = System.currentTimeMillis();
                log.trace("Start execute: " + SQL_update_AttributeData);
            }
            statement.execute();
            if (log.isTraceEnabled()) {
                log.trace("End execute, the last time is : " +
                          (System.currentTimeMillis() - startTime + "") +
                          SQL_update_AttributeData);
            }
        } finally {
            if (statement != null)
                statement.close();
        }
    }


    public AttributeData[] retrieve(PreparedStatement statement,
                                    String sql) throws
            SQLException {
        ResultSet rs = null;
        try {
            long start = 0;
            if (log.isTraceEnabled()) {
                start = System.currentTimeMillis();
            }
            rs = statement.executeQuery();
            if (log.isTraceEnabled()) {
                log.trace("[" + (System.currentTimeMillis() - start) +
                          "]: query " +
                          sql);
            }
            ArrayList<AttributeData> result = new ArrayList<AttributeData>();
            while (rs.next()) {
                AttributeData group = new AttributeData(); ;
                this.populate(rs, group);
                result.add(group);
            }

            return (AttributeData[]) result.toArray(new AttributeData[result.size()]);
            
        } finally {
            if (rs != null)
                rs.close();
            if (statement != null)
                statement.close();
        }
    }


    protected void populate(ResultSet rs, AttributeData data) throws
            SQLException {
        data.setName(rs.getString("NAME"));
        data.setObjectId(rs.getString("OBJECTID"));
        data.setType(rs.getInt("TYPE"));
        data.setValue(rs.getString("VALUE"));
    }
}
