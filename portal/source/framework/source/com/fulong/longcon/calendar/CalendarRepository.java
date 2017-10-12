package com.fulong.longcon.calendar;



/**
 * <p>Title: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公司</p>
 *
 * @author <a href='mailto:lixf@fulong.com.cn'>lixf</a>
 * @version 2.0
 */
public interface CalendarRepository {

    /**
     * 标准的假日设置
     * @return Holidays
     */
    public Holidays getStandardHolidays();

    /**
     * 无假日设置
     * @return Holidays
     */
    public Holidays getNoHolidays();
    /**
     * 创建一个新的假日设置。
     * @param parent Holidays
     * @return Holidays
     */
    public Holidays createHolidays(String holidaysID, String displayName, Holidays parent);
    /**
     * 获取指定的假日设置
     * @param ID String
     * @return Holidays
     */
    public Holidays getHolidays(String ID);

    /**
     * 遍历所有的Holidays，不包括Noholidays和standard holidays.
     * @return HolidaysIterator
     */
    public HolidaysIterator iterator();

}
