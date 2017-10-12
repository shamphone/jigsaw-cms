package com.fulong.longcon.crawler.queue;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 使用内存来管理的队列，在待转换页面数量超过65535的时候可能会出错。
 * 
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
public class BasicQueue implements Queue<Object> {
	private static Log log = LogFactory.getLog(BasicQueue.class);
	/**
	 * 已处理
	 */
	private List<Object> ready = null;
	/**
	 * 未处理
	 */
	private List<Object> pending = null;

	public BasicQueue() {
		ready = new Vector<Object>();
		pending = new Vector<Object>();
	}

	public void clear() { // 清空堆栈内容
		ready.clear();
		pending.clear();
	}

	public boolean isEmpty() {
		return this.pending.isEmpty();
	}

	public int size() {
		return this.pending.size();
	}

	public boolean contains(Object o) {
		return this.pending.contains(o) && this.ready.contains(o);
	}

	@SuppressWarnings("unchecked")
	public Iterator iterator() {
		return this.pending.iterator();
	}

	public Object[] toArray() {
		return this.pending.toArray();
	}

	@SuppressWarnings("unchecked")
	public Object[] toArray(Object[] a) {
		return this.pending.toArray(a);
	}

	public synchronized boolean add(Object url) {
		if (this.pending.contains(url) || this.ready.contains(url))
			return false;
		this.pending.add(url);
		log.trace("add url:" + url);
		return true;
	}

	public boolean remove(Object o) {
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings("unchecked")
	public boolean containsAll(Collection c) {
		return this.pending.containsAll(c);
	}

	@SuppressWarnings("unchecked")
	public synchronized boolean addAll(Collection c) {
		boolean result = false;
		for (Iterator iterator = c.iterator(); iterator.hasNext();) {
			if (this.add(iterator.next()))
				result = true;
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public boolean removeAll(Collection c) {
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings("unchecked")
	public boolean retainAll(Collection c) {
		throw new UnsupportedOperationException();
	}

	public boolean equals(Object o) {
		return false;
	}

	public int hashCode() {
		return this.pending.hashCode();
	}

	public boolean offer(Object o) {
		if (!this.add(o))
			throw new IllegalArgumentException("Item " + o + " is existing.");
		return true;
	}

	public Object poll() {
		if (pending.isEmpty()) {
			return null;
		} else {
			Object result = pending.get(0);
			pending.remove(0);
			ready.add(result);
			return result;
		}
	}

	public Object remove() {
		if (pending.isEmpty())
			throw new NoSuchElementException();
		Object result = pending.get(0);
		pending.remove(0);
		ready.add(result);
		return result;

	}

	public Object peek() {
		if (pending.isEmpty())
			return null;
		return pending.get(0);
	}

	public Object element() {
		if (pending.isEmpty())
			throw new NoSuchElementException();
		return pending.get(0);
	}

}
