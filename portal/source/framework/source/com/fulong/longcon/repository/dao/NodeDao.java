package com.fulong.longcon.repository.dao;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.fulong.common.dao.JdbcDao;
import com.fulong.common.dao.SQLParameter;
import com.fulong.longcon.repository.data.NodeData;
import java.util.Date;

/**
 * <p>Title: Coolink协同工作支撑平台</p>
 *
 * <p>Description: Coolink协同工作支撑平台</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2007</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author <a href='mailto:lixf@fulong.com.cn'>lixf</a>
 * @version 1.0
 */
public class NodeDao extends JdbcDao {

    /**
     * 插入新结点
     * @param data NodeData
     * @throws SQLException
     */
    private static final String SQL_INSERT =
        "insert into NODE (PKID, DEFINITION, PARENT_ID, NAME,TYPE, ORDERNO) VALUES (?,?,?,?,?,?)";
    public void insert(NodeData data) throws SQLException {
        if (data.getID() == null)
            data.setID("" + getNextID());   
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(SQL_INSERT);
            command.setString(1, data.getID());
            command.setString(2, data.getDefinitionID());
            command.setString(3, data.getParentID());
            command.setString(4, data.getName());
            command.setBoolean(5, data.isMain());
            command.setInt(6, data.getOrderNo());
            command.executeUpdate();
        } finally {
            this.close(command);
        }
    }

    private static final String SQL_UPDATE_ORDERNO =
        "update NODE set ORDERNO=? where PKID=?";
 
    /**
     * 更新节点的orderno
     * @param data
     * @throws SQLException
     */
    public void update(NodeData data) throws SQLException {
			PreparedStatement command = null;
			try {
			    command = connection.prepareStatement(SQL_UPDATE_ORDERNO);
			    command.setInt(1, data.getOrderNo());
			    command.setString(2, data.getID());
			    command.executeUpdate();
			}
			finally {
			    this.close(command);
			}
    }
    
    private static final String SQL_UPDATE =
        "update NODE set PARENT_ID=? ,NAME = ?, ORDERNO=? where PKID=?";
    /**
     * 更新节点的parentID或者name
     * @param data
     * @throws SQLException
     */
    public void update(NodeData data,String oldparentID,String oldname) throws SQLException {
    		CallableStatement pron = null;
			PreparedStatement command = null;
			try {
			    command = connection.prepareStatement(SQL_UPDATE);
			    command.setString(1, data.getParentID());
			    command.setString(2, data.getName());
			    command.setInt(3, data.getOrderNo());
			    command.setString(4, data.getID());
			    command.executeUpdate();
			}
			finally {
			    this.close(command);
			}
			if(oldparentID != null){
		        try {
		            pron = connection.prepareCall("{ call pro_rankorderno(?,?) }");
		            pron.setString(1, oldparentID);
		            pron.setString(2, data.getName());
		            pron.execute();
		        } finally {
		            pron.close();
		        }
			}else if(oldname != null){
		        try {
		            pron = connection.prepareCall("{ call pro_rankorderno(?,?) }");
		            pron.setString(1, data.getParentID());
		            pron.setString(2, oldname);
		            pron.execute();
		        } finally {
		            pron.close();
		        }
			}else{
		        try {
		            pron = connection.prepareCall("{ call pro_rankorderno(?,?) }");
		            pron.setString(1, data.getParentID());
		            pron.setString(2, data.getName());
		            pron.execute();
		        } finally {
		            pron.close();
		        }
			}
				
    	
        
    }

    /**
     * 删除结点（type=0或1 都删除）
     * @param ID String
     * @throws SQLException
     */
    private static final String SQL_DELETE_BY_ID =
        "delete from NODE where PKID=?";
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
    }

    /**
     * 根据ID来查找节点
     * @param ID String
     * @return NodeData
     * @throws SQLException
     */
    private static final String SQL_FIND = "select * from NODE where PKID=? and type=1";
    public NodeData findByID(String ID) throws SQLException {
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(SQL_FIND);
            command.setString(1, ID);
            NodeData[] result = this.retrieve(command, SQL_FIND + ",ID=" + ID);
            if (result.length > 0) {
                return result[0];
            }
            return null;
        } finally {
            this.close(command);
        }
    }

    protected NodeData[] retrieve(PreparedStatement statement, String sql) throws
        SQLException {
        ResultSet rs = statement.executeQuery();
        ArrayList<NodeData> result = new ArrayList<NodeData>();
        while (rs.next()) {
            NodeData group = new NodeData(); ;
            this.populate(rs, group);
            result.add(group);
        }
        rs.close();
        statement.close();
        return (NodeData[]) result.toArray(new NodeData[result.size()]);
    }

    protected void populate(ResultSet rs, NodeData data) throws
        SQLException {
        data.setID(rs.getString("PKID"));
        data.setParentID(rs.getString("PARENT_ID"));
        data.setName(rs.getString("NAME"));
        data.setOrderNo(rs.getInt("ORDERNO"));
        data.setDefinitionID(rs.getString("definition"));
        data.setMain(rs.getBoolean("type"));
    }

    /**
     * 统计指定父结点下的子节点的个数
     * @param parentID String
     * @return long
     * @throws SQLException
     */
    private static final String SQL_count_By_Parent =
        "select count(PKID) from NODE where PARENT_ID=? and type=1";

    public long countByParent(String parentID) throws SQLException {
        long result = 0;
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(SQL_count_By_Parent);
            command.setString(1, parentID);
            result = this.queryLong(command);
        } finally {
            this.close(command);
        }
        return result;
    }

    /**
     * 统计指定父结点下的子节点的个数(递归查找)
     * @param parentID String
     * @return long
     * @throws SQLException
     */
    private static final String SQL_Rec_count_By_Parent =
        "select count(PKID) from node n start with n.parent_id=? and type=1 connect by n.parent_id=prior n.pkid and type=1";

    public long countRecByParent(String parentID) throws SQLException {
        long result;
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(SQL_Rec_count_By_Parent);
            command.setString(1, parentID);
            result = this.queryLong(command);
        } finally {
            this.close(command);
        }
        return result;
    }

    /**
     * 统计指定父结点下的子节点的个数(递归查找)
     * @param parentID String
     * @return long
     * @throws SQLException
     */
    private static final String SQL_Rec_count_By_ParentAndName =
        "select count(PKID) from node n start with n.parent_id=? and type=1 connect by n.parent_id=prior n.pkid and n.name=? and type=1";

    public long countRecByParent(String parentID, String name) throws
        SQLException {
        PreparedStatement command = null;
        long result;
        try {
            command = connection.prepareStatement(
                SQL_Rec_count_By_ParentAndName);
            command.setString(1, parentID);
            command.setString(2, name);
            result = this.queryLong(command);
        } finally {
            this.close(command);
        }
        return result;
    }

    /**
     * 获取指定父结点下的所有子节点，按照分页的模式
     * @param parentID String
     * @param fromIndex int
     * @param number int
     * @return NodeData[]
     * @throws SQLException
     */

    private static final String SQL_Find_By_Parent =
        "select * from NODE where PARENT_ID=? and type=1 order by orderno";
    public NodeData[] findByParent(String parentID, long fromIndex, long number) throws
        SQLException {
        PreparedStatement command = null;
        String sql = toRangeQuery(SQL_Find_By_Parent);
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

    private static final String SQL_Find_By_Id =
        "select * from NODE where PKID=? and type=1";
    public NodeData[] findById(String pkid) throws
        SQLException {
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(SQL_Find_By_Id);
            command.setString(1, pkid);
            NodeData[] result = this.retrieve(command, SQL_Find_By_Id);
            return result;
        }
        finally {
            this.close(command);
        }
    }
    
    private static final String SQL_Find_By_ParentRec =
        "select * from node n start with n.parent_id=? and type=1 connect by n.parent_id=prior n.pkid and type=1"; // order by level, n.parent_id, n.orderno";
    public NodeData[] findByParentRec(String parentID, long fromIndex,
                                      long number) throws
        SQLException {
        PreparedStatement command = null;
        String sql = toRangeQuery(SQL_Find_By_ParentRec);
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

    private static final String SQL_Find_By_ParentAndNameRec =
        "select * from node n start with n.parent_id=? and type=1 connect by n.parent_id=prior n.pkid and n.name=? and type=1"; //order by level,n.parent_id, n.orderno";
    public NodeData[] findByParentRec(String parentID, String name,
                                      long fromIndex, long number) throws
        SQLException {
        PreparedStatement command = null;
        String sql = toRangeQuery(SQL_Find_By_ParentAndNameRec);
        try {
            command = connection.prepareStatement(sql);
            command.setString(1, parentID);
            command.setString(2, name);
            command.setLong(3, fromIndex + number);
            command.setLong(4, fromIndex);
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
        String sql = toRangeQuery(query);
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

    public long countResultNum(String query, SQLParameter[] parameters) throws
        SQLException {
        long result = 0;
        PreparedStatement command = null;
        try {
            command = this.createStatement(query, parameters);
            result = this.queryLong(command);
            return result;
        } finally {
            this.close(command);
        }
    }

    /**
     * findByName
     *
     * @param nodeId String
     * @return NodeData
     */
    private static final String SQL_Find_By_Name =
        "select * from NODE where NAME=? and type=1";
    public NodeData findByName(String nodeId) throws SQLException {
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(SQL_Find_By_Name);
            command.setString(1, nodeId);
            NodeData[] result = this.retrieve(command, SQL_Find_By_Name);
            if (result.length > 0)
                return result[0];
            else
                return null;
        } finally {
            this.close(command);
        }
    }

    private static final String SQL_Find_By_ParentAndName =
        "select * from NODE where PARENT_ID=? and NAME = ? and type=1 order by ORDERNO";
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
        String sql = toRangeQuery(SQL_Find_By_ParentAndName);
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
    /**
     * 获取制定父节点下的所有子节点
     * @param parentID String
     * @param name String
     * @param fromIndex long
     * @param number long
     * @return NodeData[]
     * @throws SQLException
     */
    public NodeData[] findByParent(String parentID, String name) throws
		SQLException {
		PreparedStatement command = null;
		try {
		command = connection.prepareStatement(SQL_Find_By_ParentAndName);
		command.setString(1, parentID);
		command.setString(2, name);
		NodeData[] result = this.retrieve(command, SQL_Find_By_ParentAndName);
		return result;
		} finally {
		this.close(command);
		}
    }

    private static final String SQL_count_By_ParentAndName =
        "select count(PKID) from NODE where PARENT_ID=? and NAME = ? and type=1";
    /**
     * 统计指定父节点下子节点的个数
     * @param parentID String
     * @param name String
     * @return long
     * @throws SQLException
     */
    public long countByParent(String parentID, String name) throws SQLException {
        long result = 0;
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(SQL_count_By_ParentAndName);
            command.setString(1, parentID);
            command.setString(2, name);
            result = this.queryLong(command);
        } finally {
            this.close(command);
        }
        return result;
    }
    
    private static final String SQL_count_By_ParentAndName_Recursion =
        "select count(PKID) from NODE start with PARENT_ID=? and NAME = ? and type=1 connect by PARENT_ID=prior PKID and type=1";
    /**
     * (递归)统计指定父节点下子节点的个数
     * @param parentID String
     * @param name String
     * @return long
     * @throws SQLException
     */
    public long countByRecParent(String parentID, String name) throws SQLException {
        long result = 0;
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(SQL_count_By_ParentAndName_Recursion);
            command.setString(1, parentID);
            command.setString(2, name);
            result = this.queryLong(command);
        } finally {
            this.close(command);
        }
        return result;
    }

    /**
     * 删除node，同时删除相关所有扩展属性的值 （该方法已废弃，暂时不删除）
     * @param PKID String
     * @throws SQLException
     */
    public void deleteAll(String PKID,String defId) throws SQLException {
        if(PKID.equals("root")||PKID.equals("1000000000000"))//系统确定的node的根。必须保护，不可以删除。
            return;
        CallableStatement proc = null;
        try {
        	/**
        	 * 功能改进：
        	 * liulei modified
        	 * （1）pro_delete_Node2(?) 新存储模式下的存储过程
        	 * （2）pro_delete_node(?,?)如果在删除推荐的内容的时候，只根据nodeid会删除所有的节点,这样是不正确的。
        	 *      因此，需要根据nodeid和definition两个参数来确定唯一删除的节点
        	 */
            proc = connection.prepareCall("{ call pro_delete_Node_liulei(?,?)  }");
            proc.setString(1, PKID);
            proc.setString(2,defId);
            proc.execute();
        } finally {
            proc.close();
        }
    }
    
    public void deleteAll(String PKID) throws SQLException {
        if(PKID.equals("root")||PKID.equals("1000000000000"))//系统确定的node的根。必须保护，不可以删除。
            return;
        NodeData node = this.findByID(PKID);
        String parentID = node.getParentID();
        String name = node.getName();
        CallableStatement proc = null;
        try {
            proc = connection.prepareCall("{ call pro_delete_Node2(?) }");
            proc.setString(1, PKID);
            proc.execute();
        } finally {
            proc.close();
        }
        
        //根据parentID和name整理orderno的值
        if(parentID == null||name == null)
        	return;
        CallableStatement pron = null;
        try {
            pron = connection.prepareCall("{ call pro_rankorderno(?,?) }");
            pron.setString(1, parentID);
            pron.setString(2, name);
            pron.execute();
        } finally {
            pron.close();
        }
    }
    
    private static final String SQL_FindID_By_ParentRec =
        "select pkid from node n start with n.parent_id=? and type=1 connect by n.parent_id=prior n.pkid and type=1";
    public String[] childrenIDs(String parentID) throws SQLException {
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(SQL_FindID_By_ParentRec);
            command.setString(1, parentID);
            return this.queryStringArray(command);
        } finally {
            this.close(command);
        }
    }
    /**
	 * 
	 * @param DefId
	 *            String
	 * @return NodeData[]
	 * 			    根据大纲ID遍历返回所有节点
	 */
    
    private static final String SQL_SELECT_BY_DEFID_TYPE =
        "select * from node  where definition in (select pkid from node_definition start with pkid=? connect by prior pkid=super_id)";
    public NodeData[] findAllNodesByDefinition(String DefId) throws SQLException {
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(SQL_SELECT_BY_DEFID_TYPE);
            command.setString(1, DefId);
            NodeData[] result = this.retrieve(command, SQL_SELECT_BY_DEFID_TYPE);
            return result;
        } finally {
            this.close(command);
        }
    }

    private static final String SQL_MAX_ORDERBYNO =
        "select Max(orderno) from node Where parent_id =? and name=? and type=1";
    /**
     * 最大序号
     * @param parentID String
     * @param name String
     * @return int
     * @throws SQLException
     */
    public int getMaxOrderNo(String parentID, String name) throws SQLException {
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(SQL_MAX_ORDERBYNO);
            command.setString(1, parentID);
            command.setString(2, name);
            int result = this.queryInt(command);
            return result;
        }
        finally {
            this.close(command);
        }
    }

    /**
     *复制内容，调用存储过程实现
     * @param contentID String
     * @throws SQLException
     */
    public NodeData copy(String NodeID) throws SQLException {
        String ID = "" + getNextID();
        CallableStatement proc = null;
        try {
        	/**
        	 * liulei modified pro_copy_node2
        	 */
            proc = connection.prepareCall("{ call pro_copy_node2(?,?) }");
            proc.setString(1, ID);
            proc.setString(2, NodeID);
            proc.execute();
            return this.findByID(ID);
        } finally {
            proc.close();
        }
    }
}

