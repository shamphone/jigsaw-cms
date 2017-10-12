package com.fulong.longcon.repository.dao.sqlserver;

import junit.framework.Assert;

import com.fulong.longcon.repository.dao.StringValueDao;
/**
 * 先建S_test_property表
 * @author songbo
 *
 */
public class SqlserverStringValueDaoTest extends SqlserverDaoTestCase {

	private StringValueDao dao = null;
	private String contentID = "test.string";
	private String property = "test_property";
	private int index = 0;
	private String value = "test.value";

	protected void setUp() throws Exception {
		super.setUp();
		dao = (StringValueDao) this.factory.getDao(StringValueDao.class);
		try {
			dao.insert(contentID, property, index, value);
		} catch (java.sql.SQLException e) {
			factory.rollback();
		}
		String[] rs=dao.load(contentID, property);
		Assert.assertEquals("passed", rs[0], value);
	}

	protected void tearDown() throws Exception {
		try {			
			dao.delete(contentID, property);
			String[] rs=dao.load(contentID, property);
			Assert.assertEquals("passed", rs.length, 0);
		} catch (java.sql.SQLException e) {
			factory.rollback();
		}		
		super.tearDown();
	}

	public final void testLoad() {
		
	}
}
