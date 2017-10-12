/**
 * 
 */
package com.fulong.longcon.repository.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.fulong.longcon.repository.PropertyType;
import com.fulong.longcon.repository.dao.ClobValueDao;
import com.fulong.longcon.repository.dao.QName;

/**
 * 
 * 
 * Coolink协同工作框架模型
 * 
 * Copyright: 2009-2011 北京中科辅龙计算机技术股份有限公司
 * 
 * Company: 北京中科辅龙计算机技术股份有限公司
 * 
 * @author LJY
 * @modified songbo 2010-8-20
 * @version 2.0
 * 
 */
public class MysqlClobValueDao extends ClobValueDao {
	/**
	 * 根据内容和属性，获取属性值
	 * 
	 * @param contentID
	 *            String
	 * @param property
	 *            String
	 * @return String[]
	 */
	public String[] load(String contentID, String property) throws SQLException {
        String SQL_SELECT =
            "select VALUE from "+QName.encode(PropertyType.TEXT,property)+" where NODE_ID=? ";
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
	 * 更新属性值。注意：更新之前要删除所有值
	 * 
	 * @param contentID
	 *            String
	 * @param property
	 *            String
	 * @param values
	 *            String
	 */
	public void insert(String contentID, String property, int index,
			String value) throws SQLException {
   	    String SQL_INSERT = 
     	      "insert into  "+QName.encode(PropertyType.TEXT,property)+" VALUES (?,?,?,?)";
		PreparedStatement command = null;
		try {
			command = connection.prepareStatement(SQL_INSERT);
			command.setString(1, contentID);
			command.setString(2, value);
			command.setInt(3, index);
			command.setInt(4, value.length());
			command.executeUpdate();
		} finally {
			this.close(command);
		}
	}
}
