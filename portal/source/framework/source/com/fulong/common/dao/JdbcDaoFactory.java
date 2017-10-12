package com.fulong.common.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * 
* JdbcDaoFactory
* @author    <a href="lixf@fulong.com.cn">李雄峰</a>
* @date      2010-9-13
 */

public class JdbcDaoFactory implements DaoFactory {
	private Connection connection = null;
	private DataSource datasource;
	private JdbcDaoProvider provider;
	private boolean autoCommit;
	private boolean committed;
	private static final Log log = LogFactory.getLog(JdbcDaoFactory.class);
	private static int openCount = 0;
	private static int closeCount = 0;
	private long start = 0;
	public JdbcDaoFactory(DataSource datasource, JdbcDaoProvider provider) {
		this.datasource = datasource;
		this.provider = provider;
		this.committed = false;		
	}

	public void open() {
		open(false);
	}

	public void open(boolean autoCommit) {
		if(log.isTraceEnabled())
			this.start = System.currentTimeMillis();
		try {
			connection = this.datasource.getConnection();
			connection.setAutoCommit(autoCommit);			
			this.committed = false;
			openCount++;
		} catch (SQLException ex) {
			throw new DatabaseException("Unable to open connection,", ex);
		}
		if(log.isTraceEnabled()){
			this.connection = new LoggableConnection(connection);
		}
	}

	@SuppressWarnings("unchecked")
	public Dao getDao(Class clazz) {
		JdbcDao dao = this.provider.getDao(clazz);
		dao.setConnection(connection);
		return dao;
	}

	public void close() {
		if (this.connection == null)
			return;
		try {
			if (!this.committed)
				connection.commit();
		} catch (SQLException ex) {
			try {
				connection.rollback();
			} catch (SQLException ex2) {
				throw new DatabaseException(" rollback error.", ex2);
			}
			throw new DatabaseException(
					"Unable to commit connection, database has been rollbacked.",
					ex);
		} finally {
			try {
				connection.close();
				if(!log.isTraceEnabled())
					connection = null;
			} catch (SQLException ex3) {
				throw new DatabaseException(
						"Error occured in close connection.", ex3);
			}
		}
		closeCount++;
		if(log.isTraceEnabled()){
			log.trace("["+(System.currentTimeMillis()-this.start)+","+(closeCount-openCount)+ "]"+this.connection);
			connection = null;
		}

	}

	public void rollback() {
		if (this.autoCommit)
			return;
		this.committed = true;
		if (this.connection != null)
			try {
				this.connection.rollback();
			} catch (SQLException ex) {
				throw new DatabaseException(ex);
			}
	}

}
