/**
 * 
 */
package com.fulong.lyvc.dao.postgresql;


import com.fulong.lyvc.dao.SystemRoleDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * SystemRolePostgresDAO
 *
 * ��Ԧ��Ƶ����ϵͳ v3.0
 *
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 *
 * @author ���۷�
 *
 * ����޸�ʱ�䣺2009-3-18
 */
public class SystemRolePostgresDAO extends PostgresqlDAO implements SystemRoleDAO {
	
	public void setUsers(String roleId, long[] members) throws SQLException {
		String str ="";
		if(members.length>0)
			str+= members[0];
		for(int i=1;i<members.length;i++)
			str+=','+members[i];
            String sql = "update systemrole set role_users=? where role_id=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, str);
            ps.setLong(2, Long.parseLong(roleId));
            ps.executeUpdate();
            ps.close();
    }

    public long[] getUsers(String roleID) throws SQLException {
    	String str="";
            String sql = "select * from systemrole where role_id=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, Long.parseLong(roleID));       
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                str=rs.getString("role_users");
            }
            rs.close();
            ps.close();
            String[] users=str.split("\\,");
            long[] result=new long[users.length];
            for(int i=0;i<users.length;i++)
            	result[i]=Long.parseLong(users[i]);
            return result;
    }

}
