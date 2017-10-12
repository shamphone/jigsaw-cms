package com.fulong.lyvc.core;

import java.sql.Connection;
import java.sql.SQLException;

import com.fulong.lyvc.DAOFactory;
import com.fulong.lyvc.ModeRole;
import com.fulong.lyvc.dao.ModeRightDAO;
import com.fulong.lyvc.dao.ModeRoleDAO;
import com.fulong.lyvc.dao.postgresql.ModeRightPostgresDAO;
import com.fulong.lyvc.data.ModeRoleData;

/**
 * 
 * ConferenceModelRoleImpl
 * 
 * 锟斤拷驭锟斤拷频锟斤拷锟斤拷系统 v3.0
 * 
 * 锟斤拷权锟斤拷锟叫ｏ拷锟斤拷锟斤拷锟叫科革拷锟斤拷锟斤拷锟斤拷锟斤拷煞锟斤拷锟斤拷薰锟剿�2009
 * 
 * @author 锟斤拷锟桔凤拷
 * 
 *         锟斤拷锟斤拷薷锟绞憋拷洌�009-3-11
 */
public class ModeRoleImpl implements ModeRole {
	private ModeRoleData data;
	private DAOFactory dataSource = null;

	public ModeRoleImpl(DAOFactory dbcm,ModeRoleData data) {
		this.dataSource = dbcm;
		this.data = data;
	}

	/**
	 * 权锟斤拷锟斤拷锟�
	 * 
	 * @param rightID
	 */
	public void addRight(String rightID) throws SQLException {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			ModeRightDAO dao = dataSource.getDAO(
					"ModeRightDAO", connection);
			dao.insert(this.data.getId(), Integer.parseInt(rightID));
			connection.commit();
		} catch (SQLException ex) {
			connection.rollback();
			throw ex;
		} finally {
			if (connection != null)
				connection.close();
		}
	}

	/**
	 * 锟斤拷色权锟睫查看
	 * 
	 * @return ConferenceMemberRight[]
	 */
	public int[] getRights() {
		Connection connection = null;
		try {
			try {
				connection = dataSource.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			ModeRightPostgresDAO dao = dataSource.getDAO("ModeRightDAO", connection);
			
			try {
				return dao.findByRole(this.data.getId());
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

	/**
	 * 权锟斤拷删锟斤拷
	 * 
	 * @param rightID
	 *            int
	 * @return long
	 */
	public void removeRight(int rightID) throws SQLException {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			ModeRightPostgresDAO dao = dataSource.getDAO(
					"ModeRightDAO", connection);
			dao.delete(this.data.getId(), rightID);
			connection.commit();
		} catch (SQLException ex) {
			connection.rollback();
			throw ex;
		} finally {
			if (connection != null)
				connection.close();
		}
	}

	/**
	 * 删锟斤拷锟斤拷锟斤拷权锟斤拷
	 * 
	 * @return void
	 */
	public void removeAllRight() throws SQLException {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			ModeRightPostgresDAO dao = dataSource.getDAO(
					"ModeRightDAO", connection);
			dao.deleteByRole(this.data.getId());
			connection.commit();
		} catch (SQLException ex) {
			connection.rollback();
			throw ex;
		} finally {
			if (connection != null)
				connection.close();
		}
	}

	public String getDesc() {
		return this.data.getDesc();
	}

	public String getId() {
		return String.valueOf(this.data.getId());
	}

	public String getModelId() {
		return String.valueOf(this.data.getModeId());
	}

	public String getName() {
		return this.data.getName();
	}

	public boolean isDefault() {
		return this.data.isDefault();
	}

	public void setDesc(String desc) throws SQLException {
		this.data.setDesc(desc);
		this.save();
	}

	public void setDefault(boolean isDefault) throws SQLException {
		this.data.setDefault(isDefault);
		this.save();
		}

	public void setName(String name) throws SQLException {
		this.data.setName(name);
		this.save();		
		
	}
	
	private void save() throws SQLException{
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            ModeRoleDAO dao=dataSource.getDAO("ModeRoleDAO", connection);     
            dao.insert(data);
            connection.commit();
        } catch( SQLException ex) {
                connection.rollback();
                throw ex;
        } finally{
            if(connection != null)
                connection.close();
        }
	}
}
