package com.fulong.longcon.repository.dao;

import com.fulong.common.dao.JdbcDao;
import com.fulong.longcon.repository.PropertyType;

import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Clob;
import java.sql.ResultSet;

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
public class ClobValueDao extends JdbcDao {
	/**
     * 根据内容和属性，获取属性值
     * @param contentID String
     * @param property String
     * @return String[]
     */
    public String[] load(String contentID, String property) throws
            SQLException {
        String SQL_SELECT =
            "select VALUE from "+QName.encode(PropertyType.TEXT,property)+" where NODE_ID=? ";
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(SQL_SELECT);
            command.setString(1, contentID);
            String[] result = this.queryClobArray(command);
            return result;
        } finally {
            this.close(command);
        }
    }

    /**
     * 更新属性值。注意：更新之前要删除所有值
     * @param contentID String
     * @param property String
     * @param values String[]
     */

    public void insert(String contentID, String property, int index,
                       String value) throws
            SQLException {
    	   String SQL_INSERT = "insert into "+QName.encode(PropertyType.TEXT,property)+" (NODE_ID, VALUE, VINDEX, LENGTH) VALUES (?,EMPTY_CLOB(),?, ?)";
    	   String SQL_SELECT_UPDATE = "select VALUE from "+QName.encode(PropertyType.TEXT,property)+" where NODE_ID=?  and VINDEX=? for update";
        PreparedStatement command = null;
        ResultSet rs = null;
        try {
            command = connection.prepareStatement(SQL_INSERT);
            command.setString(1, contentID);
            command.setInt(2, index);
            command.setInt(3, value.length());
            command.executeUpdate();

            command = connection.prepareStatement(SQL_SELECT_UPDATE);
            command.setString(1, contentID);
            command.setInt(2, index);
            rs = command.executeQuery();
            while (rs.next()) {
                Clob clob = rs.getClob("VALUE");
                clob.setString(1, value);
            }
        }
        finally {
            this.close(command, rs);
        }
    }

    /**
     * 删除所有属性值
     * @param contentID String
     * @param property String
     * @return String[]
     */
    public void delete(String contentID, String property) throws
            SQLException {
    	String SQL_DELETE_BY_ContentAndProperty =
            "delete from "+QName.encode(PropertyType.TEXT,property)+" where NODE_ID=?";    	
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
