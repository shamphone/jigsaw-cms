/**
 * 
 */
package com.fulong.lyvc.dao.postgresql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.fulong.lyvc.dao.ModeRightDAO;

/**
 * ConferenceModeRightPostgresDAO
 * 
 * 龙驭视频会议系统 v3.0
 * 
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 * 
 * @author 李雄锋
 * 
 *         最后修改时间：2009-3-17
 */
public class ModeRightPostgresDAO extends PostgresqlDAO implements
		ModeRightDAO {

	/**
	 * 权限增加
	 * 
	 * @param rightID
	 *            int
	 */
	public void insert(long roleId, int rightID) throws SQLException {
		String sql = "insert into conferenceroleright (roleid,rightid) values(?,?)";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setLong(1, roleId);
		ps.setInt(2, rightID);
		ps.execute();
		ps.close();
	}

	/**
	 * 角色权限查看
	 * 
	 * @return ConferenceMemberRight[]
	 */
	public int[] findByRole(long roleId) throws SQLException {
		String sql = "select rightid from conferenceroleright where roleid=?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setLong(1, roleId);
		return this.queryIntArray(ps);
	}

	/**
	 * 权限删除
	 * 
	 * @param rightID
	 *            int
	 * @return long
	 */
	public void delete(long roleId, int rightID) throws SQLException {
		String sql = "delete from conferenceroleright where roleid=? and rightid=?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setLong(1, roleId);
		ps.setInt(2, rightID);
		ps.execute();
		ps.close();
	}

	/**
	 * 删除所有权限
	 * 
	 * @return void
	 */
	public void deleteByRole(long roleId) throws SQLException {
		String sql = "delete from conferenceroleright where roleid=?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setLong(1, roleId);
		ps.execute();
		ps.close();
	}
}
