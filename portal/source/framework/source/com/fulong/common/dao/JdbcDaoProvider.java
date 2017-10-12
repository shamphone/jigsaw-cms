package com.fulong.common.dao;
/**
 * 
* JdbcDaoProvider
* @author    <a href="lixf@fulong.com.cn">李雄峰</a>
* @date      2010-9-12
 */
public interface JdbcDaoProvider {
	
	@SuppressWarnings("unchecked")
	public JdbcDao getDao(Class clazz);
}
