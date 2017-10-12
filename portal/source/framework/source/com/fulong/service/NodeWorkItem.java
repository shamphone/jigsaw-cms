/**
 * 
 */
package com.fulong.service;

import javax.servlet.http.HttpServletRequest;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.workflow.WorkItem;

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
public interface NodeWorkItem extends WorkItem {
	
	public Node getNode();
	
	public HttpServletRequest getHttpRequest();

}
