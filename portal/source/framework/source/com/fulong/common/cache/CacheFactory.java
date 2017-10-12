package com.fulong.common.cache;

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
public interface CacheFactory {
	
	@SuppressWarnings("unchecked")
	public Cache getCache(Class clazz);
}
