package com.fulong.lyvc.dao.postgresql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.fulong.lyvc.dao.ConferenceMemberDAO;

/**
 * 
 * ConferenceMemberPostgresDAO
 * 
 * 龙驭视频会议系统 v3.0
 * 
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 * 
 * @author 李雄锋
 * 
 *         最后修改时间：2009-3-17
 */
public class ConferenceMemberPostgresDAO extends PostgresqlDAO implements
		ConferenceMemberDAO {
	public void insert(long conferenceID, long modelRoleID,
			long userID) throws SQLException {
		String sql = "insert into conferencemember (id,conferenceid,modelroleid,userid) values(?,?,?,?)";
		PreparedStatement ps = connection.prepareStatement(sql);
		long id = getPrimaryKey();
		ps.setLong(1, id);
		ps.setLong(2, conferenceID);
		ps.setLong(3, modelRoleID);
		ps.setLong(4, userID);
		ps.execute();
		ps.close();
	}

	public boolean exists(long conferenceId, long userId) throws SQLException {	
		String sql = "select userid from conferencemember where conferenceid=? and userid=?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setLong(1, conferenceId);
		ps.setLong(2, userId);
		ResultSet rs=ps.executeQuery();
		boolean exists=rs.next();
		rs.close();
		ps.close();
		return exists;

	}
	public long[] findByConference(long conferenceId) throws SQLException {
		
		String sql = "select userid from conferencemember where conferenceid=?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setLong(1, conferenceId);
		return this.queryLongArray(ps);

	}

	public void delete(long confId, long userId) throws SQLException {
		String sql = "delete from conferencemember where userid=? and conferenceid=?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setLong(1, userId);
		ps.setLong(2, confId);
		ps.execute();
		ps.close();
	}

	public void deleteByConference(long confId) throws SQLException {
		String sql = "delete from conferencemember where conferenceid=?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setLong(1, confId);
		ps.execute();
		ps.close();
	}

	public void deleteByUser(long userId) throws SQLException {
		String sql = "delete from conferencemember where userid=? ";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setLong(1, userId);
		ps.execute();
		ps.close();
	}

	public void deleteByMode(int type) throws SQLException {
		String sql = "delete from conferencemember where conferenceid in (select id from conference where conferencemodelid = 4)";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setInt(1, type);
		ps.execute();
		ps.close();
	}

}
