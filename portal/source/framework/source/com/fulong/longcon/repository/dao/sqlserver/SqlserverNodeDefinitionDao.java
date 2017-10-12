package com.fulong.longcon.repository.dao.sqlserver;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.fulong.longcon.repository.dao.NodeDefinitionDao;
import com.fulong.longcon.repository.data.NodeDefinitionData;
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
public class SqlserverNodeDefinitionDao extends NodeDefinitionDao{
    
	/**
     * 根据ID来查询属性定义,注意：为了保持数据完整，做了删除标记的定义。
     * @param id String
     * @return NodeDefinitionData
     * @throws SQLException
     */
	private static final String SQL_FIND_BY_SUPER =
      "select distinct top 100 percent * from NODE_DEFINITION where  super_id=? and DELETE_MARK='0' order by create_time desc ";
    public NodeDefinitionData[] findBySuper(String superId) throws SQLException {
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(SQL_FIND_BY_SUPER);
            command.setString(1, superId);
            NodeDefinitionData[] result = this.retrieve(command,
                    SQL_FIND_BY_SUPER);
            return result;
        } finally {
            this.close(command);
        }
    }
    
    private static final String SQL_FIND_BY_NAME =
      "select distinct top 100 percent * from NODE_DEFINITION where  name=?  and DELETE_MARK='0' order by create_time desc ";
    /**
     * 根据NAME来查询，只查询未删除的
     * @param name String
     * @return NodeDefinitionData
     * @throws SQLException
     */
    public NodeDefinitionData findByName(String name) throws SQLException {
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(SQL_FIND_BY_NAME);
            command.setString(1, name);
            NodeDefinitionData[] result = this.retrieve(command,
                    SQL_FIND_BY_NAME);
            if (result.length > 0) {
                return result[0];
            }
            return null;
        } finally {
            this.close(command);
        }
    }
    
    private static final String SQL_FIND_ALL =
      "select distinct top 100 percent * from NODE_DEFINITION where DELETE_MARK ='0' order by create_time asc ";
    /**
     * 查找未删除的
     * @return NodeDefinitionData[]
     * @throws SQLException
     */
    public NodeDefinitionData[] findAll() throws SQLException {
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(SQL_FIND_ALL);
            return this.retrieve(command, SQL_FIND_ALL);
        } finally {
            this.close(command);
        }
    }
    
    /**
     *
     * @param superid String
     * @return NodeDefinitionData[]
     * @throws SQLException
     */
    public NodeDefinitionData[] findAllBySupser(String superid) throws
            SQLException {
    	String SQL_FIND_BY_SUPERDEFINITION=
    	  "select distinct top 100 percent * from node_definition t Where t.delete_mark='0' and t.pkid in ";
    	if(superid==null || superid.equals(""))
        	return null;
    	SQL_FIND_BY_SUPERDEFINITION =SQL_FIND_BY_SUPERDEFINITION 
                                     +" ( "+getClauseForRecursive("node_definition","pkid","SUPER_ID",
                                    		 									superid,null,"pkid") +" )"
                                     +" order by create_time desc "   		                                 ;
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(SQL_FIND_BY_SUPERDEFINITION);
            return this.retrieve(command, SQL_FIND_BY_SUPERDEFINITION);
        } finally {
            this.close(command);
        }
    }
}
