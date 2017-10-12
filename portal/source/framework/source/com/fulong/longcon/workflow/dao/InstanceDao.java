package com.fulong.longcon.workflow.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fulong.common.dao.JdbcDao;
import com.fulong.common.dao.SQLParameter;
import com.fulong.longcon.workflow.data.InstanceData;

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
public class InstanceDao extends JdbcDao {
    private static final Log log = LogFactory.getLog(InstanceDao.class);


    private static final String SQL_insert_InstanceData =
            "INSERT INTO WK_INSTANCE t (PKID, DEFINITION_ID, STATE) values (?,?,?)";

    public void insert(InstanceData data) throws SQLException {
        data.setPkid("" + getNextID());
        PreparedStatement statement = null;
        statement = connection.prepareStatement(
                SQL_insert_InstanceData);
        statement.setString(1, data.getPkid());
        statement.setString(2, data.getDefinitionID());
        statement.setString(3, data.getState() + "");
        long startTime = 0;
        if (log.isTraceEnabled()) {
            startTime = System.currentTimeMillis();
            log.trace("Start execute: " + SQL_insert_InstanceData);
        }
        statement.execute();
        if (log.isTraceEnabled()) {
            log.trace("End execute, the last time is : " +
                      (System.currentTimeMillis() - startTime + "") +
                      SQL_insert_InstanceData);
        }
        statement.close();
    }


    private static final String SQL_delete_InstanceData =
            "delete from WK_INSTANCE t where t.PKID = ?";

    public void delete(InstanceData data) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(
                    SQL_delete_InstanceData);
            statement.setString(1, data.getPkid());
            long startTime = 0;
            if (log.isTraceEnabled()) {
                startTime = System.currentTimeMillis();
                log.trace("Start execute: " + SQL_delete_InstanceData);
            }

            statement.execute();
            if (log.isTraceEnabled()) {
                log.trace("End execute, the last time is : " +
                          (System.currentTimeMillis() - startTime + "") +
                          SQL_delete_InstanceData);
            }
        } finally {
            if (statement != null)
                statement.close();
        }
    }


    private static final String SQL_findByPKID =
            "SELECT * FROM WK_INSTANCE t WHERE t.PKID=?";

    public InstanceData findByPKID(String pkid) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                SQL_findByPKID);
        statement.setString(1, pkid);

        InstanceData[] data = retrieve(statement, SQL_findByPKID);
        if (data.length == 0) {
            return null;
        }
        return data[0];
    }


    private static final String SQL_update_InstanceData =
            "update WK_INSTANCE w set w.DEFINITION_ID=?, w.STATE=? where w.PKID=?";

    public void update(InstanceData data) throws SQLException {

        PreparedStatement statement = null;
        statement = connection.prepareStatement(
                SQL_update_InstanceData);
        statement.setString(3, data.getPkid());
        statement.setString(1, data.getDefinitionID());
        statement.setInt(2, data.getState());
        long startTime = 0;
        if (log.isTraceEnabled()) {
            startTime = System.currentTimeMillis();
            log.trace("Start execute: " + SQL_update_InstanceData);
        }
        statement.execute();
        if (log.isTraceEnabled()) {
            log.trace("End execute, the last time is : " +
                      (System.currentTimeMillis() - startTime + "") +
                      SQL_update_InstanceData);
        }
        statement.close();
    }


    public InstanceData[] search(String query,
                                 SQLParameter[] parameters,
                                 int fromIndex, int number) throws SQLException {
        String sql = toRangeQuery(query);
        /*
        List all = new ArrayList();
        Collections.addAll(all, parameters);
        all.add(new SQLParameter(Types.INTEGER,
                                 new Integer(fromIndex + number)));
        all.add(new SQLParameter(Types.INTEGER,
                                 new Integer(fromIndex)));
        */

        PreparedStatement statement = null;
        try {

            statement = connection.prepareStatement(sql);

            for (int i = 0; i < parameters.length; i++) {
                statement.setObject(i + 1, parameters[i].getValue());
            }
            statement.setObject( parameters.length+1, new Integer(fromIndex + number));
            statement.setObject( parameters.length+2, new Integer(fromIndex));

            return retrieve(statement, sql);
        }

        finally {
            if (statement != null) {
                statement.close();
            }
        }

    }


    public InstanceData[] search(String query,
                                 SQLParameter[] parameters
            ) throws SQLException {
        String sql = query;
        List<SQLParameter> all = new ArrayList<SQLParameter>();
        Collections.addAll(all, parameters);

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            for (int i = 0; i < parameters.length; i++) {
                statement.setObject(i + 1, parameters[i].getValue());
            }
            return retrieve(statement, sql);
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }


    public long countResultNum(String query, SQLParameter[] parameters) throws SQLException {
        PreparedStatement statement = null;
      try {
          statement = connection.prepareStatement(query);
          for (int i = 0; i < parameters.length; i++) {
              statement.setObject(i + 1, parameters[i].getValue());
          }
          long count = queryLong(statement);
          return count;
      } finally {
          if (statement != null) {
              statement.close();
          }
      }

    }


    public InstanceData[] retrieve(PreparedStatement statement,
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
        ArrayList<InstanceData> result = new ArrayList<InstanceData>();
        while (rs.next()) {
            InstanceData group = new InstanceData(); ;
            populate(rs, group);
            result.add(group);
        }
        rs.close();
        statement.close();
        return (InstanceData[]) result.toArray(new InstanceData[result.size()]);
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
        }finally{
            close(statement, rs);
        }
        return result;
    }


    protected void populate(ResultSet rs, InstanceData data) throws
            SQLException {
        data.setPkid(rs.getString("PKID"));
        data.setState(rs.getInt("STATE"));
        data.setDefinitionID(rs.getString("DEFINITION_ID"));
    }


}
