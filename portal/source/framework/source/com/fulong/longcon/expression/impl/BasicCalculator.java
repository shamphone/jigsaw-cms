/**
 * 
 */
package com.fulong.longcon.expression.impl;

import com.fulong.longcon.expression.Calculator;
import com.fulong.longcon.repository.Repository;

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
public abstract class BasicCalculator implements Calculator {
	protected Repository repository;
	private String name;
	public String getName() {
		return name;
	}
	public Repository getRepository() {
		return repository;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setRepository(Repository repository){
		this.repository= repository;
	}

}
