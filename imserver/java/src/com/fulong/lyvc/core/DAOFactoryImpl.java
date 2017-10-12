/**
 * 
 */
package com.fulong.lyvc.core;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import com.fulong.common.dao.DaoException;
import com.fulong.lyvc.DAOFactory;
import com.fulong.lyvc.dao.BaseDAO;
import com.fulong.lyvc.dao.DAO;

/**
 * DAOFactoryImpl
 *
 * ��Ԧ��Ƶ����ϵͳ v3.0
 *
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 *
 * @author ���۷�
 *
 * ����޸�ʱ�䣺2009-3-20
 */
public class DAOFactoryImpl implements DAOFactory {
	private Properties daos;
	private DataSource dataSource;
	
	public Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@SuppressWarnings("unchecked")
	public <T extends DAO> T getDAO(String key, Connection connection) {
		String className = daos.getProperty(key);
		if(className == null)
			throw new IllegalArgumentException(key);
		T dao;
		try {
			dao = (T) Class.forName(className).newInstance();
		} catch (Exception e) {
			throw new DaoException(className);
		}
		((BaseDAO)dao).setConnection(connection);
		
		return dao;			
	}
	
	public void setMappings(Properties daos) {
		this.daos = daos;
		for(Object className:this.daos.values()) {			
			try {
				if(!(Class.forName(className.toString()).newInstance() instanceof BaseDAO))
					throw new IllegalArgumentException(className.toString());
			} catch (Exception e) {
				throw new IllegalArgumentException(className.toString());
			}
		}
	}
}
