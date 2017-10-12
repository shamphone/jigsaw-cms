package com.fulong.lyvc.dao.postgresql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.fulong.lyvc.dao.ConferenceDAO;
import com.fulong.lyvc.data.ConferenceData;

/**
 * 
 * ConferenceSessionImpl
 * 
 * 龙驭视频会议系统 v3.0
 * 
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 * 
 * @author 李雄锋
 * 
 *         最后修改时间：2009-3-11
 */
public class ConferencePostgresDAO extends PostgresqlDAO implements
		ConferenceDAO {

	public ConferencePostgresDAO() {
	}

	// 获取用户在系统中的角色id
	public long getUserRole(long userId) throws SQLException {
		String sql = "select * from systemrole";
		PreparedStatement ps = connection.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		long roleId = 0;
		boolean flag = false;
		while (rs.next() && !flag) {
			String users = rs.getString("role_users");
			if (users != null) {
				String userArray[] = users.split(",");
				for (int i = 0; i < userArray.length; i++) {
					if (Long.parseLong(userArray[i]) == userId) {
						roleId = rs.getLong("role_id");
						flag = true;
						break;
					}
				}
			}
		}

		rs.close();
		ps.close();
		return roleId;
	}


	public long insert(ConferenceData conference)
			throws SQLException {
		long id = this.getPrimaryKey();
		conference.setId(id);
		String sql = "insert into conference (id,title,description,starttime,endtime,conferencemodelid,conference_creator) values(?,?,?,?,?,?,?)";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setLong(1, id);
		ps.setString(2, conference.getTitle());
		ps.setString(3, conference.getDesc());
		ps.setTimestamp(4, new Timestamp(conference.getStartTime().getTime()));
		ps.setTimestamp(5, new Timestamp(conference.getEndTime().getTime()));
		ps.setLong(6, conference.getConferecneModelID());
		ps.setLong(7, conference.getConferenceCreatorId());
		ps.execute();
		ps.close();
		return id;
	}

	public void update(ConferenceData conference)
			throws SQLException {

			String sql = "update conference set title=?,description=?,starttime=?,endtime=? where id=?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, conference.getTitle());
			ps.setString(2, conference.getDesc());
			ps.setTimestamp(3, new Timestamp(conference.getStartTime()
					.getTime()));
			ps
					.setTimestamp(4, new Timestamp(conference.getEndTime()
							.getTime()));
			ps.setLong(5, conference.getId());
			ps.execute();
			ps.close();
	}

	

	public void delete(long conferenceId) throws SQLException {
			String sql = "delete from conference where id=?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setLong(1, conferenceId);
			ps.execute();
			ps.close();
	}

	
	/**
	 * getConference
	 * 
	 * @param conferenceID
	 *            long
	 * @return Conference
	 * @todo Implement this com.fulong.lyvc.conferencelibrary.ConferenceSession
	 *       method
	 */
	public ConferenceData findById(long conferenceID)	throws SQLException {
		ConferenceData conference = null;
			String sql = "select * from conference where id=?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setLong(1, conferenceID);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				conference = new ConferenceData();
				conference.setId(conferenceID);
				conference.setTitle(rs.getString("title"));
				conference.setDesc(rs.getString("description"));
				conference.setStartTime(rs.getTimestamp("starttime"));
				conference.setEndTime(rs.getTimestamp("endtime"));
				conference
						.setConferecneModelID(rs.getLong("conferencemodelid"));
				conference.setConferenceCreatorId(rs.getLong("conference_creator"));
			}
			rs.close();
			ps.close();
			return conference;
	}

	/**
	 * getConferenceList
	 * 
	 * @return ArrayList
	 * @todo Implement this com.fulong.lyvc.conferencelibrary.ConferenceSession
	 *       method
	 */
	public ConferenceData[] findAll() throws SQLException {
			// deleteInstantConferences(connection);
			ArrayList<ConferenceData> conferences = new ArrayList<ConferenceData>();
			String sql = "select * from conference where conferencemodelid != 4";
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ConferenceData conference = new ConferenceData();
				conference.setId(rs.getLong("id"));
				conference.setTitle(rs.getString("title"));
				conference.setDesc(rs.getString("description"));
				conference.setStartTime(rs.getTimestamp("starttime"));
				conference.setEndTime(rs.getTimestamp("endtime"));
				conference
						.setConferecneModelID(rs.getLong("conferencemodelid"));
				conference.setConferenceCreatorId(rs.getLong("conference_creator"));
				conferences.add(conference);
			}

			rs.close();
			ps.close();
			return conferences.toArray(new ConferenceData[conferences.size()]);
	}

	public ConferenceData[] findByCreator(long creatorId) throws SQLException {
		ArrayList<ConferenceData> conferences = new ArrayList<ConferenceData>();
			String sql = "select * from conference where conference_creator=? and conferencemodelid != 4";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setLong(1, creatorId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ConferenceData conference = new ConferenceData ();
				conference.setId(rs.getLong("id"));
				conference.setTitle(rs.getString("title"));
				conference.setDesc(rs.getString("description"));
				conference.setStartTime(rs.getTimestamp("starttime"));
				conference.setEndTime(rs.getTimestamp("endtime"));
				conference
						.setConferecneModelID(rs.getLong("conferencemodelid"));
				conference.setConferenceCreatorId(rs.getLong("conference_creator"));
				conferences.add(conference);
			}

			rs.close();
			ps.close();
			return conferences.toArray(new ConferenceData[conferences.size()]);
	}

	public void deleteByModel(int model)
			throws SQLException {
			String sql = "delete from conference where conferencemodelid = 4";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.execute();
			ps.close();
	}

	public void deleteByCreatorId(long creatorId) throws SQLException {
			String sql = "delete from conference where conference_creator=? and conferencemodelid != 4";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setLong(1, creatorId);
			ps.execute();
			ps.close();
	}

}
