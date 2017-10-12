package com.fulong.common.cache;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.collections.map.LRUMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.fulong.common.ResourceUtils;

/**
 * 基本缓存库。使用“LRU”，最近最久未用的算法来管理缓存的对象。
 * 使用这种缓存时，可以通过properties文件来配置需要缓存的各个对象的个数，类似apache的commons logging。
 * 如果未配置，则按照缺省个数(32)来管理缓存。
 * 
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
public class BasicCacheFactory implements CacheFactory, Serializable {

	private static final long serialVersionUID = 8918637506125415181L;

	@SuppressWarnings("unchecked")
	private Map<Class, Cache> caches;
	private Properties properties;
	private static int DEFAULT_SIZE = 32;
	private static final Log log = LogFactory.getLog(BasicCacheFactory.class);

	@SuppressWarnings("unchecked")
	public BasicCacheFactory() {
		this.properties = new Properties();
		this.caches = new Hashtable<Class, Cache>();
	}

	@SuppressWarnings("unchecked")
	public BasicCacheFactory(InputStream is) throws IOException {
		this.properties = new Properties();
		this.caches = new Hashtable<Class, Cache>();
		properties.load(is);
	}

	@SuppressWarnings("unchecked")
	public BasicCacheFactory(Properties properties) {
		this.properties.putAll(properties);
		this.caches = new Hashtable<Class, Cache>();
	}

	public void setConfig(String config) throws IOException {
		log.info("Load cache setting from file:" + config + ".");
		this.properties.load(ResourceUtils.getResourceAsStream(config));
	}

	/**
	 * getCache
	 * 
	 * @param clazz
	 *            Class
	 * @return Map
	 * @todo Implement this com.fulong.common.cache.CacheFactory method
	 */
	@SuppressWarnings("unchecked")
	public Cache getCache(Class clazz) {
		Cache cache = (Cache) this.caches.get(clazz);
		if (cache != null)
			return cache;
		String strSize = properties.getProperty(clazz.getName());
		int size = DEFAULT_SIZE;
		if (strSize != null) {
			size = Integer.parseInt(strSize);
		}
		log.trace("cache size for " + clazz.getName() + " is " + size + ".");
		cache = new BasicCache(Collections.synchronizedMap(new LRUMap(size)));
		this.caches.put(clazz, cache);
		return cache;
	}

	@SuppressWarnings("unchecked")
	public Iterator<Class> getCaches() {
		return this.caches.keySet().iterator();
	}

}
