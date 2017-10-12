package com.fulong.common.cache;

import com.fulong.common.ResourceUtils;
import java.util.Properties;
import org.apache.commons.logging.LogFactory;
import java.util.Collections;
import org.apache.commons.collections.map.LRUMap;
import java.util.Timer;
import java.io.InputStream;
import java.io.IOException;
import java.util.Collection;
import org.apache.commons.logging.Log;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Vector;
import java.util.TimerTask;

/**
 * 支持定期刷新的缓存
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
public class RefreshableCacheFactory extends TimerTask implements CacheFactory, Serializable {
	
	private static final long serialVersionUID = -7344675659243306215L;
	
	private Properties properties;
	private Collection<Cache> caches;
	private int refreshSpan = 300000; // five minutes.
	private static int DEFAULT_SIZE = 128;
	private static final Log log = LogFactory.getLog(BasicCacheFactory.class);

	public RefreshableCacheFactory() {
		this.properties = new Properties();
		this.caches = new Vector<Cache>();
		Timer timer = new Timer("cache factory refresher");
		timer.schedule(this, refreshSpan, this.refreshSpan);
	}

	public RefreshableCacheFactory(InputStream is) throws IOException {
		this.properties = new Properties();
		this.caches = new Vector<Cache>();
		Timer timer = new Timer("cache factory refresher");
		timer.schedule(this, refreshSpan, this.refreshSpan);
		properties.load(is);
	}

	public RefreshableCacheFactory(Properties properties) {
		this.properties.putAll(properties);
	}

	public void setConfig(String config) throws IOException {
		log.info("Load cache setting from file:" + config + ".");
		this.properties.load(ResourceUtils.getResourceAsStream(config));
	}

	public void setRefreshSpan(int span) {
		this.refreshSpan = span;
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
		String strSize = properties.getProperty(clazz.getName());
		int size = DEFAULT_SIZE;
		if (strSize != null) {
			size = Integer.parseInt(strSize);
		}
		log.trace("cache size for " + clazz.getName() + " is " + size + ".");
		Cache cache = new BasicCache(Collections.synchronizedMap(new LRUMap(size)));
		this.caches.add(cache);
		return cache;
	}

	public void run() {
		try {
			Iterator<Cache> iterator = this.caches.iterator();
			while (iterator.hasNext()) {
				Cache cache = (Cache) iterator.next();
				cache.clear();
			}
		} catch (Throwable tr) {
			log.trace("Error in clear cache.", tr);
		}
	}
}
