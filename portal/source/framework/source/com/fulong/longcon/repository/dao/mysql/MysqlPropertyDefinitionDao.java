package com.fulong.longcon.repository.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.fulong.longcon.repository.dao.PropertyDefinitionDao;
import com.fulong.longcon.repository.dao.QName;
import com.fulong.longcon.repository.data.PropertyDefinitionData;

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
public class MysqlPropertyDefinitionDao extends PropertyDefinitionDao {
	/**
	 * 查询ID为pkid的属性是否在大纲nodeDefinitionID以及其父大纲（完全递归）中存在。
	 * 
	 * @param nodeDefinitionID String
	 * @param pkid String
	 * @return PropertyDefinitionData[]
	 * @throws SQLException
	 */
	public PropertyDefinitionData[] findByRecPNodeDefinition(
			String nodeDefinitionID, String pkid) throws SQLException {
		String SQL_Find_ByRecPNodeDefinitionAndPKID = "Select distinct * From property_definition d Where d.pkid=? and d.node_definition_id In ";
		if (nodeDefinitionID == null || nodeDefinitionID.equals(""))
			return null;
		SQL_Find_ByRecPNodeDefinitionAndPKID = SQL_Find_ByRecPNodeDefinitionAndPKID
				+ " ( "
				+ getClauseForRecursive("node_definition", "pkid",
					                       	 "super_id", nodeDefinitionID, null,"pkid") + " )";
		PreparedStatement command = null;
		try {
			command = connection
					.prepareStatement(SQL_Find_ByRecPNodeDefinitionAndPKID);
			command.setString(1, pkid);	
			PropertyDefinitionData[] result = this.retrieve(command,
					SQL_Find_ByRecPNodeDefinitionAndPKID);
			return result;
		} finally {
			this.close(command);
		}
	}
	private static final String COLUMN_TYPE[]={"",
		"VARCHAR(2048)",
		"longblob",
		"numeric(22, 0)",
		"numeric(38,8)",
		"DATETIME",
		"CHAR(1)",
		"VARCHAR(2048)",
		"VARCHAR(2048)",
		"VARCHAR(2048)",
		"longtext"};

	private void createTable(String tableName, String column, boolean index) throws SQLException {
		StringBuffer query = new StringBuffer("CREATE TABLE ");
		query.append(tableName.toUpperCase());
		query.append("(NODE_ID VARCHAR(128) not null,");
		query.append("VALUE "+column+" null,");
		query.append("VINDEX numeric(22, 0) not null DEFAULT 0,");
		query.append("LENGTH numeric(22, 0) not null DEFAULT 0,");
		query.append("KEY "+tableName+"_node_id (NODE_ID),");
		if(index)
			query.append("KEY "+tableName+"_value (VALUE),");
		query.append("KEY "+tableName+"_vindex (VINDEX))");
		query.append(" ENGINE=InnoDB DEFAULT CHARSET=utf8");
		PreparedStatement statement = connection.prepareStatement(query.toString());
		statement.executeUpdate();
		statement.close();
	}
	
    /**
     * 新建表
     * @param data PropertyDefinitionData
     * @throws SQLException
     */
    public void insert(PropertyDefinitionData data) throws SQLException {
		super.insert(data);
		
		// 建立对应的表；
		String tableName = QName.encode(data.getType(), data.getID());

		if (this.isTableExists(tableName)||data.getType()==0)
			return;
		this.createTable(tableName,COLUMN_TYPE[data.getType()], INDEX[data.getType()]);
    }
    
    /**
     * 获取节点定义的所有简单属性定义。
     * @param nodeDefinitionID String
     * @return PropertyDefinitionData[]
     */
    private static final String SQL_Find_ByNodeDefinition =
        "select distinct * from  PROPERTY_DEFINITION where NODE_DEFINITION_ID =? order by convert(NAME using gbk) collate gbk_chinese_ci";
    public PropertyDefinitionData[] findByNodeDefinition(String
        nodeDefinitionID) throws SQLException {
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(SQL_Find_ByNodeDefinition);
            command.setString(1, nodeDefinitionID);
            PropertyDefinitionData[] result = this.retrieve(command,SQL_Find_ByNodeDefinition);
            return result;
        }
        finally {
            this.close(command);
        }
    }
}
