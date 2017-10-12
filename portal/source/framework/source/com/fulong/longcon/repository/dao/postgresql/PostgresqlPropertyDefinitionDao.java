package com.fulong.longcon.repository.dao.postgresql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.fulong.longcon.repository.dao.PropertyDefinitionDao;
import com.fulong.longcon.repository.dao.QName;
import com.fulong.longcon.repository.data.PropertyDefinitionData;
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
public class PostgresqlPropertyDefinitionDao extends PropertyDefinitionDao{
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
				+ getClauseForRecursivePostgresql("node_definition", "pkid",
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
		"VARCHAR(2048) DEFAULT NULL::character varying",
		"bytea DEFAULT NULL",
		"numeric DEFAULT NULL",
		"numeric DEFAULT NULL",
		"timestamp(6) DEFAULT NULL::timestamp without time zone",
		"bool DEFAULT NULL",
		"VARCHAR(2048) DEFAULT NULL::character varying",
		"VARCHAR(2048) DEFAULT NULL::character varying",
		"VARCHAR(2048) DEFAULT NULL::character varying",
		"text DEFAULT NULL"};

	private void createTable(String tableName, String column, boolean index) throws SQLException {
		connection.commit();//postgresql特殊 需要加上
		StringBuffer query = new StringBuffer("CREATE TABLE ");
		query.append(tableName.toUpperCase());
		query.append("(NODE_ID VARCHAR(128) DEFAULT NULL::character varying NOT NULL,");
		query.append("VALUE "+column+",");
		query.append("VINDEX numeric DEFAULT 0 NOT NULL,");
		query.append("LENGTH numeric DEFAULT 0 NOT NULL)");
		PreparedStatement statement = connection.prepareStatement(query.toString());	
		statement.executeUpdate();
		statement.close();
		// create index on node_id column;
		StringBuffer indexNode = new StringBuffer("create index ");
		indexNode.append(tableName+"_NODE_ID");
		indexNode.append(" on "+tableName);
		indexNode.append(" USING btree (NODE_ID)");
		statement = connection.prepareStatement(indexNode.toString());
		statement.executeUpdate();
		statement.close();
		// create index on vindex column;
		StringBuffer indexV = new StringBuffer("create index ");
		indexV.append(tableName+"_VINDEX");
		indexV.append(" on "+tableName);
		indexV.append(" USING btree (VINDEX)");
		statement = connection.prepareStatement(indexV.toString());
		statement.executeUpdate();
		statement.close();	
		// create index on value column;
		if(index){
		StringBuffer indexValue = new StringBuffer("create index ");
		indexValue.append(tableName+"_VALUE");
		indexValue.append(" on "+tableName);
		indexValue.append(" USING btree (Value)");		
		statement = connection.prepareStatement(indexValue.toString());
		statement.executeUpdate();
		statement.close();
		}
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
        "select distinct * from  PROPERTY_DEFINITION where NODE_DEFINITION_ID =? order by convert_to(NAME,'GBK') ";
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
