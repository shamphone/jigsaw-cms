package com.fulong.common;

import java.util.Calendar;
import java.util.Date;

/**
 * <p>
 * Title: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Description: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术有限公司 2006
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术有限公司
 * </p>
 * 
 * @author <a href='mailto:lixf@fulong.com.cn'>lixf</a>
 * @version 2.0
 */
public class Duration {
	private int count;
	private char field;
	private static char[] fields = { 'e', // Calendar.ERA
			'y', // Calendar.Year
			'm',// Calendar.MONTH
			'w',// Calendar.WEEK_OF_YEAR
			' ',//
			'd',// Calendar.DATE
	};
	public static char EAR = fields[Calendar.ERA];
	public static char YEAR = fields[Calendar.YEAR];
	public static char MONTH = fields[Calendar.MONTH];
	public static char WEEK = fields[Calendar.WEEK_OF_YEAR];
	public static char DATE = fields[Calendar.DATE];

	public Duration(String expression) {
		this.count = Integer.parseInt(expression.substring(0, expression
				.length() - 1));
		this.field = expression.charAt(expression.length() - 1);
	}

	public Duration(int count, char field) {
		this.count = count;
		this.field = field;
	}

	public Duration(int count, String field) {
		this.count = count;
		this.field = field.charAt(0);
	}

	/**
	 * 延迟期限
	 * 
	 * @param source
	 *            Date
	 * @return Date
	 */
	public Date prolong(Date source) {
		if (source == null)
			source = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(source);
		if (this.field == 'y')
			cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + this.count);
		else if (this.field == 'm')
			cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + this.count);
		else if (this.field == 'd')
			cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)
					+ this.count);
		return cal.getTime();
	}

	public String toString() {
		return "" + this.count + this.field;
	}

	public int getCount() {
		return this.count;
	}

	public String getField() {
		return this.field + "";
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof Duration))
			return false;
		else {
			return (((Duration) obj).getCount() == this.getCount())
					&& (((Duration) obj).getField().equals(this.getField()));
		}
	}

}
