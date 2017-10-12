/**
 * 
 */
package com.fulong.lyvc.core;

import java.security.Principal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Vector;

import org.apache.commons.lang.ArrayUtils;

import com.fulong.lyvc.ConferenceManager;
import com.fulong.lyvc.DAOFactory;
import com.fulong.lyvc.Group;
import com.fulong.lyvc.User;
import com.fulong.lyvc.dao.SystemRoleDAO;

/**
 * SystemGroup
 * 
 * ��Ԧ��Ƶ����ϵͳ v3.0
 * 
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 * 
 * @author ���۷�
 * 
 *         ����޸�ʱ�䣺2009-3-18
 */
public class SystemGroup implements Group {
	private DAOFactory dataSource = null;
	private ConferenceManager manager;
	private String roleId;

	public SystemGroup(ConferenceManager manager, DAOFactory dataSource, String roleId) {
		this.dataSource = dataSource;
		this.manager = manager;
		this.roleId = roleId;
	}

	public boolean addMember(Principal user) {
		String destId = getId(user);
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			SystemRoleDAO dao = dataSource.getDAO("SystemRoleDAO", connection);
			long[] users = dao.getUsers(roleId);
			if (ArrayUtils.contains(users, Long.parseLong(destId)))
				return false;
			dao.setUsers(roleId, ArrayUtils.add(users, Long.parseLong(destId)));
			return true;
		} catch (SQLException ex) {
			return false;
		} finally {
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					return false;
				}
		}
	}

	/**
	 * 
	 */
	public boolean isMember(Principal member) {
		String destId = getId(member);
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			SystemRoleDAO dao = dataSource.getDAO("SystemRoleDAO", connection);
			long[] users = dao.getUsers(roleId);
			return ArrayUtils.contains(users, Long.parseLong(destId));
		} catch (SQLException ex) {
			return false;
		} finally {
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					return false;
				}
		}

	}

	/*
	 * 
	 * 
	 * @see java.security.acl.Group#members()
	 */
	public Enumeration<? extends Principal> members() {
		return Collections.enumeration(this.users());

	}

	/**
	 * 
	 */
	public boolean removeMember(Principal user) {
		String destId = getId(user);
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			SystemRoleDAO dao = dataSource.getDAO("SystemRoleDAO", connection);
			long[] users = dao.getUsers(roleId);
			if (!ArrayUtils.contains(users, Long.parseLong(destId)))
				return false;
			dao.setUsers(roleId, ArrayUtils.removeElement(users, Long.parseLong(destId)));
			return true;
		} catch (SQLException ex) {
			return false;
		} finally {
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					return false;
				}
		}
	}

	public String getName() {
		return "" + this.roleId;
	}

	public Group addChild(String name, String desc, String creatorId) {
		throw new UnsupportedOperationException();
	}

	public boolean canAddMember(Principal principal) {

		return true;
	}

	public Collection<? extends Group> children() {
		return null;
	}

	public User getCreator() {
		return null;
	}

	public String getDesc() {
		return null;
	}

	public String getId() {
		return this.roleId;
	}

	public User getManager() {
		return null;
	}

	public Group getParentGroup() {
		return null;
	}

	public void setDesc(String desc) {
		throw new UnsupportedOperationException();
	}

	public void setName(String name) {
		throw new UnsupportedOperationException();
	}

	public Collection<? extends User> users() {
		long[] ids = null;
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			SystemRoleDAO dao = dataSource.getDAO("SystemRoleDAO", connection);
			ids = dao.getUsers(roleId);
			Vector<User> users = new Vector<User>();
			for (int i = 0; i < ids.length; i++)
				users.add(this.manager.getUser(String.valueOf(ids[i])));
			return users;
		} catch (SQLException ex) {
			return null;
		} finally {
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					return null;
				}
		}
	}

	private static final String getId(Principal user) {
		String id;
		
		if (user instanceof Group)
			id = ((Group) user).getId();
		else
			id = ((User) user).getId();
		
		return id;
	}

	@Override
	public void setParentGroup(Group parentGroup) {
		// TODO Auto-generated method stub
		
	}

}
