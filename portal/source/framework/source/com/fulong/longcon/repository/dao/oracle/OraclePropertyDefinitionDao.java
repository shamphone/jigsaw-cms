package com.fulong.longcon.repository.dao.oracle;

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
 * @author lixf
 * 
 * @version 2.0
 * 
 */
public class OraclePropertyDefinitionDao extends PropertyDefinitionDao {
	/**
	public static final int STRING = 1;
	public static final int BINARY = 2;
	public static final int LONG = 3;
	public static final int DOUBLE = 4;
	public static final int DATE = 5;
	public static final int BOOLEAN = 6;
	public static final int NAME = 7;
	public static final int PATH = 8;
	public static final int REFERENCE = 9;
	public static final int TEXT = 10;
	public static final int FIX = 0;
**/
    private static final String COLUMN_TYPE[]={"",
		"VARCHAR2(2048)",
		"BLOB",
		//处理sql server等数据库number字段数据迁移时出现的精度问题  by mali
		"NUMBER",
		"NUMBER(38,8)",
		"DATE",
		"CHAR(1)",
		"VARCHAR2(2048)",
		"VARCHAR2(2048)",
		"VARCHAR2(2048)",
		"CLOB"};

	private void createTable(String tableName, String column, boolean index) throws SQLException {
		StringBuffer query = new StringBuffer("CREATE TABLE ");
		query.append(tableName.toUpperCase());
		query.append("(NODE_ID VARCHAR2(128) not null,");
		//modified by mali 修改新建内容报错的情况
		//query.append("VALUE "+column+" not null,");
		query.append("VALUE "+column+",");
		query.append("VINDEX NUMBER default 0 not null,");
		query.append("LENGTH NUMBER default 0 not null)");
		PreparedStatement statement = connection.prepareStatement(query.toString());	
		statement.executeUpdate();
		statement.close();
		// create index on node_id column;
		StringBuffer indexNode = new StringBuffer("create index ");
		indexNode.append(tableName+"_NODE_ID");
		indexNode.append(" on "+tableName);
		indexNode.append(" (NODE_ID)");
		statement = connection.prepareStatement(indexNode.toString());
		statement.executeUpdate();
		statement.close();
		// create index on vindex column;
		StringBuffer indexV = new StringBuffer("create index ");
		indexV.append(tableName+"_VINDEX");
		indexV.append(" on "+tableName);
		indexV.append(" (VINDEX)");
		statement = connection.prepareStatement(indexV.toString());
		statement.executeUpdate();
		statement.close();	
		// create index on value column;
		if(index){
		StringBuffer indexValue = new StringBuffer("create index ");
		indexValue.append(tableName+"_VALUE");
		indexValue.append(" on "+tableName);
		indexValue.append(" (Value)");		
		statement = connection.prepareStatement(indexValue.toString());
		statement.executeUpdate();
		statement.close();
		}
	}
    public void insert(PropertyDefinitionData data) throws SQLException {
    		super.insert(data);
    		
    		// 建立对应的表；
    		String tableName = QName.encode(data.getType(), data.getID());

    		if (this.isTableExists(tableName)||data.getType()==0)
    			return;
    		this.createTable(tableName,COLUMN_TYPE[data.getType()], INDEX[data.getType()]);
    }
}
