package com.fulong.longcon.repository.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.fulong.longcon.repository.dao.NodeDefinitionDao;
import com.fulong.longcon.repository.data.NodeDefinitionData;

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
public class MysqlNodeDefinitionDao extends NodeDefinitionDao {
	/**
	 * 
	 * @param superid
	 *            String
	 * @return NodeDefinitionData[]
	 * @throws SQLException
	 */
	public NodeDefinitionData[] findAllBySupser(String superid)
			throws SQLException {
		String SQL_FIND_BY_SUPERDEFINITION = " select distinct * from node_definition t Where t.delete_mark='0' and t.pkid in ";
		if (superid == null || superid.equals(""))
			return null;
		SQL_FIND_BY_SUPERDEFINITION = SQL_FIND_BY_SUPERDEFINITION
				+ " ( "
				+ getClauseForRecursive("node_definition", "pkid",
						                     "super_id", superid, null,"pkid")
				+ " ) order by create_time desc";
		PreparedStatement command = null;
		try {
			command = connection.prepareStatement(SQL_FIND_BY_SUPERDEFINITION);			
			return this.retrieve(command, SQL_FIND_BY_SUPERDEFINITION);
		} finally {
			this.close(command);
		}
	}
}
