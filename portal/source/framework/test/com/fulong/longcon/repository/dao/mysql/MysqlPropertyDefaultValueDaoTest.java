package com.fulong.longcon.repository.dao.mysql;

import java.sql.SQLException;

import com.fulong.longcon.repository.dao.PropertyDefaultValueDao;

import junit.framework.Assert;

/**
 * 
 * @author LJY
 *
 */
public class MysqlPropertyDefaultValueDaoTest extends MysqlDaoTestCase {
	private PropertyDefaultValueDao dao = null;
	private String propertyID = "test.propertyID";
	private String value = "test.value";
	private String definitionID = "test.definitionID";

	protected void setUp() throws Exception {
		super.setUp();
		dao = (PropertyDefaultValueDao) this.factory
				.getDao(PropertyDefaultValueDao.class);
		try {
			dao.insert(propertyID, value, definitionID);
		} catch (SQLException e) {
			factory.rollback();
		}

	}

	protected void tearDown() throws Exception {
		try {
			dao.delete(propertyID, definitionID);
		} catch (SQLException e) {
			factory.rollback();
		}
		String[] rs = dao.findByPropertyDefinition(propertyID, definitionID);
		Assert.assertEquals("passed", rs.length, 0);
		super.tearDown();
	}

	public final void testFindByPropertyDefinition() throws SQLException {
		String[] rs = dao.findByPropertyDefinition(propertyID, definitionID);
		Assert.assertEquals("passed", rs[0], value);

	}

}
