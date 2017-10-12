package com.fulong.longcon.repository.dao.sqlserver;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.fulong.common.dao.SQLParameter;
import com.fulong.longcon.repository.dao.mysql.MysqlNodeDao;
import com.fulong.longcon.repository.data.NodeData;

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

public class SqlserverNodeDao extends MysqlNodeDao{
    /**
     * 获取指定父结点下的所有子节点，按照分页的模式
     * @param parentID String
     * @param fromIndex int
     * @param number int
     * @return NodeData[]
     * @throws SQLException
     */

    private static final String SQL_Find_By_Parent =
      "select distinct top 100 percent * from NODE where type=1 and PARENT_ID=? order by orderno";
    
    public NodeData[] findByParent(String parentID, long fromIndex, long number) throws
        SQLException {
        PreparedStatement command = null;
        String sql = toRangeQueryForSqlserver(SQL_Find_By_Parent);
        try {
            command = connection.prepareStatement(sql);
            command.setString(1, parentID);
            command.setLong(2, fromIndex + number);
            command.setLong(3, fromIndex);
            NodeData[] result = this.retrieve(command, sql);
            return result;
        }

        finally {
            this.close(command);
            }

        }
    
    public NodeData[] findByParentRec(String parentID, long fromIndex,
                                      long number) throws
        SQLException {
    	String SQL_Find_By_ParentRec =
    	      "select distinct * from node n where type=1 and  n.pkid in ";
    	if(parentID==null ||parentID.equals(""))
        	return null;
        SQL_Find_By_ParentRec = SQL_Find_By_ParentRec 
                             +" ( "+getClauseForRecursive("node", "pkid", "parent_id", 
                            		                                     null, parentID,"pkid")+" ) ";
        PreparedStatement command = null;
        String sql = toRangeQueryForSqlserver(SQL_Find_By_ParentRec);
        try {
            command = connection.prepareStatement(sql);
            command.setLong(1, fromIndex + number);
            command.setLong(2, fromIndex);
            NodeData[] result = this.retrieve(command, sql);
            return result;
        }
        finally {
            this.close(command);
            }
        }

    
    public NodeData[] findByParentRec(String parentID, String name,
                                      long fromIndex, long number) throws
        SQLException {
    	String SQL_Find_By_ParentAndNameRec =
    	      "select distinct * from node n where type=1 and  n.name=? and n.pkid in ";
    	if(parentID==null ||parentID.equals(""))
        	return null;
    	SQL_Find_By_ParentAndNameRec = SQL_Find_By_ParentAndNameRec 
    	                            +" ( "+getClauseForRecursive("node", "pkid", "parent_id", 
    	                            		                                    null, parentID,"pkid")+" ) ";
        PreparedStatement command = null;
        String sql = toRangeQueryForSqlserver(SQL_Find_By_ParentAndNameRec);
        try {
            command = connection.prepareStatement(sql);
            command.setString(1, name);
            command.setLong(2, fromIndex + number);
            command.setLong(3, fromIndex);
            NodeData[] result = this.retrieve(command, sql);
            return result;
        }

        finally {
            this.close(command);
        }
    }
  
    public NodeData[] search(String query,
    						 SQLParameter[] parameters,
    						 int fromIndex,
    						 int number) throws
    	SQLException {
    	String sql = toRangeQueryForSqlserver(query);
    	PreparedStatement statement = null;
    	try {
    		List<SQLParameter> all = new ArrayList<SQLParameter>();
    		if (parameters != null)Collections.addAll(all, parameters);
    		all.add(new SQLParameter(Types.INTEGER,new Integer(fromIndex + number)));
    		all.add(new SQLParameter(Types.INTEGER, new Integer(fromIndex)));
    		statement = this.createStatement(sql,(SQLParameter[]) all.toArray(new SQLParameter[all.size()]));
    		NodeData[] datas = this.retrieve(statement, sql);
    		return datas;
    	} finally {
    		this.close(statement);
    	}

    }
    
    private PreparedStatement createStatement(String sql,
            								  SQLParameter[] parameters) throws
         SQLException
         {
    		PreparedStatement command = null;
    		command = connection.prepareStatement(sql);
    		if((parameters != null) && (parameters.length > 0)) 
    		{
    			for (int i = 0; i < parameters.length; i++)
    			{
    				Object value =  parameters[i].getValue();
    				if(value instanceof Date){
    					value= toTimestame((Date)value);
    					command.setTimestamp(i + 1, (Timestamp)value);
    				}else{
    					command.setObject(i + 1, value);
    				}   
    			}
    		}
    		return command;
         }
    
    private static final String SQL_Find_By_ParentAndName =
      "select distinct top 100 percent * from NODE where type=1 and PARENT_ID=? and NAME = ? order by ORDERNO";
    /**
     * 获取制定父节点下的子节点
     * @param parentID String
     * @param name String
     * @param fromIndex long
     * @param number long
	 * @return NodeData[]
	 * @throws SQLException
	 */
	public NodeData[] findByParent(String parentID, String name, long fromIndex,
	                               long number) throws
	    SQLException {
	    PreparedStatement command = null;
	    String sql = toRangeQueryForSqlserver(SQL_Find_By_ParentAndName);
	    try {
	        command = connection.prepareStatement(sql);
	        command.setString(1, parentID);
	        command.setString(2, name);
	        command.setLong(3, fromIndex + number);
	        command.setLong(4, fromIndex);
	        NodeData[] result = this.retrieve(command, sql);
	        return result;
	    } finally {
	        this.close(command);
	    }
	}	
}
