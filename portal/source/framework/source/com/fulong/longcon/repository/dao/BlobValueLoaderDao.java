package com.fulong.longcon.repository.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.fulong.common.dao.JdbcDao;
import com.fulong.longcon.repository.PropertyType;


/**
 * <p>Title: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公司</p>
 *
 * @author <a href='mailto:lixf@fulong.com.cn'>lixf</a>
 * @version 2.0
 */
public class BlobValueLoaderDao extends JdbcDao {
    protected PreparedStatement command;
    protected ResultSet rs;
    
    public InputStream load(String contentID, String property, int index) throws
    SQLException, IOException {
		  String SQL_SELECT1 =
	        "select VALUE from "+QName.encode(PropertyType.BINARY,property)+" where NODE_ID=?  Order By vindex";
	    command = connection.prepareStatement(toRangeQuery(SQL_SELECT1));
	    command.setString(1, contentID);
	    command.setInt(2, index+1);
	    command.setInt(3, index);
	    this.rs = command.executeQuery();
	    if (rs.next()) {
	        Blob blob = rs.getBlob(1);
	        return blob.getBinaryStream();
	    }
	    return null;
    }

    public void close() throws SQLException {
        if(this.command!=null)
            this.command.close();
        if(this.rs!=null)
            this.rs.close();
    }
}
