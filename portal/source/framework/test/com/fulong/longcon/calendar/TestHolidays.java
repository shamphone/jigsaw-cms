package com.fulong.longcon.calendar;

import com.fulong.longcon.BasicTestCase;
import java.util.Calendar;
import com.fulong.longcon.repository.RepositoryTestCase;

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
public class TestHolidays
    extends RepositoryTestCase {
    private CalendarRepository calendars;
    protected void setUp() throws Exception {
        super.setUp();
        this.calendars = (CalendarRepository)this.beanFactory.getBean(
            "calendarRepository");
    }

    public void testStandardHolidays() throws Exception {
        Holidays holidays = this.calendars.getStandardHolidays();
        Calendar d20070701 = Calendar.getInstance();
        d20070701.set(2007, 6, 1); //2007.7.1
        Calendar d20070630 = Calendar.getInstance();
        d20070630.set(2007, 5, 30); //2007.6.30
        Calendar d20070730 = Calendar.getInstance();
        d20070730.set(2007, 6, 30); //2007.7.30
        Calendar d20070625 = Calendar.getInstance();
        d20070625.set(2007, 5, 25); //2007.5.25
        Calendar d20070626 = Calendar.getInstance();
        d20070626.set(2007, 5, 26); //2007.5.25
        this.assertTrue(holidays.isHoliday(d20070701));
        this.assertTrue(holidays.isHoliday(d20070630));
        this.assertEquals(holidays.workingDayBetween(d20070701, d20070630), 0);
        this.assertEquals(holidays.workingDayBetween(d20070630, d20070701), 0);
        this.assertEquals(holidays.workingDayBetween(d20070730, d20070630), -21);
        this.assertEquals(holidays.workingDayBetween(d20070630, d20070730), 21);
        this.assertEquals(holidays.workingDayBetween(d20070625, d20070626), 1);
        this.assertEquals(holidays.workingDayBetween(d20070626, d20070625), -1);
        this.assertEquals(holidays.workingDayBetween(d20070730, d20070625), -25);
        this.assertEquals(holidays.workingDayBetween(d20070625, d20070730), 25);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

}
