package com.fulong.longcon.calendar.dao;

import java.sql.SQLException;
import java.util.Date;

import com.fulong.common.dao.Dao;
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

public interface HolidaysDateDao extends Dao {
	/**
	 * 
	 * @param holidaysId
	 *            String
	 * @param date
	 *            Date
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean isHoliday(String holidaysId, Date date) throws SQLException;

	/**
	 * 
	 * @param holidaysId
	 *            String
	 * @param date
	 *            Date
	 * @return HolidaysDateData
	 * @throws SQLException
	 */
	public HolidaysDateData getHolidayDate(String holidaysId, Date date)
			throws SQLException;

	/**
	 * 
	 * @param data
	 *            HolidaysDateData
	 * @throws SQLException
	 */
	public void insert(HolidaysDateData data) throws SQLException;

	/**
	 * 
	 * @param data
	 *            HolidaysDateData
	 * @throws SQLException
	 */
	public void update(HolidaysDateData data) throws SQLException;

	/**
	 * 
	 * @param holidaysId
	 *            String
	 * @param date
	 *            Date
	 * @throws SQLException
	 */
	public void delete(String holidaysId, Date date) throws SQLException;

}
