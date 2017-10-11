/**
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2010-2015
 */
package com.fulong.lucene;

import java.util.Iterator;

/**
 * ConverterFactory
 * 
 * @author <a href="lixf@fulong.com.cn">李雄峰</a>
 * @date 2010-6-24
 */
public abstract class ConverterFactory {
	private static ConverterFactory instance;

	public static ConverterFactory getInstance() {
		return instance;
	}

	public ConverterFactory() {
		instance = this;
	}
	/**
	 * 二进制转化器
	 * @return
	 */
	public abstract Iterator<String> binaryConverterKeys();

	/**
	 * 获取注册的BinaryConverter;
	 * 
	 * @param key
	 * @return
	 */
	public abstract BinaryConverter getBinaryConverter(String key);
	
	
	/**
	 * 所有的节点转化器
	 * @return
	 */
	public abstract Iterator<String> nodeConverterKeys();


	/**
	 * 获取注册的NodeConverter；
	 * 
	 * @param key
	 * @return
	 */
	public abstract NodeConverter getNodeConverter(String key);

}
