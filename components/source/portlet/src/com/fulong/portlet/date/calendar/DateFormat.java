package com.fulong.portlet.date.calendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletException;

import com.fulong.portlet.LongconPortlet;

/**
 * 日期域占位符
 * 
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
 * @author lixiang
 * @version 3.1
 */
public class DateFormat extends LongconPortlet {

	private static final long serialVersionUID = 8273754732052367161L;

	public void initPortlet() throws PortletException {
		String separator = ",";
		String dateFormat = this.portletConfig.getInitParameter("dateFormat");
		String dateFormatKeys[] = dateFormat.split(separator);
		Map<String, String> dateFormats = buildFormatsMap(dateFormatKeys);
		this.getPortletContext().setAttribute("dFormatKeys", dateFormatKeys);
		this.getPortletContext().setAttribute("dFormats", dateFormats);
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
