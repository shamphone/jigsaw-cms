/**
 * 
 */
package com.fulong.longcon.expression.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fulong.longcon.repository.Node;
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
public class CurrentUserCalculator extends BasicCalculator {

	public Value calc(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/**
		 * modified by liuzijun in 2010-8-11
		 * if currentUser is null then it return null;
		 */
		if(request.getUserPrincipal()==null){
			return null;
		}else{
			return this.repository.getValueFactory().createValue((Node)request.getUserPrincipal());
		}		
	}
}
