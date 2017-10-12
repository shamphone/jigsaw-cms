package com.fulong.lyvc.dao.postgresql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.fulong.lyvc.dao.UserDAO;
import com.fulong.lyvc.data.UserData;

/**
 * 
 * UserPostgresDAO
 * 
 * ��Ԧ��Ƶ����ϵͳ v3.0
 * 
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 * 
 * @author ���۷�
 * 
 *         ����޸�ʱ�䣺2009-3-18
 */
public class UserPostgresDAO extends PostgresqlDAO implements UserDAO {

	// Populate a user object from result set
	private UserData populateFromResultSet(ResultSet rs) throws SQLException {
		UserData[] data = this.populateFromResultSets(rs);
		if (data.length == 0)
			return null;
		return data[0];
	}

	// Populate a user object from result set
	private UserData[] populateFromResultSets(ResultSet rs) throws SQLException {
		ArrayList<UserData> users = new ArrayList<UserData>();
		while (rs.next()) {
			UserData user = new UserData();
			user.setId(rs.getLong("id"));
			user.setAccountName(rs.getString("accountName"));
			user.setFirstName(rs.getString("firstName"));
			user.setLastName(rs.getString("LastName"));
			user.setEmail(rs.getString("Email"));
			user.setPassword(rs.getString("password"));
			users.add(user);
		}
		return users.toArray(new UserData[users.size()]);
	}

	public UserData getByAccountName(String accountName) throws SQLException {
		String sql = "select * from users where accountname=?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, accountName);
		ResultSet rs = ps.executeQuery();
		UserData user = populateFromResultSet(rs);
		rs.close();
		ps.close();
		return user;
	}

	public UserData getByEmail(String email) throws SQLException {
		String sql = "select * from users where Email=?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, email);
		ResultSet rs = ps.executeQuery();
		UserData user = populateFromResultSet(rs);
		rs.close();
		ps.close();
		return user;
	}

	public UserData getUserById(long id) throws SQLException {
		String sql = "select * from users where id=?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setLong(1, id);
		ResultSet rs = ps.executeQuery();
		UserData user = populateFromResultSet(rs);
		rs.close();
		ps.close();
		return user;
	}

	/**
	 * Create a user, if successful, return id of user, otherwise 0
	 */
	public long insert(UserData userData) throws SQLException {
		userData.setId(getPrimaryKey());
		String sql = "insert into users (id,accountname,password,firstname,lastname,email) values(?,?,?,?,?,?)";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setLong(1, userData.getId());
		ps.setString(2, userData.getAccountName());
		ps.setString(3, userData.getPassword());
		ps.setString(4, userData.getFirstName());
		ps.setString(5, userData.getLastName());
		ps.setString(6, userData.getEmail());
		ps.executeUpdate();
		ps.close();
		return userData.getId();
	}

	public void update(UserData userData) throws SQLException {
		String sql = "update users set firstname=?,lastname=?,email=? where id=?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, userData.getFirstName());
		ps.setString(2, userData.getLastName());
		ps.setString(3, userData.getEmail());
		ps.setLong(4, userData.getId());
		ps.executeUpdate();
		ps.close();
	}

	/**
	 * 
	 * @param username
	 *            String
	 * @param password
	 *            String
	 * @return UserData
	 */
	public UserData authenticate(String username, String password) throws SQLException {
		String sql = "select * from users where accountname=? and password=?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, username);
		ps.setString(2, password);
		ResultSet rs = ps.executeQuery();
		UserData user = populateFromResultSet(rs);
		rs.close();
		ps.close();
		return user;
	}

	/**
	 * �ҳ�ĳGroup�����ֱ��e���û� ����淶��ӦΪGetUsers��GetMemberUsers winston, 200804
	 */
	public UserData[] getMembers(long groupId) throws SQLException {
		String sql = "select u.* from users u,groupmember m where m.groupid = ? and m.memberid = u.id";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement(sql);
			ps.setLong(1, groupId);
			rs = ps.executeQuery();
			return this.populateFromResultSets(rs);
		} finally {
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
		}
	}

	/**
	 * ɾ���û���ݣ�Ӧ���Ƚ����û���������֯���û��Ĺ�ϵ
	 * 
	 */
	public void removeUser(long userId) throws SQLException {
		String sql = "delete from users where id=?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setLong(1, userId);
		ps.executeUpdate();
		ps.close();
	}

	public void setUserPassword(long userId, String password) throws SQLException {
		String sql = "update users set password=? where id=?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, password);
		ps.setLong(2, userId);
		ps.executeUpdate();
		ps.close();
	}

	public UserData[] getAllUsers() throws SQLException {
		String sql = "select * from users";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement(sql);	
			rs = ps.executeQuery();
			return this.populateFromResultSets(rs);
		} finally {
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
		}
	}

	public UserData[] findByConference(long conferenceId) throws SQLException {
		String sql = "select u.* from users u, conferencemember c where c.conferenceid=? and c.userid=u.id";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement(sql);
			ps.setLong(1, conferenceId);
			rs = ps.executeQuery();
			return this.populateFromResultSets(rs);
		} finally {
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
		}
	}

	public UserData[] findByConference(long conferenceId, long roleId) throws SQLException {
		String sql = "select u.* from users u, conferencemember c where c.conferenceid=? and c.modelroleid=? and c.userid=u.id";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement(sql);
			ps.setLong(1, conferenceId);
			ps.setLong(2, roleId);
			rs = ps.executeQuery();
			return this.populateFromResultSets(rs);
		} finally {
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
		}
	}

	public UserData[] findByContactGroup(long groupId) throws SQLException {
		String sql = "select u.* from users u, groupuser c where c.groupid=?  and c.userid=u.id";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement(sql);
			ps.setLong(1, groupId);
			rs = ps.executeQuery();
			return this.populateFromResultSets(rs);
		} finally {
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
		}
	}

}
