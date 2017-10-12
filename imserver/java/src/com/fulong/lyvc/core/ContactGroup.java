/**
 * 
 */
package com.fulong.lyvc.core;

import java.security.Principal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Vector;

import com.fulong.lyvc.ConferenceManager;
import com.fulong.lyvc.DAOFactory;
import com.fulong.lyvc.Group;
import com.fulong.lyvc.User;
import com.fulong.lyvc.dao.ContactGroupDAO;
import com.fulong.lyvc.dao.ContactMemberDAO;
import com.fulong.lyvc.dao.UserDAO;
import com.fulong.lyvc.data.ContactGroupData;
import com.fulong.lyvc.data.UserData;

/**
 * ContactGroup
 * 
 * ��Ԧ��Ƶ����ϵͳ v3.0
 * 
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 * 
 * @author ���۷�
 * 
 *         ����޸�ʱ�䣺2009-3-18
 */
public class ContactGroup implements Group {
	private ConferenceManager manager;
	private DAOFactory dataSource;
	private User owner;
	private ContactGroupData data;

	public ContactGroup(ConferenceManager manager, DAOFactory dataSource, User owner, ContactGroupData data) {
		this.manager = manager;
		this.dataSource = dataSource;
		this.owner = owner;
		this.data = data;
	}
	public ContactGroup(ConferenceManager manager, DAOFactory dataSource, ContactGroupData data) {
		this.manager = manager;
		this.dataSource = dataSource;
		this.owner = manager.getUser(String.valueOf(data.getUserId()));
		this.data = data;
	}

	public Group addChild(String name, String desc, String creatorId) {
		if (!this.data.isRoot())
			throw new UnsupportedOperationException();
		
		ContactGroupData data = new ContactGroupData();
		data.setName(name);
		data.setUserId(Long.parseLong(owner.getId()));

		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			ContactGroupDAO cmDAO = dataSource.getDAO("ContactGroupDAO", connection);
			cmDAO.insert(data);
			connection.commit();
			
			return new ContactGroup(this.manager, this.dataSource, owner, data);
			
		} catch (SQLException ex) {
			try {
				connection.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} finally {
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
		return null;
	}

	public boolean canAddMember(Principal principal) {
		if (owner.equals(principal))
			return false;
		
		return true;
	}

	public Collection<? extends Group> children() {
		if (!this.data.isRoot())
			return null;
		
		Vector<ContactGroup> groups = new Vector<ContactGroup>();
		Connection connection = null;
		
		try {
			connection = dataSource.getConnection();
			ContactGroupDAO cmDAO = dataSource.getDAO("ContactGroupDAO", connection);
			ContactGroupData[] data = cmDAO.findGroupsByUser(Long.parseLong(owner.getId()));
			for (int i = 0; i < data.length; i++) {
				groups.add(new ContactGroup(this.manager, this.dataSource, this.owner, data[i]));
			}
			
			return groups;
			
		} catch (SQLException ex) {
			try {
				connection.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} finally {
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
		return null;
	}

	public User getCreator() {
		return this.owner;
	}

	public String getDesc() {
		return null;
	}

	public String getId() {
		return String.valueOf(data.getId());
	}

	public User getManager() {
		return this.owner;
	}

	public String getName() {
		return this.data.getName();
	}

	public Group getParentGroup() {
		return owner.getContactGroup();
	}

	public void setDesc(String desc) {
	}

	public void setName(String name) {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			ContactGroupDAO cmDAO = dataSource.getDAO("ContactGroupDAO", connection);
			cmDAO.rename(Long.parseLong(getId()), name);
			connection.commit();
			this.data.setName(name);
		} catch (SQLException ex) {
			try {
				connection.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} finally {
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}

	public Collection<? extends User> users() {
		ArrayList<User> users = new ArrayList<User>();
		Connection connection = null;
		try {
			try {
				connection = dataSource.getConnection();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			UserDAO dao = dataSource.getDAO("UserDAO", connection);
			UserData[] data = null;
			try {
				data = dao.findByContactGroup(Long.parseLong(this.getId()));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			for (int i = 0; i < data.length; i++)
				users.add(new UserImpl(this.manager,this.dataSource, data[i]));
			
		} finally {
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
		return users;
	}

	public boolean addMember(Principal user) {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			ContactMemberDAO dao = dataSource.getDAO("ContactMemberDAO", connection);
			dao.insert(getId(user), Long.parseLong(this.getId()));
			
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.security.acl.Group#isMember(java.security.Principal)
	 */
	public boolean isMember(Principal member) {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			ContactMemberDAO dao = dataSource.getDAO("ContactMemberDAO", connection);
			
			return dao.isMember(getId(member), Long.parseLong(this.getId()));
			
		} catch (SQLException ex) {
			return false;
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					return false;
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.security.acl.Group#members()
	 */
	public Enumeration<? extends Principal> members() {
		return Collections.enumeration(users());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.security.acl.Group#removeMember(java.security.Principal)
	 */
	public boolean removeMember(Principal user) {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			ContactMemberDAO dao = dataSource.getDAO("ContactMemberDAO", connection);
			dao.delete(getId(user), Long.parseLong(this.getId()));
			
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

	private static final long getId(Principal user) {
		long id;
		if (user instanceof Group)
			id = Long.parseLong(((Group) user).getId());
		else
			id = Long.parseLong(((User) user).getId());
		return id;
	}
	@Override
	public void setParentGroup(Group parentGroup) {
		// TODO Auto-generated method stub
		
	}
}
