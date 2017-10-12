package com.fulong.longcon.repository.dao.sqlserver;

import java.sql.SQLException;

import junit.framework.Assert;

import com.fulong.longcon.repository.dao.PathValueDao;

/**先建P_test_property表
 * @author songbo
 */

public class SqlereverPathValueDaoTest extends SqlserverDaoTestCase {

	private PathValueDao dao=null;
	private String contentID="test.path";
	private String property="test_property";
	private int index =1;
	private String  value="test.path.value";
	
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
		//在tearDown()中测试delete()和load()
	}
}
