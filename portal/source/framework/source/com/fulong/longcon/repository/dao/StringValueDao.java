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
public class StringValueDao extends JdbcDao {
	/**
     * 根据内容和属性，获取属性值
     * @param contentID String
     * @param property String
     * @return String[]
     */
    public String[] load(String contentID, String property) throws SQLException {
        String SQL_SELECT =
            "select VALUE from "+QName.encode(PropertyType.STRING,property)+" where NODE_ID=?";
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(SQL_SELECT);
            command.setString(1, contentID);
            String[] result = this.queryStringArray(command);
            return result;
        } finally {
            this.close(command);
        }
    }
    
    /**
     * 插入新值
     * @param contentID String
     * @param property String
     * @param values String[]
     */
    public void insert(String contentID, String property, int index,
                       String value) throws SQLException {
        String SQL_INSERT = "insert into "+QName.encode(PropertyType.STRING,property)+" (NODE_ID,  VALUE, VINDEX, LENGTH) VALUES (?,?,?,?)";
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(SQL_INSERT);
            command.setString(1, contentID);
            command.setString(2, value);
            command.setInt(3, index);
            command.setInt(4,value.length());
            //System.out.println(SQL_INSERT);
            command.executeUpdate();
        } finally {
            this.close(command);
        }
    }

    /**
     * 删除节点的所有属性值
     * @param contentID String
     * @param property String
     * @throws SQLException
     */
    public void delete(String contentID, String property) throws SQLException {
        String SQL_DELETE_BY_ContentAndProperty =
            "delete from "+QName.encode(PropertyType.STRING,property)+" where NODE_ID=?";
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(SQL_DELETE_BY_ContentAndProperty);
            command.setString(1, contentID);
            command.executeUpdate();
        } finally {
            this.close(command);
        }
    }
}
