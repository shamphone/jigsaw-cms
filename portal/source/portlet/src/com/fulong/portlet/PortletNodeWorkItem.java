/**
 * 
 */
package com.fulong.portlet;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fulong.longcon.repository.Node;

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
public class PortletNodeWorkItem extends ServletNodeWorkItem{
	public PortletNodeWorkItem(Node node, PortletRequest request, PortletResponse response){
		super(node, (HttpServletRequest)request, (HttpServletResponse)response);
	}
}
