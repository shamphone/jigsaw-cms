/**
 * 
 */
package com.fulong.lyvc.dao;

import java.sql.SQLException;

/**
 * SystemRoleDAO
 *
 * ��Ԧ��Ƶ����ϵͳ v3.0
 *
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 *
 * @author ���۷�
 *
 * ����޸�ʱ�䣺2009-3-18
 */
public interface SystemRoleDAO extends DAO {

	public void setUsers(String roleId, long[] members) throws SQLException;

	public long[] getUsers(String roleID) throws SQLException;
  
}
