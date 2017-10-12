package com.fulong.common.util;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * <p>
 * Title: Longcon Passport System
 * </p>
 * 
 * <p>
 * Description: Longcon Passport
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) Beijing Zhongke Fulong Computer Technology LTD. 2005
 * </p>
 * 
 * <p>
 * Company: Beijing Zhongke Fulong Computer Technology LTD.
 * </p>
 * 
 * @author Lixf
 * @version 2.0.0
 */
public final class Enumerator<T> implements Enumeration<T> {

	// Iterator over which the Enumeration takes place
	private Iterator<T> iterator = null;

	/**
	 * Returns an Enumeration over the specified Collection.
	 * 
	 * @param collection
	 *            Collection with values that should be enumerated
	 */
	public Enumerator(Collection<T> collection) {
		this(collection.iterator());
	}

	/**
	 * Returns an Enumeration over the values of the specified Iterator.
	 * 
	 * @param iterator
	 *            Iterator to be wrapped
	 */
	public Enumerator(Iterator<T> iterator) {
		super();
		this.iterator = iterator;
	}

	/**
	 * Returns an Enumeration over the values of the specified Map.
	 * 
	 * @param map
	 *            Map with values that should be enumerated
	 */
	public Enumerator(Map<?, T> map) {
		this(map.values().iterator());
	}

	/**
	 * Tests if this enumeration contains more elements.
	 * 
	 * @return <code>true</code> if this enumeration contains at least one more
	 *         element to provide, <code>false</code> otherwise.
	 */
	public boolean hasMoreElements() {
		return (iterator.hasNext());
	}

	/**
	 * Returns the next element of this enumeration.
	 * 
	 * @return the next element of this enumeration
	 * 
	 * @exception NoSuchElementException
	 *                if no more elements exist
	 */
	public T nextElement() throws NoSuchElementException {
		return (iterator.next());
	}

}
