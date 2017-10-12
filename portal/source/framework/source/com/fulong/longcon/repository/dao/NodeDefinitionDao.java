package com.fulong.longcon.repository.dao;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.fulong.common.dao.JdbcDao;
import com.fulong.longcon.repository.data.NodeDefinitionData;

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
public class NodeDefinitionDao extends JdbcDao {

    /**
     * 插入属性定义值
     * @param data NodeDefinitionData
     * @throws SQLException
     */
    private static final String SQL_INSERT =
            "insert into NODE_DEFINITION (PKID, DESCRIPTION,IS_SYSTEM, DELETE_MARK,SUPER_ID,CREATE_TIME,NAME) VALUES (?,?,?,?,?,?,?)";
    public void insert(NodeDefinitionData data) throws SQLException {
        PreparedStatement command = null;
        try {
            if (data.getID() == null)
                data.setID("" + getNextID());
            command = connection.prepareStatement(SQL_INSERT);
            command.setString(1, data.getID());
            command.setString(2, data.getDescription());
            command.setBoolean(3, data.is_system());
            command.setBoolean(4, data.isDelete_mark());
            command.setString(5, data.getSuperID());
            command.setTimestamp(6, toTimestame(data.getCreateTime()));
            command.setString(7, data.getName());
            command.executeUpdate();
        } finally {
            this.close(command);
        }
    }

    /**
     *更新数据
     * @param data NodeDefinitionData
     * @throws SQLException
     */
    private static final String SQL_UPDATE =
            "update NODE_DEFINITION set DESCRIPTION =? , IS_SYSTEM =? ,DELETE_MARK =? ,SUPER_ID =? ,name =? where PKID=?";
    public void update(NodeDefinitionData data) throws SQLException {
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(SQL_UPDATE);
            command.setString(1, data.getDescription());
            command.setBoolean(2, data.is_system());
            command.setBoolean(3, data.isDelete_mark());
            command.setString(4, data.getSuperID());
            command.setString(5, data.getName());
            command.setString(6, data.getID());
            command.executeUpdate();
        } finally {
            this.close(command);
        }
    }

    /**
     * 删除指定ID的结点定义，由于实际删除结点定义需要设计太多的数据，故这里必须使用假删除。
     * 注意：此处的删除并非物理删除数据，而是作一个已经删除的标示
     * @param id String
     * @throws SQLException
     */
    public void delete(String id) throws SQLException {
        CallableStatement proc = null;
        try {
            proc = connection.prepareCall(
                    "{ call pro_delete_NodeDefinition(?)  }");
            proc.setString(1, id);
            proc.execute();
        } finally {
            proc.close();
        }
    }


    /**
     * 根据ID来查询属性定义,注意：为了保持数据完整，做了删除标记的定义。
     * @param id String
     * @return NodeDefinitionData
     * @throws SQLException
     */
    private static final String SQL_FIND =
            "select distinct * from NODE_DEFINITION where PKID=?  and DELETE_MARK='0'";
    public NodeDefinitionData findByID(String id) throws SQLException {
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(SQL_FIND);
            command.setString(1, id);
            NodeDefinitionData[] result = this.retrieve(command, SQL_FIND);
            if (result.length > 0) {
                return result[0];
            }
            return null;
        } finally {
            this.close(command);
        }
    }

    /**
     * 根据ID来查询属性定义,注意：为了保持数据完整，做了删除标记的定义。
     * @param id String
     * @return NodeDefinitionData
     * @throws SQLException
     */
    private static final String SQL_FIND_BY_SUPER =
            "select distinct * from NODE_DEFINITION where  super_id=? and DELETE_MARK='0' order by create_time desc ";
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
            "select distinct * from NODE_DEFINITION where  pkid=?  and DELETE_MARK='0' order by create_time desc ";
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
            "select distinct * from NODE_DEFINITION where DELETE_MARK ='0' order by create_time asc ";
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

    protected NodeDefinitionData[] retrieve(PreparedStatement
                                          statement, String sql) throws
            SQLException {
        ResultSet rs = null;
        try {
            rs = statement.executeQuery();
            ArrayList<NodeDefinitionData> result = new ArrayList<NodeDefinitionData>();
            while (rs.next()) {
                NodeDefinitionData group = new NodeDefinitionData(); ;
                this.populate(rs, group);
                result.add(group);
            }
            return (NodeDefinitionData[]) result.toArray(new NodeDefinitionData[
                    result.size()]);
        } finally {
            this.close(statement, rs);
        }
    }

    protected void populate(ResultSet rs, NodeDefinitionData data) throws
            SQLException {
        data.setID(rs.getString("PKID"));
        data.setDescription(rs.getString("DESCRIPTION"));
        data.setSystem(rs.getBoolean("IS_SYSTEM"));
        data.setDelete_mark(rs.getBoolean("DELETE_MARK"));
        data.setSuperID(rs.getString("SUPER_ID"));
        data.setCreateTime(rs.getTimestamp("CREATE_TIME"));
        data.setName(rs.getString("NAME"));
    }

    private static final String SQL_FIND_BY_SUPERDEFINITION =
            " select * from node_definition t Where t.delete_mark='0' Start With t.pkid =? Connect By Prior t.pkid = t.super_id order by create_time desc ";
    /**
     *
     * @param superid String
     * @return NodeDefinitionData[]
     * @throws SQLException
     */
    public NodeDefinitionData[] findAllBySupser(String superid) throws
            SQLException {
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(SQL_FIND_BY_SUPERDEFINITION);
            command.setString(1, superid);
            return this.retrieve(command, SQL_FIND_BY_SUPERDEFINITION);
        } finally {
            this.close(command);
        }
    }

    /**
     * 复制了该分类的授权、视图、大纲流程处理3类信息
     * @param PKID String
     * @throws SQLException
     */
    public void copyAll(String sourceID, String destID) throws SQLException {
        CallableStatement proc = null;
        try {
            proc = connection.prepareCall(
                    "{ call pro_copy_NodeDefinition(?, ?)  }");
            proc.setString(1, sourceID);
            proc.setString(2, destID);
            proc.execute();
        } finally {
            proc.close();
        }
    }
}
