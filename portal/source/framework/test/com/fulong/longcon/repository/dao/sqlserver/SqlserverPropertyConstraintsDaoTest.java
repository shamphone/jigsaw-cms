package com.fulong.longcon.repository.dao.sqlserver;

import java.sql.SQLException;

import junit.framework.Assert;

import com.fulong.longcon.repository.dao.PropertyConstraintsDao;

/**
 * 
 * @author songbo
 *
 */
public class SqlserverPropertyConstraintsDaoTest extends SqlserverDaoTestCase {

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
