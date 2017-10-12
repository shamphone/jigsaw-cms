/**
 * 
 */
package com.fulong.lyvc.dao.postgresql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.fulong.lyvc.dao.GroupDAO;
import com.fulong.lyvc.data.GroupData;

/**
 * GroupPostgresDAO
 * 
 * 龙驭视频会议系统 v3.0
 * 
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 * 
 * @author 李雄锋
 * 
 *         最后修改时间：2009-3-18
 */
public class GroupPostgresDAO extends PostgresqlDAO implements GroupDAO {

	public long insert(GroupData data) throws SQLException {
		data.setId(getPrimaryKey());
		String sql = "insert into usergroup (id,groupname,groupdesc,creatorid) values(?,?,?,?)";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setLong(1, data.getId());
		ps.setString(2, data.getName());
		ps.setString(3, data.getDesc());
		ps.setLong(4, data.getCreatorId());
		ps.executeUpdate();
		ps.close();
		return data.getId();
	}

	public void update(GroupData data) throws SQLException {
		String sql = "update usergroup set groupname=?,groupdesc=? where id=?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, data.getName());
		ps.setString(2, data.getDesc());
		ps.setLong(3, data.getId());
		ps.executeUpdate();
		ps.close();
	}

	public GroupData findById(long groupId) throws SQLException {
		GroupData data = null;
		String sql = "select * from usergroup where id=?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setLong(1, groupId);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			data = new GroupData();
			data.setId(groupId);
			data.setName(rs.getString("groupname"));
			data.setDesc(rs.getString("groupdesc"));
			data.setCreatorId(rs.getLong("creatorid"));
			data.setGroupManagerId(rs.getLong("groupmanager"));
		}
		rs.close();
		ps.close();

		return data;
	}

	public GroupData[] findByParent(long groupId) throws SQLException {
		ArrayList<GroupData> shippers = new ArrayList<GroupData>();
		String sql = "select g.* from usergroup g,groupmember m where m.groupid = ? and m.memberid = g.id";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setLong(1, groupId);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			GroupData data = new GroupData();
			data.setId(rs.getLong("id"));
			data.setName(rs.getString("groupname"));
			data.setDesc(rs.getString("groupdesc"));
			data.setCreatorId(rs.getLong("creatorid"));
			shippers.add(data);
		}
		rs.close();
		ps.close();

		return shippers.toArray(new GroupData[shippers.size()]);
	}

	/**
	 * 删除组织数据，应事先解除该组织与其他组织或用户的关系
	 */
	public void delete(long groupId) throws SQLException {
		String sql = "delete from usergroup where id=?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setLong(1, groupId);
		ps.executeUpdate();
		ps.close();
	}

	public boolean isGroupManager(long userId) throws SQLException {
		boolean returnValue = false;
		String sql = "select groupmanager from usergroup where groupmanager=?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setLong(1, userId);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			returnValue = true;
		}
		rs.close();
		ps.close();
		return returnValue;
	}

	public void updateManager(long groupId, long userId)
			throws SQLException {
		String sql = "update usergroup set groupmanager=? where id=?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setLong(1, userId);
		ps.setLong(2, groupId);
		ps.executeUpdate();
		ps.close();
	}
}
