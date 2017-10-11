package com.fulong.portlet.field.string;

import com.fulong.portlet.LongconPortlet;

/**
 * 
 * 内容域占位符。这个占位符可以装配在内容页或者其他页面。 如果装配在其他页面，则需要用户指定内容库、内容、属性
 * 如果装配在内容页，从request中获取当前请求的内容的ID，获取内容中指定的属性的值，并渲染这个值。 占位符的配置参数包括：
 * <ul>
 * <li>repository-id，可选，内容库，如果不是装配在内容页则需要指定内容库和内容
 * <li>content-id，可选，内容，如果不是装配在内容页则需要指定内容库和内容
 * <li>field，可选，属性
 * <li>format，可选：显示格式
 * <li>expression，可选:表达式
 * <ul>
 * 用户可以为这个占位符指定属性和显示格式，系统根据这个属性和显示格式来渲染属性值。此外，用户还可以 通过表达式来定义要显示的内容。系统对表达式作数据绑定.
 * 未解决的问题：对于页面片断，只能直接将内容写到页面上。
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
 * @author Lixf
 * @version 1.0
 */
public class ContentField extends LongconPortlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1761673472009269990L;

}
