package com.fulong.lyvc.core;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.fulong.lyvc.DAOFactory;
import com.fulong.lyvc.Mode;
import com.fulong.lyvc.ModeRole;
import com.fulong.lyvc.dao.ModeRoleDAO;
import com.fulong.lyvc.data.ModeData;
import com.fulong.lyvc.data.ModeRoleData;

/**
 * 
 * ConferenceModelImpl
 * 
 * ��Ԧ��Ƶ����ϵͳ v3.0
 * 
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 * 
 * @author ���۷�
 * 
 *         ����޸�ʱ�䣺2009-3-11
 */
public class ModeImpl implements Mode {
	private ModeData data;
	private DAOFactory dataSource = null;

	public ModeImpl(DAOFactory dbcm, ModeData data) {
		this.dataSource = dbcm;
		this.data = data;
	}

	/**
	 * addRole
	 * 
	 * @param role
	 *            ConferenceModelRole
	 */

	public ModeRole addRole(String name, String desc, boolean isDefault, int[] rightIds) {
		ModeRoleData data = new ModeRoleData();
		data.setName(name);
		data.setDesc(desc);
		data.setDefault(isDefault);
		data.setModeId(this.data.getId());
		Connection connection = null;
		
		try {
			connection = dataSource.getConnection();
			ModeRoleDAO dao = dataSource.getDAO("ModeRoleDAO", connection);
			dao.insert(data);
			connection.commit();
			
			return new ModeRoleImpl(this.dataSource, data);
			
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

	/**
	 * getRoles
	 * 
	 * @return ConferenceModelRole[]
	 */
	public Collection<ModeRole> getRoles() {
		Connection connection = null;
		try {
			try {
				connection = dataSource.getConnection();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			ArrayList<ModeRole> roles = new ArrayList<ModeRole>();
			ModeRoleDAO dao = dataSource.getDAO("ModeRoleDAO", connection);
			ModeRoleData[] data = null;
			try {
				data = dao.findByModeId(Long.parseLong(getId()));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			for (int i = 0; i < data.length; i++)
				roles.add(new ModeRoleImpl(this.dataSource, data[i]));
			return roles;
		} finally {
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}

	/**
	 * removeRole
	 * 
	 * @param roleID
	 *            long
	 * @return boolean
	 */
	public void removeRole(long roleID) {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			ModeRoleDAO dao = dataSource.getDAO("ModeRoleDAO", connection);
			dao.delete(roleID);
			connection.commit();
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

	public String getDesc() {
		return this.data.getDesc();
	}

	public String getId() {
		return String.valueOf(this.data.getId());
	}

	public String getName() {
		return this.data.getName();
	}

	public void setDesc(String desc) {
		this.data.setDesc(desc);
	}

	public void setName(String name) {
		this.data.setName(name);
	}

	public ModeRole getRole(String roleID) {
		Connection connection = null;
		try {
			try {
				connection = dataSource.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			ModeRoleDAO dao = dataSource.getDAO("ModeRoleDAO", connection);
			ModeRoleData data = null;

			try {
				data = dao.findByID(Long.parseLong(roleID));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			if (data != null)
				return new ModeRoleImpl(this.dataSource, data);

			return null;

		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
