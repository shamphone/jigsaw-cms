package com.fulong.longcon.repository.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fulong.common.dao.JdbcDao;
import com.fulong.longcon.repository.PropertyType;
import com.fulong.longcon.repository.data.NodePropertyData;

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
public class ReferenceValueDao
    extends JdbcDao {
	/**
     * 根据内容和属性，获取属性值
     * @param contentID String
     * @param property String
     * @return String[]
     */
    public String[] load(String contentID, String property) throws SQLException {
        String SQL_SELECT =
            "select VALUE from "+QName.encode(PropertyType.REFERENCE,property)+" where NODE_ID=? ";
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(SQL_SELECT);
            command.setString(1, contentID);
            String[] result = this.queryStringArray(command);
            return result;
        }        finally {
            this.close(command);
        }
    }

    /**
     * 更新属性值。注意：更新之前要删除所有值
     * @param contentID String
     * @param property String
     * @param values String[]
     */
    public void insert(String contentID, String property, int index,
                       String value) throws SQLException {
        String SQL_INSERT =
            "insert into "+QName.encode(PropertyType.REFERENCE,property)+" (NODE_ID, VALUE, VINDEX) VALUES (?,?,?)";
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(SQL_INSERT);
            command.setString(1, contentID);
            command.setString(2, value);
            command.setInt(3, index);
            command.executeUpdate();
        }
        finally {
            this.close(command);
        }
    }

    /**
     * 删除指定节点的所有属性值
     * @param contentID String
     * @param property String
     */
    public void delete(String nodeID, String property) throws SQLException {
        String SQL_DELETE_BY_ContentAndProperty =
            "delete from "+QName.encode(PropertyType.REFERENCE,property)+" where NODE_ID=?";
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(
                SQL_DELETE_BY_ContentAndProperty);
            command.setString(1, nodeID);
            command.executeUpdate();
        }
        finally {
            this.close(command);
        }
    }
    
    /**
     * 删除指定节点和值的引用关系
     * @param nodeID String
     * @param value String
     * @param property String
     */
    public void delete(String nodeID, String value ,String property) throws SQLException {
        String SQL_DELETE_BY_ContentAndProperty =
            "delete from "+QName.encode(PropertyType.REFERENCE,property)+" where NODE_ID=? and value =?";
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(
                SQL_DELETE_BY_ContentAndProperty);
            command.setString(1, nodeID);
            command.setString(2, value);
            command.executeUpdate();
        }
        finally {
            this.close(command);
        }
    }
    
    //取得所有r_%的表名 by mali 2010-8-12
    private static final String SQL_REFERENCE_TABLES = "SELECT TNAME FROM TAB WHERE TABTYPE='TABLE' AND TNAME LIKE 'R/_%' ESCAPE '/' ";
    protected List<String> getReferenceTableName() throws SQLException{
    	PreparedStatement command = null;
    	ResultSet rs = null;
    	List<String> tabs = new ArrayList<String>();
    	try {
            command = connection.prepareStatement(
            		SQL_REFERENCE_TABLES);
            rs = command.executeQuery();
            while(rs.next()){
            	tabs.add(rs.getString(1));
            }
    	}finally {
                this.close(command,rs);
         }
		return tabs;
    }
    
    //构建查询语句 like 'select * from tab1 union all select * from tab2' by mali 2010-8-12
    protected String makeSelQuery(String value) throws SQLException{
    	StringBuffer query = new StringBuffer();
    	String query1 = "select node_id from ";
    	String query2 = " where value = '" + value + "'";
    	List<String> tabs = getReferenceTableName();
    	for(int i = 0; i < tabs.size(); i++){
    		query.append(query1).append(tabs.get(i)).append(query2);
    		if(i != (tabs.size() - 1) )
    			query = query.append(" union all ");
    	}
    	return query.toString();
    }

    public int countReferenceByNode(String nodeID) throws SQLException {
    	String SQL_REFERENCE_COUNT_BY_Content =
            "select count(*) from (" + this.makeSelQuery(nodeID) + ")";
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(
                SQL_REFERENCE_COUNT_BY_Content);
            int result = this.queryInt(command);
            return result;
        }
        finally {
            this.close(command);
        }
    }

    public NodePropertyData[] findReferenceByNode(String nodeID, int fromIndex,
                                                  int pageSize) throws
        SQLException {
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(toRangeQuery(
            		this.makeSelQuery(nodeID)));
            command.setInt(1, fromIndex + pageSize);
            command.setInt(2, fromIndex);
            NodePropertyData[] result = this.retrieve(command);
            return result;
        }
        finally {
            this.close(command);
        }
    }
    
    public NodePropertyData[] findReferenceByNodeId(String nodeID) throws
		SQLException {
		PreparedStatement command = null;
		try {
			command = connection.prepareStatement(this.makeSelQuery(nodeID));
			NodePropertyData[] result = this.retrieve(command);
			return result;
		}
		finally {
			this.close(command);
		}
    }

    protected NodePropertyData[] retrieve(PreparedStatement
                                        statement) throws
        SQLException {
        ResultSet rs = null;
        try{
            rs = statement.executeQuery();
            ArrayList<NodePropertyData> result = new ArrayList<NodePropertyData>();
            while (rs.next()) {
                NodePropertyData group = new NodePropertyData(); ;
                this.populate(rs, group);
                result.add(group);
            }
            return (NodePropertyData[]) result.toArray(new NodePropertyData[
                result.size()]);
        }finally{
            rs.close();
            statement.close();
        }
    }
    private void populate(ResultSet rs, NodePropertyData data) throws
        SQLException {
        data.setID(rs.getString("NODE_ID"));
        //data.setName(rs.getString("PROPERTY_ID"));
    }
}
