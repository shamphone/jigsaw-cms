package com.fulong.update.portlet;


/**
 * <p>Title: Coolink平台协同管理系统</p>
 *
 * <p>Description: Coolink平台协同管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2010</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author luobin
 * @date 2010-10-8	
 * @version 1.0.1
 */
public interface PortletElement {
	
	public String getType();
	
	public String accept(Visitor visitor) throws Exception;
}
