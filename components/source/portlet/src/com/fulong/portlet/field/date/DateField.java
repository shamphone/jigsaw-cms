package com.fulong.portlet.field.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletException;

import com.fulong.portlet.LongconPortlet;

/**
 * 日期内容域占位符
 * 
 * 内容域占位符。这个占位符可以装配在内容页或者其他页面。 如果装配在其他页面，则需要用户指定内容库、内容、属性
 * 如果装配在内容页，从request中获取当前请求的内容的ID，获取内容中指定的属性的值，并渲染这个值。 占位符的配置参数包括：
 * <ul>
 * <li>repository-id，可选，内容库，如果不是装配在内容页则需要指定内容库和内容
 * <li>content-id，可选，内容，如果不是装配在内容页则需要指定内容库和内容
 * <li>field，可选，属性
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
 * @author lichengzhao
 * @version 3.1
 */
public class DateField extends LongconPortlet {
	
	private static final long serialVersionUID = 8693015118980925339L;

	public void initPortlet() throws PortletException {
		String separator = ",";
		String dateFormat = this.portletConfig.getInitParameter("dateFormat");
		String timeFormat = this.portletConfig.getInitParameter("timeFormat");
		String dateFormatKeys[] = dateFormat.split(separator);
		String timeFormatKeys[] = timeFormat.split(separator);
		Map<String, String> dateFormats = this.buildFormatsMap(dateFormatKeys);
		Map<String, String> timeFormats = this.buildFormatsMap(timeFormatKeys);
		this.getPortletContext().setAttribute("dateFormatKeys", dateFormatKeys);
		this.getPortletContext().setAttribute("timeFormatKeys", timeFormatKeys);
		this.getPortletContext().setAttribute("dateFormats", dateFormats);
		this.getPortletContext().setAttribute("timeFormats", timeFormats);
	}

	protected final Map<String, String> buildFormatsMap(String[] formatKeys) {
		Calendar c = Calendar.getInstance();
		c.set(2000, Calendar.JANUARY, 5);
		Date time = c.getTime();
		Map<String, String> formats = new HashMap<String, String>();
		for (int i = 0; i < formatKeys.length; i++)
			formats.put(formatKeys[i], new SimpleDateFormat(formatKeys[i]).format(time));
		
		return formats;
	}
}
