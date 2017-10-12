package com.fulong.common.cache;

import java.util.Map;
import java.io.Serializable;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

/**
 * <p>
 * Title: 龙驭问答系统
 * </p>
 * 
 * <p>
 * Description: 龙驭知识管理系统子系统
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术有限公司
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术有限公司
 * </p>
 * 
 * @author lixf
 * @version 1.0
 */
public class BasicCache implements Cache, Serializable {
	
	private static final long serialVersionUID = 2809995592380453994L;
	
	private Map<Object, Object> map;
	private static final Log log = LogFactory.getLog(BasicCache.class);

	public BasicCache(Map<Object, Object> map) {
		this.map = map;
	}

	/**
   *
   */
	public synchronized void clear() {
		this.map.clear();
	}

	/**
	 * 
	 * @param key
	 *            Object
	 * @return Object
	 */
	public Object get(Object key) {
		return this.map.get(key);
	}

	/**
	 * 
	 * @param key
	 *            Object
	 * @param value
	 *            Object
	 */
	public void put(Object key, Object value) {
		this.map.put(key, value);
	}

	/**
	 * 
	 * @param key
	 *            Object
	 */
	public void remove(Object key) {
		this.map.remove(key);
		log.trace("Remove " + key + " from cache,cache size :"
				+ this.map.size() + ".");
	}

	public boolean containsKey(Object key) {
		return this.map.containsKey(key);
	}

	public long getSize() {
		return this.map.size();
	}
}
