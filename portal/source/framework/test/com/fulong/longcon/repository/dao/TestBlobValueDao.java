package com.fulong.longcon.repository.dao;

import com.fulong.common.dao.DaoTestCase;
import java.sql.SQLException;
import java.io.InputStream;
import java.io.StringBufferInputStream;


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
 * @author lishaobo@fulong.com.cn
 * @version 1.0
 */
public class TestBlobValueDao extends DaoTestCase {
    private BlobValueDao blobValueDao = null;

    protected void setUp() throws Exception {
        super.setUp();
        blobValueDao = (BlobValueDao)this.factory.getDao(BlobValueDao.class);
    }

    protected void tearDown() throws Exception {
        blobValueDao = null;
        super.tearDown();
    }

    public void testDelete() throws SQLException {
        String contentID = "";
        blobValueDao.delete(contentID);

    }

    public void testDelete1() throws SQLException {
        String contentID = "";
        String property = "";
        blobValueDao.delete(contentID, property);

    }

    public void testInsert() throws SQLException {
        String contentID = "contentID";
        String property = "property";
        int index = 0;
        String s = "value";
        InputStream values = new StringBufferInputStream(s);

        blobValueDao.insert(contentID, property, index, values);
        InputStream[] actualReturn = blobValueDao.load(contentID, property);
         assertEquals("return value", values, actualReturn[0]);
          blobValueDao.delete(contentID, property);

    }

    public void testLoad() throws SQLException {
        String contentID = "contentID";
        String property = "property";
        InputStream[] expectedReturn = null;
        InputStream[] actualReturn = blobValueDao.load(contentID, property);
        assertEquals("return value", expectedReturn, actualReturn);

    }


}
