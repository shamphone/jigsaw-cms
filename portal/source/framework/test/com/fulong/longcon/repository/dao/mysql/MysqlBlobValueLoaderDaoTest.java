package com.fulong.longcon.repository.dao.mysql;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import com.fulong.longcon.repository.dao.BlobValueLoaderDao;
/**
 * @author songbo
 * 
 */
public class MysqlBlobValueLoaderDaoTest extends MysqlDaoTestCase {

	private BlobValueLoaderDao dao=null;
	private String contentID="test.mysql.nodeid";
	private String property="RESOURCE_CONTENT";
	private InputStream res=null;

	protected void setUp() throws Exception {
		super.setUp();
		dao=(BlobValueLoaderDao)this.factory.getDao(BlobValueLoaderDao.class);
	}

	protected void tearDown() throws Exception {
		dao.close();
		super.tearDown();
	}

	
	public final void testLoad() throws SQLException, IOException {
		res=dao.load(contentID, property, 0);
		res.read(new byte[0]);
		System.out .print(res.available());
		
	}

}
