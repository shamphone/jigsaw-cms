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
public class CurrentNodeCalculator extends BasicCalculator {
	private static final String REQUEST_CONTENT="com.fulong.longcon.Content";

	public Value calc(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Node node = null;
		// 获取内容对象.如果定义了内容库,则从preferences中获取当前内容，否则从request中获取
		node = (Node) request.getAttribute(REQUEST_CONTENT);
		if (node == null)
			node = this.getRepository().getNode(request.getParameter("contentId"));
		// 保存到页面的request而不是占位符的request中
		/**
		 * modified by liuzijun in 2010-8-11
		 * if node is null then it return null;
		 */
		if(node==null){
			return null;
		}else{
			return this.getRepository().getValueFactory().createValue(node);
		}		
	}

}
