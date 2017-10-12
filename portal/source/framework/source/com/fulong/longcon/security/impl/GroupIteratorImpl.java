package com.fulong.longcon.security.impl;

import java.util.Vector;

import com.fulong.common.dao.SQLParameter;
import com.fulong.longcon.repository.Repository;
import com.fulong.longcon.security.Group;
import com.fulong.longcon.security.GroupIterator;
import com.fulong.longcon.security.ext.PassportContext;

/**
 * MembershipIterator辅助基类，实现了一些通用的方法
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
 * @author lishaobo
 * @version 2.0
 */
class GroupIteratorImpl implements GroupIterator {
	private int position;
	private int pageSize;
	@SuppressWarnings("rawtypes")
	public GroupIteratorImpl(PassportContext provider, Repository repository,
			SQLParameter[] parameters, String query, String count) {
		this.position = 0;
		new Vector().iterator();
		this.pageSize = 20;
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
	 * Returns <tt>true</tt> if the iteration has more elements.
	 * 
	 * @return <tt>true</tt> if the iterator has more elements.
	 */
	public boolean hasNext() {
		return this.position < this.getSize();
	}


	/**
	 * 
	 * @return Membership
	 */
	public Group nextGroup() {
		return (Group) next();
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

	@Override
	public long getSize() {
		return 0;
	}

	@Override
	public Object next() {
		return null;
	}

}
