/**
 * 
 */
package com.fulong.common.id;

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
public class FastIDGenerator extends IDGenerator {
	private long count;
	private long seed = 0;
	public FastIDGenerator() {
		this.count = System.currentTimeMillis() * 2 + seed;
	}

	public long getNextID() {
		return count++;
	}
	
	public void setSeed(long seed){
		this.seed = seed;
	}

}
