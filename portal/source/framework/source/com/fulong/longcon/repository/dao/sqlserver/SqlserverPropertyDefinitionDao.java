package com.fulong.longcon.repository.dao.sqlserver;

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
public class SqlserverPropertyDefinitionDao extends PropertyDefinitionDao{
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
	
   /**
    * 获取节点定义的所有属性定义。
    * @param nodeDefinitionID String
    * @return PropertyDefinitionData[]
    */
	private static final String SQL_Find_All =
      "select distinct top 100 percent * from  PROPERTY_DEFINITION where NODE_DEFINITION_ID =? order by ORDERNO ";
	public PropertyDefinitionData[] findDefs(String
		       nodeDefID) throws SQLException {
		       PreparedStatement command = null;
		       try {
		           command = connection.prepareStatement(SQL_Find_All);
		           command.setString(1, nodeDefID);
		           PropertyDefinitionData[] result = this.retrieve(command,SQL_Find_All);
		           return result;
		       }
		       finally {
		           this.close(command);
		       }
	}
	
    /**
     * 获取节点定义的所有简单属性定义。
     * @param nodeDefinitionID String
     * @return PropertyDefinitionData[]
     */
	private static final String SQL_Find_ByNodeDefinition = 
	  "select distinct top 100 percent * from  PROPERTY_DEFINITION where NODE_DEFINITION_ID =? order by NAME ";
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
    
    private static final String SQL_FindChild_ByNodeDefinition = 
	  "select distinct top 100 percent * from  PROPERTY_DEFINITION where NODE_DEFINITION_ID =? and type=0 order by  ORDERNO ";
    /**
     * 查询复合属性
     * @param nodeDefinitionID String
     * @param pkid String
     * @return PropertyDefinitionData[]
     * @throws SQLException
     */
    public PropertyDefinitionData[] findChildsByNodeDef(String
        nodeDefinitionID) throws SQLException {
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(SQL_FindChild_ByNodeDefinition);
            command.setString(1, nodeDefinitionID);
            PropertyDefinitionData[] result = this.retrieve(command,SQL_FindChild_ByNodeDefinition);
            return result;
        }
        finally {
            this.close(command);
        }
    }
    
	private static final String COLUMN_TYPE[]={"",
		"VARCHAR(900)",
		"varbinary(max)",
		"numeric(38, 0)",
		"numeric(38,8)",
		"DATETIME",
		"CHAR(1)",
		"VARCHAR(900)",
		"VARCHAR(900)",
		"VARCHAR(900)",
		"VARCHAR(max)"};

	private void createTable(String tableName, String column, boolean index) throws SQLException {
		StringBuffer query = new StringBuffer("CREATE TABLE ");
		query.append(tableName.toUpperCase());
		query.append("(NODE_ID VARCHAR(128) not null,");
		query.append("VALUE "+column+" null,");
		query.append("VINDEX numeric(38, 0) not null CONSTRAINT [DF_"+tableName+"_VINDEX]  DEFAULT ((0)),");
		query.append("LENGTH numeric(38, 0) not null CONSTRAINT [DF_"+tableName+"_LENGTH]  DEFAULT ((0)))");
		PreparedStatement statement = connection.prepareStatement(query.toString());		
		statement.executeUpdate();
		statement.close();
		// create index on node_id column;
		StringBuffer indexNode = new StringBuffer("create NONCLUSTERED index ");
		indexNode.append(tableName+"_NODE_ID");
		indexNode.append(" on "+tableName);
		indexNode.append(" (NODE_ID)");
		statement = connection.prepareStatement(indexNode.toString());
		statement.executeUpdate();
		statement.close();
		// create index on vindex column;
		StringBuffer indexV = new StringBuffer("create NONCLUSTERED index ");
		indexV.append(tableName+"_VINDEX");
		indexV.append(" on "+tableName);
		indexV.append(" (VINDEX)");
		statement = connection.prepareStatement(indexV.toString());
		statement.executeUpdate();
		statement.close();	
		// create index on value column;
		if(index){
		StringBuffer indexValue = new StringBuffer("create NONCLUSTERED index ");
		indexValue.append(tableName+"_VALUE");
		indexValue.append(" on "+tableName);
		indexValue.append(" (Value)");
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
}
