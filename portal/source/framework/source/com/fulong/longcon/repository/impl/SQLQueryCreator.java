package com.fulong.longcon.repository.impl;

import java.sql.DatabaseMetaData;
import java.util.Properties;

import com.fulong.common.dao.DaoFactory;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.Query;
import com.fulong.longcon.repository.Repository;
import com.fulong.longcon.repository.RepositoryException;
import com.fulong.longcon.repository.core.InternalRepository;
import com.fulong.longcon.repository.core.QueryCreator;
import com.fulong.longcon.repository.dao.NodeDao;

/**
 * <p>
 * Title: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Description: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术有限公司 2006
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术有限公司
 * </p>
 * 
 * @author <a href='mailto:lixf@fulong.com.cn'>lixf</a>
 * @version 2.0
 */
public class SQLQueryCreator implements QueryCreator {

	private Repository repository;
	private Class<SQLQuery> queryClass = SQLQuery.class;
	private Properties classes;

	public SQLQueryCreator() {
	}

	public void setRepository(Repository repository) {
		repository.getQueryManager().register(Query.SQL, this);
		this.repository = repository;
	}

	public void setQueryClasses(Properties classes) {
		this.classes = classes;
	}

	/**
	 * 初始化
	 * 
	 * @throws Exception
	 */

	@SuppressWarnings("unchecked")
	public void init() throws Exception {
		// 获取数据库的类型；
		DatabaseMetaData meta = null;
		DaoFactory factory = ((InternalRepository) repository).newJdbcDaoFactory();
		try {
			factory.open();
			NodeDao dao = (NodeDao) factory.getDao(NodeDao.class);
			meta = dao.getMetaData();
		} finally {
			factory.close();
		}
		String className = this.classes.getProperty(meta.getDatabaseProductName(), "oracle");

		this.queryClass = (Class<SQLQuery>) Class.forName(className);
	}

	/**
	 * 
	 * @param node
	 *            Repository
	 * @return Query
	 */
	public Query createQuery(NodeDefinition definition) {
		SQLQuery query = null;
		try {
			query = this.queryClass.newInstance();
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
		query.setNodeDefinition(definition);
		query.setRepository(this.repository);
		return query;
	}
}
