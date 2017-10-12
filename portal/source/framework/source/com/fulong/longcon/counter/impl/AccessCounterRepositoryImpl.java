package com.fulong.longcon.counter.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fulong.common.cache.Cache;
import com.fulong.common.cache.CacheFactory;
import com.fulong.common.dao.DaoFactory;
import com.fulong.common.dao.DatabaseException;
import com.fulong.common.dao.JdbcDaoFactory;
import com.fulong.common.dao.JdbcDaoProvider;
import com.fulong.common.dao.PropertiesDaoProvider;
import com.fulong.longcon.counter.ext.AccessCounter;
import com.fulong.longcon.counter.dao.RealTimeCountDao;
import com.fulong.longcon.counter.dao.TotallyCountDao;
import com.fulong.longcon.counter.ext.AccessCounterRepositoryExt;

/**
 * 计数器库。作为计数器包的总入口，提供系统的初始化机制。
 * 这个类提供的功能作为计数器的工厂类，提供计数器获取的方法。为了提高系统计数器的性能，这个类采用了如下算法：
 * <ul>
 * <li>使用缓存机制来保存计数器。
 * <li>
 * 使用额外的线程来异步保存计数值。各计数器中并不直接将计数值立即保存到数据库中，而是通过这个类，通过专门的计数器dump线程，向数据库批量更新计数值。
 * </ul>
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
public class AccessCounterRepositoryImpl implements Runnable, AccessCounterRepositoryExt {
	private DataSource dataSource;
	private JdbcDaoProvider provider;
	private Cache storage;
	private CacheFactory cacheFactory;
	private AccessCounterQueue queue;
	private String threadName = "accessCounter thread.";

	private static final Log log = LogFactory.getLog(AccessCounterRepositoryImpl.class);

	public AccessCounterRepositoryImpl() {
		this.queue = new AccessCounterQueue();
	}

	public void init() throws Exception {
		this.storage = this.cacheFactory.getCache(AccessCounterRepositoryImpl.class);
		PropertiesDaoProvider provider = new PropertiesDaoProvider();
		provider.loadMappingFile("com.fulong.longcon.counter.dao", dataSource);
		this.provider = provider;
		Thread thread = new Thread(this, threadName);
		thread.setDaemon(false);
		thread.start();
	}

	public void setCacheFactory(CacheFactory cacheFactory) throws IOException {
		this.cacheFactory = cacheFactory;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * 设置dao映射。在ForumService.getInstance()方法中通过java bean的反射机制来调用这个方法。
	 * 
	 * @param dao
	 *            String
	 */
	public void setDao(String dao) {
		
	}

	/**
	 * 新建JDBC Dao
	 * 
	 * @return JdbcDaoFactory
	 */
	public JdbcDaoFactory newJdbcDaoFactory() {

		return new JdbcDaoFactory(this.dataSource, this.provider);
	}

	public void run() {
		log.info("Access counter thread " + this.threadName + " started.");
		while (true) {
			AccessCounterNode[] nodeArray = this.queue.get(5);
			if ((nodeArray != null) && (nodeArray.length > 0)) {
				DaoFactory factory = this.newJdbcDaoFactory();
				try {
					factory.open();
					RealTimeCountDao realTimedao = (RealTimeCountDao) factory.getDao(RealTimeCountDao.class);

					TotallyCountDao totallydao = (TotallyCountDao) factory.getDao(TotallyCountDao.class);
					for (int i = 0; i < nodeArray.length; i++) {
						long counter = totallydao.loadCount(nodeArray[i].getName());
						if(counter<0){
							totallydao.insertCount(nodeArray[i].getName());
							counter=0;
						}
						counter += 1;
						realTimedao.insertCount(nodeArray[i].getName(), nodeArray[i].getAccessDate());
						totallydao.saveCount(nodeArray[i].getName(), counter);
					}
					log.debug(nodeArray.length + " counter recorded.");
				} catch (SQLException ex) {
					factory.rollback();
					throw new DatabaseException(ex);
				} finally {
					factory.close();
				}
			}
		}
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}

	public long getCount(String name) {
		return this.getAccessCounter(name).getCount();
	}

	public long getDayCount(String name) {
		return this.getAccessCounter(name).getDayCount();
	}

	public void increase(String name) {
		AccessCounterNode node = new AccessCounterNode(name, new Date());
		this.queue.put(node);
		if (this.storage.containsKey(name)){
			((AccessCounter) this.storage.get(name)).increase();
		}
	}

	public long getCount(String name, Calendar date, int type) {
		// TODO Auto-generated method stub
		return 0;
	}

	public long getCount(String name, int type) {
		switch (type) {
		case Calendar.ERA:
			return this.getAccessCounter(name).getCount();
		case Calendar.YEAR:
			return this.getAccessCounter(name).getYearlyCount();
		case Calendar.MONTH:
			return this.getAccessCounter(name).getMonthlyCount();
		case Calendar.WEEK_OF_YEAR:
		case Calendar.WEEK_OF_MONTH:
			return this.getAccessCounter(name).getWeeklyCount();
		case Calendar.DATE:
			return this.getAccessCounter(name).getDayCount();
		default:
			throw new IllegalArgumentException("Unknown type for :" + type + ".");
		}
	}

	protected AccessCounter getAccessCounter(String name) {
		if (this.storage.containsKey(name))
			return (AccessCounter) this.storage.get(name);
		AccessCounter counter = new AccessCounterImpl(name, this);
		this.storage.put(name, counter);
		return counter;
	}

}
