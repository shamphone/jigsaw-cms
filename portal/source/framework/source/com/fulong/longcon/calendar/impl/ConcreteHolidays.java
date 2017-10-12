package com.fulong.longcon.calendar.impl;

import java.sql.SQLException;
import java.util.Calendar;

import com.fulong.common.dao.DaoFactory;
import com.fulong.common.dao.DatabaseException;
import com.fulong.longcon.calendar.Holidays;
import com.fulong.longcon.calendar.dao.HolidaysDateDao;
import com.fulong.longcon.calendar.data.HolidaysData;
import com.fulong.longcon.calendar.data.HolidaysDateData;

/**
 * 
 * <p>
 * Title: 日历系统
 * </p>
 * 
 * <p>
 * Description: 日历系统
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术有限公司
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术有限公司
 * </p>
 * 
 * @author lishaobo
 * @version 2.0
 */

public class ConcreteHolidays extends Holidays {
	private CalendarRepositoryImpl repository;
	private HolidaysData data;

	public ConcreteHolidays(CalendarRepositoryImpl repository, HolidaysData data) {
		this.repository = repository;
		this.data = data;
	}

	/**
	 * 
	 * @param day
	 *            Calendar
	 * @return boolean
	 */
	public boolean isHoliday(Calendar day) {
		DaoFactory factory = this.repository.newDaoFactory();
		try {
			factory.open();
			HolidaysDateDao dao = (HolidaysDateDao) factory
					.getDao(HolidaysDateDao.class);
			HolidaysDateData data = dao.getHolidayDate(this.getID(), day
					.getTime());
			if (data == null) {
				if (this.getParentHolidays() != null)
					return this.getParentHolidays().isHoliday(day);
				else {
					return false;
				}
			}
			return data.isIsHoliday();
		} catch (SQLException se) {
			factory.rollback();
			throw new DatabaseException(se);
		} finally {
			factory.close();
		}
	}

	/**
	 * 无作用
	 * 
	 * @param day
	 *            Calendar
	 */
	public void setHoliday(Calendar day) {
		DaoFactory factory = this.repository.newDaoFactory();
		try {
			factory.open();
			HolidaysDateDao dao = (HolidaysDateDao) factory
					.getDao(HolidaysDateDao.class);
			HolidaysDateData data = dao.getHolidayDate(this.getID(), day
					.getTime());
			if (data == null) {
				data = new HolidaysDateData();
				data.setHolidaysId(this.getID());
				data.setDate(day.getTime());
				data.setIsHoliday(true);
				dao.insert(data);
			} else {
				if (data.isIsHoliday())
					return;
				else {
					data.setIsHoliday(true);
					dao.update(data);
				}
			}
		} catch (SQLException se) {
			factory.rollback();
			throw new DatabaseException(se);
		} finally {
			factory.close();
		}

	}

	/**
	 * 无作用
	 * 
	 * @param day
	 *            Calendar
	 */
	public void setWorkingDay(Calendar day) {
		DaoFactory factory = this.repository.newDaoFactory();
		try {
			factory.open();
			HolidaysDateDao dao = (HolidaysDateDao) factory
					.getDao(HolidaysDateDao.class);
			HolidaysDateData data = dao.getHolidayDate(this.getID(), day
					.getTime());
			if (data == null) {
				data = new HolidaysDateData();
				data.setHolidaysId(this.getID());
				data.setDate(day.getTime());
				data.setIsHoliday(false);
				dao.insert(data);
			} else {
				if (data.isIsHoliday()) {
					data.setIsHoliday(false);
					dao.update(data);
				}
			}
		} catch (SQLException se) {
			factory.rollback();
			throw new DatabaseException(se);
		} finally {
			factory.close();
		}

	}

	public Holidays getParentHolidays() {
		if (this.getID().equalsIgnoreCase(NO_HOLIDAYS))
			return null;
		return this.repository.getHolidays(this.data.getParentId());
	}

	public String getID() {
		return this.data.getId();
	}

	public String getDisplayName() {
		return this.data.getDisplayName();
	}
}
