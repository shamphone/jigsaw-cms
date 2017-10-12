package com.fulong.common.util;

import java.util.List;
import java.util.Collection;
import java.util.Vector;

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
public class ListRangeIterator<T> implements RangeIterator<T> {
	private List<T> rows;
	private long position;

	public ListRangeIterator(List<T> rows) {
		this.position = 0;
		this.rows = rows;
	}

	public ListRangeIterator(Collection<T> rows) {
		this.position = 0;
		this.rows = new Vector<T>(rows);
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
		return this.rows.size();
	}

	/**
	 * Returns <tt>true</tt> if the iteration has more elements.
	 * 
	 * @return <tt>true</tt> if the iterator has more elements.
	 */
	public boolean hasNext() {
		return this.position < this.rows.size();
	}

	/**
	 * Returns the next element in the iteration.
	 * 
	 * @return the next element in the iteration.
	 */
	public T next() {
		// 注意：get方法是从0开始的，所以，应该先取后＋＋
		return this.rows.get((int) position++);
	}

	/**
	 * Removes from the underlying collection the last element returned by the
	 * iterator (optional operation).
	 * 
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
}
