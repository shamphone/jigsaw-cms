package com.fulong.longcon.repository.dao;

import com.fulong.common.dao.JdbcDao;
import com.fulong.longcon.repository.PropertyType;

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
public class DoubleValueDao extends JdbcDao {
   /**
     * 根据内容和属性，获取属性值
     * @param contentID String
     * @param property String
     * @return String[]
     */
    public double[] load(String contentID, String property) throws SQLException {
        String SQL_SELECT =
            "select VALUE from "+QName.encode(PropertyType.DOUBLE,property)+" where NODE_ID=? ";
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(SQL_SELECT);
            command.setString(1, contentID);
            double[] result = this.queryDoubleArray(command);
            return result;
        } finally {
            this.close(command);
        }
    }
    
    /**
     *
     * @param contentID String
     * @param property String
     * @param index int
     * @param value double
     * @throws SQLException
     */
    public void insert(String contentID, String property, int index,
                       double value) throws SQLException {
        String SQL_INSERT = "insert into "+QName.encode(PropertyType.DOUBLE,property)+" (NODE_ID, VALUE, VINDEX) VALUES (?,?,?)";
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(SQL_INSERT);
            command.setString(1, contentID);
            command.setDouble(2, value);
            command.setInt(3, index);
            command.executeUpdate();
        } finally {
            this.close(command);
        }
    }

    /**
     * 删除所有属性值
     * @param contentID String
     * @param property String
     * @throws SQLException
     */
    public void delete(String contentID, String property) throws SQLException {
        String SQL_DELETE_BY_ContentAndProperty =
            "delete from "+QName.encode(PropertyType.DOUBLE,property)+" where NODE_ID=?";
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(
                    SQL_DELETE_BY_ContentAndProperty);
            command.setString(1, contentID);
            command.executeUpdate();
        } finally {
            this.close(command);
        }
    }
}
