/**
 * 
 */
package com.fulong.longcon.repository.dao.mysql;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.fulong.longcon.repository.PropertyType;
import com.fulong.longcon.repository.dao.BlobValueDao;
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
 * @author lixf
 * @modified songbo 2010-8-20
 * @version 2.0
 * 
 */
public class MysqlBlobValueDao extends BlobValueDao {
	/**
	 * 更新属性值。注意：更新之前要删除所有值
	 * 
	 * @param contentID
	 *            String
	 * @param property
	 *            String
	 * @param index
	 *            int
	 */
	public void insert(String contentID, String property, int index)
			throws SQLException {
	}
	
	/**
	 * 
	 * @param contentID
	 *            String
	 * @param property
	 *            String
	 * @param index
	 *            int
	 * @param in
	 *            InputStream
	 * @throws SQLException
	 */
	public void insertValue(String contentID, String property, int index,
			InputStream in) throws SQLException, IOException {
	    String SQL_INSERT = 
		    "insert into "+QName.encode(PropertyType.BINARY,property)+" VALUES (?,?,?,?)";
 		ByteArrayOutputStream baos=new ByteArrayOutputStream();
        long pos = 0;
        int read = 0;
        byte[] buffer = new byte[BUFFER_SIZE];
        while((read=in.read(buffer))>=0)
        {
        	baos.write(buffer,0,read);
        	pos+=read;
        }
        byte[] is =baos.toByteArray();
		PreparedStatement command = null;
		try {
			command = connection.prepareStatement(SQL_INSERT);
			command.setString(1, contentID);	
			command.setBytes(2, is);
			command.setInt(3, index);
			command.setLong(4, pos);
			command.executeUpdate();
		} finally {
			this.close(command);
		}
	}
}
