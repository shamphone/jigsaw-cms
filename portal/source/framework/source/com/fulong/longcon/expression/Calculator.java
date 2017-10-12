/**
 * 
 */
package com.fulong.longcon.expression;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fulong.longcon.repository.Value;

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
public interface Calculator {
	public Value calc(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
