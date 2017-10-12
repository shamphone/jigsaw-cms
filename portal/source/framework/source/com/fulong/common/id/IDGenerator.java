package com.fulong.common.id;

/**
 * 
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

public abstract class IDGenerator {	
	private static IDGenerator instance = new FastIDGenerator();
	
	public static IDGenerator getInstance(){
		return instance;
	}
	public IDGenerator(){
		instance = this;
	}

   public abstract long getNextID() ;
   
   public String getNextStringID(){
     return ""+(this.getNextID());
   }

}
