package com.fulong.common.util;

/**
 * <p>
 * Title: 龙驭电子商务系统
 * </p>
 * 
 * <p>
 * Description: 龙驭电子商务系统
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术股份有限公司 2006
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * @author 李雄锋
 * @version 2.0
 */
@SuppressWarnings("unchecked")
public class RangeIteratorWrapper implements RangeIterator {
	
	protected RangeIterator wrapper;

	public RangeIteratorWrapper(RangeIterator wrapper) {
		this.wrapper = wrapper;
	}

	/**
	 * Returns the current position within the iterator.
	 * 
	 * @return a long
	 */
	public long getPosition() {
		return this.wrapper.getPosition();
	}

	/**
	 * Returns the number of elements in the iterator.
	 * 
	 * @return a long
	 */
	public long getSize() {
		return this.wrapper.getSize();
	}

	/**
	 * Returns <tt>true</tt> if the iteration has more elements.
	 * 
	 * @return <tt>true</tt> if the iterator has more elements.
	 */
	public boolean hasNext() {
		return this.wrapper.hasNext();
	}

	/**
	 * Returns the next element in the iteration.
	 * 
	 * @return the next element in the iteration.
	 */
	public Object next() {
		return this.wrapper.next();
	}

	/**
	 * Removes from the underlying collection the last element returned by the
	 * iterator (optional operation).
	 * 
	 */
	public void remove() {
		this.wrapper.remove();
	}

	/**
	 * Skip a number of elements in the iterator.
	 * 
	 * @param skipNum
	 *            the non-negative number of elements to skip
	 */
	public void skip(long skipNum) {
		this.wrapper.skip(skipNum);
	}

}
