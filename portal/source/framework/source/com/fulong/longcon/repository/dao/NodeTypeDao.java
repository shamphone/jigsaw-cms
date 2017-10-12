package com.fulong.longcon.repository.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.fulong.common.dao.JdbcDao;
import com.fulong.longcon.repository.data.NodeData;

/**
 * <p>Title: Coolink协同工作支撑平台</p>
 *
 * <p>Description: Coolink协同工作支撑平台</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2007</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author <a href='mailto:lishaobo@fulong.com.cn'>lixf</a>
 * @version 1.0
 */
public class NodeTypeDao extends JdbcDao {

    /**
     * 插入新结点
     * @param data NodeData
     * @throws SQLException
     */
   /* private static final String SQL_INSERT =
        "insert into node_type (pkid, definition, type) VALUES (?,?,?)";
    public void insert(NodeTypeData data) throws SQLException {
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(SQL_INSERT);
            command.setString(1, data.getID());
            command.setString(2, data.getDefinitionID());
            command.setBoolean(3, data.isMain());
            command.executeUpdate();
        } finally {
            this.close(command);
        }
    }*/

    /**
     * 删除结点
     * @param ID String
     * @throws SQLException
     */
/*    private static final String SQL_DELETE_BY_ID =
        "delete from node_type where PKID=?";
    public void delete(String ID) throws SQLException {
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(SQL_DELETE_BY_ID);
            command.setString(1, ID);
            command.executeUpdate();
        }
        finally {
            this.close(command);
        }
    }*/
    
    
    private static final String SQL_DELETEPrimary_BY_ID =
        "delete from node where PKID=? And type='1'";
    /**
     * 删除某节点与其主分类的关系
     * @param ID
     * @throws SQLException
     */
    public void deletePrimary(String ID) throws SQLException {
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(SQL_DELETEPrimary_BY_ID);
            command.setString(1, ID);
            command.executeUpdate();
        }
        finally {
            this.close(command);
        }
    }
    

    private static final String SQL_DELETE_BY_DefinitionID =
        "delete from node where definition=?";
    public void deleteByDefinition(String definitionID) throws SQLException {
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(SQL_DELETE_BY_DefinitionID);
            command.setString(1, definitionID);
            command.executeUpdate();
        }
        finally {
            this.close(command);
        }
    }

    private static final String SQL_DELETE_BY_DefinitionAndPKID =
        "delete from node where definition=? and pkid=?";
    public void deleteByDefinitionAndPKID(String definitionID, String pkid) throws
        SQLException {
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(
                SQL_DELETE_BY_DefinitionAndPKID);
            command.setString(1, definitionID);
            command.setString(2, pkid);
            command.executeUpdate();
        }
        finally {
            this.close(command);
        }
    }

    private static final String SQL_select_BY_DefinitionAndPKID =
        "select * from node where definition =?  and pkid=?";
    public NodeData findByDefinitionAndPKID(String definitionID,
                                                  String pkid) throws
        SQLException {
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(
                SQL_select_BY_DefinitionAndPKID);
            command.setString(1, definitionID);
            command.setString(2, pkid);
            NodeData[] result = this.retrieve(command,
                                                  SQL_select_BY_DefinitionAndPKID);
            if (result.length > 0) {
                return result[0];
            }
            return null;
        } finally {
            this.close(command);
        }
    }

    private static final String SQL_select_BY_DefinitionRecAndPKID =
       "select * from node where definition in (select d.pkid from node_definition d start with d.pkid=? connect by d.super_id= prior d.pkid)  and pkid=?";
    /**
     * 获得某节点与分类之间的对应关系（包括该分类的子分类）
     * @param definitionID
     * @param pkid
     * @return
     * @throws SQLException
     */
    
   public NodeData[] findByDefinitionRecAndPKID(String definitionID,
                                                 String pkid) throws
       SQLException {
       PreparedStatement command = null;
       try {
           command = connection.prepareStatement(
               SQL_select_BY_DefinitionRecAndPKID);
           command.setString(1, definitionID);
           command.setString(2, pkid);
           NodeData[] result = this.retrieve(command,
                                                 SQL_select_BY_DefinitionRecAndPKID);                                
           return result;
       } finally {
           this.close(command);
       }
   }


   private static final String SQL_delete_BY_DefinitionRec =
      "delete from node where definition in (select d.pkid from node_definition d start with d.pkid=? connect by d.super_id= prior d.pkid) ";
   /**
    * 删除大纲与node的对应关系，并递归删除该大纲下子大纲的对应关系。
    * @param definitionID
    * @throws SQLException
    */
  public void deleteByDefinitionRec(String definitionID) throws
      SQLException {
      PreparedStatement command = null;
      try {
          command = connection.prepareStatement(
              SQL_delete_BY_DefinitionRec);
          command.setString(1, definitionID);
          command.executeUpdate();
      } finally {
          this.close(command);
      }
  }



    private static final String SQL_select_PrimaryByPKID =
        "select definition from node where  pkid=? and type=?";
    public String findPrimaryByPKID(String pkid) throws
        SQLException {
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(
                SQL_select_PrimaryByPKID);
            command.setString(1, pkid);
            command.setBoolean(2, true);
            return this.queryString(command);
        } finally {
            this.close(command);
        }
    }

    protected NodeData[] retrieve(PreparedStatement
                                    statement, String sql) throws
        SQLException {
        ResultSet rs = statement.executeQuery();
        ArrayList<NodeData> result = new ArrayList<NodeData>();
        while (rs.next()) {
            NodeData group = new NodeData();
            this.populate(rs, group);
            result.add(group);
        }
        rs.close();
        statement.close();
        return (NodeData[]) result.toArray(new NodeData[
                                               result.size()]);
    }

    private static final String SQL_select_BY_ID =
        "select t.definition from node t where t.PKID=? and t.type=?";
    public String[] findMixinNodeDefinitions(String ID) throws SQLException {
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(SQL_select_BY_ID);
            command.setString(1, ID);
            command.setBoolean(2, false);
            return this.queryStringArray(command);
        } finally {
            this.close(command);
        }
    }

    private void populate(ResultSet rs, NodeData data) throws
        SQLException {
        data.setID(rs.getString("PKID"));
        data.setDefinitionID(rs.getString("definition"));
        data.setMain(rs.getBoolean("type"));
        data.setParentID(rs.getString("PARENT_ID"));
        data.setName(rs.getString("NAME"));
        data.setOrderNo(rs.getInt("ORDERNO"));
    }
    
   /**
    * 
    * @param pkid,definitionID
    * @throws SQLException
    */
    private static final String SQL_SELECT_BY_ID_DEFID = "SELECT TYPE FROM NODE WHERE PKID = ? AND DEFINITION = ?";
    public int getType(String pkid,String defid) throws SQLException 
    {
    	PreparedStatement command = null;
    	try
    	{
    		command = connection.prepareStatement(SQL_SELECT_BY_ID_DEFID);
    		command.setString(1, pkid);
    		command.setString(2, defid);
    		return Integer.parseInt(this.queryString(command));
    	}
    	finally
    	{
    		this.close(command);
    	}
    }
}

