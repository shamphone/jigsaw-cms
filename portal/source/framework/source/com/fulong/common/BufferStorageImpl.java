package com.fulong.common;

import java.util.Hashtable;
import java.io.Serializable;

public class BufferStorageImpl implements BufferStorage, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 238641426867710924L;
	private Hashtable<String, Object> buffer;
	private int size;

	public BufferStorageImpl() {
		size = 100;
		buffer = new Hashtable<String, Object>(100);
	}

	public BufferStorageImpl(int size) {
		this.size = size;
		buffer = new Hashtable<String, Object>(size);
	}

	/**
	 * add
	 * 
	 * @param obj
	 *            Object
	 * @todo Implement this com.fulong.common.BufferStorage method
	 */
	public void add(String key, Object obj) {
		if (buffer.size() >= size) {
			this.delete();
		}
		buffer.put(key, obj);
	}

	/**
	 * delete
	 */
	private void delete() {
		buffer.remove(buffer.keys().nextElement());
	}

	/**
	 * lookup
	 * 
	 * @param key
	 *            String
	 * @return Object
	 * @todo Implement this com.fulong.common.BufferStorage method
	 */
	public Object lookup(String key) {
		return buffer.get(key);
	}

	public int getSize() {
		return buffer.size();
	}

	public void clear() {
		while (!buffer.isEmpty()) {
			this.delete();
		}
	}
}
