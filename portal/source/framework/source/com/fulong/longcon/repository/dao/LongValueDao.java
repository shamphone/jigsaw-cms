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
public class LongValueDao extends JdbcDao {
    /**
     * 根据内容和属性，获取属性值
     * @param contentID String
     * @param property String
     * @return String[]
     */
    public long[] load(String contentID, String property) throws SQLException {
        String SQL_SELECT =
            "select VALUE from "+QName.encode(PropertyType.LONG,property)+" where NODE_ID=?";
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(SQL_SELECT);
            command.setString(1, contentID);
            long[] result = this.queryLongArray(command);
            return result;
        } finally {
            this.close(command);
        }
    }

    /**
     * 更新属性值。
     * @param contentID String
     * @param property String
     * @param values String[]
     */
    public void insert(String contentID, String property, int index, long value) throws
            SQLException {
        String SQL_INSERT =
            "insert into "+QName.encode(PropertyType.LONG,property)+" (NODE_ID, VALUE, VINDEX) VALUES (?,?,?)";
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
     * 更新属性值。
     * @param contentID String
     * @param property String
     * @param values String[]
     */
    public void delete(String contentID, String property) throws SQLException {
        String SQL_DELETE_BY_ContentAndProperty =
            "delete from "+QName.encode(PropertyType.LONG,property)+" where NODE_ID=?";
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

    /**
     *取某个类下属性的总和
     * @param property String
     * @param parent String
     * @return int
     * @throws SQLException
     */
    public int getSum(String property, String parent) throws SQLException {
        String SQL_SUM =
            "Select Sum(Value)  From "+QName.encode(PropertyType.LONG,property)+" t  " +
            " Where  t.Node_Id In (Select pkid From node n Where n.parent_id =?)";
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(SQL_SUM);
            command.setString(1, parent);
            int result = this.queryInt(command);
            return result;
        } finally {
            this.close(command);
        }
    }
}
