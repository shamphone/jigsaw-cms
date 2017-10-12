package com.fulong.longcon.repository.dao.mysql;

import java.sql.SQLException;

import com.fulong.longcon.repository.dao.PropertyConstraintsDao;

import junit.framework.Assert;

;
/**
 * 
 * @author LJY
 * 
 */

public class MysqlPropertyConstraintsDaoTest extends MysqlDaoTestCase {
	private PropertyConstraintsDao dao = null;
	private String propertyID = "test.propertyID";
	private String constraint = "test.constraint";
	private String nodeID = "test.nodeID";

	protected void setUp() throws Exception {
		super.setUp();
		dao = (PropertyConstraintsDao) this.factory
				.getDao(PropertyConstraintsDao.class);
		try {
			dao.insert(propertyID, constraint, nodeID);
		} catch (SQLException e) {
			factory.rollback();
		}
	}

	protected void tearDown() throws Exception {
		try{
			dao.delete(propertyID, nodeID);
		}catch(SQLException e){
			factory.rollback();
		}
		String[] rs=dao.findByPropDefAndNodeDefID(propertyID, nodeID);
		Assert.assertEquals("passed", rs.length,0);
		super.tearDown();
	}

	public final void testFindByPropDefAndNodeDefID() throws SQLException {
		String[] rs=dao.findByPropDefAndNodeDefID(propertyID, nodeID);
		Assert.assertEquals("passed", rs[0],this.constraint);
	}

}
