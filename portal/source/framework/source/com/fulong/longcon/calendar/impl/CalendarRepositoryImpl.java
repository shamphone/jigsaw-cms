package com.fulong.longcon.calendar.impl;

import com.fulong.longcon.calendar.CalendarRepository;
import com.fulong.longcon.calendar.Holidays;
import com.fulong.longcon.calendar.HolidaysIterator;
import java.util.Map;
import java.util.HashMap;
import com.fulong.common.dao.JdbcDaoProvider;
import javax.sql.DataSource;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.fulong.common.dao.JdbcDaoFactory;
import com.fulong.common.dao.DaoFactory;
import com.fulong.common.dao.DatabaseException;
import java.sql.SQLException;
import com.fulong.longcon.calendar.dao.HolidaysDao;
import com.fulong.longcon.calendar.data.HolidaysData;
import com.fulong.common.dao.PropertiesDaoProvider;

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
public class CalendarRepositoryImpl implements CalendarRepository {
	
	private static final Log log = LogFactory.getLog(CalendarRepositoryImpl.class);
	
	private Holidays noHolidays;
	private Holidays standardHolidays;
	private Map<String, ConcreteHolidays> all;
	private String dao;
	private DataSource dataSource;
	private JdbcDaoProvider provider;

	public void init() {

		this.noHolidays = new NoHolidays();
		this.standardHolidays = new StandardHolidays(this);
		this.all = new HashMap<String, ConcreteHolidays>();

		PropertiesDaoProvider provider = new PropertiesDaoProvider();
		provider.addMappingFile("com.fulong.longcon.calendar." + dao);
		this.provider = provider;
		log.info(" Dao mapping file loaded!");

		// 从数据库中加载全部设置
		this.loadHolidays();

	}

	private void loadHolidays() {
		DaoFactory factory = this.newDaoFactory();
		try {
			factory.open();
			HolidaysDao dao = (HolidaysDao) factory.getDao(HolidaysDao.class);
			HolidaysData[] data = dao.findAll();
			for (int i = 0; i < data.length; i++) {
				this.all.put(data[i].getId(), new ConcreteHolidays(this,
						data[i]));
			}
		} catch (SQLException se) {
			throw new DatabaseException(se);
		} finally {
			factory.close();
		}

	}

	/**
	 * 
	 * @param parent
	 *            Holidays
	 * @return Holidays
	 */
	public Holidays createHolidays(String holidaysID, String displayName,
			Holidays parent) {
		DaoFactory factory = this.newDaoFactory();
		try {
			factory.open();
			HolidaysDao dao = (HolidaysDao) factory.getDao(HolidaysDao.class);
			HolidaysData data = new HolidaysData();
			data.setId(holidaysID);
			data.setDisplayName(displayName);
			data.setParentId(parent.getID());
			dao.insert(data);
			return new ConcreteHolidays(this, data);
		} catch (SQLException se) {
			factory.rollback();
			throw new DatabaseException(se);
		} finally {
			factory.close();
		}

	}

	/**
	 * 
	 * @param ID
	 *            String
	 * @return Holidays
	 */
	public Holidays getHolidays(String ID) {
		if (ID.equalsIgnoreCase(Holidays.NO_HOLIDAYS))
			return this.noHolidays;
		if (ID.equalsIgnoreCase(Holidays.STANDARD_HOLIDAYS))
			return this.standardHolidays;
		DaoFactory factory = this.newDaoFactory();
		try {
			factory.open();
			HolidaysDao dao = (HolidaysDao) factory.getDao(HolidaysDao.class);
			HolidaysData data = dao.findByID(ID);
			if (data == null)
				return null;
			return new ConcreteHolidays(this, data);
		} catch (SQLException se) {
			throw new DatabaseException(se);
		} finally {
			factory.close();
		}
	}

	/**
	 * 
	 * @return Holidays
	 */
	public Holidays getStandardHolidays() {
		return this.standardHolidays;
	}

	public Holidays getNoHolidays() {
		return this.noHolidays;
	}

	public String getDao() {
		return dao;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public JdbcDaoProvider getProvider() {
		return provider;
	}

	public void setDao(String dao) {
		this.dao = dao;

	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void setProvider(JdbcDaoProvider provider) {
		this.provider = provider;
	}

	public DaoFactory newDaoFactory() {
		if (this.provider == null) {
			this.setDao(dao);
		}
		return new JdbcDaoFactory(this.dataSource, this.provider);
	}

	public HolidaysIterator iterator() {
		return new HolidaysIteratorImpl(this.all.values());
	}

}
