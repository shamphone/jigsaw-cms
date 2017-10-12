package com.fulong.portal.model;

import javax.portlet.Portlet;
import javax.portlet.PortletConfig;

/**
 * 具体页面上的运行中的占位符实例
 * <p>
 * Title: WebMaster Core Library
 * </p>
 * 
 * <p>
 * Description: Longcon WebMaster SV3
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Beijing Zhongke Fulong Computer Tech. LTD
 * </p>
 * 
 * @author lixf
 * @version 1.0
 */
public interface PortletWindow {
	/**
	 * 唯一标识符
	 * 
	 * @return String
	 */
	public String getId();

	/**
	 * 占位符的类型
	 * 
	 * @return String
	 */
	public String getType();

	/**
	 * 标题
	 * 
	 * @return String
	 */
	public String getTitle();

	/**
	 * 描述
	 * 
	 * @return String
	 */
	public String getDescription();

	/**
	 * 模板中的PreferenceSet
	 * 
	 * @return PreferenceSet
	 */
	public PreferenceSet getTemplatePreferenceSet();

	/**
	 * 
	 * @param set
	 *            PreferenceSet
	 * @return PreferenceSet
	 */
	public void setTemplatePreferenceSet(PreferenceSet set);

	/**
	 * 占位符的定义
	 * 
	 * @return PortletDefinition
	 */
	public PortletDefinition getDefinition();

	/**
	 * 修改标题
	 * 
	 * @param title
	 *            String
	 */
	public void setTitle(String title);

	/**
	 * 对应的占位符
	 * 
	 * @return Portlet
	 */
	public Portlet getPortlet();

	/**
	 * 占位符配置
	 * 
	 * @return PortletConfig
	 */
	public PortletConfig getPortletConfig();

	/**
	 * 转换成XML字符串
	 * 
	 * @return String
	 */
	public String toXML();

	/**
	 * 转换成HTML字符串
	 * 
	 * @return String
	 */
	public String toHTML();

}
