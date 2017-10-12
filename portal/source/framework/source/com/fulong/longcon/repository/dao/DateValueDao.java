package com.fulong.longcon.repository.dao;

import com.fulong.common.dao.JdbcDao;
import com.fulong.longcon.repository.PropertyType;

import java.sql.SQLException;
import java.util.Date;
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
public class DateValueDao extends JdbcDao {
	 /**
     * 根据内容和属性，获取属性值
     * @param contentID String
     * @param property String
     * @return String[]
     */
    public Date[] load(String contentID, String property) throws
            SQLException {
    	String SQL_SELECT = "select value from "+QName.encode(PropertyType.DATE,property)+" where NODE_ID=? ";
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(SQL_SELECT);
            command.setString(1, contentID);
            Date[] result = this.queryDateArray(command);
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
     * @param value Date
     * @throws SQLException
     */
    public void insert(String contentID, String property, int index, Date value) throws
            SQLException {
    	String SQL_INSERT = "insert into "+QName.encode(PropertyType.DATE,property)+
    	                      " (NODE_ID, VALUE, VINDEX) VALUES (?,?,?)";
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(SQL_INSERT);
            command.setString(1, contentID);
            command.setTimestamp(2, toTimestame(value));
            command.setInt(3, index);
            command.executeUpdate();
        } finally {
            this.close(command);
        }
    }

    public void delete(String contentID, String property) throws SQLException {
    	String SQL_DELETE_BY_ContentAndProperty =
    		"delete from "+QName.encode(PropertyType.DATE,property)+" where NODE_ID=?";
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
