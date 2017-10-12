package com.fulong.longcon.calendar.data;

import java.io.*;
import java.util.Date;

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

public class HolidaysDateData implements Serializable {

	private static final long serialVersionUID = -792835662939379035L;
	
	private String holidaysId;
	private Date date;
	private boolean isHoliday;

	public Date getDate() {
		return date;
	}

	public String getHolidaysId() {
		return holidaysId;
	}

	public boolean isIsHoliday() {
		return isHoliday;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setHolidaysId(String holidaysId) {
		this.holidaysId = holidaysId;
	}

	public void setIsHoliday(boolean isHoliday) {
		this.isHoliday = isHoliday;
	}

}
