package com.fulong.longcon.calendar.impl;

import java.util.Calendar;
import com.fulong.longcon.calendar.Holidays;

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
public class NoHolidays extends Holidays {

	/**
	 * 
	 * @param day
	 *            Calendar
	 * @return boolean
	 */
	public boolean isHoliday(Calendar day) {
		return false;
	}

	/**
	 * 无作用
	 * 
	 * @param day
	 *            Calendar
	 */
	public void setHoliday(Calendar day) {
	}

	/**
	 * 无作用
	 * 
	 * @param day
	 *            Calendar
	 */
	public void setWorkingDay(Calendar day) {
	}

	public Holidays getParentHolidays() {
		return null;
	}

	public String getID() {
		return NO_HOLIDAYS;
	}

	public String getDisplayName() {
		return "";
	}
}
