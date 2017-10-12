/**
 * 
 */
package com.fulong.lyvc.dao.postgresql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.fulong.lyvc.dao.MemberDAO;

/**
 * MemberPostgresDAO
 *
 * 龙驭视频会议系统 v3.0
 *
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 *
 * @author 李雄锋
 *
 * 最后修改时间：2009-3-18
 */
public class MemberPostgresDAO extends PostgresqlDAO implements MemberDAO{
/**
 * 
 * @param parentGroupId
 * @param userId
 * @throws SQLException
 */
	public void insert(long parentGroupId, long userId)
			throws SQLException {
			String sql = "insert into groupmember (groupid,memberid) values(?,?)";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setLong(1, parentGroupId);
			ps.setLong(2, userId);
			ps.executeUpdate();
			ps.close();
	}
	public long getParentId(long Id) throws SQLException {
		long pId = -1;
			String sql = "select groupid, memberid from groupmember where memberid=?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setLong(1, Id);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				pId = rs.getLong("groupid");
			rs.close();
			ps.close();
			return pId;
	}

	
	/**
	 * 仅删除关系，并不影响用户或机构本身，但Member将失去所属组织 应与其他函数联合使用，以保证数据完整性
	 */
	public void delete(long groupId, long memberId) throws SQLException {
			String sql = "delete from groupmember where groupid=? and memberid=?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setLong(1, groupId);
			ps.setLong(2, memberId);
			ps.executeUpdate();
			ps.close();
	}
	public void deleteByGroup(long groupId) throws SQLException {
		String sql = "delete from groupmember where groupid=? ";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setLong(1, groupId);
	
		ps.executeUpdate();
		ps.close();
		
	}
	public boolean isMember(long groupId, long memberId)	throws SQLException {
		boolean result = false;
		String sql = "select * from groupmember where groupid=? and memberid=?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setLong(1, groupId);
		ps.setLong(2, memberId);
		ResultSet rs = ps.executeQuery();
		result = rs.next();
		ps.close();
		return result;
	}	
	



	
}
