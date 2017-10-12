package com.fulong.longcon.calendar.impl;

import java.util.Calendar;

import com.fulong.longcon.calendar.Holidays;
import com.fulong.longcon.calendar.CalendarRepository;

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
public class StandardHolidays extends Holidays {
	private CalendarRepository repository;

	public StandardHolidays(CalendarRepository repository) {
		this.repository = repository;
	}

	/**
	 * 
	 * @param day
	 *            Calendar
	 * @return boolean
	 */
	public boolean isHoliday(Calendar day) {
		int date = day.get(Calendar.DAY_OF_WEEK);
		return date == Calendar.SUNDAY || date == Calendar.SATURDAY;
	}

	/**
	 * 
	 * @param day
	 *            Calendar
	 */
	public void setHoliday(Calendar day) {

	}

	/**
	 * 
	 * @param day
	 *            Calendar
	 */
	public void setWorkingDay(Calendar day) {
	}

	/**
	 * 父节点
	 * 
	 * @return Holidays
	 */
	public Holidays getParentHolidays() {
		return this.repository.getHolidays(Holidays.NO_HOLIDAYS);
	}

	public String getID() {
		return STANDARD_HOLIDAYS;
	}

	public String getDisplayName() {
		return "StandardHolidays";
	}
}
