/**
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2010-2015
 */
package com.fulong.lucene.core;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import com.fulong.lucene.BinaryConverter;
import com.fulong.lucene.ConverterFactory;
import com.fulong.lucene.NodeConverter;

/**
 * BaseConverterFactory
 * 
 * @author <a href="lixf@fulong.com.cn">李雄峰</a>
 * @date 2010-6-24
 */
public class BaseConverterFactory extends ConverterFactory {
	private Map<String, BinaryConverter> binaryConverters;
	private Map<String, NodeConverter> nodeConverters;

	public BaseConverterFactory() {
		super();
		this.binaryConverters = new Hashtable<String, BinaryConverter>();
		this.nodeConverters = new Hashtable<String, NodeConverter>();
	}

	public void setBinaryConverters(Map<String, BinaryConverter> converters) {
		this.binaryConverters = converters;
	}

	public void setNodeConverters(Map<String, NodeConverter> converters) {
		this.nodeConverters = converters;
	}

	@Override
	public Iterator<String> binaryConverterKeys() {
		return this.binaryConverters.keySet().iterator();
	}

	@Override
	public BinaryConverter getBinaryConverter(String key) {
		return this.binaryConverters.get(key);
	}

	@Override
	public NodeConverter getNodeConverter(String key) {
		return this.nodeConverters.get(key);
	}

	@Override
	public Iterator<String> nodeConverterKeys() {
		return this.nodeConverters.keySet().iterator();
	}

}
