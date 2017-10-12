package com.fulong.longcon.repository.dao;

import com.fulong.common.dao.JdbcDao;
import com.fulong.longcon.repository.data.PropertyDefinitionData;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * <p>Title: Coolink协同工作支撑平台</p>
 *
 * <p>Description: Coolink协同工作支撑平台</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2007</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author <a href='mailto:lishaobo@fulong.com.cn'>lishaobo</a>
 * @version 1.0
 */
public class PropertyDefinitionDao
    extends JdbcDao {

    /**
    * 获取节点定义的所有属性定义。
    * @param nodeDefinitionID String
    * @return PropertyDefinitionData[]
    */

   private static final String SQL_Find_All =
       "select distinct * from  PROPERTY_DEFINITION where NODE_DEFINITION_ID =? order by ORDERNO ";
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
        "select distinct * from  PROPERTY_DEFINITION where NODE_DEFINITION_ID =? order by nlssort(name,'NLS_SORT=SCHINESE_PINYIN_M') ";
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

    private static final String SQL_Find_ByNodeDefinitionAndPKID =
        "select distinct * from  PROPERTY_DEFINITION where NODE_DEFINITION_ID =? and PKID=?";
    public PropertyDefinitionData[] findByNodeDefinition(String
        nodeDefinitionID, String pkid) throws SQLException {
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(
                SQL_Find_ByNodeDefinitionAndPKID);
            command.setString(1, nodeDefinitionID);
            command.setString(2, pkid);
            PropertyDefinitionData[] result = this.retrieve(command,SQL_Find_ByNodeDefinitionAndPKID);
            return result;
        }
        finally {
            this.close(command);
        }
    }
    
    private static final String SQL_Find_ByRecPNodeDefinitionAndPKID =
        "Select * From property_definition d Where d.pkid=? and d.node_definition_id In " +
        "(Select n.pkid From node_definition n Start With n.pkid=? Connect By Prior n.super_id=n.pkid)";
    /**
     * 查询ID为pkid的属性是否在大纲nodeDefinitionID以及其父大纲（完全递归）中存在。
     * @param nodeDefinitionID
     * @param pkid
     * @return
     * @throws SQLException
     */
    public PropertyDefinitionData[] findByRecPNodeDefinition(String
        nodeDefinitionID, String pkid) throws SQLException {
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(SQL_Find_ByRecPNodeDefinitionAndPKID);
            command.setString(1, pkid);
            command.setString(2, nodeDefinitionID);
            
            PropertyDefinitionData[] result = this.retrieve(command,SQL_Find_ByRecPNodeDefinitionAndPKID);
            return result;

        }
        finally {
            this.close(command);
        }
    }

    private static final String SQL_Find_ByRefNodeDefinition =
          "select distinct * from  PROPERTY_DEFINITION where reference_type =?";
    /**
     * 查询引用了ID为nodeDefinitionID大纲的所有的属性定义
     * @param nodeDefinitionID
     * @return
     * @throws SQLException
     */
      public PropertyDefinitionData[] findByRefNodeDefinition(String nodeDefinitionID) throws SQLException {
          PreparedStatement command = null;
          try {
              command = connection.prepareStatement(SQL_Find_ByRefNodeDefinition);
              command.setString(1, nodeDefinitionID);
              PropertyDefinitionData[] result = this.retrieve(command,SQL_Find_ByRefNodeDefinition);
              return result;
            }
          finally {
              this.close(command);
          }
    }

      private static final String SQL_FindChild_ByNodeDefinition =
         "select distinct * from  PROPERTY_DEFINITION where NODE_DEFINITION_ID =? and type=0 order by  ORDERNO ";
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

    protected PropertyDefinitionData[] retrieve(PreparedStatement
                                              statement, String sql) throws
        SQLException {
        ResultSet rs = statement.executeQuery();
        ArrayList<PropertyDefinitionData> result = new ArrayList<PropertyDefinitionData>();
        while (rs.next()) {
            PropertyDefinitionData group = new PropertyDefinitionData(); ;
            this.populate(rs, group);
            result.add(group);
        }
        rs.close();
        statement.close();
        return (PropertyDefinitionData[]) result.toArray(new
            PropertyDefinitionData[
            result.size()]);
    }

    protected void populate(ResultSet rs, PropertyDefinitionData data) throws
        SQLException {
        data.setID(rs.getString("PKID"));
        data.setName(rs.getString("NAME"));
        data.setType(rs.getInt("TYPE"));
        data.setMaxLength(rs.getInt("MAX_LENGTH"));
        data.setMinLength(rs.getInt("MIN_LENGTH"));
        data.setMultiple(rs.getBoolean("MULTIPLE"));
        data.setNodeDefinitionID(rs.getString("NODE_DEFINITION_ID"));
        data.setOrderNo(rs.getInt("ORDERNO"));
        data.setDescription(rs.getString("DESCRIPTION"));
        data.setReferenceType(rs.getString("REFERENCE_TYPE"));
        data.setEnumEntry(rs.getString("ENUM_ENTRY"));
        data.setDeletable(rs.getBoolean("DELETABLE"));
        data.setNodeType(rs.getString("NODE_TYPE"));
        data.setReadOnly(rs.getBoolean("READ_ONLY"));
    }
	protected static final boolean INDEX[]={false,
		true,
		false,
		true,
		true,
		true,
		true,
		true,
		true,
		true,
		false};
    private static final String SQL_CREATE =
        "insert into PROPERTY_DEFINITION (PKID, NODE_DEFINITION_ID, NAME, TYPE, MULTIPLE, MIN_LENGTH,MAX_LENGTH," +
        "  ORDERNO, DESCRIPTION, ENUM_ENTRY, REFERENCE_TYPE, DELETABLE,NODE_TYPE,READ_ONLY) " +
        " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";
    /**
     * 插入属性定义
     * @param data PropertyDefinitionData
     * @throws SQLException
     */
    public void insert(PropertyDefinitionData data) throws SQLException {
        if (data.getID() == null)
            data.setID("" + getNextID());

        PropertyDefinitionData[] datas = this.findByNodeDefinition(data.
            getNodeDefinitionID(), data.getID());
        if ( (datas != null) && (datas.length > 0))
            return;

        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(SQL_CREATE);
            command.setString(1, data.getID());
            command.setString(2, data.getNodeDefinitionID());
            command.setString(3, data.getName());
            command.setInt(4, data.getType());
            command.setBoolean(5, data.isMultiple());
            command.setInt(6, data.getMinLength());
            command.setInt(7, data.getMaxLength());
            command.setInt(8, data.getOrderNo());
            command.setString(9, data.getDescription());
            command.setString(10, data.getEnumEntry());
            command.setString(11, data.getReferenceType());
            command.setBoolean(12, data.isDeletable());
            command.setString(13,data.getNodeType());
            command.setBoolean(14,data.isReadOnly());
            command.executeUpdate();
        }
        finally {
            this.close(command);
        }
    }

    private static final String SQL_UPDATE =
        "update PROPERTY_DEFINITION set NAME=?, TYPE=?, MULTIPLE=?, MIN_LENGTH=?,MAX_LENGTH=?,  ORDERNO = ?, DESCRIPTION = ?, ENUM_ENTRY=?, REFERENCE_TYPE=?, DELETABLE=?,NODE_TYPE=?,READ_ONLY=?  where PKID=? and NODE_DEFINITION_ID=?";
    /**
     * 更新属性定义
     * @param data PropertyDefinitionData
     * @throws SQLException
     */
    public void update(PropertyDefinitionData data) throws SQLException {
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(SQL_UPDATE);
            command.setString(1, data.getName());
            command.setInt(2, data.getType());
            command.setBoolean(3, data.isMultiple());
            command.setInt(4, data.getMinLength());
            command.setInt(5, data.getMaxLength());
            command.setInt(6, data.getOrderNo());
            command.setString(7, data.getDescription());
            command.setString(8, data.getEnumEntry());
            command.setString(9, data.getReferenceType());
            command.setBoolean(10, data.isDeletable());
            command.setString(11,data.getNodeType());
            command.setBoolean(12,data.isReadOnly());
            command.setString(13, data.getID());
            command.setString(14, data.getNodeDefinitionID());
            command.executeUpdate();
        }
        finally {
            this.close(command);
        }
    }

    private static final String SQL_DELETE =
        "delete from PROPERTY_DEFINITION where NODE_DEFINITION_ID=? and PKID=? and DELETABLE = '1'";
    /**
     *
     * @param nodeID String
     * @param ID String
     * @throws SQLException
     */
    public void delete(String nodeDefinitionID, String ID) throws SQLException {
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(SQL_DELETE);
            command.setString(1, nodeDefinitionID);
            command.setString(2, ID);
            command.executeUpdate();
        }
        finally {
            this.close(command);
        }
    }
    
    private static final String SQL_updateOrderNum =
        "update PROPERTY_DEFINITION set  ORDERNO = ? where NODE_DEFINITION_ID=? and PKID=?";
    /**
     * 更新属性的序号
     * @param nodeId String
     * @param propertyId String
     * @param num int
     */
    public void updateOrderNum(String nodeId, String propertyId, int num) throws SQLException{
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(SQL_updateOrderNum);
            command.setInt(1, num);
            command.setString(2, nodeId);
            command.setString(3, propertyId);
            command.executeUpdate();
        }
        finally {
            this.close(command);
        }
    }
}
