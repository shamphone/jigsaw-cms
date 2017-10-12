/**
 * 
 */
package com.fulong.lyvc.dao.postgresql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fulong.lyvc.dao.ConferenceRightDAO;
import com.fulong.lyvc.data.ConferenceRightData;

/**
 * ConferenceRightPostgresDAO
 * 
 * 龙驭视频会议系统 v3.0
 * 
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 * 
 * @author 李雄锋
 * 
 *         最后修改时间：2009-3-17
 */
public class ConferenceRightPostgresDAO extends PostgresqlDAO implements
		ConferenceRightDAO {
	public void deleteConferenceRight(long rightID) throws SQLException {
		String sql = "delete from conferenceright where id=?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setLong(1, rightID);
		ps.execute();
		ps.close();
	}

	public ConferenceRightData getConferenceRight(long rightID)
			throws SQLException {
		ConferenceRightData right = null;
		String sql = "select * from conferenceright where id=?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setLong(1, rightID);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			right = new ConferenceRightData();
			right.setRightID(rightID);
			right.setName(rs.getString("name"));
			right.setDesc(rs.getString("description"));
		}

		rs.close();
		ps.close();
		return right;

	}

	public List<ConferenceRightData> getAllConferenceRight()
			throws SQLException {
		ArrayList<ConferenceRightData> rights = new ArrayList<ConferenceRightData>();
		String sql = "select * from conferenceright";
		PreparedStatement ps = connection.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			ConferenceRightData right = new ConferenceRightData();
			right.setRightID(rs.getLong("id"));
			right.setName(rs.getString("name"));
			right.setDesc(rs.getString("description"));
			rights.add(right);
		}
		rs.close();
		ps.close();
		return rights;
	}
}
