package com.fulong.longcon.repository.dao.mysql;

import junit.framework.Assert;
import java.sql.*;
import com.fulong.longcon.repository.dao.DoubleValueDao;

/**
 * 先建F_test_property和F_test_property2表
 * @author songbo
 * @author LJY
 * 
 */

public class MysqlDoubleValueDaoTest extends MysqlDaoTestCase {
	private DoubleValueDao dao = null;
	private String contentID = "test.double";
	private String property = "test_property";
	private String property2 = "test_property2";
	private int index = 0;
	private double value = 655360;
	private double value2 = -655360;

	protected void setUp() throws Exception {
		super.setUp();
		dao = (DoubleValueDao) this.factory.getDao(DoubleValueDao.class);
		try {
			dao.insert(contentID, property, index, value);
			dao.insert(contentID, property2, index, value2);
		} catch (SQLException e) {
			factory.rollback();
		}
	}

	protected void tearDown() throws Exception {
		try {
			dao.delete(contentID, property2);
			double[] rs = dao.load(contentID, property2);
			Assert.assertEquals("passed", rs.length, 0);
			dao.delete(contentID);
		} catch (SQLException e) {
			factory.rollback();
		}
		super.tearDown();
	}

	public void testLoad() {
		try {
			double[] rs = dao.load(contentID, property);
			Assert.assertEquals("passed", rs[0], value);
		} catch (SQLException e) {
			// do
		}

	}

}
