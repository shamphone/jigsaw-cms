package com.fulong.longcon.calendar.impl;

import java.util.Vector;
import java.util.Iterator;
import java.util.Collection;
import com.fulong.longcon.calendar.HolidaysIterator;
import com.fulong.longcon.calendar.Holidays;

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
 * @author lishaobo
 * @version 1.0
 */
public class HolidaysIteratorImpl implements HolidaysIterator {
	@SuppressWarnings("unchecked")
	private Vector list;
	@SuppressWarnings("unchecked")
	private Iterator iterator;
	private long size;
	private long position;

	@SuppressWarnings("unchecked")
	public HolidaysIteratorImpl() {
		list = new Vector();
		iterator = null;
		size = 0;
		this.position = 0;
	}

	@SuppressWarnings("unchecked")
	public HolidaysIteratorImpl(Collection objs) {
		list = new Vector(objs);
		iterator = null;
		size = 0;
		this.position = 0;
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
		return this.nextHolidays();
	}

	/**
	 * Removes from the underlying collection the last element returned by the
	 * iterator (optional operation).
	 * 
	 * @todo Implement this java.util.Iterator method
	 */
	public void remove() {
		if (this.iterator == null)
			this.iterator = this.list.iterator();
		this.iterator.remove();
	}

	public Holidays nextHolidays() {
		if ((this.iterator == null) || (!this.iterator.hasNext()))
			this.iterator = this.list.iterator();
		this.position++;
		if ((this.iterator != null) && (this.iterator.hasNext()))
			return (Holidays) this.iterator.next();
		return null;

	}

	/**
	 * Skip a number of elements in the iterator.
	 * 
	 * @param skipNum
	 *            the non-negative number of elements to skip
	 * @throws java.util.NoSuchElementException
	 *             if skipped past the last element in the iterator.
	 */
	public void skip(long skipNum) {
		for (; skipNum > 0; skipNum--) {
			this.nextHolidays();
		}
	}

	/**
	 * Returns the current position within the iterator. The number returned is
	 * the 0-based index of the next element in the iterator, i.e. the one that
	 * will be returned on the subsequent <code>next</code> call.
	 * <p/>
	 * Note that this method does not check if there is a next element, i.e. an
	 * empty iterator will always return 0.
	 * 
	 * @return a long
	 */
	public long getPosition() {
		return this.position;
	}

}
