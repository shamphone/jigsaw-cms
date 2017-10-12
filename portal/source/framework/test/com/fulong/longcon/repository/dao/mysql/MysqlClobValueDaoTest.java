package com.fulong.longcon.repository.dao.mysql;

import com.fulong.longcon.repository.dao.ClobValueDao;
import java.sql.*;

import junit.framework.Assert;

;
/**
 * 先建立T_test_property和T_test_property2表
 * @author songbo
 * @author LJY
 * 
 */
public class MysqlClobValueDaoTest extends MysqlDaoTestCase {
	private ClobValueDao dao = null;
	private String contentID = "test.clob";
	private String property = "test_property";
	private String property2 = "test_property2";
	private String value = "test.value";
	private String value2 = "test.value.2";
	private int index = 100;

	protected void setUp() throws Exception {
		super.setUp();
		dao = (ClobValueDao) factory.getDao(ClobValueDao.class);
		try {
			//插入2条数据
			dao.insert(contentID, property, index, value);
			dao.insert(contentID, property2, index, value2);
		} catch (SQLException e) {
			factory.rollback();
		}
		String[] rs = dao.load(contentID, property);
		//校验返回结果
		Assert.assertEquals("passed", rs[0], value);
	}

	protected void tearDown() throws Exception {
		try {
			//删除其中一条数据，后校验删除是否成功
			dao.delete(contentID, property);
			String[] rs = dao.load(contentID, property);
			Assert.assertEquals("passed", rs.length, 0);
			dao.delete(contentID);
		} catch (SQLException e) {
			factory.rollback();
		}
		super.tearDown();
	}

	public void testLoad() {

	}
}
