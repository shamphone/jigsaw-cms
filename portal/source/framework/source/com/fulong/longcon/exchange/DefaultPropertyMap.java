/**
 * 
 */
package com.fulong.longcon.exchange;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 *   
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
public class DefaultPropertyMap implements PropertyMap{
	private Map<String, String> values; //映射到值
	private Map<String, String> properties;//映射到属性
	private List<String> keys;//有序的键值
	/**
	 * 目标属性=源属性，或者目标属性="常量"形式的数组；
	 * @param maps
	 */
	public DefaultPropertyMap(String[] maps){
		this.values = new HashMap<String,String>();
		this.properties= new HashMap<String,String>();
		this.keys = new Vector<String>();
		if(maps == null)
			return;
		for(int i=0;i<maps.length;i++){
			String[] splits = maps[i].split("\\=");
			if(splits.length==1){
				this.keys.add(splits[0]);
				this.values.put(splits[0], "");
			}else if(splits.length==2){
				this.keys.add(splits[0]);
				if(splits[1].charAt(0)=='\"')
					this.values.put(splits[0], splits[1].substring(1, splits[1].length()-1));
				else
					this.properties.put(splits[0], splits[1]);					
			}
		}
	}
	
	/**
	 * 被映射的属性集合
	 */
	public Iterator<String> keys(){
		return keys.iterator();
	}
	/**
	 * 是否映射到值
	 */
	public boolean mapToValue(String key){
		return this.values.containsKey(key);
	}
	/**
	 * 对应的映射值
	 */
	public String getMappedValue(String key){
		return this.values.get(key);
	}
	/**
	 * 对应的映射属性
	 */
	public String getMappedProperty(String key){
		return this.properties.get(key);
	}
	
	/* 
	 * 获取映射的源属性
	 * @param sourceProperty
	 * @return
	 * @author lixf
	 * @lastupdate 2009-10-25下午02:17:00
	 */
	public String getKey(String sourceProperty){
		for(String key:this.properties.keySet()){
			if(this.properties.get(key).equals(sourceProperty))
				return key;
		}
		return null;
	}

	public void addPropertyMap(String key, String property) {
		this.properties.put(key, property);
		
	}

	public void addValueMap(String key, String value) {
		if(value == null)
			value ="";
		this.values.put(key, value);
		
	}

}
