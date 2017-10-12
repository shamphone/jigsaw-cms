package com.fulong.longcon.calendar.dao;

import com.fulong.common.dao.Dao;
import java.sql.SQLException;
import com.fulong.longcon.calendar.data.HolidaysData;

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

public interface HolidaysDao extends Dao {
	/**
	 * 
	 * @param ID
	 *            String
	 * @return HolidaysData
	 * @throws SQLException
	 */
	public HolidaysData findByID(String ID) throws SQLException;

	/**
	 * 
	 * @return HolidaysData[]
	 * @throws SQLException
	 */
	public HolidaysData[] findAll() throws SQLException;

	/**
	 * 
	 * @param data
	 *            HolidaysData
	 * @throws SQLException
	 */
	public void insert(HolidaysData data) throws SQLException;

	/**
	 * 
	 * @param data
	 *            HolidaysData
	 * @throws SQLException
	 */
	public void update(HolidaysData data) throws SQLException;

	/**
	 * 
	 * @param ID
	 *            String
	 * @throws SQLException
	 */
	public void delete(String ID) throws SQLException;

}
