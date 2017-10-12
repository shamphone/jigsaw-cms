package com.fulong.longcon.repository.dao.mysql;

import junit.framework.Assert;
import com.fulong.longcon.repository.dao.BlobValueDao;
import java.io.*;
import java.sql.SQLException;

/**
 * @author songbo
 * 
 */
public class MysqlBlobValueDaoTest extends MysqlDaoTestCase {
	BlobValueDao blobDao = null;
	String testNodeID = "test.mysql.nodeid";
	String testProperty = "RESOURCE_CONTENT";
	String testValue = "test.mysql.value";
	int testVindex = 1;
	int testLength = testValue.length();

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		blobDao = (BlobValueDao) this.factory.getDao(BlobValueDao.class);

		FileInputStream in = new FileInputStream("c:\\fulong.jpg");
		int count = 0;		
		// FileInputStream tmp=in;
		try {
			//blobDao.insert(testNodeID, testProperty, testVindex);
			blobDao.insertValue(this.testNodeID, this.testProperty,
					this.testVindex, in);
		} catch (java.sql.SQLException e) {
			factory.rollback();
		}

		count = blobDao.count(this.testNodeID, this.testProperty);
		Assert.assertEquals("passsed", count, 1);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		//如测试MysqlBlobValueLoaderDao接口 则把这里的delete方法注销
		try {
			blobDao.delete(testNodeID, testProperty);
		} catch (java.sql.SQLException e) {
			factory.rollback();
		} finally {
			this.factory.close();
		}
		super.tearDown();
	}

	/**
	 * Test method for
	 * .
	 * @throws SQLException 
	 */
	public void testGetLengthes() throws SQLException {
     long[] rs=blobDao.getLengthes(testNodeID, testProperty);
     System.out.print(rs[0]);
	}

}
