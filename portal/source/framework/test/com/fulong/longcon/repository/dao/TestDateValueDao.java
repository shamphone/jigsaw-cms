package com.fulong.longcon.repository.dao;

import com.fulong.common.dao.DaoTestCase;
import java.sql.SQLException;
import java.util.Date;

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
public class TestDateValueDao extends DaoTestCase {
    private DateValueDao dateValueDao = null;

    protected void setUp() throws Exception {
        super.setUp();
        dateValueDao = (DateValueDao)this.factory.getDao(DateValueDao.class);
    }

    protected void tearDown() throws Exception {
        dateValueDao = null;
        super.tearDown();
    }

//    public void testTest() throws SQLException {
//        Date value = new Date();
//        String[]  result = dateValueDao.test(value, value);
//        this.assertEquals(result.length, 0);
//
//        dateValueDao.load("2445179747004", "createdTime");
//
//    }
    /**
     * author:liulei modified
     * problem: useless
     * date:09/07/13
     */
    
    /**
     * 测试策略：
     * 1.insert方法插入一个初始值
     * 2.load方法取出所插入的值
     * 3.assertEquals断言插入的值和取出的值相等
     * 4.delete方法删除该值，
     * 5.assertEquals断言插取出的值为空
     * @throws SQLException
     */
    public void testDelete() throws SQLException {

    	String contentID = "testcase_contentid";
        String property = "createdTime";
        int index = 0;
        Date value = new Date();
        //dateValueDao.test(value, value);
        /**
         * author:liulei modified
         * problem: useless
         * date:09/07/13
         */
        dateValueDao.insert(contentID, property, index, value);
        Date[] actualReturn = dateValueDao.load(contentID, property);
        assertEquals("return value", value.getDate(), actualReturn[0].getDate());
        dateValueDao.delete(contentID,property);
        actualReturn = dateValueDao.load(contentID, property);
        assertEquals("return value", 0, actualReturn.length);
    }

    /**
     * 测试策略：
     * 1.insert方法插入一个初始值
     * 2.load方法取出所插入的值
     * 3.assertEquals断言插入的值和取出的值相等
     * 4.delete方法删除该值，
     * 5.assertEquals断言插取出的值为空
     * @throws SQLException
     */
    public void testDelete1() throws SQLException {
        String contentID = "testcase_contentid";
        String property = "createdTime";
        int index = 0;
        Date value = new Date(System.currentTimeMillis());
        dateValueDao.insert(contentID, property, index, value);

        Date[] actualReturn = dateValueDao.load(contentID, property);

        assertEquals("return value", value.getDate(), actualReturn[0].getDate());
        dateValueDao.delete(contentID, property);
        actualReturn = dateValueDao.load(contentID, property);

        assertEquals("return value", 0, actualReturn.length);

    }

    /**
     * 测试策略：
     * 1.insert方法插入一个初始值
     * 2.load方法取出所插入的值
     * 3.assertEquals断言插入的值和取出的值相等
     * 4.delete方法删除该值，
     * 5.assertEquals断言插取出的值为空
     * @throws SQLException
     */
    public void testInsert() throws SQLException {
    	String contentID = "testcase_contentid";
        String property = "createdTime";
        int index = 0;        
        Date value = new Date(System.currentTimeMillis());
        dateValueDao.insert(contentID, property, index, value);

        Date[] actualReturn = dateValueDao.load(contentID, property);

        assertEquals("return value", value.getDate(), actualReturn[0].getDate());
        dateValueDao.delete(contentID, property);
        actualReturn = dateValueDao.load(contentID, property);

        assertEquals("return value", 0, actualReturn.length);

    }

    /**
     * 测试策略：
     * 1.insert方法插入一个初始值
     * 2.load方法取出所插入的值
     * 3.assertEquals断言插入的值和取出的值相等
     * 4.delete方法删除该值，
     * 5.assertEquals断言插取出的值为空
     * @throws SQLException
     */
    public void testLoad() throws SQLException {
    	String contentID = "testcase_contentid";
        String property = "createdTime";
        int index = 0;
        Date value = new Date(System.currentTimeMillis());
        dateValueDao.insert(contentID, property, index, value);

        Date[] actualReturn = dateValueDao.load(contentID, property);

        assertEquals("return value", value.getDate(), actualReturn[0].getDate());
        dateValueDao.delete(contentID, property);
        actualReturn = dateValueDao.load(contentID, property);

        assertEquals("return value", 0, actualReturn.length);

    }
}
