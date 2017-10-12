package com.fulong.longcon.repository.dao.mysql;

import java.sql.SQLException;

import junit.framework.Assert;
import com.fulong.longcon.repository.dao.ReferenceValueDao;

/**
 * 先建R_test_property表
 * @author songbo
 * @author LJY
 * 
 */
public class MysqlReferenceValueDaoTest extends MysqlDaoTestCase {
	private ReferenceValueDao dao = null;
	private String contentID1 = "test.ref1";
	private String contentID2 = "test.ref2";
	private String contentID3 = "test.ref3";
	private String property = "test_property";
	private int index = 1;
	private String value = "test.ref.value";

	protected void setUp() throws Exception {
		super.setUp();
		dao = (ReferenceValueDao) this.factory.getDao(ReferenceValueDao.class);
		try {
			dao.insert(contentID1, property, index, value);
			dao.insert(contentID2, property, index, value);
			dao.insert(contentID3, property, index, value);
		} catch (SQLException e) {
			factory.rollback();
		}
		//通过NODEID和PROPERTY取得VALUE值
		String[] rs=dao.load(contentID1, property);
		Assert.assertEquals("passed", rs[0],value);
		
	}

	protected void tearDown() throws Exception {
		try {
			dao.delete(contentID1, property);
			String[] rs=dao.load(contentID1, property);
			Assert.assertEquals("passed", rs.length,0);
			dao.delete(contentID2, property);
			dao.delete(contentID3, property);			
		} catch (SQLException e) {
			factory.rollback();
		}
		super.tearDown();
	}

	public final void testLoad() {

	}



}
