package com.fulong.longcon.resource.impl;

import java.util.NoSuchElementException;
import java.util.Iterator;
import java.util.Vector;
import com.fulong.common.dao.DaoFactory;
import com.fulong.common.dao.SQLParameter;
import com.fulong.common.dao.DatabaseException;
import java.sql.SQLException;
import com.fulong.longcon.resource.ResourceIterator;
import com.fulong.longcon.resource.ext.ResourceManagerExt;
import com.fulong.longcon.resource.dao.ResourceDao;
import com.fulong.longcon.resource.data.ResourceData;
import com.fulong.longcon.resource.Resource;

/**
 * ResourceIterator辅助基类，实现了一些通用的方法
 * <p>
 * Title: 龙驭会员管理系统2.0
 * </p>
 * 
 * <p>
 * Description: 龙驭会员管理系统2.0
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 北京中科辅龙计算机技术有限公司 2006
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术有限公司
 * </p>
 * 
 * @author Lixf
 * @version 2.0
 */
class BasicResourceIterator implements ResourceIterator {
	private Iterator<Resource> iterator;
	private int position;
	private long size;
	private int pageSize;
	protected ResourceManagerExt provider;
	private SQLParameter[] parameters;
	private String query;
	private String count;

	@SuppressWarnings("unchecked")
	public BasicResourceIterator(ResourceManagerExt provider,
			SQLParameter[] parameters, String query, String count) {
		this.provider = provider;
		this.size = -1;
		this.position = 0;
		this.iterator = new Vector().iterator(); // init an empty iterator;
		this.parameters = parameters;
		this.query = query;
		this.count = count;
		this.pageSize = 20;
	}

	protected ResourceData[] loadMore(int fromIndex, int pageSize) {
		DaoFactory factory = this.provider.newDaoFactory();
		try {
			factory.open();
			ResourceDao dao = (ResourceDao) factory.getDao(ResourceDao.class);
			return dao.search(query, parameters, fromIndex, pageSize);
		} catch (SQLException se) {
			throw new DatabaseException(se);
		} finally {
			factory.close();
		}
	}

	protected long loadSize() {
		if (this.size < 0) {
			DaoFactory factory = this.provider.newDaoFactory();
			try {
				factory.open();
				ResourceDao dao = (ResourceDao) factory
						.getDao(ResourceDao.class);
				this.size = dao.countResultNum(count, parameters);
			} catch (SQLException se) {
				throw new DatabaseException(se);
			} finally {
				factory.close();
			}
		}
		return this.size;
	}

	/**
	 * Returns the current position within the iterator.
	 * 
	 * @return a long
	 */
	public long getPosition() {
		return this.position;
	}

	/**
	 * Returns the number of elements in the iterator.
	 * 
	 * @return a long
	 */
	public long getSize() {
		if (size < 0) {
			this.size = this.loadSize();
		}
		return this.size;
	}

	/**
	 * Returns <tt>true</tt> if the iteration has more elements.
	 * 
	 * @return <tt>true</tt> if the iterator has more elements.
	 */
	public boolean hasNext() {
		return this.position < this.getSize();
	}

	/**
	 * Returns the next element in the iteration.
	 * 
	 * @return the next element in the iteration.
	 */
	public Object next() {
		if (!this.hasNext())
			throw new NoSuchElementException();
		if (!this.iterator.hasNext()) {
			ResourceData[] data = this.loadMore(this.position, this.position
					+ this.pageSize);
			Vector<Resource> contents = new Vector<Resource>();
			for (int i = 0; i < data.length; i++) {
				contents.add(this.provider.getResource(data[i].getPkid()));
			}
			this.iterator = contents.iterator();
		}
		if (this.iterator.hasNext()) {
			this.position++;
			return this.iterator.next();
		} else
			throw new NoSuchElementException();
	}

	/**
	 * 
	 * @return Membership
	 */
	public Resource nextResource() {
		return (Resource) next();
	}

	/**
	 * Removes from the underlying collection the last element returned by the
	 * iterator (optional operation).
	 */
	public void remove() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Skip a number of elements in the iterator.
	 * 
	 * @param skipNum
	 *            the non-negative number of elements to skip
	 */
	public void skip(long skipNum) {
		this.position += skipNum;
	}

	public void setFetchSize(long size) {
		this.pageSize += (int) size;
	}

}
