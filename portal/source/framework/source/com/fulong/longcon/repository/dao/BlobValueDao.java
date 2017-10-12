package com.fulong.longcon.repository.dao;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.fulong.common.dao.JdbcDao;
import com.fulong.longcon.repository.PropertyType;

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
public class BlobValueDao extends JdbcDao {
	  public static final int BUFFER_SIZE = 1024 * 1024;
	    /**
	     * 根据内容和属性，获取属性值
	     * @param contentID String
	     * @param property String
	     * @return String[]
	     */
	    public int count(String contentID, String property) throws
	        SQLException {
		    StringBuffer sql =new StringBuffer("select count(*) from ");
		    sql.append(QName.encode(PropertyType.BINARY, property));
		    sql.append(" where NODE_ID=?");
	        PreparedStatement command = null;
	        try {
	            command = connection.prepareStatement(sql.toString());
	            command.setString(1, contentID);
	            return this.queryInt(command);
	        } finally {
	            this.close(command);
	        }
	    }

	    /**
	     * 查找长度
	     * @param contentID String
	     * @param property String
	     * @return long[]
	     * @throws SQLException
	     */
	    public long[] getLengthes(String contentID, String property) throws
	        SQLException {
		    StringBuffer sql =new StringBuffer();
		    sql.append("select LENGTH from ");
		    sql.append(QName.encode(PropertyType.BINARY,property));
	    	sql.append(" where NODE_ID=? Order By vindex");
	        PreparedStatement command = null;
	        try {
	            command = connection.prepareStatement(sql.toString());
	            command.setString(1, contentID);
	            long[] result = this.queryLongArray(command);
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

	    /**
	     *
	     * @param contentID String
	     * @param property String
	     * @param index int
	     * @param values InputStream
	     * @throws SQLException
	     */
	    public void insert(String contentID, String property, int index) throws
	        SQLException {
		    String SQL_INSERT = "insert into "+QName.encode(PropertyType.BINARY,property)+" (NODE_ID, VALUE, VINDEX) VALUES (?,EMPTY_BLOB(),?)";
	        PreparedStatement command = null;
	        try {
	            command = connection.prepareStatement(SQL_INSERT);
	            command.setString(1, contentID);
	            command.setInt(2, index);
	            command.executeUpdate();
	        } finally {
	            this.close(command);
	        }

	    }

	    public void insertValue(String contentID, String property, int index,
	                            InputStream in) throws
	        SQLException, IOException {
		    String SQL_SELECT_UPDATE = "select VALUE from "+QName.encode(PropertyType.BINARY,property)+" where NODE_ID=? and VINDEX=? for update";
	        PreparedStatement command = null;
	        ResultSet rs=null;
	        try {
	            command = connection.prepareStatement(SQL_SELECT_UPDATE);
	            command.setString(1, contentID);
	            command.setInt(2, index);
	            rs = command.executeQuery();
	            if (rs.next()) {
	                /* 取出此BLOB對像 */
	                Blob blob =  rs.getBlob("VALUE");
	                long pos = 0;
	                int read = 0;
	                byte[] buffer = new byte[BUFFER_SIZE];
	                OutputStream os = blob.setBinaryStream(0);
	                while ( (read = in.read(buffer)) >= 0) {
	                    os.write(buffer, 0, read);
	                    pos += read;
	                }
	                this.updateLength(contentID, property, index, pos);
	                in.close();
	                os.close();
	            }
	        } finally {
	            this.close(command, rs);
	        }
	    }

	    /**
	     * 向blob_value中添加流的长度
	     * @param contentID 节点
	     * @param length 流长度
	     * @throws SQLException
	     */
	    private void updateLength(String contentID, String property, int index,
	                              long length) throws
	        SQLException {
		    String SQL_UPDATE =
		        "Update "+QName.encode(PropertyType.BINARY,property)+" t Set t.length =? Where t.node_id =?  and vindex=?";
	        PreparedStatement command = null;
	        try {
	            command = connection.prepareStatement(SQL_UPDATE);
	            command.setLong(1, length);
	            command.setString(2, contentID);
	            command.setInt(3, index);
	            command.executeQuery();
	        } finally {
	            this.close(command);
	        }
	    }

	    /**
	     * 删除结点属性的值
	     * @param contentID String
	     * @param property String
	     * @throws SQLException
	     */
	    public void delete(String contentID, String property) throws
	        SQLException {
		    String SQL_DELETE_BY_ContentAndProperty =
		        "delete from "+QName.encode(PropertyType.BINARY,property)+" where NODE_ID=?";
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
