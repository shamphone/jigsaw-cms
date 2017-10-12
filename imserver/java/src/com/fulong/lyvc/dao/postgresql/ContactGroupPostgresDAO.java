/**
 * 
 */
package com.fulong.lyvc.dao.postgresql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.fulong.lyvc.dao.ContactGroupDAO;
import com.fulong.lyvc.data.ContactGroupData;

/**
 * ContactGroupPostgresDAO
 * 
 * 龙驭视频会议系统 v3.0
 * 
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 * 
 * @author 李雄锋
 * 
 *         最后修改时间：2009-3-18
 */
public class ContactGroupPostgresDAO extends PostgresqlDAO implements
		ContactGroupDAO {

	/**
	 * addGroup
	 * 
	 * @param userId
	 *            long
	 * @param groupName
	 *            String
	 * @return long
	 * @throws SQLException
	 */
	public long insert(ContactGroupData data) throws SQLException {

		String sql = " select nextval('groupid')";
		PreparedStatement ps = connection.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		rs.next();
		int id = rs.getInt(1);
		rs.close();
		ps.close();
		data.setId(id);
		data.setIndex(id);
		// insert record
		sql = "insert into groups(id, name, userid,displaysort) values(?, ?, ?, ?)";
		ps = connection.prepareStatement(sql);
		ps.setLong(1, data.getId());
		ps.setString(2, data.getName());
		ps.setLong(3, data.getUserId());
		ps.setInt(4, data.getIndex());
		/*
		 * if(groupName.equals("总站")) ps.setInt(4, -1); else
		 * if(groupName.equals("北京中科辅龙计算机技术有限公司")) ps.setInt(4, 1); else
		 * ps.setInt(4, 0);
		 */
		ps.executeUpdate();
		ps.close();
		return id;

	}

	/**
 * 
 */
	public ContactGroupData[] findGroupsByUser(long userId) throws SQLException {
		// get all groups of the user
		String sql = "select * from groups where userid=? order by displaysort asc";
		PreparedStatement psgroup = connection.prepareStatement(sql);
		psgroup.setLong(1, userId);
		ResultSet rsgroup = psgroup.executeQuery();

		ArrayList<ContactGroupData> groupList = new ArrayList<ContactGroupData>();
		while (rsgroup.next()) {
			ContactGroupData agroup = new ContactGroupData();
			agroup.setId(rsgroup.getLong("id"));
			agroup.setName(rsgroup.getString("name"));
			agroup.setIndex(rsgroup.getInt("displaysort"));
			agroup.setUserId(rsgroup.getLong("userid"));
			agroup.setRoot(false);
			groupList.add(agroup);
		}
		rsgroup.close();
		psgroup.close();
		return groupList.toArray(new ContactGroupData[groupList.size()]);
	}
	public ContactGroupData findGroupsById(long id) throws SQLException {
		// get all groups of the user
		ContactGroupData agroup = null;
		String sql = "select * from groups where id=? ";
		PreparedStatement psgroup = connection.prepareStatement(sql);
		psgroup.setLong(1, id);
		ResultSet rsgroup = psgroup.executeQuery();
		if (rsgroup.next()) {
			agroup = new ContactGroupData();
			agroup.setId(rsgroup.getLong("id"));
			agroup.setName(rsgroup.getString("name"));
			agroup.setIndex(rsgroup.getInt("displaysort"));
			agroup.setUserId(rsgroup.getLong("userid"));
			agroup.setRoot(false);			
		}
		rsgroup.close();
		psgroup.close();
		return agroup;
	}

	/*
	 * private void getAllContactGroups() throws Exception { try { // get all
	 * userids String sql =
	 * "select userid,groupid from userdefaultgroup order by userid asc";
	 * PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs =
	 * ps.executeQuery();
	 * 
	 * while(rs.next()) { contactTable.addDefaultGroupId( rs.getLong(1),
	 * rs.getLong(2) ); getGroupsInternal( rs.getLong(1) ); } rs.close();
	 * ps.close(); connection.commit(); } catch( SQLException ex) { try {
	 * connection.rollback(); } catch(Exception ex2) { } ex.printStackTrace();
	 * throw new ContactLibraryException(ex); } }
	 */
	// 判断某个用户(contactId)在另外一个用户(ownerId)中的那一个group中
	// 如果不在，抛出异常
	/*
	 * public long getGroupIdForContact(long ownerId, long contactId) { boolean
	 * isContactFlag = this.isContact(contactId, ownerId); if( ! isContactFlag )
	 * { throw new ContactLibraryException( Long.toString(contactId) +
	 * " is not a contact of " + ownerId); } return
	 * contactTable.getGroupIdForContact(ownerId, contactId); }
	 */
	/**
	 * removeGroup
	 * 
	 * @param groupId
	 *            long
	 * @throws SQLException
	 */
	public void removeGroup(long groupId) throws SQLException {

		// Test if there are user in this group
		boolean hasUser = false;
		String sql = " select * from groupuser where groupid=?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setLong(1, groupId);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			hasUser = true;
		}
		rs.close();
		ps.close();

		// If no user, remove this group
		if (!hasUser) {
			sql = "delete from groups where id=?";
			ps = connection.prepareStatement(sql);
			ps.setLong(1, groupId);
			ps.executeUpdate();
			ps.close();
			connection.commit();

		}
	}


	/**
	 * 
	 * @param userId
	 * @throws SQLException
	 */
	public void deleteUser(long userId) throws SQLException {

		String sql = "delete from groupuser where userid=?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setLong(1, userId);
		ps.executeUpdate();
		ps.close();
		sql = "delete from groupuser where groupid in ( select id from groups where userid=? )";
		ps = connection.prepareStatement(sql);
		ps.setLong(1, userId);
		ps.executeUpdate();
		ps.close();
		sql = "delete from groups where userid=?";
		ps = connection.prepareStatement(sql);
		ps.setLong(1, userId);
		ps.executeUpdate();
		ps.close();
		sql = "delete from userdefaultgroup where userid=?";
		ps = connection.prepareStatement(sql);
		ps.setLong(1, userId);
		ps.executeUpdate();
		ps.close();

	}

	/**
	 * renameGroup
	 * 
	 * @param groupId
	 *            long
	 * @param newName
	 *            String
	 * @throws SQLException
	 */
	public void rename(long groupId, String newName) throws SQLException {
		String sql = "update groups set name = ? where id=?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, newName);
		ps.setLong(2, groupId);
		ps.executeUpdate();
		ps.close();
	}

	public void restoreContact(long contactAdminId) {
	}

}
