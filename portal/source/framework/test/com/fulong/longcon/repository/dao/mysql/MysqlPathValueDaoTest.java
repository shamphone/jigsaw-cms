/**
 * 
 */
package com.fulong.longcon.repository.dao.mysql;

import java.sql.SQLException;

import com.fulong.longcon.repository.dao.PathValueDao;

import junit.framework.Assert;

/**
 * 先建p_test_property和p_test_property2表
 * @author LJY
 *
 */
public class MysqlPathValueDaoTest extends MysqlDaoTestCase {
	private PathValueDao dao=null;
	private String contentID="test.path";
	private String property="test_property";
	private int index =1;
	private String  value="test.path.value";
	

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		dao=(PathValueDao)this.factory.getDao(PathValueDao.class);
		try{
		dao.insert(contentID, property, index, value);
		dao.insert(contentID, property+"2", index, value);
		}catch(SQLException e){
			factory.rollback();
		}
		String[] rs=dao.load(contentID, property);
		Assert.assertEquals("passed", rs[0],value);
		
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		try{
			dao.delete(contentID, property);
			String[] rs=dao.load(contentID, property);
			Assert.assertEquals("passed", rs.length,0);
			dao.delete(contentID);
			}catch(SQLException e){
				factory.rollback();
			}
		super.tearDown();
	}

	
	public final void testLoad() {
		//
	}



}
