/**
 * 
 */
package com.fulong.longcon.workflow;

import java.util.Enumeration;

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
public interface Parameters {
	/**
	 * 清除所有的值
	 */
	public void clear();
	/**
	 * 所有变量名
	 * @return
	 */
	public Enumeration<String> getNames();
	/**
	 * 获取服务参数
	 * @param name
	 * @return
	 */
	public String getValue(String name);
	/**
	 * 获取多值参数
	 * @param name
	 * @return
	 */
	public String[] getValues(String name);
	
	/**
	 * 设置参数，单值
	 * @param name
	 * @param value
	 */
	public void setValue(String name, String value);
	/**
	 * 设置参数，单值
	 * @param name
	 * @param value
	 */
	public void setValues(String name, String[] value);
}
