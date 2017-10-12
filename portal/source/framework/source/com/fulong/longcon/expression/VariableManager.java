/**
 * 
 */
package com.fulong.longcon.expression;


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
public interface VariableManager {
	/**
	 * 获取某变量计算器
	 * @param name
	 * @return
	 */
	public Calculator getCalculator(String variable);
	/**
	 * 所有变量名称
	 * @return
	 */
	public String[] getVariables();
	
	/**
	 * 显示名称
	 * @param variable
	 * @return
	 */
	public String getDisplayName(String variable);

}
