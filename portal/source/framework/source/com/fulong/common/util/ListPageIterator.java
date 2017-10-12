package com.fulong.common.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

/**
 * 
 * <p>
 * Title: WebMaster Core Library
 * </p>
 * 
 * <p>
 * Description: Longcon WebMaster SV3
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Beijing Zhongke Fulong Computer Tech. LTD
 * </p>
 * 
 * @author Lixf
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class ListPageIterator implements PageIterator {
	
	private Vector<Object> list;
	private Iterator<Object> iterator;
	private long size;

	public ListPageIterator() {
		list = new Vector<Object>();
		iterator = null;
		size = 0;
	}

	public ListPageIterator(Collection<?> objs) {
		list = new Vector<Object>(objs);
		iterator = null;
		size = 0;
	}

	public ListPageIterator(Object item[]) {
		list = new Vector<Object>();
		iterator = null;
		size = 0;
		for (int i = 0; i < item.length; i++) {
			this.add(item[i]);
		}
	}

	public void add(Object obj) {
		list.add(obj);
	}

	public void addAll(Collection<?> objs) {
		list.addAll(objs);
	}

	/**
	 * 
	 * @return long
	 * @todo Implement this com.fulong.longcon.PageIterator method
	 */
	public long getSize() {
		if (size > 0) {
			return size;
		}
		return list.size();
	}

	/**
	 * Returns <tt>true</tt> if the iteration has more elements.
	 * 
	 * @return <tt>true</tt> if the iterator has more elements.
	 * @todo Implement this java.util.Iterator method
	 */
	public boolean hasNext() {
		if (iterator == null) {
			iterator = list.iterator();
		}
		return iterator.hasNext();
	}

	/**
	 * Returns the next element in the iteration.
	 * 
	 * @return the next element in the iteration.
	 * @todo Implement this java.util.Iterator method
	 */
	public Object next() {
		if (iterator == null) {
			iterator = list.iterator();
		}

		return iterator.next();
	}

	/**
	 * Removes from the underlying collection the last element returned by the
	 * iterator (optional operation).
	 * 
	 * @todo Implement this java.util.Iterator method
	 */
	public void remove() {
	}

	public void setSize(long size) {
		this.size = size;
	}

	public PageIterator<?> reverse() {
		Vector<Object> tmpList = new Vector<Object>();
		for (int i = list.size(); i > 0; i--) {
			tmpList.add(list.get(i - 1));
		}
		list = tmpList;
		return this;
	}
}
