package com.fulong.longcon.workflow.dao;

import com.fulong.common.dao.JdbcDao;
import java.sql.SQLException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.sql.PreparedStatement;

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
public class AuthorizationDao extends JdbcDao {
    private static final Log log = LogFactory.getLog(AuthorizationDao.class);


    private static final String SQL_insert_Authorization =
            "INSERT INTO WK_AUTHORIZATION t (ACTIVITY_ID, PRINCIPAL_ID) values (?,?)";

    public void insert(String activityID, String principalID) throws
            SQLException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(
                    SQL_insert_Authorization);
            statement.setString(1, activityID);
            statement.setString(2, principalID);
            long startTime = 0;
            if (log.isTraceEnabled()) {
                startTime = System.currentTimeMillis();
                log.trace("Start execute: " + SQL_insert_Authorization);
            }
            statement.execute();
            if (log.isTraceEnabled()) {
                log.trace("End execute, the last time is : " +
                          (System.currentTimeMillis() - startTime + "") +
                          SQL_insert_Authorization);
            }
        } finally {
            if (statement != null)
                statement.close();
        }

    }


    private static final String SQL_delete_Authorization =
            "delete from WK_AUTHORIZATION t where t.ACTIVITY_ID = ?";

    public void delete(String activityID) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(
                    SQL_delete_Authorization);
            statement.setString(1, activityID);
            long startTime = 0;
            if (log.isTraceEnabled()) {
                startTime = System.currentTimeMillis();
                log.trace("Start execute: " + SQL_delete_Authorization);
            }

            statement.execute();
            if (log.isTraceEnabled()) {
                log.trace("End execute, the last time is : " +
                          (System.currentTimeMillis() - startTime + "") +
                          SQL_delete_Authorization);
            }
        }

        finally {
            if (statement != null)
                statement.close();
        }

    }

    private static final String SQL_delete_AuthorizationByPrincipal =
            "delete from WK_AUTHORIZATION t where t.ACTIVITY_ID = ? and t.PRINCIPAL_ID=?";

    public void delete(String activityID, String principalID) throws
            SQLException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(
                    SQL_delete_AuthorizationByPrincipal);
            statement.setString(1, activityID);
            statement.setString(2, principalID);
            long startTime = 0;
            if (log.isTraceEnabled()) {
                startTime = System.currentTimeMillis();
                log.trace("Start execute: " +
                          SQL_delete_AuthorizationByPrincipal);
            }

            statement.execute();
            if (log.isTraceEnabled()) {
                log.trace("End execute, the last time is : " +
                          (System.currentTimeMillis() - startTime + "") +
                          SQL_delete_AuthorizationByPrincipal);
            }
        }

        finally {
            if (statement != null)
                statement.close();
        }

    }


    private static final String SQL_findByActivityID =
            "SELECT t.PRINCIPAL_ID FROM WK_AUTHORIZATION t WHERE t.ACTIVITY_ID=?";

    public String[] findByActivityID(String activityID) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                SQL_findByActivityID);
        statement.setString(1, activityID);
        String[] result = this.queryStringArray(statement);

        if (result.length == 0) {
            return null;
        }
        return result;
    }


}
