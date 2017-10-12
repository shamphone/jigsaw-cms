package com.fulong.longcon.workflow.dao;

import com.fulong.common.dao.JdbcDao;
import com.fulong.longcon.workflow.data.ExecutionData;
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
public class ExecutionDao extends JdbcDao {
    private static final Log log = LogFactory.getLog(ExecutionDao.class);


    private static final String SQL_insert_ExecutionData =
            "INSERT INTO WK_EXECUTION t (INSTANCE_ID, EXECUTOR_ID, EXECUTION_TIME, TRANSITION_ID) values (?,?,?,?)";

    public void insert(ExecutionData data) throws SQLException {
        PreparedStatement statement = null;
        statement = connection.prepareStatement(
                SQL_insert_ExecutionData);
        statement.setString(1, data.getInstanceID());
        statement.setString(2, data.getExecutorID());
        statement.setTimestamp(3, toTimestame(data.getExecutionTime()));
        statement.setString(4, data.getTransitionID());
        long startTime = 0;
        if (log.isTraceEnabled()) {
            startTime = System.currentTimeMillis();
            log.trace("Start execute: " + SQL_insert_ExecutionData);
        }
        statement.execute();
        if (log.isTraceEnabled()) {
            log.trace("End execute, the last time is : " +
                      (System.currentTimeMillis() - startTime + "") +
                      SQL_insert_ExecutionData);
        }
        statement.close();
    }


    private static final String SQL_delete_ExecutionData =
            "delete from wk_execution t where t.INSTANCE_ID = ?";

    public void delete(ExecutionData data) throws SQLException {
        PreparedStatement statement = null;
        statement = connection.prepareStatement(
                SQL_delete_ExecutionData);
        statement.setString(1, data.getInstanceID());
        long startTime = 0;
        if (log.isTraceEnabled()) {
            startTime = System.currentTimeMillis();
            log.trace("Start execute: " + SQL_delete_ExecutionData);
        }

        statement.execute();
        if (log.isTraceEnabled()) {
            log.trace("End execute, the last time is : " +
                      (System.currentTimeMillis() - startTime + "") +
                      SQL_delete_ExecutionData);
        }
        statement.close();
    }


    private static final String SQL_findByPKID =
            "SELECT * FROM wk_execution t WHERE t.INSTANCE_ID=? order by t.EXECUTION_TIME";

    public ExecutionData[] findByInstanceID(String instanceID) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                SQL_findByPKID);
        statement.setString(1, instanceID);

        ExecutionData[] data = retrieve(statement, SQL_findByPKID);
        if (data.length == 0) {
            return null;
        }
        return data;
    }


    private static final String SQL_findFirstExecutionByInstanceID =
            "select * from wk_execution t where t.instance_id=? and t.execution_time = (select min(w.execution_time) from wk_execution w where w.instance_id=? )";

        public ExecutionData findFirstExecutionByInstanceID(String instanceID) throws SQLException {
            PreparedStatement statement = connection.prepareStatement(
                    SQL_findFirstExecutionByInstanceID);
            statement.setString(1, instanceID);
            statement.setString(2, instanceID);

            ExecutionData[] data = retrieve(statement, SQL_findFirstExecutionByInstanceID);
            if (data.length == 0) {
                return null;
            }
            return data[0];
    }


    private static final String SQL_findLastExecutionByInstanceID =
            "select * from wk_execution t where t.instance_id=? and t.execution_time = (select max(w.execution_time) from wk_execution w where w.instance_id=? )";

        public ExecutionData findLastExecutionByInstanceID(String instanceID) throws SQLException {
            PreparedStatement statement = connection.prepareStatement(
                    SQL_findLastExecutionByInstanceID);
            statement.setString(1, instanceID);
            statement.setString(2, instanceID);

            ExecutionData[] data = retrieve(statement, SQL_findLastExecutionByInstanceID);
            if (data.length == 0) {
                return null;
            }
            return data[0];
    }



    public ExecutionData[] retrieve(PreparedStatement statement,
                                            String sql) throws
            SQLException {
        long start = 0;
        if (log.isTraceEnabled()) {
            start = System.currentTimeMillis();
        }
        ResultSet rs = statement.executeQuery();
        if (log.isTraceEnabled()) {
            log.trace("[" + (System.currentTimeMillis() - start) + "]: query " +
                      sql);
        }
        ArrayList<ExecutionData> result = new ArrayList<ExecutionData>();
        while (rs.next()) {
            ExecutionData group = new ExecutionData(); ;
            populate(rs, group);
            result.add(group);
        }
        rs.close();
        statement.close();
        return (ExecutionData[]) result.toArray(new ExecutionData[result.size()]);
    }

    public int retrieveInt(PreparedStatement statement, String sql) throws
            SQLException {
        long start = 0;
        if (log.isTraceEnabled()) {
            start = System.currentTimeMillis();
        }
        ResultSet rs = statement.executeQuery();
        if (log.isTraceEnabled()) {
            log.trace("[" + (System.currentTimeMillis() - start) + "]: query " +
                      sql);
        }
        int result = 0;
        try{
            if (rs.next()) {
                result = rs.getInt(1);
            } else {
                throw new SQLException("No rows found to extract  value!");
            }
        }
        finally{
            close(statement, rs);
        }
        return result;
    }


    protected void populate(ResultSet rs, ExecutionData data) throws
            SQLException {

        data.setExecutionTime(toDate(rs.getTimestamp("EXECUTION_TIME")));
//        data.setExecutionTime(rs.getDate("EXECUTION_TIME"));
        data.setExecutorID(rs.getString("EXECUTOR_ID"));
        data.setInstanceID(rs.getString("INSTANCE_ID"));
        data.setTransitionID(rs.getString("TRANSITION_ID"));
    }

}
