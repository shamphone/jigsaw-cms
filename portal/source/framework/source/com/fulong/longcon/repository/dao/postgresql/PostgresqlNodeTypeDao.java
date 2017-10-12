package com.fulong.longcon.repository.dao.postgresql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.fulong.longcon.repository.dao.NodeTypeDao;
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

public class PostgresqlNodeTypeDao extends NodeTypeDao {
    /**
     * 获得某节点与分类之间的对应关系（包括该分类的子分类）
     * @param definitionID String
     * @param pkid String
     * @return
     * @throws SQLException
     */
   public NodeData[] findByDefinitionRecAndPKID(String definitionID,
                                                 String pkid) throws
       SQLException {
	   String SQL_select_BY_DefinitionRecAndPKID =
	       "select distinct * from node where  pkid=? and definition in (select d.pkid from node_definition d where  d.pkid in ";
	   if(definitionID==null|| definitionID.equals(""))
	   return null;
	   SQL_select_BY_DefinitionRecAndPKID = SQL_select_BY_DefinitionRecAndPKID
				+ " ( "
				+ getClauseForRecursivePostgresql("node_definition", "pkid",
						                     "super_id", definitionID, null,"pkid") + " ))";	  	   
       PreparedStatement command = null;
       try {
           command = connection.prepareStatement(
               SQL_select_BY_DefinitionRecAndPKID);           
           command.setString(1, pkid);
           NodeData[] result = this.retrieve(command,
                                                 SQL_select_BY_DefinitionRecAndPKID);           
           return result;
       } finally {
           this.close(command);
       }
   }
    
   /**
    * 删除大纲与node的对应关系，并递归删除该大纲下子大纲的对应关系。
    * @param definitionID
    * @throws SQLException
    */
  public void deleteByDefinitionRec(String definitionID) throws
      SQLException {
	  String SQL_delete_BY_DefinitionRec =
	      "delete from node where definition in (select d.pkid from node_definition d where  d.pkid in  ";
	  if(definitionID==null|| definitionID.equals(""))
		   return ;
	  SQL_delete_BY_DefinitionRec = SQL_delete_BY_DefinitionRec
					+ " ( "
					+ getClauseForRecursivePostgresql("node_definition", "pkid",
							                     "super_id", definitionID, null,"pkid") + " ))";
      PreparedStatement command = null;
      try {
          command = connection.prepareStatement(
              SQL_delete_BY_DefinitionRec);          
          command.executeUpdate();
      } finally {
          this.close(command);
      }
  }
}
