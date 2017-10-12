package com.fulong.longcon.counter;

import java.util.Calendar;

/**
 *
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
public interface AccessCounterRepository {
	  /**
     * 增加计数值
     */
    public void increase(String name);
    /**
     * 获取当前计数
     * @return long
     */
    public long getCount(String name);

    /**
     * 获取日访问量
     * @return long
     */
    public long getDayCount(String name);
    /**
     * 获取指定类型的访问量
     * @param name 对象名称
     * @param date  日期
     * @param type  参见Calendar的常量，可以是EAR\YEAR\MONTH\WEEK\DAY,其中ERA表示总计数
     * @return
     */
    public long getCount(String name, Calendar date, int type);
    /**
     * 获取指定类型的当前访问量， 相当于 getCount(name, Calendar.getInstance(), type);
     * @param name 对象名称
     * @param type  参见Calendar的常量，可以是EAR\YEAR\MONTH\WEEK\DAY,其中ERA表示总计数
     * @return
     */
    public long getCount(String name, int type);
}
