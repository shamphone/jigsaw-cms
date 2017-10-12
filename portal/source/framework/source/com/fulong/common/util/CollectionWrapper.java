package com.fulong.common.util;

import java.util.Collection;
import java.util.Iterator;

/**
 * <p>
 * Title: 龙驭门户引擎
 * </p>
 * 
 * <p>
 * Description: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * @author 李雄锋
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class CollectionWrapper implements Collection {
	private Collection collection;

	public CollectionWrapper(Collection collection) {
		this.collection = collection;
	}

	public CollectionWrapper() {

	}

	public void setCollection(Collection collection) {
		this.collection = collection;
	}

	public int size() {
		return this.collection.size();
	}

	public boolean isEmpty() {
		return this.collection.isEmpty();
	}

	public boolean contains(Object o) {
		return this.collection.contains(o);
	}

	public Object[] toArray() {
		return this.collection.toArray();
	}

	public Object[] toArray(Object[] a) {
		return this.collection.toArray(a);
	}

	public boolean containsAll(Collection c) {
		return this.collection.containsAll(c);
	}

	public boolean addAll(Collection c) {
		for (Iterator iterator = c.iterator(); iterator.hasNext();) {

			this.add(iterator.next());
		}
		return c.size() > 0;
	}

	public boolean removeAll(Collection c) {
		for (Iterator iterator = c.iterator(); iterator.hasNext();) {
			this.remove(iterator.next());
		}
		return c.size() > 0;

	}

	public Iterator iterator() {
		return this.collection.iterator();
	}

	public boolean retainAll(Collection c) {
		throw new UnsupportedOperationException();
	}

	public boolean equals(Object o) {
		return this.collection.equals(o);
	}

	public int hashCode() {
		return this.collection.hashCode();
	}

	public boolean add(Object o) {
		return false;
	}

	public boolean remove(Object o) {
		return false;
	}

	public void clear() {
		this.collection.clear();
	}

}
