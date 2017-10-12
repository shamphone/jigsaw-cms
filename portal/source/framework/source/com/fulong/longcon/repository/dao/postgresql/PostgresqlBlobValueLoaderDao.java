package com.fulong.longcon.repository.dao.postgresql;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import com.fulong.longcon.repository.PropertyType;
import com.fulong.longcon.repository.dao.BlobValueLoaderDao;
import com.fulong.longcon.repository.dao.QName;

/**
 *  
 * Coolink协同工作框架模型
 * 
 * Copyright: 2009-2011 北京中科辅龙计算机技术股份有限公司
 * 
 * Company: 北京中科辅龙计算机技术股份有限公司
 * 
 * @author songbo
 * 
 * @version 2.0
 * 
 */

public class PostgresqlBlobValueLoaderDao extends BlobValueLoaderDao{

	public InputStream load(String contentID, String property, int index) throws
    SQLException, IOException {
		String SQL_SELECT1 =
	      "select VALUE from "+QName.encode(PropertyType.BINARY,property)+" where NODE_ID=?  Order By vindex";
	    command = connection.prepareStatement(toRangeQueryForPostgresql(SQL_SELECT1));
	    command.setString(1, contentID);
	    command.setInt(2, index);
	    command.setInt(3, 1);
	    this.rs = command.executeQuery();
	    if (rs.next()) {
	    	InputStream out = rs.getBinaryStream(1);
	        return out;
	    }
	    return null;
	}
}
