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
 * @author <a href='mailto:lixf@fulong.com.cn'>lixf</a>
 * @version 1.0
 */
public class PropertyDefaultValueDao extends JdbcDao {
    /**
     * 获取缺省值
     * @param propertyID String
     * @return String[]
     * @throws SQLException
     */
    private static final String SQL_SELECT =
            "select VALUE from PROPERTY_DEFAULT where PROPERTY_DEFINITION_ID=? and definition_id=?";
    public String[] findByPropertyDefinition(String propertyID, String definitionID) throws
            SQLException {
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(SQL_SELECT);
            command.setString(1, propertyID);
            command.setString(2, definitionID);
            String[] result = this.queryStringArray(command);
            return result;
        } finally {
            this.close(command);
        }
    }

    /**
     * 删除指定属性定义的所有缺省值
     * @param propertyID String
     * @throws SQLException
     */
    private static final String SQL_DELETE_BY_ID =
            "delete from PROPERTY_DEFAULT where PROPERTY_DEFINITION_ID=? and  definition_id=?";
    public void delete(String propertyID, String definitionID) throws SQLException {
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(SQL_DELETE_BY_ID);
            command.setString(1, propertyID);
            command.setString(2, definitionID);
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
            "insert into PROPERTY_DEFAULT (PROPERTY_DEFINITION_ID, VALUE, definition_id) VALUES (?,?, ?)";
    public void insert(String propertyID, String value, String definitionID) throws
            SQLException {
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(SQL_INSERT);
            command.setString(1, propertyID);
            command.setString(2, value);
            command.setString(3, definitionID);
            command.executeUpdate();
        } finally {
            this.close(command);
        }
    }
}
