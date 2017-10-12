package com.fulong.longcon.repository.dao.sqlserver;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import junit.framework.Assert;

import com.fulong.longcon.repository.dao.DateValueDao;

/**先建D_test_property和D_test_property2表
 * @author songbo
 */

public class SqlserverDateValueDaoTest extends SqlserverDaoTestCase {

	private DateValueDao dao = null;
	private String contentID = "test.date";
	private String property = "test_property";
	private String property2 = "test_property2";
	private int index = 99;
	private Date value = new Date();

	protected void setUp() throws Exception {
		super.setUp();
		dao = (DateValueDao) factory.getDao(DateValueDao.class);

	}

	protected void tearDown() throws Exception {	
		try {
			dao.delete(contentID, property2);
			Date[] rs = dao.load(contentID, property2);
			Assert.assertEquals("passed", rs.length, 0);
			dao.delete(contentID, property);
		} catch (SQLException e) {
			factory.rollback();
		}
		super.tearDown();

	}

	public void testLoad() {
		try {
			dao.insert(contentID, property, index, value);
			dao.insert(contentID, property2, index, value);
		} catch (SQLException e) {
			factory.rollback();
		}
		Date[] rs;
		try {
			rs = dao.load(contentID, property);			
			//在插入日期数据后，对日期做了时间转换，校验时需要同步
			System.out.println("target date is :" +new Timestamp(value.getTime()));
			System.out.println("actual date is :" +rs[0]);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
