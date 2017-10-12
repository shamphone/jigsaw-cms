package com.fulong.common.util;

import java.util.LinkedHashMap;

/**
 * <p>
 * Title: WebMaster Core Library
 * </p>
 * 
 * <p>
 * Description: Longcon WebMaster SV3
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Beijing Zhongke Fulong Computer Tech. LTD
 * </p>
 * 
 * @author Lixf
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class NoCaseMap extends LinkedHashMap {
	
	private static final long serialVersionUID = -4674751054427273476L;

	public boolean containsKey(Object key) {
		return super.containsKey(key.toString().toLowerCase());
	}

	public Object put(Object key, Object value) {
		return super.put(((String) key).toLowerCase(), value);
	}

	public Object get(Object key) {
		return super.get(((String) key).toLowerCase());
	}

	public Object remove(Object key) {
		return super.remove(((String) key).toLowerCase());
	}

}
