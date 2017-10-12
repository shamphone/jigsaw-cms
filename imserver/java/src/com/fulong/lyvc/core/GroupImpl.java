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

import com.fulong.lyvc.ConferenceManager;
import com.fulong.lyvc.DAOFactory;
import com.fulong.lyvc.Group;
import com.fulong.lyvc.User;
import com.fulong.lyvc.dao.GroupDAO;
import com.fulong.lyvc.dao.MemberDAO;
import com.fulong.lyvc.dao.UserDAO;
import com.fulong.lyvc.data.GroupData;
import com.fulong.lyvc.data.UserData;

/**
 * GroupImpl
 * 
 * ��Ԧ��Ƶ����ϵͳ v3.0
 * 
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 * 
 * @author ���۷�
 * 
 *         ����޸�ʱ�䣺2009-3-18
 */
public class GroupImpl implements Group {
	private ConferenceManager manager;
	private DAOFactory dataSource;
	private GroupData data;

	public GroupImpl(ConferenceManager manager, DAOFactory dataSource, GroupData data) {
		this.manager = manager;
		this.dataSource = dataSource;
		this.data = data;
	}

	public boolean canAddMember(Principal principal) {
		return true;
	}

	public User getCreator() {
		return manager.getUser(String.valueOf(this.data.getCreatorId()));
	}

	public String getDesc() {
		return data.getDesc();
	}

	public String getId() {
		return String.valueOf(data.getId());
	}

	public User getManager() {
		return manager.getUser(String.valueOf(this.data.getGroupManagerId()));
	}

	public String getName() {
		return data.getName();
	}

	public void setDesc(String desc) {
		this.data.setDesc(desc);
		this.save();

	}

	public void setName(String name) {
		this.data.setName(name);
		this.save();
	}

	public boolean addMember(Principal user) {

		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			MemberDAO dao = dataSource.getDAO("MemberDAO", connection);
			dao.insert(Long.parseLong(getId()), getId(user));
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

	public boolean isMember(Principal member) {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			MemberDAO dao = dataSource.getDAO("MemberDAO", connection);
			return dao.isMember(Long.parseLong(getId()), getId(member));
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

	public Enumeration<? extends Principal> members() {
		return Collections.enumeration(users());
	}

	public boolean removeMember(Principal user) {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			MemberDAO dao = dataSource.getDAO("MemberDAO", connection);
			dao.delete(Long.parseLong(getId()), getId(user));
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

	public Collection<? extends Group> children() {
		ArrayList<Group> children = new ArrayList<Group>();
		Connection connection = null;
		try {
			try {
				connection = dataSource.getConnection();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			GroupDAO dao = dataSource.getDAO("GroupDAO", connection);
			GroupData[] data = null;
			try {
				data = dao.findByParent(Long.parseLong(getId()));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			for (int i = 0; i < data.length; i++)
				children.add(new GroupImpl(this.manager, this.dataSource,
						data[i]));
		} finally {
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return children;
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
				data = dao.getMembers(Long.parseLong(getId()));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			for (int i = 0; i < data.length; i++) {
				if(data[i]!=null)
					users.add(new UserImpl(this.manager, this.dataSource, data[i]));
			}
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

	public Group addChild(String name, String desc, String creatorId) {
		GroupData data = new GroupData();
		data.setName(name);
		data.setDesc(desc);
		data.setGroupManagerId(Long.parseLong(creatorId));
		data.setCreatorId(Long.parseLong(creatorId));
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			GroupDAO dao = dataSource.getDAO("GroupDAO", connection);
			dao.insert(data);
			MemberDAO mdao = dataSource.getDAO("MemberDAO", connection);
			mdao.insert(Long.parseLong(getId()), data.getId());
			
			return new GroupImpl(this.manager, this.dataSource, data);
			
		} catch (SQLException ex) {
			try {
				connection.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return null;
	}

	private void save() {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			GroupDAO dao = dataSource.getDAO("GroupDAO", connection);
			dao.update(data);
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

	private static final long getId(Principal user) {
		long id;
		
		if (user instanceof Group)
			id = Long.parseLong(((Group) user).getId());
		else
			id = Long.parseLong(((User) user).getId());
		
		return id;
	}

	public Group getParentGroup() {
		long id = 0l;
		Connection connection = null;
		try {
			try {
				connection = dataSource.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			MemberDAO dao = dataSource.getDAO("MemberDAO", connection);
			try {
				id = dao.getParentId(this.data.getId());
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
		
		return this.manager.getGroup(String.valueOf(id));
	}

	@Override
	public void setParentGroup(Group parentGroup) {
		// TODO Auto-generated method stub
		
	}
}
