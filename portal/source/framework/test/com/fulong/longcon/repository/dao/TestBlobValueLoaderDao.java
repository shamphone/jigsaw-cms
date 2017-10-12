package com.fulong.longcon.repository.dao;

import com.fulong.common.dao.DaoTestCase;
import java.sql.SQLException;

/**
*
* <p>Title: 龙驭核心引擎</p>
*
* <p>Description: 龙驭核心引擎</p>
*
* <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2007</p>
*
* <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
*
* @author liulei
* @version 1.0
*/

public class TestBlobValueLoaderDao extends DaoTestCase
{
	private BlobValueLoaderDao blobValueLoaderDao = null;

    protected void setUp() throws Exception 
    {
        super.setUp();
        blobValueLoaderDao = (BlobValueLoaderDao)this.factory.getDao(BlobValueLoaderDao.class);
    }

    protected void tearDown() throws Exception
    {
    	blobValueLoaderDao = null;
        super.tearDown();
    }
    
    public void testLoad() throws SQLException 
    {
    	String contentID = "";
    	String property = "";
        int index = "";
    }
}
