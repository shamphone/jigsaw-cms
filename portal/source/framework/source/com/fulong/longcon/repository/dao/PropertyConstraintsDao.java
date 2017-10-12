package com.fulong.longcon.repository.dao;

import com.fulong.common.dao.JdbcDao;
import java.sql.SQLException;
import java.sql.PreparedStatement;


/**
 * <p>Title: Coolink协同工作支撑平台</p>
 *
 * <p>Description: Coolink协同工作支撑平台</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2007</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author <a href='mailto:lishaobo@fulong.com.cn'>lishaobo</a>
 * @version 1.0
 */
public class PropertyConstraintsDao extends JdbcDao {
    /**
     * 获取缺省值
     * @param propertyID String
     * @return String[]
     * @throws SQLException
     */
    private static final String SQL_SELECT =
            "select CONSTRAINTS from PROPERTY_CONSTRAINTS where PROPERTY_DEFINITION_ID=? and NODE_ID=?";
    public String[] findByPropDefAndNodeDefID(String propertyID, String nodeID) throws
            SQLException {
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(SQL_SELECT);
            command.setString(1, propertyID);
            command.setString(2, nodeID);
            String[] result = this.queryStringArray(command);
            return result;
        } finally {
            this.close(command);
        }
    }

    private static final String SQL_DELETE_BY_ID =
            "delete from PROPERTY_CONSTRAINTS where PROPERTY_DEFINITION_ID=? and NODE_ID=?";
    /**
     *
     * @param propertyID String
     * @param nodeID String
     * @throws SQLException
     */
    public void delete(String propertyID, String nodeID) throws SQLException {
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(SQL_DELETE_BY_ID);
            command.setString(1, propertyID);
            command.setString(2, nodeID);
            command.executeUpdate();
        } finally {
            this.close(command);
        }
    }

    /**
     * 插入值
     * @param propertyID String
     * @param value String
     * @throws SQLException
     */
    private static final String SQL_INSERT =
            "insert into PROPERTY_CONSTRAINTS (PROPERTY_DEFINITION_ID, CONSTRAINTS, NODE_ID) VALUES (?, ?, ?)";
    public void insert(String propertyID, String constraint, String nodeID) throws
            SQLException {
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(SQL_INSERT);
            command.setString(1, propertyID);
            command.setString(2, constraint);
            command.setString(3, nodeID);
            command.executeUpdate();
        } finally {
            this.close(command);
        }
    }
}
