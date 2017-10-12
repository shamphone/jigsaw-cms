/**
 * 
 */
package com.fulong.lyvc.dao.postgresql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.fulong.lyvc.dao.ModeRoleDAO;
import com.fulong.lyvc.data.ModeRoleData;

/**
 * ConferenceModelRolePostgresDAO
 * 
 * 龙驭视频会议系统 v3.0
 * 
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 * 
 * @author 李雄锋
 * 
 *         最后修改时间：2009-3-17
 */
public class ModelRolePostgresDAO extends PostgresqlDAO implements ModeRoleDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fulong.lyvc.dao.ConferenceModelRoleDAO#deleteByModelId(long)
	 */
	public void deleteByModelId(long modelId) throws SQLException {
		String sql = "delete from conferencemodelrole where modelid=?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setLong(1, modelId);
		ps.execute();
		ps.close();

	}

	public long insert(ModeRoleData data) throws SQLException {
		String sql = "insert into conferencemodelrole (id,modelid,name,description,isdefault) values(?,?,?,?,?)";
		PreparedStatement ps = connection.prepareStatement(sql);
		data.setId(getPrimaryKey());
		ps.setLong(1, data.getId());
		ps.setLong(2, data.getModeId());
		ps.setString(3, data.getName());
		ps.setString(4, data.getDesc());
		ps.setBoolean(5, data.isDefault());
		ps.execute();
		ps.close();
		return data.getId();
	}

	/**
	 * modifyRole
	 * 
	 * @param role
	 *            ConferenceModelRole
	 */
	public void update(ModeRoleData role) throws SQLException {
		String sql = "update conferencemodelrole set name=?,description=?,isdefault=? where id=?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, role.getName());
		ps.setString(2, role.getDesc());
		ps.setBoolean(3, role.isDefault());
		ps.setLong(4, role.getId());
		ps.execute();
		ps.close();
	}

	/**
	 * getRoles
	 * 
	 * @return ConferenceModelRole[]
	 */
	public ModeRoleData[] findByModeId(long modeId) throws SQLException {
		ArrayList<ModeRoleData> roles = new ArrayList<ModeRoleData>();
		String sql = "select * from conferencemodelrole where modelid=? order by id";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setLong(1, modeId);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			ModeRoleData role = new ModeRoleData();
			role.setId(rs.getLong("id"));
			role.setModeId(rs.getLong("modelid"));
			role.setName(rs.getString("name"));
			role.setDesc(rs.getString("description"));
			role.setDefault(rs.getBoolean("isdefault"));
			roles.add(role);
		}
		rs.close();
		ps.close();
		return roles.toArray(new ModeRoleData[roles.size()]);
	}
	public ModeRoleData[] findByConferenceAndUser(long conferenceId, long userId) throws SQLException {
		ArrayList<ModeRoleData> roles = new ArrayList<ModeRoleData>();
		String sql = "select r.* from conferencemember m,conferencemodelrole r where m.conferenceid=? and m.userid=? and r.id=m.modelroleid";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setLong(1, conferenceId);
		ps.setLong(2, userId);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			ModeRoleData role = new ModeRoleData();
			role.setId(rs.getLong("id"));
			role.setModeId(rs.getLong("modelid"));
			role.setName(rs.getString("name"));
			role.setDesc(rs.getString("description"));
			role.setDefault(rs.getBoolean("isdefault"));
			roles.add(role);
		}
		return roles.toArray(new ModeRoleData[roles.size()]);
	}
	/**
	 * removeRole
	 * 
	 * @param roleID
	 *            long
	 * @return boolean
	 */
	public void delete(long roleID) throws SQLException {
		String sql = "delete from conferenceroleright where roleid=?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setLong(1, roleID);
		ps.execute();
		ps.close();
		sql = "delete from conferencemodelrole where id=?";
		ps = connection.prepareStatement(sql);
		ps.setLong(1, roleID);
		ps.execute();
		ps.close();
	}

	public ModeRoleData findByID(long conferenceModelRoleID)
			throws SQLException {
		ModeRoleData role = null;
		String sql = "select * from conferencemodelrole where id=?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setLong(1, conferenceModelRoleID);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			role = new ModeRoleData();
			role.setId(conferenceModelRoleID);
			role.setName(rs.getString("name"));
			role.setDesc(rs.getString("description"));
			role.setDefault(rs.getBoolean("isdefault"));
			role.setModeId(rs.getLong("modelid"));
		}

		rs.close();
		ps.close();
		return role;
	}

}
