/**
 * 
 */
package com.fulong.longcon.exchange;

import java.util.Iterator;

/**
 * 源和目标之间的属性映射 
 * 
 * Coolink协同工作框架模型 
 *
 * Copyright: 2009-2011 北京中科辅龙计算机技术股份有限公司
 *
 * Company: 北京中科辅龙计算机技术股份有限公司
 *
 * @author lixf
 *
 * @version 2.0
 *
 */
public interface PropertyMap {
	/**
	 * 被映射的属性集合
	 * 
	 * @return
	 * @author lixf
	 * @lastupdate 2009-10-25下午02:17:50
	 */
	public Iterator<String> keys();
	/**
	 * 是否映射到值，
	 * 
	 * @param key
	 * @return true:映射到值；false映射到属性
	 * @author lixf
	 * @lastupdate 2009-10-25下午02:17:59
	 */
	public boolean mapToValue(String key);
	/**
	 * 对应的映射值
	 * 
	 * @param key
	 * @return
	 * @author lixf
	 * @lastupdate 2009-10-25下午02:18:28
	 */
	public String getMappedValue(String key);
	/**
	 * 对应的映射属性
	 * 
	 * @param key
	 * @return
	 * @author lixf
	 * @lastupdate 2009-10-25下午02:18:36
	 */
	public String getMappedProperty(String key);
	/* 
	 * 获取映射的源属性
	 * @param sourceProperty
	 * @return
	 * @author lixf
	 * @lastupdate 2009-10-25下午02:17:28
	 */
	public String getKey(String sourceProperty);
	/**
	 * 添加值映射
	 * 
	 * @param key
	 * @param value
	 * @author lixf
	 * @lastupdate 2009-10-25下午02:18:47
	 */
	public void addValueMap(String key, String value);
	/**
	 * 添加属性映射
	 * 
	 * @param key
	 * @param property
	 * @author lixf
	 * @lastupdate 2009-10-25下午02:18:57
	 */
	public void addPropertyMap(String key, String property);

}
