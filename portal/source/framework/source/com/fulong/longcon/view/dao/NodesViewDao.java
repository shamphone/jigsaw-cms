package com.fulong.longcon.view.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.fulong.common.dao.JdbcDao;
import com.fulong.longcon.view.data.NodesViewData;

/**
 * <p>
 * Title: Coolink协同工作支撑平台
 * </p>
 * 
 * <p>
 * Description: Coolink协同工作支撑平台
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术股份有限公司 2007
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * @author <a href='mailto:lishaobo@fulong.com.cn'>lixf</a>
 * @version 1.0
 */
public class NodesViewDao extends JdbcDao {

	/**
	 * 插入新结点
	 * 
	 * @param data
	 *            NodeData
	 * @throws SQLException
	 */
	private static final String SQL_INSERT = "insert into node_view (nodefid, prodefid, width, orderno) VALUES (?,?,?,?)";

	public void insert(NodesViewData data) throws SQLException {
		PreparedStatement command = null;
		try {
			command = connection.prepareStatement(SQL_INSERT);
			command.setString(1, data.getNodeDefinitionID());
			command.setString(2, data.getProDefinitionID());
			command.setInt(3, data.getWidth());
			command.setInt(4, data.getOrderNo());
			command.executeUpdate();
		} finally {
			this.close(command);
		}

	}

	private static final String SQL_SELECT = "select * from node_view t where t.nodefid=?";

	public NodesViewData[] findByName(String definitionID) throws SQLException {
		PreparedStatement command = null;
		try {
			command = connection.prepareStatement(SQL_SELECT);
			command.setString(1, definitionID);

			NodesViewData[] result = this.retrieve(command, SQL_SELECT);
			return result;
		} finally {
			this.close(command);
		}

	}

	private static final String SQL_Delete = "delete from node_view t where t.nodefid=?";

	public void deleteByName(String definitionID) throws SQLException {
		PreparedStatement command = null;
		try {
			command = connection.prepareStatement(SQL_Delete);
			command.setString(1, definitionID);
			command.executeUpdate();
		} finally {
			this.close(command);
		}

	}

	private static final String SQL_Update = "update node_view v set v.width=? where v.nodefid=? and v.prodefid=?";

	public void update(NodesViewData data) throws SQLException {
		PreparedStatement command = null;
		try {
			command = connection.prepareStatement(SQL_Update);
			command.setInt(1, data.getWidth());
			command.setString(2, data.getNodeDefinitionID());
			command.setString(3, data.getProDefinitionID());
			command.executeUpdate();
		} finally {
			this.close(command);
		}

	}

	private NodesViewData[] retrieve(PreparedStatement statement, String sql)
			throws SQLException {
		ResultSet rs = statement.executeQuery();
		ArrayList<NodesViewData> result = new ArrayList<NodesViewData>();
		while (rs.next()) {
			NodesViewData group = new NodesViewData();
			this.populate(rs, group);
			result.add(group);
		}
		rs.close();
		statement.close();
		return (NodesViewData[]) result
				.toArray(new NodesViewData[result.size()]);
	}

	protected void populate(ResultSet rs, NodesViewData data)
			throws SQLException {
		data.setNodeDefinitionID(rs.getString("nodefid"));
		data.setProDefinitionID(rs.getString("prodefid"));
		data.setWidth(rs.getInt("width"));
		data.setOrderNo(rs.getInt("orderno"));
	}

}
