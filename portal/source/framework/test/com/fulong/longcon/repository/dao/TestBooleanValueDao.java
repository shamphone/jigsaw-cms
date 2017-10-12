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
 * @author lishaobo@fulong.com.cn
 * @version 1.0
 */
public class TestBooleanValueDao extends DaoTestCase {
    private BooleanValueDao booleanValueDao = null;

    protected void setUp() throws Exception {
        super.setUp();
        booleanValueDao = (BooleanValueDao)this.factory.getDao(BooleanValueDao.class);
    }

    protected void tearDown() throws Exception {
        booleanValueDao = null;
        super.tearDown();
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
    public void testDelete() throws SQLException {
        String contentID = "testcase_contentID";
        String property = "ok";
        int index = 0;
        boolean value = true;
        booleanValueDao.insert(contentID, property, index, value);
        boolean[] actualReturn = booleanValueDao.load(contentID, property);
        assertEquals("return value", value, actualReturn[0]);
        booleanValueDao.delete(contentID,property);
        actualReturn = booleanValueDao.load(contentID, property);
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
    	String contentID = "testcase_contentID";
        String property = "ok";
        int index = 0;
        boolean value = false;
        booleanValueDao.insert(contentID, property, index, value);

        boolean[] actualReturn = booleanValueDao.load(contentID, property);
        assertEquals("return value", value, actualReturn[0]);

        booleanValueDao.delete(contentID, property);
        actualReturn = booleanValueDao.load(contentID, property);
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
    	String contentID = "testcase_contentID";
        String property = "ok";
        int index = 0;
        boolean value = false;
        booleanValueDao.insert(contentID, property, index, value);

        boolean[] actualReturn = booleanValueDao.load(contentID, property);
        assertEquals("return value", value, actualReturn[0]);

        booleanValueDao.delete(contentID, property);
        actualReturn = booleanValueDao.load(contentID, property);
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
    	String contentID = "testcase_contentID";
        String property = "ok";
        int index = 0;
        boolean value = false;
        //插入一个初始值
        booleanValueDao.insert(contentID, property, index, value);

        boolean[] actualReturn = booleanValueDao.load(contentID, property);
        assertEquals("return value", value, actualReturn[0]);

        booleanValueDao.delete(contentID, property);
        actualReturn = booleanValueDao.load(contentID, property);
        assertEquals("return value", 0, actualReturn.length);

    }

}
