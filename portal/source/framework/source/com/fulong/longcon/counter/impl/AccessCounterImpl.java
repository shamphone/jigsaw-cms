package com.fulong.longcon.counter.impl;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import com.fulong.common.dao.DaoFactory;
import com.fulong.common.dao.DatabaseException;
import com.fulong.longcon.counter.dao.DiaryCountDao;
import com.fulong.longcon.counter.dao.MonthlyCountDao;
import com.fulong.longcon.counter.dao.TotallyCountDao;
import com.fulong.longcon.counter.dao.WeeklyCountDao;
import com.fulong.longcon.counter.dao.YearlyCountDao;
import com.fulong.longcon.counter.ext.AccessCounterExt;
import com.fulong.longcon.counter.ext.AccessCounterRepositoryExt;

/**
 *   会员系统计数器提供如下功能：
 * 1. 和标签AccessCounterTag相结合，实现页面计数的显示
 * 2. 将计数值汇报给内容管理系统，其内容管理系统的地址由reportURL来指定。
 * 3. 从持久性存储中获取和保存当前计数

 * <p>Title: 龙驭会员管理系统2.0</p>
 *
 * <p>Description: 龙驭会员管理系统2.0</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公司</p>
 *
 * @author lixf
 * @version 2.0
 */
public class AccessCounterImpl implements
        AccessCounterExt {
    public String name;
    public AccessCounterRepositoryExt repository;
    public long counter = -1;
    public long dairy = -1;
    public long weekly = -1;
    public long monthly = -1;
    public long yearly = -1;
    public Calendar date = null; //最近一次更新时间
    public Calendar week = null; //最近一次更新时间
    public Calendar month = null; //最近一次更新时间
    public Calendar year = null; //最近一次更新时间


    public AccessCounterImpl(String name, AccessCounterRepositoryExt repository) {
        this.name = name;
        this.repository = (AccessCounterRepositoryExt) repository;

    }

    /**
     * 从总计数器表中读取当前计数值的数据
     * @return long
     */
    public long getCount() {
    	if (counter < 0){
    		DaoFactory factory = this.repository.newJdbcDaoFactory();
    		try {
    			factory.open();
    			TotallyCountDao dao = (TotallyCountDao) factory.getDao(TotallyCountDao.class);
    			counter = dao.loadCount(this.name);
    		} catch (SQLException ex) {
    			throw new DatabaseException(ex);
    		} finally {
    			factory.close();
    		}
    	}
    	if(counter<0)
    		counter = 0;
    	return counter;
    }

  

    /**
     * 添加增加计数值,这个方法并不直接增加计数值,而是保存到访问计数器库中,由计数器库统一保存.
     */
    public void increase() {
        if (counter >= 0)
          this.counter ++;
        
        Calendar now = Calendar.getInstance();

        if(this.date!=null){
        	//新一天
        	if(!inOneDate(now, date))
        		this.dairy=0;
        	this.dairy++;
        	this.date=now;
        }
        if(this.week!=null){
        	//新一周
        	if(!inOneWeek(now, week))
        		this.weekly=0;
        	this.weekly++;
        	this.week=now;
        }
        if(this.month!=null){
        	//新一月
        	if(!inOneMonth(now, month))
        		this.monthly=0;
        	this.monthly++;
        	this.month=now;
        }
        if(this.year!=null){
        	//新一年
        	if(!inOneYear(now, year))
        		this.yearly=0;
        	this.yearly++;
        	this.year=now;
        }
    }
    
    private boolean inOneDate(Calendar date1,Calendar date2){
    	return date1.get(Calendar.DAY_OF_MONTH)== date2.get(Calendar.DAY_OF_MONTH)
    	&&date1.get(Calendar.MONTH)== date2.get(Calendar.MONTH)
    	&&date1.get(Calendar.YEAR)== date2.get(Calendar.YEAR);
    }
    
    private boolean inOneWeek(Calendar date1,Calendar date2){
    	return date1.get(Calendar.WEEK_OF_YEAR)== date2.get(Calendar.WEEK_OF_YEAR)
    	&&date1.get(Calendar.YEAR)== date2.get(Calendar.YEAR);
    }
    
    private boolean inOneMonth(Calendar date1,Calendar date2){
    	return date1.get(Calendar.MONTH)== date2.get(Calendar.MONTH)
    	&&date1.get(Calendar.YEAR)== date2.get(Calendar.YEAR);
    }
    
    private boolean inOneYear(Calendar date1,Calendar date2){
    	return date1.get(Calendar.YEAR)== date2.get(Calendar.YEAR);
    }

    /**
     *
     * @return long
     */
    public long getDayCount() {
    	Calendar now = Calendar.getInstance();
    	if(this.dairy<0||this.date==null||!inOneDate(now, date)){
    		DaoFactory factory = this.repository.newJdbcDaoFactory();
    		try {
    			factory.open();
    			DiaryCountDao dao = (DiaryCountDao) factory.getDao(DiaryCountDao.class);
    			dairy = dao.loadCount(name, new Date());
    			this.date = now;
    		} catch (SQLException ex) {
    			throw new DatabaseException(ex);
    		} finally {
    			factory.close();
    		}     
    	}
    	if(this.dairy<0)
    		this.dairy=0;
    	return dairy;
    }
    
    public long getWeeklyCount(){
    	Calendar now = Calendar.getInstance();
    	if(this.weekly<0||this.week==null||!inOneWeek(now, week)){
            DaoFactory factory = this.repository.newJdbcDaoFactory();
            try {
                factory.open();
                WeeklyCountDao dao = (WeeklyCountDao) factory.getDao(WeeklyCountDao.class);
               
                weekly = dao.loadCount(name, now.get(Calendar.WEEK_OF_YEAR)-1, now.get(Calendar.YEAR));
                this.week = now;
            } catch (SQLException ex) {
                throw new DatabaseException(ex);
            } finally {
                factory.close();
            }
    	}
    	if(this.weekly<0)
    		this.weekly=0;
        return weekly;
    }
    public long getMonthlyCount(){
    	Calendar now = Calendar.getInstance();
    	if(this.monthly<0||this.month==null||!inOneMonth(now, month)){
            DaoFactory factory = this.repository.newJdbcDaoFactory();
            try {
                factory.open();
                MonthlyCountDao dao = (MonthlyCountDao) factory.getDao(MonthlyCountDao.class);
                
                monthly = dao.loadCount(name, now.get(Calendar.MONTH)+1, now.get(Calendar.YEAR));
                this.month = now;
            } catch (SQLException ex) {
                throw new DatabaseException(ex);
            } finally {
                factory.close();
            }
    	}
    	if(this.monthly<0)
    		this.monthly=0;
        return monthly;
    }
    public long getYearlyCount(){
    	Calendar now = Calendar.getInstance();
    	if(this.yearly<0||this.year==null||!inOneYear(now, year)){
    		DaoFactory factory = this.repository.newJdbcDaoFactory();
    		try {
    			factory.open();
    			YearlyCountDao dao = (YearlyCountDao) factory.getDao(YearlyCountDao.class);
    			yearly = dao.loadCount(name, now.get(Calendar.YEAR));
    			this.year = now;
    		} catch (SQLException ex) {
    			throw new DatabaseException(ex);
    		} finally {
    			factory.close();
    		}
    	}
    	if(this.yearly<0)
    		this.yearly=0;
    	return yearly;
    }

}
