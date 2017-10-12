/**
 * 
 */
package com.fulong.longcon.repository.dao.mysql;

import com.fulong.longcon.repository.dao.StringValueDao;

import junit.framework.Assert;


/**
 * @author LJY
 * 
 */
public class MysqlStringValueDaoTest extends MysqlDaoTestCase {
	private StringValueDao dao = null;
	private String contentID = "test.string";
	private String property = "test_property";
	private int index = 0;
	private String value = "测试";

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
//		try {			
//			dao.delete(contentID, property);
//			String[] rs=dao.load(contentID, property);
//			Assert.assertEquals("passed", rs.length, 0);
//			
//		} catch (java.sql.SQLException e) {
//			factory.rollback();
//		}		
		super.tearDown();
	}


	public final void testLoad() {
		
	}


}
