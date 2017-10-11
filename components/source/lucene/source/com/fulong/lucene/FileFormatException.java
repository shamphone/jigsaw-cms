package com.fulong.lucene;

/**
 * < >
 * 
 * < Title: Coolink协同工作支撑平台Lucene全文检索引擎 >
 * 
 * < Description: Coolink协同工作支撑平台Lucene全文检索引擎 >
 * 
 * < Copyright: 北京中科辅龙计算机技术股份有限公司 2010 >
 * 
 * < Company: 北京中科辅龙计算机技术股份有限公司 >
 * 
 * @author LJY
 * @version 1.0.1
 */
public class FileFormatException extends java.io.IOException {
	
	private static final long serialVersionUID = 4454471776399329847L;

	public FileFormatException() {
		super();
	}

	public FileFormatException(String mess) {
		super(mess);
	}
	
	public FileFormatException(Throwable e) {
		super(e);
	}
	
	
}