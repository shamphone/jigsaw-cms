package com.fulong.longcon.workflow.dao;

import com.fulong.common.dao.JdbcDao;
import com.fulong.longcon.workflow.data.TransitionData;
import java.sql.SQLException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

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
public class TransitionDao
    extends JdbcDao {
    private static final Log log = LogFactory.getLog(TransitionDao.class);

    private static final String SQL_insert_TransitionData =
        "INSERT INTO WK_TRANSITION t (PKID, NAME, DEFINITION_ID, BEGIN_ID, END_ID,TYPE) values (?,?,?,?,?,?)";

    public void insert(TransitionData data) throws SQLException {
        data.setPkid("" + getNextID());
        PreparedStatement statement = null;
        statement = connection.prepareStatement(
            SQL_insert_TransitionData);
        statement.setString(1, data.getPkid());
        statement.setString(2, data.getName());
        statement.setString(3, data.getDefinitionID());
        statement.setString(4, data.getBeginID());
        statement.setString(5, data.getEndID());
        statement.setString(6, data.getType());
        long startTime = 0;
        if (log.isTraceEnabled()) {
            startTime = System.currentTimeMillis();
            log.trace("Start execute: " + SQL_insert_TransitionData);
        }
        statement.executeUpdate();
        if (log.isTraceEnabled()) {
            log.trace("End execute, the last time is : " +
                      (System.currentTimeMillis() - startTime + "") +
                      SQL_insert_TransitionData);
        }
        statement.close();
    }

    private static final String SQL_delete_TransitionData =
        "delete from WK_TRANSITION t where t.PKID = ? and t.definition_id=?";

    public void delete(String ID, String definitionID) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(
                SQL_delete_TransitionData);
            statement.setString(1, ID);
            statement.setString(2, definitionID);
            long startTime = 0;
            if (log.isTraceEnabled()) {
                startTime = System.currentTimeMillis();
                log.trace("Start execute: " + SQL_delete_TransitionData);
            }

            statement.executeUpdate();
            if (log.isTraceEnabled()) {
                log.trace("End execute, the last time is : " +
                          (System.currentTimeMillis() - startTime + "") +
                          SQL_delete_TransitionData);
            }
        }
        finally {
            if (statement != null)
                statement.close();
        }
    }

    private static final String SQL_delete_BY_ACTIVITY =
        "delete from WK_TRANSITION t where (t.BEGIN_ID = ? OR t.END_ID=?) and t.definition_ID=?";

    public void deleteByActivity(String activityID, String definitionID) throws
        SQLException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(
                SQL_delete_BY_ACTIVITY);
            statement.setString(1, activityID);
            statement.setString(2, activityID);
            statement.setString(3, definitionID);
            long startTime = 0;
            if (log.isTraceEnabled()) {
                startTime = System.currentTimeMillis();
                log.trace("Start execute: " + SQL_delete_TransitionData);
            }

            statement.executeUpdate();
            if (log.isTraceEnabled()) {
                log.trace("End execute, the last time is : " +
                          (System.currentTimeMillis() - startTime + "") +
                          SQL_delete_TransitionData);
            }
        }
        finally {
            if (statement != null)
                statement.close();
        }
    }

    private static final String SQL_findByPKID =
        "SELECT * FROM WK_TRANSITION t WHERE t.PKID=? and definition_ID=?";

    public TransitionData findByPKID(String pkid, String definitionID) throws
        SQLException {
        PreparedStatement statement = connection.prepareStatement(
            SQL_findByPKID);
        statement.setString(1, pkid);
        statement.setString(2, definitionID);

        TransitionData[] data = retrieve(statement, SQL_findByPKID);
        if (data.length == 0) {
            return null;
        }
        return data[0];
    }

    private static final String SQL_findByDefinitionID =
        "SELECT * FROM WK_TRANSITION t WHERE t.DEFINITION_ID=?";

    public TransitionData[] findByDefinitionID(String definitionID) throws
        SQLException {
        PreparedStatement statement = connection.prepareStatement(
            SQL_findByDefinitionID);
        statement.setString(1, definitionID);

        return retrieve(statement, SQL_findByDefinitionID);
    }

    private static final String SQL_findByEndID =
        "SELECT * FROM WK_TRANSITION t WHERE t.END_ID=? and t.definition_id=?";
    /**
     * 获取所有以该参数为终点的转移
     * @param definitionID String
     * @return TransitionData[]
     * @throws SQLException
     */
    public TransitionData[] findByEndID(String endID, String definitionID) throws
        SQLException {
        PreparedStatement statement = connection.prepareStatement(
            SQL_findByEndID);
        statement.setString(1, endID);
        statement.setString(2, definitionID);

        TransitionData[] data = retrieve(statement, SQL_findByEndID);
        if (data.length == 0) {
            return null;
        }
        return data;
    }

    private static final String SQL_findByBeginID =
        "SELECT * FROM WK_TRANSITION t WHERE t.BEGIN_ID=?and t.definition_id=?";
    /**
     * 获取所有以该参数为起点的转移
     * @param definitionID String
     * @return TransitionData[]
     * @throws SQLException
     */
    public TransitionData[] findByBeginID(String endID, String definitionID) throws
        SQLException {
        PreparedStatement statement = connection.prepareStatement(
            SQL_findByBeginID);
        statement.setString(1, endID);
        statement.setString(2, definitionID);
        TransitionData[] data = retrieve(statement, SQL_findByBeginID);
        if (data.length == 0) {
            return null;
        }
        return data;
    }

    private static final String SQL_update_TransitionData =
        "update WK_TRANSITION w set w.NAME=?, w.DEFINITION_ID=?, w.TYPE=?, w.BEGIN_ID=?, w.END_ID=? where w.PKID=?";

    public void update(TransitionData data) throws SQLException {

        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(
                SQL_update_TransitionData);
            statement.setString(6, data.getPkid());
            statement.setString(1, data.getName());
            statement.setString(2, data.getDefinitionID());
            statement.setString(3, data.getType());
            statement.setString(4, data.getBeginID());
            statement.setString(5, data.getEndID());

            long startTime = 0;
            if (log.isTraceEnabled()) {
                startTime = System.currentTimeMillis();
                log.trace("Start execute: " + SQL_update_TransitionData);
            }
            statement.execute();
            if (log.isTraceEnabled()) {
                log.trace("End execute, the last time is : " +
                          (System.currentTimeMillis() - startTime + "") +
                          SQL_update_TransitionData);
            }
        }finally{
            if(statement!=null)
            statement.close();
        }
    }

    public TransitionData[] retrieve(PreparedStatement statement,
                                     String sql) throws
        SQLException {
        long start = 0;
        if (log.isTraceEnabled()) {
            start = System.currentTimeMillis();
        }
        ResultSet rs = null;
        try {
            rs = statement.executeQuery();
            if (log.isTraceEnabled()) {
                log.trace("[" + (System.currentTimeMillis() - start) +
                          "]: query " +
                          sql);
            }
            ArrayList<TransitionData> result = new ArrayList<TransitionData>();
            while (rs.next()) {
                TransitionData group = new TransitionData(); ;
                populate(rs, group);
                result.add(group);
            }
            return (TransitionData[]) result.toArray(new TransitionData[result.size()]);
        }
        finally {
            if (rs != null)
                rs.close();
            statement.close();
        }
    }

    public int retrieveInt(PreparedStatement statement, String sql) throws
        SQLException {
        long start = 0;
        if (log.isTraceEnabled()) {
            start = System.currentTimeMillis();
        }
        ResultSet rs = null;
        try {
            rs = statement.executeQuery();
            if (log.isTraceEnabled()) {
                log.trace("[" + (System.currentTimeMillis() - start) +
                          "]: query " +
                          sql);
            }
            int result = 0;
            if (rs.next()) {
                result = rs.getInt(1);
            }
            return result;
        }
        finally {
            if (rs != null)
                rs.close();
            statement.close();

        }
    }

    protected void populate(ResultSet rs, TransitionData data) throws
        SQLException {
        data.setName(rs.getString("NAME"));
        data.setPkid(rs.getString("PKID"));
        data.setType(rs.getString("TYPE"));
        data.setDefinitionID(rs.getString("DEFINITION_ID"));
        data.setBeginID(rs.getString("BEGIN_ID"));
        data.setEndID(rs.getString("END_ID"));
    }

}
