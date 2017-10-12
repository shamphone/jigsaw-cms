package com.fulong.longcon.repository.dao.mysql;


import junit.framework.Assert;
import com.fulong.longcon.repository.dao.BooleanValueDao;

/**
 * 先建立B_test_property和B_test_property_anoter的表
 * @author ljy
 * @author songbo
 */
public class MysqlBooleanValueDaoTest extends MysqlDaoTestCase {
	private BooleanValueDao dao = null;
	private String contentID = "test.contentID";
	private String property = "test_property";
	private String property2 = "test_property_anoter";
	private int index = 100;
	private boolean value = true;

	protected void setUp() throws Exception {
		super.setUp();
		dao = (BooleanValueDao) this.factory.getDao(BooleanValueDao.class);
		try {
			dao.insert(contentID, property, index, value);
			dao.insert(contentID, property2, index, false);
		} catch (java.sql.SQLException e) {
			factory.rollback();
		}
		boolean[] rs = dao.load(contentID, property);
		Assert.assertEquals("passed", rs[0], value);
	}

	protected void tearDown() throws Exception {
		try {
			dao.delete(contentID, property);
			boolean[] rs = dao.load(contentID, property2);
			Assert.assertEquals("passed", rs[0], false);
			dao.delete(contentID, property2);
			rs = dao.load(contentID, property2);
			Assert.assertEquals("passed", rs.length, 0);

		} catch (java.sql.SQLException e) {
			factory.rollback();
		} finally {
			factory.close();
		}
		super.tearDown();
	}

	public void testLoad() {

	}
}
