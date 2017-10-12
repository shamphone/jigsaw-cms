package com.fulong.longcon.calendar;

import java.util.Calendar;

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
public abstract class Holidays {
    /**
     * 无假日设置
     */
    public static final String NO_HOLIDAYS = "no-holidays";
    /**
     * 标准的假日设置
     */
    public static final String STANDARD_HOLIDAYS = "standard-holidays";
    /**
     * 唯一标识
     * @return String
     */
    public abstract String getID();

    /**
     * 获取显示名称
     * @return String
     */
    public String getDisplayName(){
        return "";
    }

    /**
     * 判断指定日期是否是假日
     * @param day Calendar
     * @return boolean
     */
    public abstract boolean isHoliday(Calendar day);

    /**
     * 将指定日期设为假日，如果这个日期本来就是假日，则设置无效
     * @param day Calendar
     */
    public abstract void setHoliday(Calendar day);

    /**
     * 设为工作日。如果这个日期本来就是工作日，则设置无效
     * @param day Calendar
     */
    public abstract void setWorkingDay(Calendar day);

    /**
     * 继承自
     * @return Holidays
     */
    public abstract Holidays getParentHolidays();
    /**
     * 今天
     * @return Calendar
     */
    public Calendar today(){
        Calendar today=Calendar.getInstance();
        today.set(Calendar.HOUR,0 );
        today.set(Calendar.MINUTE,0 );
        today.set(Calendar.SECOND,0 );
        today.set(Calendar.MILLISECOND,0 );
        return today;
    }

    /**
     * 计算在calendar基础上增加workingDay个工作日后的日期
     * @param calendar Calendar
     * @param day int 增加的工作日
     * @return Calendar
     */
    public Calendar add(Calendar calendar, int workingDay) {
        Calendar result=Calendar.getInstance();
        result.setTimeInMillis(calendar.getTimeInMillis());
        for(int i=0;i<workingDay;i++)
        {
            result.add(Calendar.DATE,1);
            while(this.isHoliday(result))
                result.add(Calendar.DATE,1);
        }
        for(int i=0;i>workingDay;i--)
        {
            result.add(Calendar.DATE,-1);
            while(this.isHoliday(result))
                result.add(Calendar.DATE,-1);
        }
        return result;
    }

    /**
     * 两个日期间的工作日天数.如果to小于from，则为负数
     * @param from Calendar
     * @param to Calendar
     * @return int
     */
    public int workingDayBetween(Calendar from, Calendar to) {
        if(from.compareTo(to)<0)
            return this.calcWorkingDayBetween(from,to);
        else
            return -this.calcWorkingDayBetween(to,from);
    }
    /**
     * 计算工作日，begin比to小
     * @param from Calendar
     * @param to Calendar
     * @return int
     */
    private int calcWorkingDayBetween(Calendar from, Calendar to) {
          Calendar end = Calendar.getInstance();
          end.clear();
          end.set(to.get(Calendar.YEAR), to.get(Calendar.MONTH),
                  to.get(Calendar.DATE));
          Calendar begin = Calendar.getInstance();
          begin.setTimeInMillis(from.getTimeInMillis());
          int span = 0;
          while (begin.compareTo(end) < 0) {
              begin.add(Calendar.DATE, 1);
              if (!this.isHoliday(begin))
                  span++;
          }
          return span;
    }
}
