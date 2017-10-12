/**
 * 
 */
package com.fulong.lyvc.dao.postgresql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.fulong.lyvc.dao.ContactDefaultGroupDAO;
import com.fulong.lyvc.data.ContactGroupData;

/**
 * ContactDefaultGroupPostgresDAO
 * 
 * ��Ԧ��Ƶ����ϵͳ v3.0
 * 
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 * 
 * @author ���۷�
 * 
 *         ����޸�ʱ�䣺2009-3-18
 */
public class ContactDefaultGroupPostgresDAO extends PostgresqlDAO implements
		ContactDefaultGroupDAO {
	/**
	 * getDefaultGroupId
	 * 
	 * @param userId
	 *            long
	 * @return long
	 * @throws SQLException
	 */
	public void insert(ContactGroupData data) throws SQLException {

		data.setId(this.getPrimaryKey());
		String sql = "insert into userdefaultgroup(userid, groupid) values(?, ?)";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setLong(1, data.getUserId());
		ps.setLong(2, data.getId());
		ps.executeUpdate();
		ps.close();

	}

	/**
	 * getDefaultGroupId
	 * 
	 * @param userId
	 *            long
	 * @return long
	 */
	public ContactGroupData findByUser(long userId) throws SQLException {
		ContactGroupData agroup = null;
		String sql = "select * from userdefaultgroup where userid=? order by userid asc";
		PreparedStatement psgroup = connection.prepareStatement(sql);
		psgroup.setLong(1, userId);
		ResultSet rsgroup = psgroup.executeQuery();

		if (rsgroup.next()) {
			agroup = new ContactGroupData();
			agroup.setId(rsgroup.getLong("groupid"));
			agroup.setName(rsgroup.getString("groupid"));
			agroup.setIndex(0);
			agroup.setUserId(rsgroup.getLong("userid"));
			agroup.setRoot(true);
		}
		rsgroup.close();
		psgroup.close();
		return agroup;

	}
	
	/**
	 * getDefaultGroupId
	 * 
	 * @param userId
	 *            long
	 * @return long
	 */
	public ContactGroupData findById(long id) throws SQLException {
		ContactGroupData agroup = null;
		String sql = "select * from userdefaultgroup where id=? order by displaysort asc";
		PreparedStatement psgroup = connection.prepareStatement(sql);
		psgroup.setLong(1, id);
		ResultSet rsgroup = psgroup.executeQuery();

		if (rsgroup.next()) {
			agroup = new ContactGroupData();
			agroup.setId(rsgroup.getLong("groupid"));
			agroup.setName(rsgroup.getString("groupid"));
			agroup.setIndex(0);
			agroup.setUserId(rsgroup.getLong("userid"));
			agroup.setRoot(true);
		}
		rsgroup.close();
		psgroup.close();
		return agroup;

	}	
}
