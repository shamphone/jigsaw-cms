package com.fulong.common.dao;

import javax.sql.DataSource;

/**
 * <p>
 * Title: 龙驭会员管理系统2.0
 * </p>
 * 
 * <p>
 * Description: 龙驭会员管理系统2.0
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术有限公司
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术有限公司
 * </p>
 * 
 * @author lixf
 * @version 2.0
 */
public class JdbcDaoContext implements DaoContext {
	private PropertiesDaoProvider provider;
	private DataSource datasource;
	private String daoType;

	public JdbcDaoContext() {
	}

	/**
	 * getDaoType
	 * 
	 * @return String
	 * @todo Implement this com.fulong.longcon.DaoService method
	 */
	public String getDaoType() {
		return this.daoType;
	}

	/**
	 * newJdbcDaoFactory
	 * 
	 * @return JdbcDaoFactory
	 * @todo Implement this com.fulong.longcon.DaoService method
	 */
	public DaoFactory getDaoFactory() {
		return new JdbcDaoFactory(this.datasource, provider);
	}

	public void destroy() throws Exception {
	}

	public void init() throws Exception {
		this.provider = new PropertiesDaoProvider();
		this.provider.addMappingFile(this.daoType);

	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}

	public void setDaoType(String daoType) {
		this.daoType = daoType;
	}

}
